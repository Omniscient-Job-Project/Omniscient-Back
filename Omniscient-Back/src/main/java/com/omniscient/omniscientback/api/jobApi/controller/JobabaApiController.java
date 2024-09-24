package com.omniscient.omniscientback.api.jobApi.controller;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.model.JobabaEntity;
import com.omniscient.omniscientback.api.jobApi.service.JobabaService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobaba")
public class JobabaApiController {

    @Value("${api_jobaba.key}")
    private String apiKey;

    private final JobabaService jobabaService;
    private static final Logger logger = LoggerFactory.getLogger(JobabaApiController.class);

    @Autowired
    public JobabaApiController(JobabaService jobabaService) {
        this.jobabaService = jobabaService;
    }

    // 전체조회
    @GetMapping("/jobinfo")
    public ResponseEntity<?> jobabainfo(@RequestParam(value = "param", required = false) String param) throws IOException {
        if (param == null || param.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input: param is required.");
        }

        // API 호출을 위한 URL 빌더
        StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr/GGJOBABARECRUSTM?");
        urlBuilder.append("KEY=").append(URLEncoder.encode(apiKey, StandardCharsets.UTF_8)); // API 키 추가
        urlBuilder.append("&TYPE=").append(URLEncoder.encode("xml", StandardCharsets.UTF_8)); // 요청 타입 추가
        urlBuilder.append("&pIndex=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); // 페이지 인덱스 추가
        urlBuilder.append("&pSize=").append(URLEncoder.encode("100", StandardCharsets.UTF_8)); // 페이지 사이즈 추가

        // URL 객체 생성
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // HTTP 연결 생성
        conn.setRequestMethod("GET"); // GET 요청 방식 설정

        BufferedReader rd; // BufferedReader 객체 선언
        // 응답 코드에 따라 입력 스트림 설정
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); // 정상 응답
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream())); // 오류 응답
        }

        StringBuilder sb = new StringBuilder(); // 응답 데이터를 저장할 StringBuilder
        String line; // 한 줄씩 읽기 위한 변수
        // 입력 스트림에서 데이터를 읽어 StringBuilder에 추가
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close(); // BufferedReader 종료
        conn.disconnect(); // HTTP 연결 종료

        String rawData = sb.toString(); // 읽어온 데이터 문자열로 변환
        String jsonData = convertXmlToJson(rawData); // XML 데이터를 JSON으로 변환

        // JSON 데이터 파싱하여 DTO 리스트로 변환
        JSONObject jsonObject = new JSONObject(jsonData); // JSON 객체 생성
        JSONArray jsonArray = jsonObject.getJSONObject("GGJOBABARECRUSTM").getJSONArray("row"); // "row" 배열 추출

        // 각 job 정보를 DTO로 변환하여 저장
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i); // 배열에서 각 job JSON 객체 추출
            JobTotalDTO jobDTO = new JobTotalDTO(); // JobTotalDTO 객체 생성
            jobDTO.setCompanyName(jobJson.optString("ENTRPRS_NM", "")); // 회사명 설정
            jobDTO.setInfoTitle(jobJson.optString("PBANC_CONT", "")); // 정보 제목 설정
            jobDTO.setWageType(jobJson.optString("PBANC_FORM_DIV", "")); // 임금 형태 설정
            jobDTO.setSalary(jobJson.optString("SALARY_COND", "")); // 급여 조건 설정
            jobDTO.setLocation(jobJson.optString("WORK_REGION_CONT", "")); // 근무 지역 설정
            jobDTO.setEmploymentType(jobJson.optString("PBANC_FORM_DIV", "")); // 고용 형태 설정
            jobDTO.setCareerCondition(jobJson.optString("CAREER_DIV", "")); // 경력 조건 설정
            jobDTO.setWebInfoUrl(jobJson.optString("URL", "")); // 웹 정보 URL 설정
            jobDTO.setWorkRegionCdCont(jobJson.optInt("WORK_REGION_CD_CONT", 0)); // 근무 지역 코드 설정
            jobDTO.setRecrutFieldCdNm(jobJson.optInt("RECRUT_FIELD_CD_NM", 0)); // 채용 분야 코드 설정
            jobDTO.setRecrutFieldNm(jobJson.optString("RECRUT_FIELD_NM", "")); // 채용 분야 이름 설정
            jobDTO.setEmplmntPsncnt(jobJson.optInt("EMPLMNT_PSNCNT", 0)); // 고용 인원 수 설정

            boolean isSaved = jobabaService.saveJob(jobDTO); // DTO를 서비스에 저장하고 결과 확인

            if (isSaved) {
                logger.info("잡아바 데이터 저장 성공: {}", jobDTO.getCompanyName()); // 저장 성공 로그
            } else {
                logger.warn("잡아바 데이터 저장 실패: {}", jobDTO.getCompanyName()); // 저장 실패 로그
            }
        }
        return ResponseEntity.ok(jsonData); // JSON 데이터 반환
    }

    // 상세조회
    @GetMapping("/jobinfo/{Id}")
    public ResponseEntity<?> getJobInfoById(@PathVariable("Id") String jobId) throws IOException {
        // jobId가 null인지 확인하고 처리
        if (jobId == null || jobId.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid input: jobId is required.");
        }

        // 서비스에서 jobId에 해당하는 정보를 가져옴
        Optional<JobabaEntity> jobabaEntity = jobabaService.getJobById(jobId);

        if (jobabaEntity.isPresent()) {
            // 존재하는 경우, jobabaEntity를 JSON으로 변환하여 반환
            return ResponseEntity.ok(jobabaEntity.get());
        } else {
            // 존재하지 않는 경우, 404 에러 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found for id: " + jobId);
        }
    }


    // XML을 JSON으로 변환하는 메서드
    private String convertXmlToJson(String xmlData) {
        JSONObject jsonObject = XML.toJSONObject(xmlData); // XML 데이터를 JSON으로 변환
        return jsonObject.toString(); // 변환된 JSON 문자열 반환
    }
}

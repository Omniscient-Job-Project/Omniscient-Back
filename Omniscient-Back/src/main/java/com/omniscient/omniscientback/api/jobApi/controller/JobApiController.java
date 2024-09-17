package com.omniscient.omniscientback.api.jobApi.controller;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.service.JobService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/seoul")
public class JobApiController {

    @Value("${api.key}")
    private String apiKey;

    private JobService jobService;

    @Autowired
    public JobApiController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobinfo")
    public String jobinfo() throws IOException, ParseException {
        // API 호출을 위한 URL 빌더 생성
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        // 인증키를 URL에 추가
        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8")); // 인증키
        // 요청 파일 타입을 XML로 설정
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8")); /* 요청파일타입 */
        // 서비스명을 "GetJobInfo"로 설정
        urlBuilder.append("/" + URLEncoder.encode("GetJobInfo", "UTF-8")); /* 서비스명 */
        // 요청 시작 위치를 1로 설정
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /* 요청시작위치 */
        // 요청 종료 위치를 100으로 설정
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8")); /* 요청종료위치 */

        // 완성된 URL로 URL 객체 생성
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // HTTP 연결 생성
//        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(); // HTTPS 연결 생성
        conn.setRequestMethod("GET"); // GET 방식으로 요청 설정
        conn.setRequestProperty("Content-type", "application/xml"); // 요청 헤더에 Content-type 설정

        BufferedReader rd; // 응답을 읽기 위한 BufferedReader 선언
        // 응답 코드에 따라 입력 스트림을 설정
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            // 정상 응답인 경우 입력 스트림을 통해 읽기
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            // 오류 응답인 경우 오류 스트림을 통해 읽기
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder(); // 응답 데이터를 저장할 StringBuilder 선언
        String line; // 한 줄씩 읽기 위한 변수
        // 응답을 한 줄씩 읽어 StringBuilder에 추가
        while ((line = rd.readLine()) != null) {
            sb.append(line); // 응답 데이터 추가
        }
        rd.close(); // BufferedReader 닫기
        conn.disconnect(); // HTTP 연결 종료

        String rawData = sb.toString(); // 응답 데이터를 문자열로 변환
        String jsonData = convertXmlToJson(rawData); // XML 데이터를 JSON으로 변환

        // JSON 데이터에서 "GetJobInfo" 객체와 "row" 배열 가져오기
        JSONObject jsonObject = new JSONObject(jsonData); // JSON 객체 생성
        JSONArray jsonArray = jsonObject.getJSONObject("GetJobInfo").getJSONArray("row"); // "row" 배열 가져오기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 포맷 정의

        // JSON 배열을 순회하며 JobTotalDTO 객체 생성 및 저장
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i); // 현재 JSON 객체
            JobTotalDTO jobDTO = new JobTotalDTO(); // 새로운 DTO 객체 생성

            // DTO 객체의 필드에 JSON 데이터 설정
            jobDTO.setCompanyName(jobJson.getString("CMPNY_NM")); // 회사 이름 설정
            jobDTO.setInfoTitle(jobJson.getString("JO_SJ")); // 공고 제목 설정
            jobDTO.setWageType(jobJson.getString("HOPE_WAGE")); // 희망 임금 설정
            jobDTO.setSalary(jobJson.getString("HOPE_WAGE")); // 급여 설정
            jobDTO.setLocation(jobJson.getString("WORK_PARAR_BASS_ADRES_CN")); // 근무 주소 설정
            jobDTO.setEmploymentType(jobJson.getString("EMPLYM_STLE_CMMN_CODE_SE")); // 고용 형태 설정
            jobDTO.setCareerCondition(jobJson.getString("CAREER_CND_NM")); // 경력 조건 설정

            // 날짜 변환 처리 (주석 처리됨)
//        jobDTO.setPostedDate(LocalDate.parse(jobJson.getString("JO_REG_DT"), formatter)); // 게시일 변환
//        jobDTO.setClosingDate(LocalDate.parse(jobJson.getString("RCEPT_CLOS_NM"), formatter)); // 마감일 변환

            // 웹 정보 URL 및 모바일 정보 URL 설정
            jobDTO.setWebInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO")); // 웹 정보 URL 설정
            jobDTO.setMobileInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO")); // 모바일 정보 URL 설정

            jobService.saveJob(jobDTO); // DTO 객체를 서비스에 저장
        }

        return jsonData; // JSON 데이터 반환
    }

    @GetMapping("/jobinfo/{jobId}")
    public ResponseEntity<String> getJobInfoById(@PathVariable("jobId") String jobId) throws IOException {
        // API 호출을 위한 URL 빌더 생성
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        // 인증키를 URL에 추가
        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8")); // 인증키
        // 요청 파일 타입을 XML로 설정
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8")); // 요청파일타입
        // 서비스명을 "GetJobInfo"로 설정
        urlBuilder.append("/" + URLEncoder.encode("GetJobInfo", "UTF-8")); // 서비스명
        // 요청 시작 위치를 1로 설정
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); // 요청시작위치
        // 요청 종료 위치를 100으로 설정
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8")); // 요청종료위치

        // 완성된 URL로 URL 객체 생성
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // HTTP
//        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection(); // HTTPS
        conn.setRequestMethod("GET"); // GET 방식으로 요청 설정
        conn.setRequestProperty("Content-type", "application/xml"); // 요청 헤더에 Content-type 설정

        BufferedReader rd; // 응답을 읽기 위한 BufferedReader 선언
        // 응답 코드에 따라 입력 스트림을 설정
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            // 정상 응답인 경우 입력 스트림을 통해 읽기
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            // 오류 응답인 경우 오류 스트림을 통해 읽기
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder(); // 응답 데이터를 저장할 StringBuilder 선언
        String line; // 한 줄씩 읽기 위한 변수
        // 응답을 한 줄씩 읽어 StringBuilder에 추가
        while ((line = rd.readLine()) != null) {
            sb.append(line); // 응답 데이터 추가
        }
        rd.close(); // BufferedReader 닫기
        conn.disconnect(); // HTTP 연결 종료

        String rawData = sb.toString(); // 응답 데이터를 문자열로 변환
        String jsonData = convertXmlToJson(rawData); // XML 데이터를 JSON으로 변환

        // JSON 데이터에서 "GetJobInfo" 객체와 "row" 배열 가져오기
        JSONObject jsonObject = new JSONObject(jsonData); // JSON 객체 생성
        JSONArray jsonArray = jsonObject.getJSONObject("GetJobInfo").getJSONArray("row"); // "row" 배열 가져오기

        // JSON 배열을 순회하며 jobId에 맞는 데이터를 찾기
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i); // 현재 JSON 객체
            // 현재 JSON 객체의 "JO_REQST_NO" 값이 jobId와 일치하는지 확인
            if (jobJson.getString("JO_REQST_NO").equals(jobId)) {
                // 해당 jobId에 맞는 데이터 반환
                return ResponseEntity.ok(jobJson.toString()); // JSON 문자열 반환
            }
        }

        // 해당 jobId가 없을 경우의 처리
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 채용정보의 ID값을 찾을 수 없습니다."); // 404 상태 코드와 함께 오류 메시지 반환
    }

    // XML 데이터를 JSON 객체로 변환하는 메서드
    private String convertXmlToJson(String xmlData) {
        // XML 데이터를 JSON 객체로 변환
        JSONObject json = XML.toJSONObject(xmlData);
        // 4개의 공백으로 들여쓰기
        return json.toString(4); // 포맷팅된 JSON 문자열 반환
    }
}

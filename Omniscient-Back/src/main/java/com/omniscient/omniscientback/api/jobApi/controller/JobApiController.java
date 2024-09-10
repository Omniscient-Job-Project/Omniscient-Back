package com.omniscient.omniscientback.api.jobApi.controller;


import com.omniscient.omniscientback.api.jobApi.model.JobDTO;
import com.omniscient.omniscientback.api.jobApi.service.JobService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/v1/seoul")
public class JobApiController {

    // yml 파일에 key 값 등록되어 있음.
    @Value("${api.key}")
    private String apiKey;

    private JobService jobService;

    @Autowired
    public JobApiController(JobService jobService) {
        this.jobService = jobService;
    }

    // 서울 열린데이터 광장 api
    // https://data.seoul.go.kr/
    // 전체조회
    @GetMapping("/jobinfo")
    public String jobinfo() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");

        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8")); // 인증키
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8")); /* 요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("GetJobInfo", "UTF-8")); /* 서비스명 (대소문자 구분 필수입니다.) */
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /* 요청시작위치 (sample인증키 사용시 5이내 숫자) */
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8")); /* 요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨) */

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다. */
        BufferedReader rd;

        /* 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다. */
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String rawData = sb.toString();
        String jsonData = convertXmlToJson(rawData);

        // JSON 데이터 파싱하여 DTO 리스트로 변환
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONObject("GetJobInfo").getJSONArray("row");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            JobDTO jobDTO = new JobDTO();
            jobDTO.setJobCompanyName(jobJson.getString("CMPNY_NM"));
            jobDTO.setJobInfoTitle(jobJson.getString("JO_SJ"));
            jobDTO.setJobWageType(jobJson.getString("HOPE_WAGE"));
            jobDTO.setJobSalary(jobJson.getString("HOPE_WAGE"));
            jobDTO.setJobLocation(jobJson.getString("WORK_PARAR_BASS_ADRES_CN"));
            jobDTO.setJobEmploymentType(jobJson.getString("EMPLYM_STLE_CMMN_CODE_SE"));
            jobDTO.setJobCareerCondition(jobJson.getString("CAREER_CND_NM"));  // 경력 정보 추가
//            jobDTO.setJobPostedDate(LocalDate.parse(jobJson.getString("JO_REG_DT")));
//            jobDTO.setJobClosingDate(LocalDate.parse(jobJson.getString("RCEPT_CLOS_NM")));
            jobDTO.setJobWebInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO"));
            jobDTO.setJobMobileInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO"));

            jobService.saveJob(jobDTO);

        }

        return jsonData;

    }

    // 상세 조회
    @GetMapping("/jobinfo/{jobId}")
    public ResponseEntity<String> getJobInfoById(@PathVariable("jobId") String jobId) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("GetJobInfo", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String rawData = sb.toString();
        String jsonData = convertXmlToJson(rawData);

        // 특정 jobId에 맞는 데이터를 필터링하여 반환
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONObject("GetJobInfo").getJSONArray("row");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            if (jobJson.getString("JO_REQST_NO").equals(jobId)) {
                return ResponseEntity.ok(jobJson.toString());
            }
        }

        // 해당 jobId가 없을 때의 처리
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 채용정보의 ID값을 찾을 수 없습니다.");
    }

    // xml 파일을 json 형식으로 변환해주기
    private String convertXmlToJson(String xmlData) {
        // XML 데이터를 JSON 객체로 변환
        JSONObject json = XML.toJSONObject(xmlData);
        // JSON 데이터를 예쁘게 포맷팅 (4개의 공백으로 들여쓰기)
        return json.toString(4);
    }
}

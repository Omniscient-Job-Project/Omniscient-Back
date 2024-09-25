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


@RestController
@RequestMapping("/api/v1/seoul")
public class JobApiController {

    @Value("${api.key}")
    private String apiKey;

    private final JobService jobService;

    @Autowired
    public JobApiController(JobService jobService) {
        this.jobService = jobService;
    }

    // 전체 조회
    @GetMapping("/jobinfo")
    public ResponseEntity<String> jobinfo() throws IOException, ParseException {
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

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONObject("GetJobInfo").getJSONArray("row");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            JobTotalDTO jobDTO = new JobTotalDTO();

            jobDTO.setCompanyName(jobJson.getString("CMPNY_NM")); // 회사 이름 설정
            jobDTO.setInfoTitle(jobJson.getString("JO_SJ")); // 공고 제목 설정
            jobDTO.setWageType(jobJson.getString("HOPE_WAGE")); // 희망 임금 설정
            jobDTO.setSalary(jobJson.getString("HOPE_WAGE")); // 급여 설정
            jobDTO.setLocation(jobJson.getString("WORK_PARAR_BASS_ADRES_CN")); // 근무 주소 설정
            jobDTO.setEmploymentType(jobJson.getString("EMPLYM_STLE_CMMN_CODE_SE")); // 고용 형태 설정
            jobDTO.setCareerCondition(jobJson.getString("CAREER_CND_NM")); // 경력 조건 설정

            // 날짜 변환 처리
//        jobDTO.setPostedDate(LocalDate.parse(jobJson.getString("JO_REG_DT"), formatter)); // 게시일 변환
//        jobDTO.setClosingDate(LocalDate.parse(jobJson.getString("RCEPT_CLOS_NM"), formatter)); // 마감일 변환

            // 웹 정보 URL 및 모바일 정보 URL 설정
            jobDTO.setWebInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO")); // 웹 정보 URL 설정
            jobDTO.setMobileInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO")); // 모바일 정보 URL 설정

            jobService.saveJob(jobDTO);
        }
        // 성공 상태 코드 200과 함께 응답 반환
        return ResponseEntity.ok(jsonData);
    }

    // getJobInfoById() 메소드 수정
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

        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONObject("GetJobInfo").getJSONArray("row");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            if (jobJson.getString("JO_REQST_NO").equals(jobId)) {
                // 찾은 데이터와 함께 200 상태 코드 반환
                return ResponseEntity.ok(jobJson.toString());
            }
        }
        // jobId가 없을 경우 404 상태 코드와 함께 메시지 반환
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 채용정보의 ID값을 찾을 수 없습니다.");
    }

    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4);
    }
}


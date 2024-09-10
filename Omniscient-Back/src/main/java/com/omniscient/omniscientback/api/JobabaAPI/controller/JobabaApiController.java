package com.omniscient.omniscientback.api.JobabaAPI.controller;


import com.omniscient.omniscientback.api.JobabaAPI.model.JobabaDTO;
import com.omniscient.omniscientback.api.JobabaAPI.service.JobabaService;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/v1/jobaba")
public class JobabaApiController {

    // yml 파일에 key 값 등록되어 있음.
    @Value("${api_jobaba.key}")
    private String apiKey;

    private final JobabaService jobabaService;

    @Autowired
    public JobabaApiController(JobabaService jobabaService) {
        this.jobabaService = jobabaService;
    }

//    경기데이터드림 API
    @GetMapping("/jobinfo")
    public String jobabainfo() throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr:8088");

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
            JobabaDTO jobDTO = new JobabaDTO();
            jobDTO.setJobabaCompanyName(jobJson.getString("CMPNY_NM"));
            jobDTO.setJobabaInfoTitle(jobJson.getString("JO_SJ"));
            jobDTO.setJobabaWageType(jobJson.getString("HOPE_WAGE"));
            jobDTO.setJobabaSalary(jobJson.getString("HOPE_WAGE"));
            jobDTO.setJobabaLocation(jobJson.getString("WORK_PARAR_BASS_ADRES_CN"));
            jobDTO.setJobabaEmploymentType(jobJson.getString("EMPLYM_STLE_CMMN_CODE_SE"));
            jobDTO.setJobabaCareerCondition(jobJson.getString("CAREER_CND_NM"));  // 경력 정보 추가
//            jobDTO.setJobabaPostedDate(LocalDate.parse(jobJson.getString("JO_REG_DT")));
//            jobDTO.setJobabaClosingDate(LocalDate.parse(jobJson.getString("RCEPT_CLOS_NM")));
            jobDTO.setJobabaWebInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO"));
            jobDTO.setJobabaMobileInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO"));

            jobabaService.saveJob(jobDTO);

        }

        return jsonData;

    }

    private String convertXmlToJson(String xmlData) {
        // XML 데이터를 JSON 객체로 변환
        JSONObject json = XML.toJSONObject(xmlData);
        // JSON 데이터를 예쁘게 포맷팅 (4개의 공백으로 들여쓰기)
        return json.toString(4);
    }

}


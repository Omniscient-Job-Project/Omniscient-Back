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
@CrossOrigin(origins = "http://localhost:8083")
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
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode(apiKey, "UTF-8")); // 인증키
        urlBuilder.append("/" + URLEncoder.encode("xml", "UTF-8")); /* 요청파일타입 */
        urlBuilder.append("/" + URLEncoder.encode("GetJobInfo", "UTF-8")); /* 서비스명 */
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /* 요청시작위치 */
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8")); /* 요청종료위치 */

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            JobTotalDTO jobDTO = new JobTotalDTO();

            jobDTO.setCompanyName(jobJson.getString("CMPNY_NM"));
            jobDTO.setInfoTitle(jobJson.getString("JO_SJ"));
            jobDTO.setWageType(jobJson.getString("HOPE_WAGE"));
            jobDTO.setSalary(jobJson.getString("HOPE_WAGE"));
            jobDTO.setLocation(jobJson.getString("WORK_PARAR_BASS_ADRES_CN"));
            jobDTO.setEmploymentType(jobJson.getString("EMPLYM_STLE_CMMN_CODE_SE"));
            jobDTO.setCareerCondition(jobJson.getString("CAREER_CND_NM"));
//            jobDTO.setPostedDate(LocalDate.parse(jobJson.getString("JO_REG_DT"), formatter));
//            jobDTO.setClosingDate(LocalDate.parse(jobJson.getString("RCEPT_CLOS_NM"), formatter));
            jobDTO.setWebInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO"));
            jobDTO.setMobileInfoUrl("http://www.work.go.kr/empInfo/empInfoSrch/list/dtlEmpSrch.do?joReqstNo=" + jobJson.getString("JO_REQST_NO"));

            jobService.saveJob(jobDTO);
        }

        return jsonData;
    }

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
                return ResponseEntity.ok(jobJson.toString());
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 채용정보의 ID값을 찾을 수 없습니다.");
    }

    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4);
    }
}

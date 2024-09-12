package com.omniscient.omniscientback.api.jobApi.controller;

import com.omniscient.omniscientback.api.jobApi.model.JobTotalDTO;
import com.omniscient.omniscientback.api.jobApi.service.JobabaService;
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
@RequestMapping("/api/v1/jobaba")
@CrossOrigin(origins = "http://localhost:8083")
public class JobabaApiController {

    @Value("${api_jobaba.key}")
    private String apiKey;

    private final JobabaService jobabaService;

    @Autowired
    public JobabaApiController(JobabaService jobabaService) {
        this.jobabaService = jobabaService;
    }

    // 전체조회
    @GetMapping("/jobinfo")
    public String jobabainfo() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr/GGJOBABARECRUSTM?");
        urlBuilder.append("KEY=").append(URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("&TYPE=").append(URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&pIndex=").append(URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&pSize=").append(URLEncoder.encode("100", "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

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

        // JSON 데이터 파싱하여 DTO 리스트로 변환
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONObject("GGJOBABARECRUSTM").getJSONArray("row");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            JobTotalDTO jobDTO = new JobTotalDTO();
            jobDTO.setCompanyName(jobJson.optString("ENTRPRS_NM", ""));
            jobDTO.setInfoTitle(jobJson.optString("PBANC_CONT", ""));
            jobDTO.setWageType(jobJson.optString("PBANC_FORM_DIV", ""));
            jobDTO.setSalary(jobJson.optString("SALARY_COND", ""));
            jobDTO.setLocation(jobJson.optString("WORK_REGION_CONT", ""));
            jobDTO.setEmploymentType(jobJson.optString("PBANC_FORM_DIV", ""));
            jobDTO.setCareerCondition(jobJson.optString("CAREER_DIV", ""));

//            // 날짜 변환 처리
//            String postedDateStr = jobJson.optString("RCPT_BGNG_DE", "");
//            String closingDateStr = jobJson.optString("RCPT_END_DE", "");
//            if (!postedDateStr.isEmpty()) {
//                jobDTO.setPostedDate(LocalDate.parse(postedDateStr, formatter));
//            }
//            if (!closingDateStr.isEmpty()) {
//                jobDTO.setClosingDate(LocalDate.parse(closingDateStr, formatter));
//            }

            jobDTO.setWebInfoUrl(jobJson.optString("URL", ""));
            jobDTO.setWorkRegionCdCont(jobJson.optInt("WORK_REGION_CD_CONT", 0));
            jobDTO.setRecrutFieldCdNm(jobJson.optInt("RECRUT_FIELD_CD_NM", 0));
            jobDTO.setRecrutFieldNm(jobJson.optString("RECRUT_FIELD_NM", ""));
            jobDTO.setEmplmntPsncnt(jobJson.optInt("EMPLMNT_PSNCNT", 0));

            jobabaService.saveJob(jobDTO);
        }

        return jsonData;
    }

    // 상세조회
    @GetMapping("/jobinfo/{Id}")
    public ResponseEntity<String> getJobInfoById(@PathVariable("Id") String jobId) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://openapi.gg.go.kr/GGJOBABARECRUSTM?");
        urlBuilder.append("KEY=").append(URLEncoder.encode(apiKey, "UTF-8"));
        urlBuilder.append("&TYPE=").append(URLEncoder.encode("xml", "UTF-8"));
        urlBuilder.append("&pIndex=").append(URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&pSize=").append(URLEncoder.encode("100", "UTF-8"));

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
        JSONArray jsonArray = jsonObject.getJSONObject("GGJOBABARECRUSTM").getJSONArray("row");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            if (jobJson.getString("ENTRPRS_NM").equals(jobId)) {
                // 해당 jobId에 맞는 데이터 반환
                return ResponseEntity.ok(jobJson.toString(4)); // 예쁘게 출력 (4칸 들여쓰기)
            }
        }

        // 해당 jobId가 없을 때의 처리
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 채용정보의 ID값을 찾을 수 없습니다.");
    }

    private String convertXmlToJson(String xmlData) {
        // XML 데이터를 JSON 객체로 변환
        JSONObject json = XML.toJSONObject(xmlData);
        // JSON 데이터를 예쁘게 포맷팅 (4개의 공백으로 들여쓰기)
        return json.toString(4);
    }
}

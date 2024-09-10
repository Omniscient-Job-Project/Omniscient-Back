package com.omniscient.omniscientback.api.JobabaAPI.controller;

import com.omniscient.omniscientback.api.JobabaAPI.model.JobabaDTO;
import com.omniscient.omniscientback.api.JobabaAPI.service.JobabaService;
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

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jobJson = jsonArray.getJSONObject(i);
            JobabaDTO jobDTO = new JobabaDTO();
            jobDTO.setJobabaCompanyName(jobJson.optString("ENTRPRS_NM", ""));
            jobDTO.setJobabaInfoTitle(jobJson.optString("PBANC_CONT", ""));
            jobDTO.setJobabaWageType(jobJson.optString("PBANC_FORM_DIV", ""));
            jobDTO.setJobabaSalary(jobJson.optString("SALARY_COND", ""));
            jobDTO.setJobabaLocation(jobJson.optString("WORK_REGION_CONT", ""));
            jobDTO.setJobabaEmploymentType(jobJson.optString("PBANC_FORM_DIV", ""));
            jobDTO.setJobabaCareerCondition(jobJson.optString("CAREER_DIV", ""));

            // 날짜 변환 처리
//            String postedDateStr = jobJson.optString("RCPT_BGNG_DE", "");
//            String closingDateStr = jobJson.optString("RCPT_END_DE", "");
//            LocalDate postedDate = LocalDate.parse(postedDateStr, formatter);
//            LocalDate closingDate = LocalDate.parse(closingDateStr, formatter);
//
//            jobDTO.setJobabaPostedDate(postedDate);
//            jobDTO.setJobabaClosingDate(closingDate);

            jobDTO.setJobabaWebInfoUrl(jobJson.optString("URL", ""));
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
            if (jobJson.getString("JO_REQST_NO").equals(jobId)) {
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

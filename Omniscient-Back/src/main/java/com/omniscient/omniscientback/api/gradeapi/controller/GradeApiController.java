package com.omniscient.omniscientback.api.gradeapi.controller;


import com.omniscient.omniscientback.api.gradeapi.model.GradeDTO;
import com.omniscient.omniscientback.api.gradeapi.service.GradeApiService;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gradejob")
// 한국산업인력공단_등급별 자격취득 상위종목 현황
public class GradeApiController {

    @Value("${api_grade.key}")
    private String apiGradeKey;

    private final GradeApiService gradeApiService;

    @Autowired
    public GradeApiController(GradeApiService gradeApiService) {
        this.gradeApiService = gradeApiService;
    }

    @GetMapping
    public ResponseEntity<String> getGradeJobList(
            @RequestParam(value = "grdCd", defaultValue = "") String[] grdCds // 배열로 받기
    ) {
        // grdCd 값 로그 출력
        System.out.println("Received grdCd values: " + Arrays.toString(grdCds));

        String serviceUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryQualRankSVC/getList";
        String pageNo = "1";
        String numOfRows = "10";
        String baseYY = "2024";

        JSONArray allItemsArray = new JSONArray(); // 결과를 통합할 배열

        // grdCd 배열을 돌면서 API 호출
        for (String grdCd : grdCds) {
            if (grdCd.isEmpty()) {
                continue; // 빈 grdCd 값은 무시
            }

            // grdCd 값 로그 출력
            System.out.println("Fetching data for grdCd: " + grdCd);

            try {
                StringBuilder urlBuilder = new StringBuilder(serviceUrl);
                urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiGradeKey, "UTF-8"));
                urlBuilder.append("&pageNo=").append(pageNo);
                urlBuilder.append("&numOfRows=").append(numOfRows);
                urlBuilder.append("&baseYY=").append(baseYY);
                urlBuilder.append("&grdCd=").append(URLEncoder.encode(grdCd, "UTF-8"));

                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/xml");

                int responseCode = conn.getResponseCode();
                BufferedReader rd;
                if (responseCode >= 200 && responseCode < 300) {
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
                JSONObject bodyObject = jsonObject.getJSONObject("response").getJSONObject("body");
                JSONArray itemsArray = bodyObject.optJSONObject("items") != null
                        ? bodyObject.getJSONObject("items").optJSONArray("item")
                        : new JSONArray(); // 빈 배열로 대체

                // 모든 항목을 통합
                for (int i = 0; i < itemsArray.length(); i++) {
                    allItemsArray.put(itemsArray.getJSONObject(i));
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error occurred while fetching data for grdCd: " + grdCd);
            }
        }

        // JSON 결과 생성
        JSONObject responseJson = new JSONObject();
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("items", new JSONObject().put("item", allItemsArray));
        responseJson.put("response", new JSONObject().put("body", bodyJson));

        return ResponseEntity.ok(responseJson.toString(4)); // Pretty print JSON with 4-space indentation
    }




    @GetMapping("/{gradeId}")
    public ResponseEntity<String> getGradeById(@PathVariable String gradeId) throws IOException {
        String serviceUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryQualRankSVC/getList";
        String pageNo = "1";
        String numOfRows = "10";
        String baseYY = "2024";

        // URL 생성
        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiGradeKey, "UTF-8"));
        urlBuilder.append("&pageNo=").append(pageNo);
        urlBuilder.append("&numOfRows=").append(numOfRows);
        urlBuilder.append("&baseYY=").append(baseYY);
        urlBuilder.append("&grdCd=").append(URLEncoder.encode(gradeId, "UTF-8")); // gradeId를 grdCd에 사용

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();
        BufferedReader rd;
        if (responseCode >= 200 && responseCode <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        // 응답 데이터 처리
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String rawData = sb.toString();

        // XML을 JSON으로 변환
        String jsonData = convertXmlToJson(rawData);

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject bodyObject = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONArray itemsArray = bodyObject.getJSONObject("items").getJSONArray("item");

            // gradeId에 맞는 항목 찾기
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jobJson = itemsArray.getJSONObject(i);
                if (jobJson.getString("grdCd").equals(gradeId)) { // grdCd로 비교
                    return ResponseEntity.ok(jobJson.toString(4)); // 예쁘게 출력 (4칸 들여쓰기)
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("자격증 ID에 대한 데이터를 찾을 수 없습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 처리 중 오류 발생.");
        }
    }




    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4); // JSON 데이터를 보기 쉽게 4칸 들여쓰기 처리
    }


}

package com.omniscient.omniscientback.api.categoryapi.controller;

import com.omniscient.omniscientback.api.categoryapi.model.CategoryDTO;
import com.omniscient.omniscientback.api.categoryapi.service.CategoryService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("api/v1/categoryjob")
public class CategoryController {

    @Value("${api_grade.key}")
    private String apiCategoryKey;

    private final CategoryService categoryservice;

    @Autowired
    public CategoryController(CategoryService categoryservice) {
        this.categoryservice = categoryservice;
    }

    @GetMapping
    public String getCategoryList(@RequestParam(defaultValue = "1320") String jmCd) throws IOException {
        String serviceUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryInformationTradeNTQSVC/getList";

        // URL 빌더 설정 (API Key 및 기타 파라미터 포함)
        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        urlBuilder.append("?ServiceKey=").append(apiCategoryKey);
        urlBuilder.append("&jmCd=").append(jmCd);

        // URL 연결 설정
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        // 응답 코드 확인
        int responseCode = conn.getResponseCode();
        BufferedReader rd;
        if (responseCode >= 200 && responseCode <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            System.out.println("Error response received: " + responseCode);
        }

        // 응답 데이터 읽기
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        // XML 데이터를 JSON으로 변환
        String rawData = sb.toString();
        String jsonData = convertXmlToJson(rawData);  // XML -> JSON 변환 함수 호출

        try {
            // JSON 데이터로 변환
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject responseObject = jsonObject.optJSONObject("response");

            // response가 있고 body가 존재하는지 확인
            if (responseObject != null && responseObject.has("body")) {
                JSONObject bodyObject = responseObject.getJSONObject("body");

                // items가 있는지 확인
                if (bodyObject.has("items")) {
                    Object itemsObject = bodyObject.opt("items");

                    // items가 빈 문자열이거나 null인 경우 처리
                    if (itemsObject == null || itemsObject.toString().isEmpty()) {
                        return "No data available.";
                    }

                    // items가 존재할 때 item 처리
                    if (itemsObject instanceof JSONObject || itemsObject instanceof JSONArray) {
                        // item 데이터를 처리
                        JSONArray itemsArray;

                        // item이 배열인지, 단일 객체인지 확인
                        if (itemsObject instanceof JSONArray) {
                            itemsArray = (JSONArray) itemsObject;
                        } else {
                            itemsArray = new JSONArray().put(itemsObject); // 단일 객체를 배열로 변환
                        }

                        // item 배열을 처리하여 DTO로 저장
                        for (int i = 0; i < itemsArray.length(); i++) {
                            JSONObject jobJson = itemsArray.getJSONObject(i);

                            // DTO에 값을 할당
                            CategoryDTO categoryDTO = new CategoryDTO();
                            System.out.println("CategoryDTO: " + categoryDTO);

                            categoryDTO.setJmfldnm(jobJson.optString("jmfldnm", null));
                            categoryDTO.setInfogb(jobJson.optString("infogb", null));
                            categoryDTO.setContents(jobJson.optString("contents", null));
                            categoryDTO.setObligfldcd(jobJson.optString("obligfldcd", null));
                            categoryDTO.setObligfldnm(jobJson.optString("obligfldnm", null));
                            categoryDTO.setMdobligfldcd(jobJson.optString("mdobligfldcd", null));
                            categoryDTO.setMdobligfldnm(jobJson.optString("mdobligfldnm", null));

                            // 서비스 호출하여 DTO 저장

                            categoryservice.saveCategory(categoryDTO);
                            System.out.println(categoryDTO);
                        }
                    } else {
                        System.out.println("No items found in response body. Raw data: " + rawData);
                    }
                } else {
                    System.out.println("No items field found in response body. Raw data: " + rawData);
                }
            } else {
                System.out.println("Body not found in response. Raw data: " + rawData);
            }

        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Converted JSON Data: " + jsonData);  // JSON 데이터 전체 출력
        return jsonData;
    }







    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4); // 보기 쉽게 들여쓰기 처리
    }

}

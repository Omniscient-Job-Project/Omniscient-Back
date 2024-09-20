package com.omniscient.omniscientback.api.testapi.controller;

import com.omniscient.omniscientback.api.testapi.model.TestDTO;
import com.omniscient.omniscientback.api.testapi.service.TestApiService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v1/testjob")
// 한국산업인력공단_국가자격 시험일정 조회 서비스
public class TestApiController {

    @Value("${api_grade.key}")
    private String apiTestKey;

    private final TestApiService testApiService;

    @Autowired
    public TestApiController(TestApiService testApiService) {
        this.testApiService = testApiService;
    }

    @GetMapping
    public String getTestJobList() throws IOException {
        String serviceUrl = "http://apis.data.go.kr/B490007/qualExamSchd/getQualExamSchdList";
        String numOfRows = "5";
        String pageNo = "10";
        String dataFormat = "xml";
        String implYy = "2024";
        String qualgbCd = "T";

        // URL 생성
        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiTestKey, "UTF-8"));
        urlBuilder.append("&numOfRows=").append(numOfRows);
        urlBuilder.append("&pageNo=").append(pageNo);
        urlBuilder.append("&dataFormat=").append(dataFormat);
        urlBuilder.append("&implYy=").append(implYy);
        urlBuilder.append("&qualgbCd=").append(qualgbCd);

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

            // items 필드 처리
            JSONArray itemsArray;
            if (bodyObject.has("items")) {
                Object itemsObject = bodyObject.get("items");

                // items가 JSONObject인지 String인지 확인
                if (itemsObject instanceof JSONObject) {
                    JSONObject itemsJsonObject = (JSONObject) itemsObject;
                    if (itemsJsonObject.has("item")) {
                        if (itemsJsonObject.get("item") instanceof JSONArray) {
                            itemsArray = itemsJsonObject.getJSONArray("item");
                        } else if (itemsJsonObject.get("item") instanceof JSONObject) {
                            itemsArray = new JSONArray().put(itemsJsonObject.getJSONObject("item")); // 단일 객체를 배열로 처리
                        } else {
                            itemsArray = new JSONArray(); // 빈 배열로 처리
                        }
                    } else {
                        itemsArray = new JSONArray(); // 빈 배열로 처리
                    }
                } else if (itemsObject instanceof String) {
                    // items가 빈 문자열일 때 처리
                    String itemsString = (String) itemsObject;
                    if (itemsString.isEmpty()) {
                        itemsArray = new JSONArray(); // 빈 배열로 처리
                    } else {
                        throw new JSONException("Unexpected items format: " + itemsString);
                    }
                } else {
                    throw new JSONException("Unexpected items data type: " + itemsObject.getClass().getName());
                }
            } else {
                itemsArray = new JSONArray(); // 빈 배열로 처리
            }

            // itemsArray의 각 항목을 처리
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jobJson = itemsArray.getJSONObject(i);

                // TestDTO 객체에 데이터 매핑
                TestDTO testDTO = new TestDTO();
                testDTO.setNumOfRows(bodyObject.optString("numOfRows", null));
                testDTO.setPageNo(bodyObject.optString("pageNo", null));
                testDTO.setDataFormat(bodyObject.optString("dataFormat", null));
                testDTO.setImplYy(bodyObject.optString("implYy", null));
                testDTO.setResultCode(bodyObject.optString("resultCode", null));
                testDTO.setResultMsg(bodyObject.optString("resultMsg", null));
                testDTO.setImplSeq(jobJson.optString("implSeq", null));
                testDTO.setQualgbNm(jobJson.optString("qualgbNm", null)); // qualgbCd를 qualgbNm으로 수정
                testDTO.setDescription(jobJson.optString("description", null));

                testDTO.setDocRegStartDt(jobJson.optString("docRegStartDt", null));
                testDTO.setDocRegEndDt(jobJson.optString("docRegEndDt", null));

                testDTO.setDocExamStartDt(jobJson.optString("docExamStartDt", null));
                testDTO.setDocExamEndDt(jobJson.optString("docExamEndDt", null));

                testDTO.setDocPassDt(jobJson.optString("docPassDt", null));

                testDTO.setPracRegStartDt(jobJson.optString("pracRegStartDt", null));
                testDTO.setPracRegEndDt(jobJson.optString("pracRegEndDt", null));

                testDTO.setPracExamStartDt(jobJson.optString("pracExamStartDt", null));
                testDTO.setPracExamEndDt(jobJson.optString("pracExamEndDt", null));

                testDTO.setPracPassDt(jobJson.optString("pracPassDt", null));
                testDTO.setTotalCount(bodyObject.optString("totalCount", null));

                // 저장 서비스 호출
                testApiService.saveTestJob(testDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonData;
    }

    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4); // 보기 쉽게 들여쓰기 처리
    }
}

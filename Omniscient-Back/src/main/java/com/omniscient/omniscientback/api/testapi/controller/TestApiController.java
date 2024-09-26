package com.omniscient.omniscientback.api.testapi.controller;

import com.omniscient.omniscientback.api.testapi.model.TestDTO;
import com.omniscient.omniscientback.api.testapi.service.TestApiService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        String pageNo = "1";
        String dataFormat = "xml";
        String implYy = "2024";
        String qualgbCd = "T";
        System.out.println(apiTestKey);


        // URL 생성
        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        try {
            if (apiTestKey == null || apiTestKey.isEmpty()) {
                System.out.println(apiTestKey);
                throw new IllegalArgumentException("API 키가 설정되지 않았습니다.");
            } else {
                urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiTestKey, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new IOException("API 키 인코딩 실패", e);
        }
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
            JSONArray itemsArray = getItemsArray(bodyObject);

            // itemsArray의 각 항목을 처리
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jobJson = itemsArray.getJSONObject(i);
                TestDTO testDTO = mapToTestDTO(bodyObject, jobJson);
                validateDocumentDates(testDTO);
                testApiService.saveTestJob(testDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw new IOException("JSON 처리 오류", e);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new IOException("날짜 파싱 오류", e);
        }

        return jsonData;
    }

    // itemsArray를 추출하는 메소드
    private JSONArray getItemsArray(JSONObject bodyObject) {
        JSONArray itemsArray = new JSONArray();

        if (bodyObject.has("items")) {
            Object itemsObject = bodyObject.get("items");
            if (itemsObject instanceof JSONObject) {
                JSONObject itemsJsonObject = (JSONObject) itemsObject;
                if (itemsJsonObject.has("item")) {
                    if (itemsJsonObject.get("item") instanceof JSONArray) {
                        itemsArray = itemsJsonObject.getJSONArray("item");
                    } else if (itemsJsonObject.get("item") instanceof JSONObject) {
                        itemsArray.put(itemsJsonObject.getJSONObject("item")); // 단일 객체를 배열로 처리
                    }
                }
            }
        }

        return itemsArray;
    }

    // TestDTO 객체에 데이터 매핑
    private TestDTO mapToTestDTO(JSONObject bodyObject, JSONObject jobJson) {
        TestDTO testDTO = new TestDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        testDTO.setImplYy(bodyObject.optString("implYy", null));
        testDTO.setResultCode(bodyObject.optString("resultCode", null));
        testDTO.setResultMsg(bodyObject.optString("resultMsg", null));
        testDTO.setImplSeq(jobJson.optString("implSeq", null));
        testDTO.setQualgbNm(jobJson.optString("qualgbCd", null)); // qualgbCd를 qualgbNm으로 수정
        testDTO.setQualgbNm(jobJson.optString("qualgbNm", null));
        testDTO.setDescription(jobJson.optString("description", null));

        // 날짜 파싱 시 유효성 검사 추가
        testDTO.setDocRegStartDt(parseDate(jobJson.optString("docRegStartDt", null), formatter));
        testDTO.setDocRegEndDt(parseDate(jobJson.optString("docRegEndDt", null), formatter));
        testDTO.setDocExamStartDt(parseDate(jobJson.optString("docExamStartDt", null), formatter));
        testDTO.setDocExamEndDt(parseDate(jobJson.optString("docExamEndDt", null), formatter));
        testDTO.setDocPassDt(parseDate(jobJson.optString("docPassDt", null), formatter));
        testDTO.setPracRegStartDt(parseDate(jobJson.optString("pracRegStartDt", null), formatter));
        testDTO.setPracRegEndDt(parseDate(jobJson.optString("pracRegEndDt", null), formatter));
        testDTO.setPracExamStartDt(parseDate(jobJson.optString("pracExamStartDt", null), formatter));
        testDTO.setPracExamEndDt(parseDate(jobJson.optString("pracExamEndDt", null), formatter));
        testDTO.setPracPassDt(parseDate(jobJson.optString("pracPassDt", null), formatter));
        testDTO.setTotalCount(bodyObject.optString("totalCount", null));

        return testDTO;
    }

    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4); // 보기 쉽게 들여쓰기 처리
    }

    // 날짜 문자열을 LocalDate로 변환하는 유틸리티 메서드
    private LocalDate parseDate(String dateString, DateTimeFormatter formatter) {
        if (dateString != null && !dateString.trim().isEmpty()) {
            try {
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                System.err.println("날짜 파싱 오류: " + dateString);
                e.printStackTrace();
            }
        } else {
            System.err.println("날짜 문자열이 null 또는 비어 있습니다.");
        }
        return null; // null을 반환하여 유효하지 않은 날짜 처리
    }

    // 문서 등록 및 시험 날짜 유효성 검사 메서드
    private void validateDocumentDates(TestDTO testDTO) {
        // 문서 등록기간 유효성 검사
        if (testDTO.getDocRegStartDt() == null && testDTO.getDocRegEndDt() == null) {
            testDTO.setDocRegStartDt(LocalDate.parse("0001-01-01"));  // '없음' 대신 기본값을 설정
            testDTO.setDocRegEndDt(LocalDate.parse("0001-01-01"));
        } else if (testDTO.getDocRegStartDt() != null && testDTO.getDocRegEndDt() != null &&
                testDTO.getDocRegStartDt().isAfter(testDTO.getDocRegEndDt())) {
            throw new IllegalArgumentException("문서 등록 종료일이 시작일보다 이전입니다."); // 예외 발생
        }

        // 문서 시험기간 유효성 검사
        if (testDTO.getDocExamStartDt() == null && testDTO.getDocExamEndDt() == null) {
            testDTO.setDocExamStartDt(LocalDate.parse("0001-01-01"));  // '없음' 대신 기본값을 설정
            testDTO.setDocExamEndDt(LocalDate.parse("0001-01-01"));
        } else if (testDTO.getDocExamStartDt() != null && testDTO.getDocExamEndDt() != null &&
                testDTO.getDocExamStartDt().isAfter(testDTO.getDocExamEndDt())) {
            throw new IllegalArgumentException("문서 시험 종료일이 시작일보다 이전입니다."); // 예외 발생
        }
    }



}



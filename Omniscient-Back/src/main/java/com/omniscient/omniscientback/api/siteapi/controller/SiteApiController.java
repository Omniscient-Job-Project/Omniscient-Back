package com.omniscient.omniscientback.api.siteapi.controller;

import com.omniscient.omniscientback.api.siteapi.model.SiteDTO;
import com.omniscient.omniscientback.api.siteapi.service.SiteApiService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/v1/sitejob")
// 국가자격시험 시험장소 정보
public class SiteApiController {

    @Value("${api_site.key}")
    private String apiSiteKey;

    private final SiteApiService siteApiService;

    @Autowired
    public SiteApiController(SiteApiService siteApiService) {
        this.siteApiService = siteApiService;
    }

    @GetMapping
    public ResponseEntity<String> getSiteJobList() {
        String serviceUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryExamAreaSVC/getList";
        String brchCd = "01";
        String numOfRows = "5";
        String pageNo = "1";

        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        try {
            urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiSiteKey, "UTF-8"));
            urlBuilder.append("&brchCd=").append(brchCd);
            urlBuilder.append("&numOfRows=").append(numOfRows);
            urlBuilder.append("&pageNo=").append(pageNo);

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");

            // 응답 코드 확인
            int responseCode = conn.getResponseCode();
            BufferedReader rd = responseCode >= 200 && responseCode <= 300
                    ? new BufferedReader(new InputStreamReader(conn.getInputStream()))
                    : new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            // 응답 데이터 처리
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String rawData = sb.toString();
            String jsonData = convertXmlToJson(rawData);

            // JSON 데이터 파싱 및 유효성 검사
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject bodyObject = jsonObject.getJSONObject("response").getJSONObject("body");

                // items 필드 처리
                JSONArray itemsArray = new JSONArray();
                if (bodyObject.has("items")) {
                    if (bodyObject.get("items") instanceof JSONArray) {
                        itemsArray = bodyObject.getJSONArray("items");
                    } else if (bodyObject.get("items") instanceof JSONObject) {
                        JSONObject itemsObject = bodyObject.getJSONObject("items");
                        if (itemsObject.has("item") && itemsObject.get("item") instanceof JSONArray) {
                            itemsArray = itemsObject.getJSONArray("item");
                        }
                    }
                }

                // DTO 변환 및 서비스 호출
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jobJson = itemsArray.getJSONObject(i);

                    SiteDTO siteDTO = new SiteDTO();
                    siteDTO.setNumOfRows(jobJson.optString("numOfRows", null));
                    siteDTO.setPageNo(jobJson.optString("pageNo", null));
                    siteDTO.setBrchCd(jobJson.optString("brchCd", null));
                    siteDTO.setAddress(jobJson.optString("address", null));
                    siteDTO.setBrchNm(jobJson.optString("brchNm", null));
                    siteDTO.setExamAreaGbNm(jobJson.optString("examAreaGbNm", null));
                    siteDTO.setExamAreaNm(jobJson.optString("examAreaNm", null));
                    siteDTO.setPlceLoctGid(jobJson.optString("plceLoctGid", null));
                    siteDTO.setTelNo(jobJson.optString("telNo", null));
                    siteDTO.setResultCode(jobJson.optString("resultCode", null));
                    siteDTO.setResultMsg(jobJson.optString("resultMsg", null));
                    siteDTO.setTotalCount(jobJson.optString("totalCount", null));

                    // 예외 처리 및 데이터 저장
                    try {
                        siteApiService.saveSiteJob(siteDTO);
                    } catch (Exception e) {
                        // 로깅 및 예외 처리
                        System.err.println("Site 작업 저장 중 오류 발생: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                return new ResponseEntity<>("응답 데이터 처리 오류", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(jsonData, HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>("API에 연결하는 동안 오류 발생", HttpStatus.BAD_REQUEST);
        }
    }
// 확인
    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        try {
            JSONObject json = XML.toJSONObject(xmlData);
            return json.toString(4); // JSON 데이터를 보기 쉽게 4칸 들여쓰기 처리
        } catch (Exception e) {
            throw new RuntimeException("XML을 JSON으로 변환하는 동안 오류가 발생", e);
        }
    }
}

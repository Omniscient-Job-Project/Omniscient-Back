package com.omniscient.omniscientback.api.siteapi.controller;

import com.omniscient.omniscientback.api.siteapi.model.SiteDTO;
import com.omniscient.omniscientback.api.siteapi.service.SiteApiService;
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
@RequestMapping("/api/v1/sitejob")
// 한국산업인력공단_국가자격시험 시험장소 정보
public class SiteApiController {

    @Value("${api_grade.key}")
    private String apiSiteKey;

    private final SiteApiService siteApiService;

    @Autowired
    public SiteApiController(SiteApiService siteApiService) {
        this.siteApiService = siteApiService;
    }

    @GetMapping
    public String getSiteJobList() throws IOException {
        String serviceUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryExamAreaSVC/getList";
        String brchCd = "01";
        String numOfRows = "5";
        String pageNo = "1";

        StringBuilder urlBuilder = new StringBuilder(serviceUrl); /*URL*/
        urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiSiteKey,"UTF-8")); /*Service Key*/
        urlBuilder.append("&brchCd=").append(brchCd); /*00 : 본부 , 01 : 서울, 02: 서부, 03 : 부산, 04 : 대구 ,05 : 인천 , 06 : 광주 , 07 : 남부 , 08 : 충남 , 09 : 울산 , 10 : 경기 , 11: 강원 , 12 : 충북 , 13: 대전, 14: 전북 , 15 : 전남 , 16 : 경북 , 17 : 경남 , 18 : 제주 , 19 : 강원동부, 20 : 전남서부 , 21 : 부산남부 , 22 : 경북동부 , 23 : 경기북부 , 24 : 경기동부*/
        urlBuilder.append("&numOfRows=").append(numOfRows); /*한 페이지 결과 수*/
        urlBuilder.append("&pageNo=").append(pageNo);/*페이지 번호*/


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

        String jsonData = convertXmlToJson(rawData);

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject bodyObject = jsonObject.getJSONObject("response").getJSONObject("body");

            // items 필드 처리
            JSONArray itemsArray;
            if (bodyObject.has("items")) {
                if (bodyObject.get("items") instanceof JSONArray) {
                    itemsArray = bodyObject.getJSONArray("items");
                } else if (bodyObject.get("items") instanceof JSONObject) {
                    // 'items'가 JSONObject일 때, 'item' 배열을 가져옴
                    JSONObject itemsObject = bodyObject.getJSONObject("items");
                    if (itemsObject.has("item") && itemsObject.get("item") instanceof JSONArray) {
                        itemsArray = itemsObject.getJSONArray("item");
                    } else {
                        itemsArray = new JSONArray(); // 'item'이 없거나 빈 배열일 경우
                    }
                } else {
                    itemsArray = new JSONArray(); // 'items'가 배열이나 객체가 아닌 경우
                }
            } else {
                itemsArray = new JSONArray(); // 'items'가 없는 경우
            }


            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jobJson = itemsArray.getJSONObject(i);

                SiteDTO siteDTO = new SiteDTO();
                siteDTO.setNumOfRows(jobJson.optString("numOfRows",null));
                siteDTO.setPageNo(jobJson.optString("pageNo",null));
                siteDTO.setBrchCd(jobJson.optString("brchCd",null));
                siteDTO.setAddress(jobJson.optString("address",null));
                siteDTO.setBrchNm(jobJson.optString("brchNm",null));
                siteDTO.setExamAreaGbNm(jobJson.optString("examAreaGbNm",null));
                siteDTO.setExamAreaNm(jobJson.optString("examAreaNm",null));
                siteDTO.setPlceLoctGid(jobJson.optString("plceLoctGid",null));
                siteDTO.setTelNo(jobJson.optString("telNo",null));
                siteDTO.setResultCode(jobJson.optString("resultCode",null));
                siteDTO.setResultMsg(jobJson.optString("resultMsg",null));
                siteDTO.setTotalCount(jobJson.optString("totalCount",null));

                siteApiService.saveSiteJob(siteDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4); // JSON 데이터를 보기 쉽게 4칸 들여쓰기 처리
    }
}


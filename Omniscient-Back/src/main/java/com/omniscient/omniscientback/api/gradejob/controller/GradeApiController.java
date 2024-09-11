package com.omniscient.omniscientback.api.gradejob.controller;


import com.omniscient.omniscientback.api.gradejob.model.GradeDTO;
import com.omniscient.omniscientback.api.gradejob.service.GradeApiService;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/v1/gradejob")
// 한국산업인력공단_등급별 자격취득 상위종목 현황
public class GradeApiController {

    @Value("${apiCar.key}")
    private String apiCarKey;

    private final GradeApiService gradeApiService;

    @Autowired
    public GradeApiController(GradeApiService gradeApiService) {
        this.gradeApiService = gradeApiService;
    }

    @GetMapping
    public String getGradeJobList() throws IOException {
        String serviceUrl = "http://openapi.q-net.or.kr/api/service/rest/InquiryQualRankSVC/getList";
        String pageNo = "1";
        String numOfRows = "10";
        String baseYY = "2020";
        String grdCd = "31";

        // URL 생성
        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        urlBuilder.append("?ServiceKey=").append(URLEncoder.encode(apiCarKey, "UTF-8"));
        urlBuilder.append("&pageNo=").append(pageNo);
        urlBuilder.append("&numOfRows=").append(numOfRows);
        urlBuilder.append("&baseYY=").append(baseYY);
        urlBuilder.append("&grdCd=").append(grdCd);

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
        System.out.println("Raw XML Data: \n" + rawData);

        String jsonData = convertXmlToJson(rawData);
        System.out.println("Converted JSON Data: \n" + jsonData);

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject bodyObject = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONArray itemsArray = bodyObject.getJSONObject("items").getJSONArray("item");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jobJson = itemsArray.getJSONObject(i);
                System.out.println("Job JSON Object: \n" + jobJson.toString(4)); // 4 칸 들여쓰기로 보기 쉽게 출력

                // DTO로 변환 및 저장
                GradeDTO gradeDTO = new GradeDTO();
                gradeDTO.setGrdCd(jobJson.optString("grdNm", ""));
                gradeDTO.setBaseYY(jobJson.optString("baseYY", ""));
                gradeDTO.setPageNo(jobJson.optString("pageNo", ""));
                gradeDTO.setNumOfRows(jobJson.optString("numOfRows", ""));
                gradeDTO.setResultCode(jobJson.optString("resultCode", ""));
                gradeDTO.setResultMsg(jobJson.optString("resultMsg", ""));
                gradeDTO.setGrdNm(jobJson.optString("grdNm", ""));
                gradeDTO.setInstiNm(jobJson.optString("instiNm", ""));
                gradeDTO.setJmNm(jobJson.optString("jmNm", ""));
                gradeDTO.setPreyyAcquQualIncRate(jobJson.optString("preyyAcquQualIncRate", ""));
                gradeDTO.setPreyyQualAcquCnt(jobJson.optString("preyyQualAcquCnt", ""));
                gradeDTO.setQualAcquCnt(jobJson.optString("qualAcquCnt", ""));
                gradeDTO.setQualAcquRank(jobJson.optString("qualAcquRank", ""));
                gradeDTO.setStatisYy(jobJson.optString("statisYy", ""));
                gradeDTO.setSumYy(jobJson.optString("sumYy", ""));
                gradeDTO.setTotalCount(jobJson.optString("totalCount", ""));

                 gradeApiService.saveGradeJob(gradeDTO);
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

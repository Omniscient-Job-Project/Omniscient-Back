package com.omniscient.omniscientback.api.employmentapi.controller;

import com.omniscient.omniscientback.api.employmentapi.model.EmploymentDTO;
import com.omniscient.omniscientback.api.employmentapi.service.EmploymentService;
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
@RequestMapping("/api/v1/employment")
public class EmploymentApiController {

    @Value("${api_employment.key}")
    private String apiEmploymentKey;

    private final EmploymentService employmentService;

    @Autowired
    public EmploymentApiController(EmploymentService employmentService) {
        this.employmentService = employmentService;
    }

    @GetMapping
    public String getWomanList(Integer pIndex, Integer pSize) throws IOException {


        String serviceUrl = "https://openapi.gg.go.kr/GGEMPLTSP";


        // URL 빌더 생성
        StringBuilder urlBuilder = new StringBuilder(serviceUrl);
        urlBuilder.append("?KEY=").append(apiEmploymentKey);
        urlBuilder.append("&pIndex=").append(pIndex);
        urlBuilder.append("&pSize=").append(pSize);

        // URL 연결 설정
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
        String jsonData = convertXmlToJson(rawData);

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray rowsArray = jsonObject.getJSONObject("GGEMPLTSP").getJSONArray("row");



            for (int i = 0; i < rowsArray.length(); i++) {
                JSONObject rowJson = rowsArray.getJSONObject(i);
                EmploymentDTO employmentDTO = new EmploymentDTO();
                employmentDTO.setRefineLotnoAddr(rowJson.optString("REFINE_LOTNO_ADDR", null));
                employmentDTO.setRefineZipNo(rowJson.optString("REFINE_ZIPNO", null));
                employmentDTO.setContctNm(rowJson.optString("CONTCT_NM", null));
                employmentDTO.setInstNm(rowJson.optString("INST_NM", null));
                employmentDTO.setRefineRoadnmAddr(rowJson.optString("REFINE_ROADNM_ADDR", null));
                employmentDTO.setRefineWgs84Lat(rowJson.optString("REFINE_WGS84_LAT", null));
                employmentDTO.setDivNm(rowJson.optString("DIV_NM", null));
                employmentDTO.setRegionNm(rowJson.optString("REGION_NM", null));
                employmentDTO.setHmpgNm(rowJson.optString("HMPG_NM", null));
                employmentDTO.setRefineWgs84Logt(rowJson.optString("REFINE_WGS84_LOGT", null));

                employmentService.saveWoman(employmentDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonData; // 모든 데이터를 포함한 결과 반환
    }



    // XML 데이터를 JSON으로 변환하는 메소드
    private String convertXmlToJson(String xmlData) {
        JSONObject json = XML.toJSONObject(xmlData);
        return json.toString(4); // 보기 쉽게 들여쓰기 처리
    }


}

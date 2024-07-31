package com.sw.fd.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

    private static final String KAKAO_MAP_API_KEY = "bc442228556bbf4d02c4a71483482345";
    private static final String KAKAO_MAP_GEOCODE_URL = "https://dapi.kakao.com/v2/local/search/address.json?query=";

    public double[] getCoordinates(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String url = KAKAO_MAP_GEOCODE_URL + address;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_MAP_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // JSON 파싱하여 위도와 경도 추출
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode documents = root.path("documents");

            if (documents.isArray() && documents.size() > 0) {
                JsonNode location = documents.get(0);
                double latitude = location.path("y").asDouble();
                double longitude = location.path("x").asDouble();
                return new double[]{latitude, longitude};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new double[]{0.0, 0.0};
    }
}

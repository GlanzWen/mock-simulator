package com.example.mock.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/simulate")
public class RequestSimulatorController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/request")
    public Map<String, Object> simulateRequest(@RequestBody Map<String, Object> body) {
        String url = (String) body.get("url");
        String method = ((String) body.get("method")).toUpperCase();

        HttpHeaders headers = new HttpHeaders();
        Map<String, String> headerMap = (Map<String, String>) body.get("headers");
        if (headerMap != null) {
            headerMap.forEach(headers::add);
        }

        Object requestBody = body.get("body");
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Object> response =
                restTemplate.exchange(url, HttpMethod.valueOf(method), entity, Object.class);

        return Collections.unmodifiableMap(new HashMap<String, Object>() {{
            put("status", response.getStatusCodeValue());
            put("response", response.getBody());
        }});
    }
}

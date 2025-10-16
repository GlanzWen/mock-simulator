package com.example.mock.controller;

import com.example.mock.service.MockRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/mock")
public class MockController {

    @Autowired
    private MockRuleService mockRuleService;

    @PostMapping("/config")
    public String addMock(@RequestBody Map<String, Object> config) {
        String path = (String) config.get("path");
        Object response = config.get("response");
        mockRuleService.addMock(path, response);
        return "✅ Mock rule added for: " + path;
    }

    @GetMapping("/list")
    public Map<String, Object> listAll() {
        return mockRuleService.allMocks();
    }

    @RequestMapping("/**")
    public Object handleMock(HttpServletRequest request) {
        String uri = request.getRequestURI().replace("/mock", "");
        if (mockRuleService.hasMock(uri)) {
            return mockRuleService.getMock(uri);
        } else {
            return Collections.singletonMap("error", "no mock found for path: " + uri);
        }
    }
}

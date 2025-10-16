package com.glanz.mock.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MockRuleService {

    private final Map<String, Object> mockRules = new ConcurrentHashMap<>();

    public void addMock(String path, Object response) {
        mockRules.put(path, response);
    }

    public Object getMock(String path) {
        return mockRules.get(path);
    }

    public boolean hasMock(String path) {
        return mockRules.containsKey(path);
    }

    public Map<String, Object> allMocks() {
        return mockRules;
    }
}

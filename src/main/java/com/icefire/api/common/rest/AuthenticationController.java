package com.icefire.api.common.rest;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthenticationController {
    @GetMapping("/api/authenticate")
    public Map<String, String> authenticate() {
        Map<String, String> response =  new HashMap<>();
        response.put("message", "You are authenticated");
        return response;
    }
}
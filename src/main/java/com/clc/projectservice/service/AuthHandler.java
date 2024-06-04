package com.clc.projectservice.service;

import com.clc.projectservice.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthHandler {

    @Autowired
    private RestTemplate restTemplate;

    public List authHandler(String token) throws URISyntaxException {

        List response = new ArrayList();
        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Create RequestEntity with headers
        RequestEntity<Void> request = RequestEntity
                .get(new URI("http://shout-style-auth/auth/validateAndVerify"))
                .headers(headers)
                .build();

        ResponseEntity<AuthResponse> exchange = restTemplate.exchange(request, AuthResponse.class);

        if(exchange.getStatusCode() == HttpStatus.OK){
            AuthResponse body = exchange.getBody();
            if(body.isExpired() || body.isInvalid() || !body.isUserFound()){
                response.add(false);
                response.add(null);
                return response;
            }else{
                response.add(true);
                response.add(body.getUser());
                return response;
            }
        }else{
            response.add(false);
            response.add(null);
            return response;
        }

    }

}

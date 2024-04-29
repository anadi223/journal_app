package com.demo.journalapp.service;

import com.demo.journalapp.dtos.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuoteService {

    private static final String url = "https://dummyjson.com/quotes/random";

    @Autowired
    private RestTemplate restTemplate;

    public QuoteResponse getRandomQuote() {
        ResponseEntity<QuoteResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, QuoteResponse.class);
        QuoteResponse quoteResponse = responseEntity.getBody();
        return quoteResponse;
    }
}

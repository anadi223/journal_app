package com.demo.journalapp.service;

import com.demo.journalapp.dtos.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;




@Component
public class WeatherService {
    
    @Value("${weather.api.key}")
    private String apiKey;

    private String URL = "https://api.openweathermap.org/data/2.5/weather?q=CITY&appid=API_KEY&units=metric";


    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_"+ city, WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalURL = URL.replace("CITY", city).replace("API_KEY", apiKey);
            ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalURL, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = responseEntity.getBody();
            if(body != null){
                redisService.set("weather_of_"+city, body,3000);
            }
            return responseEntity.getBody();
        }
    }
}

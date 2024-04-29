package com.demo.journalapp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;


public class WeatherResponse {
    private Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public class Main {
        private Double temp;
        @JsonProperty("feels_like")
        private Double feelsLike;
        private Integer pressure;
        private Integer humidity;
        public Double getTemp() {
            return temp;
        }
        public void setTemp(Double temp) {
            this.temp = temp;
        }
        public Double getFeelsLike() {
            return feelsLike;
        }
        public void setFeelsLike(Double feelsLike) {
            this.feelsLike = feelsLike;
        }

        public Integer getPressure() {
            return pressure;
        }
        public void setPressure(Integer pressure) {
            this.pressure = pressure;
        }
        public Integer getHumidity() {
            return humidity;
        }
        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }
    }
}



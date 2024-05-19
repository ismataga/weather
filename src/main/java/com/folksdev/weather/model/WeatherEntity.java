package com.folksdev.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private String id;
    private String requestedCityName;
    private String cityName;
    private String country;
    private Integer temperature;
    private LocalDateTime updatedTime;
    private LocalDateTime responseLocalTime;


    public WeatherEntity(String id, String requestedCityName, String cityName, String country, Integer temperature, LocalDateTime updatedTime, LocalDateTime responseLocalTime) {
        this.id = id;
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updatedTime = updatedTime;
        this.responseLocalTime = responseLocalTime;
    }
    public WeatherEntity( String requestedCityName, String cityName, String country, Integer temperature, LocalDateTime updatedTime, LocalDateTime responseLocalTime) {
        this.id = "";
        this.requestedCityName = requestedCityName;
        this.cityName = cityName;
        this.country = country;
        this.temperature = temperature;
        this.updatedTime = updatedTime;
        this.responseLocalTime = responseLocalTime;
    }

    public WeatherEntity() {

    }

    public String getId() {
        return id;
    }

    public String getRequestedCityName() {
        return requestedCityName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public LocalDateTime getResponseLocalTime() {
        return responseLocalTime;
    }
}

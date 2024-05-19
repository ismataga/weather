package com.folksdev.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folksdev.weather.constans.Constants;
import com.folksdev.weather.dto.WeatherDto;
import com.folksdev.weather.dto.WeatherResponse;
import com.folksdev.weather.model.WeatherEntity;
import com.folksdev.weather.repository.WeatherRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = {"weathers"})
public class WeatherService {

    public static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    //api.weatherstack.com/current?access_key=6cb7539e1a6509ea4fc2b6d637a540fe&query=baku
    private static final String API_KEY = "6cb7539e1a6509ea4fc2b6d637a540fe&query=";
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherService(WeatherRepository weatherRepository, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable(key = "#city")
    public WeatherDto getWeatherByCityName(String city) {
        logger.info("getWeatherByCity " + city);
        Optional<WeatherEntity> weatherEntityOptional = weatherRepository.findFirstByRequestedCityNameOrderByUpdatedTimeDesc(city);

        return weatherEntityOptional.map(weather -> {
                    if (weather.getUpdatedTime().isBefore(LocalDateTime.now().minusMinutes(30))) {
                        return WeatherDto.convert(getWeatherFromWeatherStack(city));
                    }
                    return WeatherDto.convert(weather);
                })
                .orElseGet(() -> WeatherDto.convert(getWeatherFromWeatherStack(city)));
    }


    private WeatherEntity getWeatherFromWeatherStack(String city) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(getWeatherStackUrl(city), String.class);
        try {
            WeatherResponse weatherResponse = objectMapper.readValue(responseEntity.getBody(), WeatherResponse.class);
            return saveWeatherEntity(city, weatherResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedDelayString = "10000")
    public void clearCache() {
        logger.info("Clearing cache");
    }

    private WeatherEntity saveWeatherEntity(String city, WeatherResponse weatherResponse) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        WeatherEntity weatherEntity = new WeatherEntity(
                city,
                weatherResponse.location().name(),
                weatherResponse.location().country(),
                weatherResponse.current().temperature(),
                LocalDateTime.now(),
                LocalDateTime.parse(weatherResponse.location().localtime(), dateTimeFormatter)
        );
        return weatherRepository.save(weatherEntity);
    }

    private String getWeatherStackUrl(String city) {
        return Constants.API_URL + Constants.ACCESS_KEY_PARAM + Constants.API_KEY + Constants.QUERY_KEY_PARAM + city;
    }
}

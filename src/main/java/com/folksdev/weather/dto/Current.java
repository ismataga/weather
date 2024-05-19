package com.folksdev.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public record Current(
        String observation_time,
        Integer temperature,
        Integer weather_code,
        List<String> weather_icons,
        List<String> weather_descriptions,
        Integer wind_speed,
        Integer wind_degree,
        String wind_dir,
        Integer pressure,
        Integer precip,
        Integer humidity,
        Integer cloudcover,
        Integer feelslike,
        Integer uv_index,
        Integer visibility,
        String is_day
) {
}

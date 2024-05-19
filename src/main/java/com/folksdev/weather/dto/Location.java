package com.folksdev.weather.dto;

public record Location(
        String name,
        String country,
        String region,
        String lat,
        String lon,
        String timezone_id,
        String localtime,
        int localtime_epoch,
        String utc_offset
) {
}

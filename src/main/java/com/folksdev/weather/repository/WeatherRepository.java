package com.folksdev.weather.repository;


import com.folksdev.weather.model.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherRepository extends JpaRepository<WeatherEntity, String> {
    //Select * from entity where requestedCityName order by updatedTime desc limit 1
    Optional<WeatherEntity> findFirstByRequestedCityNameOrderByUpdatedTimeDesc(String city);
}

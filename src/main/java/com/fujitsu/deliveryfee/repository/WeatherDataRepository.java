package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    @Query("SELECT wd FROM WeatherData wd WHERE wd.stationName = :stationName " +
            "AND wd.timestamp = (SELECT MAX(wd2.timestamp) FROM WeatherData wd2 WHERE wd2.stationName = :stationName)")
    WeatherData findLatestByCity(@Param("stationName") String stationName);
}

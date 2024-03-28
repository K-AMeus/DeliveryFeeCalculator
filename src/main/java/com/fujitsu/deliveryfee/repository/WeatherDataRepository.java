package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.WeatherData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    @Query("SELECT wd FROM WeatherData wd WHERE wd.stationName = :stationName AND (:dateTime IS NULL OR wd.timestamp <= :dateTime) ORDER BY wd.timestamp DESC")
    List<WeatherData> findByCityAndTimestampBefore(@Param("stationName") String stationName, @Param("dateTime") LocalDateTime dateTime, Pageable pageable);
}

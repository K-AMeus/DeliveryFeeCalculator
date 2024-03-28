package com.fujitsu.deliveryfee.repository;

import com.fujitsu.deliveryfee.model.WeatherData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Weather data repository for accessing weather data stored in the database.
 * Provides methods to find weather data by station name and time, supporting the
 * delivery fee calculation process by providing relevant weather conditions.
 */
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Finds the latest weather data for a given station name before a specific datetime.
     *
     * @param stationName the name of the weather station
     * @param dateTime the datetime before which the weather data should be found
     * @param pageable pagination information to limit the results
     * @return a list of {@link WeatherData} entries, which is typically a single result
     */
    @Query("SELECT wd FROM WeatherData wd WHERE wd.stationName = :stationName AND (:dateTime IS NULL OR wd.timestamp <= :dateTime) ORDER BY wd.timestamp DESC")
    List<WeatherData> findByCityAndTimestampBefore(@Param("stationName") String stationName, @Param("dateTime") LocalDateTime dateTime, Pageable pageable);
}

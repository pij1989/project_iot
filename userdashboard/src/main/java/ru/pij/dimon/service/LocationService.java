package ru.pij.dimon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pij.dimon.dto.LocationDto;
import ru.pij.dimon.pojo.Location;
import ru.pij.dimon.repository.SensorRepository;

import javax.transaction.Transactional;
@Service
public class LocationService {
    private static Logger logger = LogManager.getLogger(LocationService.class);

    @Autowired
    SensorRepository sensorRepository;

    @Transactional
    public LocationDto getLocation(Long id){
        Location location = sensorRepository.findById(id).getDevice().getLocation();
        LocationDto locationDto = new LocationDto(location.getCity(),location.getLatitude(),location.getLongitude());
        logger.info("LocationDto: "+locationDto);
        return locationDto;
    }
}

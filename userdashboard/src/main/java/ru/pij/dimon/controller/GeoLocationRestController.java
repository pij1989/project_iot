package ru.pij.dimon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pij.dimon.dto.LocationDto;
import ru.pij.dimon.service.LocationService;


@RestController
public class GeoLocationRestController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/geolocation/{id}")
    public LocationDto getLocation(@PathVariable Long id) {
      return locationService.getLocation(id);

    }

}

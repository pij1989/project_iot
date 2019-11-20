package ru.pij.dimon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.pij.dimon.dto.DeviceDto;
import ru.pij.dimon.service.DeviceService;

@RestController
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<String> createNewDevice(@RequestBody DeviceDto deviceDto){
        try {
            logger.info("Recived: "+deviceDto.toString());
            deviceService.create(deviceDto);
            return new ResponseEntity<>("Device is created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>("Device is not created ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

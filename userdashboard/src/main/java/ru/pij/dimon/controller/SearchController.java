package ru.pij.dimon.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pij.dimon.service.DeviceService;
import ru.pij.dimon.service.SensorService;


@Controller
public class SearchController {

    private static Logger logger = LogManager.getLogger(SearchController.class);

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/searchsensor")
    public String showSensorSearchResult(@RequestParam(name = "search") String search, Model model){
        logger.info("Invoked method showSensorSearchResult() with request parametr: name = "+search);
        model.addAttribute("sensors",sensorService.searchSensors(search));
        return "sensorsCatalogPage";
    }

    @GetMapping("/searchdevice")
    public String showDeviceSearchResult(@RequestParam(name = "search") String search, Model model){
        logger.info("Invoked method showDeviceSearchResult() with request parametr: name = "+search);
        model.addAttribute("devices",deviceService.searchDevices(search));
        return "devicesCatalogPage";
    }

}

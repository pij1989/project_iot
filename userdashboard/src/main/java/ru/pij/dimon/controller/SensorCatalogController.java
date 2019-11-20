package ru.pij.dimon.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pij.dimon.dto.SensorDto;
import ru.pij.dimon.service.SensorService;
import ru.pij.dimon.util.JsonUtil;


@Controller
@RequestMapping("/sensorscatalog")
public class SensorCatalogController {

    private static Logger logger = LogManager.getLogger(SensorCatalogController.class);

    @Autowired
    private SensorService sensorService;

    @GetMapping("/sensors/{id}")
    public String showSensorsPage(@PathVariable Long id, Model model){
        model.addAttribute("sensors",sensorService.getSensorsOfDevice(id));
        return "sensorsCatalogPage";

    }

    @GetMapping("/sensors/sensor/{id}")
    public String showSensorPage(@PathVariable Long id, Model model){
        try {
            SensorDto sensor = sensorService.getSensor(id);
            model.addAttribute("jsonSensor",
                    JsonUtil.createJsonSensor(sensor));
            model.addAttribute("sensor",sensor);
            return "sensorPage";
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return "error";
        }
    }





}

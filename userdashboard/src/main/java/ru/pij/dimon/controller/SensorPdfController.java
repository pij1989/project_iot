package ru.pij.dimon.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pij.dimon.model.DateForm;
import ru.pij.dimon.service.SensorService;


@Controller
public class SensorPdfController {
    private static Logger logger = LogManager.getLogger(SensorPdfController.class);


    @Autowired
    private SensorService sensorService;
    @GetMapping("/pdf/sensor/{id}")
    public String downloadSensorPdf(@PathVariable Long id, Model model){
        model.addAttribute("sensor",sensorService.getSensor(id));
        return "reportSensorView";
    }


    @GetMapping("/pdf/filter/sensor/{id}")
    public String downloadFilteredSensorPdf(@PathVariable Long id,
                                    @SessionAttribute("dateForm")DateForm dateForm,
                                    Model model){

        logger.info("Date form from session: "+dateForm);
        model.addAttribute("sensor",sensorService.filterByDate(id,dateForm.getFromDate(),dateForm.getToDate()));
        return "reportSensorView";
    }
}

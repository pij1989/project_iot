package ru.pij.dimon.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.pij.dimon.dto.SensorDto;
import ru.pij.dimon.model.DateForm;
import ru.pij.dimon.service.SensorService;
import ru.pij.dimon.util.JsonUtil;
import ru.pij.dimon.validator.DateFormValidator;


@Controller
@SessionAttributes("dateForm")
public class FilterController {

    private static Logger logger = LogManager.getLogger(FilterController.class);

    @Autowired
    private SensorService sensorService;

    @Autowired
    private DateFormValidator  dateFormValidator;



    @GetMapping("/filter/sensor/{id}")
    public String showFilteredSensorPage(@PathVariable Long id,
                                         @ModelAttribute("dateForm") DateForm dateForm,
                                         BindingResult result,
                                         Model model){
        dateFormValidator.validate(dateForm,result);
        if(result.hasErrors()){
            model.addAttribute("errors",result.getAllErrors());
            return "invalidDatePage";
        }
        logger.info("fromDate: "+dateForm.getFromDate()+" "+" toDate: "+dateForm.getToDate());
        SensorDto sensor = sensorService.filterByDate(id,dateForm.getFromDate(),dateForm.getToDate());
        if(sensor == null){
            return "error";
        }
        logger.info("Filtered sensor by date: "+sensor);
        model.addAttribute("jsonSensor",
                JsonUtil.createJsonSensor(sensor));
        model.addAttribute("sensor",sensor);
        return "filterSensorPage";
    }

    @ModelAttribute("dateForm")
    public DateForm dateForm(){
        return new DateForm();
    }
}

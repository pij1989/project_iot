package ru.pij.dimon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pij.dimon.service.DeviceService;


@Controller
public class DeviceCatalogController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/devicescatalog")
    public String showDeviceCatalogPage(Model model){
        model.addAttribute("devices",deviceService.getDevices());
        return "devicesCatalogPage";
    }
}

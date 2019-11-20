package ru.pij.dimon.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.device.Device;
import ru.pij.dimon.dto.DeviceDto;
import ru.pij.dimon.model.Location;
import ru.pij.dimon.model.Sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceService {
    private final Device device;
    private final LocationService locationService;
    private final SensorService sensorService;
    private final List<SensorType>sensorTypes;
    private final IpService ipService;
    private Location location;
    private static  Logger log = LogManager.getLogger(DeviceService.class.getName());

    public DeviceService(Device device, LocationService locationService, SensorService sensorService, List<SensorType> sensorTypes, IpService ipService) {
        this.device = device;
        this.locationService = locationService;
        this.sensorService = sensorService;
        this.sensorTypes = sensorTypes;
        this.ipService = ipService;
    }

    public DeviceDto getDeviceDto(){
        DeviceDto deviceDto = null;
        try {
            if(location == null){
                location = locationService.getLocation(ipService.getIp());
            }
            List<Sensor>sensorList = sensorTypes.stream()
                    .map(sensorService::getSensor)
                    .collect(Collectors.toList());
            deviceDto = new DeviceDto(device.getType(),device.getSerialNumber(),location,sensorList);
        } catch (InterruptedException |IOException | GeoIp2Exception e) {
            log.error(e.getMessage());
        }
        log.info("Device: " + deviceDto);
        return deviceDto;
    }
}

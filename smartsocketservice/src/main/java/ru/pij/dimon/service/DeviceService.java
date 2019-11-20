package ru.pij.dimon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pij.dimon.dto.DeviceDto;
import ru.pij.dimon.helper.DeviceHelper;
import ru.pij.dimon.helper.SensorHelper;
import ru.pij.dimon.pojo.Device;
import ru.pij.dimon.pojo.Location;
import ru.pij.dimon.pojo.Sensor;
import ru.pij.dimon.pojo.SensorValue;
import ru.pij.dimon.repository.DeviceRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Date;


@Service
public class DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    DeviceHelper deviceHelper;

    @Autowired
    SensorHelper sensorHelper;

    @Autowired
    DeviceRepository deviceRepository;

    public void create(DeviceDto deviceDto){
        if(deviceDto == null){
            throw new IllegalArgumentException("Device DTO can not be null");
        }

        if (deviceRepository.findBySerialNumber(deviceDto.getSerialNumber()) == null){
            Device device = new Device();
            Location location = new Location();
            location.setIp(deviceDto.getLocation().getIp());
            location.setCity(deviceDto.getLocation().getCity());
            location.setLatitude(deviceDto.getLocation().getLatitude());
            location.setLongitude(deviceDto.getLocation().getLongitude());
            deviceDto.getSensorList().forEach(sensorDto -> {
                Sensor sensor = new Sensor();
                SensorValue sensorValue = new SensorValue();
                sensorValue.setValue(sensorDto.getValue());
                sensorValue.setDate(new Date());
                sensorHelper.addSensorValue(sensor,sensorValue);
                sensor.setType(sensorDto.getType());
                sensor.setUnits(sensorDto.getUnits());
                deviceHelper.addSensor(device,sensor);
            });
            device.setType(deviceDto.getType());
            device.setSerialNumber(deviceDto.getSerialNumber());
            device.setLocation(location);
            deviceRepository.save(device);
            logger.info("Saved device");
        } else {
            Device device = deviceRepository.findBySerialNumber(deviceDto.getSerialNumber());
            if(device == null){
                throw new EntityNotFoundException("Device with serial number "+deviceDto.getSerialNumber()+" not found");
            }
            device.getSensors().forEach(sensor -> deviceDto.getSensorList().forEach(sensorDto -> {
                if(sensor.getType().equals(sensorDto.getType())){
                    SensorValue sensorValue = new SensorValue();
                    sensorValue.setDate(new Date());
                    sensorValue.setValue(sensorDto.getValue());
                   sensorHelper.addSensorValue(sensor,sensorValue);
                }
            }));
            deviceRepository.save(device);
            logger.info("Saved data from sensors");
        }

    }
}

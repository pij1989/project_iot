package ru.pij.dimon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.device.Device;
import ru.pij.dimon.model.Sensor;


public class SensorService {

    private final Device device;
    private static Logger logger = LogManager.getLogger(SensorService.class);

    public SensorService(Device device) {
        this.device = device;
    }

    public Sensor getSensor(SensorType sensorType) {
        Sensor sensor = null;
        try {
            sensor = new Sensor(sensorType.getType(),sensorType.getUnits(),device.getSensorValue(sensorType));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return sensor;
    }
}

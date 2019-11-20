package ru.pij.dimon.helper;

import ru.pij.dimon.pojo.Device;
import ru.pij.dimon.pojo.Sensor;


import java.util.ArrayList;

public class DeviceHelper {
    public void addSensor(Device device, Sensor sensor){
        if(device == null || sensor == null){
            throw new IllegalArgumentException("Device and sensor can not be null");
        }
        if(device.getSensors() == null) device.setSensors(new ArrayList<>());
        device.getSensors().add(sensor);
        sensor.setDevice(device);
    }

    public void removeSensor(Device device, Sensor sensor){
        if(device == null || sensor == null){
            throw new IllegalArgumentException("Device and sensor can not be null");
        }
        if(device.getSensors() != null)device.getSensors().remove(sensor);
    }
}

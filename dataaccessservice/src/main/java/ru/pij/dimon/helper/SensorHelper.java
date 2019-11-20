package ru.pij.dimon.helper;

import ru.pij.dimon.pojo.Sensor;
import ru.pij.dimon.pojo.SensorValue;

import java.util.ArrayList;

public class SensorHelper {
    public void addSensorValue(Sensor sensor, SensorValue sensorValue){
        if(sensor == null || sensorValue == null){
            throw new IllegalArgumentException("Sensor and sensor value can not be null");
        }
        if(sensor.getValues() == null) sensor.setValues(new ArrayList<>());
        sensor.getValues().add(sensorValue);
        sensorValue.setSensor(sensor);
    }

    public void removeSensor(Sensor sensor, SensorValue sensorValue){
        if(sensor == null || sensorValue == null){
            throw new IllegalArgumentException("Sensor and sensor value can not be null");
        }
        if(sensor.getValues() != null)sensor.getValues().remove(sensorValue);
    }
}

package ru.pij.dimon.dto;

import ru.pij.dimon.model.Location;
import ru.pij.dimon.model.Sensor;

import java.util.List;

public class DeviceDto {

    private String type;

    private String serialNumber;

    private Location location;

    private List<Sensor> sensorList;

    public DeviceDto(String type, String serialNumber, Location location, List<Sensor> sensorList) {
        this.type = type;
        this.serialNumber = serialNumber;
        this.location = location;
        this.sensorList = sensorList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "type='" + type + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", location=" + location +
                ", sensorList=" + sensorList +
                '}';
    }
}

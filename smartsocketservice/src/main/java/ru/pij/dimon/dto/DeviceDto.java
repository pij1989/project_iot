package ru.pij.dimon.dto;

import java.util.List;

public class DeviceDto {

    private String type;

    private String serialNumber;

    private LocationDto locationDto;

    private List<SensorDto> sensorsDto;

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public LocationDto getLocation() {
        return locationDto;
    }

    public void setLocation(LocationDto location) {
        this.locationDto = location;
    }

    public List<SensorDto> getSensorList() {
        return sensorsDto;
    }

    public void setSensorList(List<SensorDto> sensorList) {
        this.sensorsDto = sensorList;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "type='" + type + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", locationDto=" + locationDto +
                ", sensorsDto=" + sensorsDto +
                '}';
    }
}

package ru.pij.dimon.service;

public enum DeviceType {
    TEMPERATURE_ILLUMINATION_HUMIDITY_DEVICE("Temperature, illumination, humidity device"),SMART_SOCKET("Smart socket");

    private String type;

    DeviceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

package ru.pij.dimon.service;



public  enum SensorType {

    TEMPERATURE_SENSOR("temperature sensor","C"),ILLUMINATION_SENSOR("illumination sensor","Lx"),HUMIDITY_SENSOR("humidity sensor","%"),
    POWER_SENSOR("power sensor","W"),VOLTAGE_SENSOR("voltage sensor","V"),AMPERAGE_SENSOR("amperage sensor","A");

    private String type;

    private String units;

    SensorType(String type, String units) {
        this.type = type;
        this.units = units;
    }

    public String getType() {
        return type;
    }

    public String getUnits() {
        return units;
    }
}

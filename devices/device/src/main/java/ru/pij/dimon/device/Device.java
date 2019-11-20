package ru.pij.dimon.device;

import ru.pij.dimon.service.SensorType;

public abstract class Device {

    private final String type;

    private final String serialNumber;

    protected boolean isStart;

    protected Device(String type, String serialNumber) {
        this.type = type;
        this.serialNumber = serialNumber;
    }

    abstract public void startDevice();

    abstract public void stopDevice();

    abstract public Integer getSensorValue(SensorType sensorType) throws InterruptedException;

    public boolean isStart(){
     return isStart;
    }

    public String getType() {
        return type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}

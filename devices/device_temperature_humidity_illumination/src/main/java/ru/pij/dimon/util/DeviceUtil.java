package ru.pij.dimon.util;

import ru.pij.dimon.device.DeviceHumidityIlluminationTemperature;

public class DeviceUtil {

    private static DeviceHumidityIlluminationTemperature deviceHumidityIlluminationTemperature;

    private static DeviceUtil deviceUtil;

    private DeviceUtil(){
        deviceHumidityIlluminationTemperature = new DeviceHumidityIlluminationTemperature.DeviceBuilder()
                .build();
    }

    public synchronized static DeviceUtil getInstance(){
        if(deviceUtil == null) deviceUtil = new DeviceUtil();
        return deviceUtil;
    }

    public DeviceHumidityIlluminationTemperature getDeviceHumidityIlluminationTemperature() {
        return deviceHumidityIlluminationTemperature;
    }
}

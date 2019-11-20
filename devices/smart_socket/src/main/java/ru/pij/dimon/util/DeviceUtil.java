package ru.pij.dimon.util;

import ru.pij.dimon.device.SmartSocket;

public class DeviceUtil {
    private static SmartSocket smartSocket;

    private static DeviceUtil deviceUtil;

    private DeviceUtil(){
        smartSocket = new SmartSocket.DeviceBuilder()
                .build();
    }

    public synchronized static DeviceUtil getInstance(){
        if(deviceUtil == null) deviceUtil = new DeviceUtil();
        return deviceUtil;
    }

    public SmartSocket getSmartSocket() {
        return smartSocket;
    }
}

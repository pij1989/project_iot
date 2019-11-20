package ru.pij.dimon;

import ru.pij.dimon.device.Device;
import ru.pij.dimon.httpclient.DeviceHttpClient;
import ru.pij.dimon.httpserver.DeviceHttpServer;
import ru.pij.dimon.service.*;
import ru.pij.dimon.util.DeviceUtil;

import java.io.IOException;
import java.util.List;

public class Main {
    private static final String URL_DEVICE = "http://localhost:8081/device";
    public static void main(String[] args) {
        try {
            Device device = DeviceUtil.getInstance().getDeviceHumidityIlluminationTemperature();
            SensorService sensorService = new SensorService(device);
            DeviceService deviceService =new DeviceService(device,new LocationService(),
                    sensorService, List.of(SensorType.TEMPERATURE_SENSOR,SensorType.ILLUMINATION_SENSOR,SensorType.HUMIDITY_SENSOR),new IpService());
            if(!device.isStart()){
                device.startDevice();
            }
            DeviceHttpServer.startServer(device,8500);
            DeviceHttpClient.startClient(deviceService,device,URL_DEVICE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ru.pij.dimon.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.device.Device;
import ru.pij.dimon.service.DeviceService;
import ru.pij.dimon.service.SensorService;
import ru.pij.dimon.util.JsonUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public final class DeviceHttpClient {

    private static final long TIME_OUT = 500;
    private static Logger log = LogManager.getLogger(DeviceHttpClient.class);

    private DeviceHttpClient() {
    }

    public static void startClient(DeviceService deviceService, Device device,String url) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request;
            HttpResponse<String> response;

            while (true){
                log.info("Device is started: "+device.isStart());
                if (device.isStart()) {
                    request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(JsonUtil.createJsonDevice(objectMapper,deviceService.getDeviceDto())))
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    log.info(response.statusCode());
                    log.info(response.body());
                    Thread.sleep(TIME_OUT);
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}

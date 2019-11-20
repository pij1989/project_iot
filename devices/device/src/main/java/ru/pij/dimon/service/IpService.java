package ru.pij.dimon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IpService {
    private static Logger log = LogManager.getLogger(IpService.class);
    private static final String URI_IP = "https://api.ipify.org?format=text";
    private HttpClient client;
    private HttpRequest requestIp;

    public IpService(){
        this.client = HttpClient.newHttpClient();
        this.requestIp = HttpRequest.newBuilder()
                .uri(URI.create(URI_IP))
                .GET()
                .build();
        log.info("Http client for getting IP adress build");
    }

    public String getIp() throws IOException, InterruptedException {
        HttpResponse<String> responseIp = client.send(requestIp, HttpResponse.BodyHandlers.ofString());
        String ip = responseIp.body();
        log.info("IP address: "+ip);
        return ip;
    }
}

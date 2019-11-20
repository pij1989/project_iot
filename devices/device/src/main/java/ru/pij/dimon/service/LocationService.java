package ru.pij.dimon.service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.model.Location;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

public class LocationService {
    private static final String GEOLOCATION_BASE="GeoLite2-City.mmdb";
    private static Logger log = LogManager.getLogger(LocationService.class);
    private DatabaseReader dbReader;

    public LocationService() throws IOException {
        InputStream database = getClass().getClassLoader().getResourceAsStream(GEOLOCATION_BASE);
        dbReader = new DatabaseReader.Builder(database).build();
        log.info("Create connect to geolocation base");
    }

    public Location getLocation(String ip) throws IOException, GeoIp2Exception {
        CityResponse response = dbReader.city(InetAddress.getByName(ip));
        String city = response.getCity().getName();
        Double latitude = response.getLocation().getLatitude();
        Double longitude = response.getLocation().getLongitude();
        log.info("Return location: ip "+ ip + " city: "+city+" latitude: "+latitude+" longitude: "+longitude);
        return  new Location(ip,city,latitude,longitude);
    }
}

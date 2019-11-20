package ru.pij.dimon.pojo;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ip;

    private String city;

    private Double latitude;

    private Double longitude;

    public Location() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(getIp(), location.getIp()) &&
                Objects.equals(getCity(), location.getCity()) &&
                Objects.equals(getLatitude(), location.getLatitude()) &&
                Objects.equals(getLongitude(), location.getLongitude());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIp(), getCity(), getLatitude(), getLongitude());
    }
}

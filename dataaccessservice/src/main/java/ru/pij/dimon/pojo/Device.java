package ru.pij.dimon.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk", sequenceName = "device_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pk")
    private Long id;

    private String type;

    @Column(name = "serial_number")
    private String serialNumber;

    @Embedded
    private Location location;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
    private List<Sensor> sensors;

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return Objects.equals(getId(), device.getId()) &&
                Objects.equals(getType(), device.getType()) &&
                Objects.equals(getSerialNumber(), device.getSerialNumber()) &&
                Objects.equals(getLocation(), device.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getSerialNumber(), getLocation());
    }
}
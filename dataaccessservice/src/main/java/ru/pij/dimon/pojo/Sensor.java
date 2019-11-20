package ru.pij.dimon.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk",sequenceName = "sensor_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pk")
    private Long id;

    private String type;

    private String units;

    @OneToMany(mappedBy = "sensor",cascade = CascadeType.ALL)
    private List<SensorValue> values;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_sensor_device"))
    private Device device;

    public Sensor() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public List<SensorValue> getValues() {
        return values;
    }

    public void setValues(List<SensorValue> values) {
        this.values = values;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(getId(), sensor.getId()) &&
                Objects.equals(getType(), sensor.getType()) &&
                Objects.equals(getUnits(), sensor.getUnits()) &&
                Objects.equals(getDevice(), sensor.getDevice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getUnits(), getDevice());
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", values=" + values +
                ", device=" + device +
                '}';
    }
}
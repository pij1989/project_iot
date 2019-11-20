package ru.pij.dimon.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sensor_value")
public class SensorValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk",sequenceName = "sensor_value_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk")
    private Long id;

    private Integer value;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_sensor_value_sensor"))
    private Sensor sensor;

    public SensorValue() {
    }

    public Long getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorValue)) return false;
        SensorValue that = (SensorValue) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getSensor(), that.getSensor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue(), getDate(), getSensor());
    }

    @Override
    public String toString() {
        return "SensorValue{" +
                "id=" + id +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}

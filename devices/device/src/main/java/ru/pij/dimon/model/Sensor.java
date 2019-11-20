package ru.pij.dimon.model;

public class Sensor {
    private String type;
    private String units;
    private Integer value;

    public Sensor(String type, String units, Integer value) {
        this.type = type;
        this.units = units;
        this.value = value;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", value=" + value +
                '}';
    }
}

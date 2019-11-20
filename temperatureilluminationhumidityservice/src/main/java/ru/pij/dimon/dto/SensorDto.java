package ru.pij.dimon.dto;

public class SensorDto {

    private String type;
    private String units;
    private Integer value;

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
        return "SensorDto{" +
                "type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", value=" + value +
                '}';
    }
}

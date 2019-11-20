package ru.pij.dimon.dto;

import java.util.List;

public class SensorDto {

    private Long id;
    private String type;
    private String units;
    List<SensorValueDto> sensorValueList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<SensorValueDto> getSensorValueList() {
        return sensorValueList;
    }

    public void setSensorValueList(List<SensorValueDto> sensorValueList) {
        this.sensorValueList = sensorValueList;
    }

    @Override
    public String toString() {
        return "SensorDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", sensorValueList=" + sensorValueList +
                '}';
    }
}

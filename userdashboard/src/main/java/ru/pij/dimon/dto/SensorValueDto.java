package ru.pij.dimon.dto;

public class SensorValueDto {
    private Long id;

    private Integer value;

    private String date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SensorValueDto{" +
                "id=" + id +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}

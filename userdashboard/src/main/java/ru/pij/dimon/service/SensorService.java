package ru.pij.dimon.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pij.dimon.dto.SensorDto;
import ru.pij.dimon.dto.SensorValueDto;
import ru.pij.dimon.pojo.Sensor;
import ru.pij.dimon.pojo.SensorValue;
import ru.pij.dimon.repository.SensorRepository;
import ru.pij.dimon.repository.SensorValueRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private static final String DATE_PATTERN="dd.MM.yyyy HH:mm:ss";
    private static final int MAX_RESULT_SET=10;
    private static SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

    private static Logger logger = LogManager.getLogger(SensorService.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorValueRepository sensorValueRepository;

    @Transactional
    public List<Sensor> getSensorsOfDevice(Long id){
        return deviceService.getDevice(id).getSensors().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Sensor> searchSensors(String search) {
        return sensorRepository.findSensorsByType(search);
    }

    @Transactional
    public SensorDto getSensor(Long id){
        Sensor sensor = sensorRepository.findById(id);
        if(sensor == null){
            throw new NoSuchElementException("Sensor with "+id+ "not found");
        }
        AtomicLong atomicLong = new AtomicLong();
        List<SensorValueDto>sensorValueDtos = sensor.getValues().stream()
//                .sorted((value,value1)->(int)(value1.getId()-value.getId()))
                .sorted(Comparator.comparing(SensorValue::getId).reversed())
                .limit(MAX_RESULT_SET)
                .map(sensorValue -> {
                    SensorValueDto dto = new SensorValueDto();
                    dto.setId(atomicLong.incrementAndGet());
                    dto.setValue(sensorValue.getValue());
                    dto.setDate(formatter.format(sensorValue.getDate()));
                    return dto;
                })
                .collect(Collectors.toList());
        SensorDto sensorDto = new SensorDto();
        sensorDto.setId(sensor.getId());
        sensorDto.setType(sensor.getType());
        sensorDto.setUnits(sensor.getUnits());
        sensorDto.setSensorValueList(sensorValueDtos);
        return sensorDto;
    }



    @Transactional
    public SensorDto filterByDate(Long id, String fromDate, String toDate)  {
        try {
            List<SensorValue> sensorValues = sensorValueRepository.findByDateInterval(id,new Timestamp(formatter.parse(fromDate).getTime()),
                    new Timestamp(formatter.parse(toDate).getTime()));
            if(sensorValues == null || sensorValues.size() == 0 ) return null;
            AtomicLong atomicLong = new AtomicLong();
            List<SensorValueDto>sensorValueDtos=sensorValues.stream()
                    .sorted(Comparator.comparing(SensorValue::getId).reversed())
                    .map(sensorValue -> {
                        SensorValueDto dto = new SensorValueDto();
                        dto.setId(atomicLong.incrementAndGet());
                        dto.setValue(sensorValue.getValue());
                        dto.setDate(formatter.format(sensorValue.getDate()));
                        return dto;
                    })
                    .collect(Collectors.toList());
            Sensor sensor = sensorValues.stream()
                    .findFirst()
                    .orElseThrow()
                    .getSensor();
            SensorDto sensorDto = new SensorDto();
            sensorDto.setId(sensor.getId());
            sensorDto.setType(sensor.getType());
            sensorDto.setUnits(sensor.getUnits());
            sensorDto.setSensorValueList(sensorValueDtos);
            return sensorDto;
        } catch (ParseException | NoSuchElementException e) {
            logger.warn(e.getMessage());
            return null;
        }
    }
}


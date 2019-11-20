package ru.pij.dimon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pij.dimon.pojo.Device;
import ru.pij.dimon.repository.DeviceRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DeviceService {
    private static final int MAX_RESULT_SET=10;

    @Autowired
    DeviceRepository deviceRepository;

    public List<Device> getDevices(){
        return deviceRepository.findDevices(MAX_RESULT_SET);
    }

    public Device getDevice(Long id){
        Device device = deviceRepository.findById(id);
        if(device == null){
            throw new EntityNotFoundException("Device with "+id+" not found");
        }
        return device;
    }

    public List<Device> searchDevices(String type){
        return deviceRepository.findDeviceByType(type);
    }

}

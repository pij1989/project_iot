package ru.pij.dimon.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pij.dimon.pojo.Device;

public interface DeviceRepository extends CrudRepository<Device,Long> {

    Device findBySerialNumber(String serialNumber);

}

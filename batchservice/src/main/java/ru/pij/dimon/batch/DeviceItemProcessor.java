package ru.pij.dimon.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import ru.pij.dimon.pojo.Device;


public class DeviceItemProcessor implements ItemProcessor<Device,Device> {

    private static final Logger log = LoggerFactory.getLogger(DeviceItemProcessor.class);


    @Override
    public Device process(Device device) throws Exception {
        log.info("Type: "+device.getType()+ " S/N: "+ device.getSerialNumber());
        device.getSensors().forEach(sensor -> log.info("Sensor type: "+sensor.getType()));
        return device;
    }
}

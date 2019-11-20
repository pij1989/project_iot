package ru.pij.dimon.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pij.dimon.pojo.SensorValue;
import ru.pij.dimon.service.NotificationService;


public class SensorValueItemProcessor implements ItemProcessor<SensorValue,SensorValue> {

    @Autowired
    private NotificationService notificationService;

    private static Logger logger = LoggerFactory.getLogger(SensorValueItemProcessor.class);
    private long date = 0L;

    @Override
    public SensorValue process(SensorValue sensorValue) throws Exception {
        logger.info(sensorValue.toString());
        notificationService.updateNotificationDate(sensorValue.getDate());
        return sensorValue;
    }
}

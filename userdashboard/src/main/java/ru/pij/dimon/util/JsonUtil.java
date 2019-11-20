package ru.pij.dimon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pij.dimon.dto.SensorDto;


public final class JsonUtil {

    private static Logger logger = LogManager.getLogger(JsonUtil.class);

    private JsonUtil(){ }

    public static String createJsonSensor(SensorDto sensor)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(sensor);
        } catch (JsonProcessingException e) {
            logger.warn(e.getMessage());
            return "error";
        }
    }
}

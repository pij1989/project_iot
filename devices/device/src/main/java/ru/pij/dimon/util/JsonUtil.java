package ru.pij.dimon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.pij.dimon.dto.DeviceDto;

public final class JsonUtil {

    private JsonUtil(){}

    public static String createJsonDevice(ObjectMapper objectMapper, DeviceDto device) throws JsonProcessingException {
        if(objectMapper == null) throw new IllegalArgumentException("Object mapper can not be null");
        return objectMapper.writeValueAsString(device);
    }
}

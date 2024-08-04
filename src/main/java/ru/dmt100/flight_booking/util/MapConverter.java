package ru.dmt100.flight_booking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.dmt100.flight_booking.exception.MapConversionException;

import java.io.IOException;
import java.util.Map;

@Converter
public class MapConverter implements AttributeConverter<Map<String, String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String convertWithIteration(Map<String, ?> map) {
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : map.keySet()) {
            mapAsString.append(key + "=" + map.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new MapConversionException("Failed to convert Map to String", e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
                try {
            return objectMapper.readValue(dbData, new TypeReference<Map<String, String>>(){});
        } catch (IOException e) {
            throw new MapConversionException("Failed to convert String to Map", e);
        }
    }
}

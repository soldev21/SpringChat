package main.handler.messagehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Sherif on 7/7/2018.
 */
@Component
public class JacksonMessageHandler implements AbstractMessageHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public <T> T convertToObject(String message, Class<T> c) throws IOException {
        return mapper.readValue(message,c);
    }

    @Override
    public <T> String stringify(T t) throws JsonProcessingException {
        return mapper.writeValueAsString(t);
    }
}

package main.handler.messagehandler;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Created by Sherif on 7/7/2018.
 */
public interface AbstractMessageHandler {
    <T> T convertToObject(String message,Class<T> c) throws Exception;
    <T> String stringify(T t) throws Exception;
}

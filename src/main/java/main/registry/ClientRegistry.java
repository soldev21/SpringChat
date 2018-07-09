package main.registry;

import java.util.Map;

/**
 * Created by Sherif on 7/7/2018.
 */
public interface ClientRegistry<T> {
    void addClient(String id,T t);
    T getClient(String id);
    void remove(String id);
    Map<String ,T> getAll();
    void setAll(Map<String,T> clients);
}

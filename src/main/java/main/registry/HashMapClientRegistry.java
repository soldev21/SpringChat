package main.registry;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sherif on 7/7/2018.
 */
public class HashMapClientRegistry<T> implements ClientRegistry<T> {

    private Map<String,T> registry;

    public HashMapClientRegistry(){
        registry = new HashMap<>();
    }

    @Override
    public void addClient(String id,T t) {
        registry.put(id,t);
    }

    @Override
    public T getClient(String id) {
        return registry.get(id);
    }

    @Override
    public void remove(String id) {
        registry.remove(id);
    }

    @Override
    public Map<String, T> getAll() {
        return registry;
    }

    @Override
    public void setAll(Map<String, T> clients) {
        this.registry = clients;
    }
}

package ar.edu.unlam.tallerweb1.modelo;

import java.util.*;

import org.springframework.cache.support.SimpleCacheManager;

public class Cache {

    private static SimpleCacheManager instance;
    private static Object monitor = new Object();
    private Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());

    private Cache() {
    }

    public void put(String cacheKey, Object value) {
        cache.put(cacheKey, value);
    }

    public Object get(String cacheKey) {
        return cache.get(cacheKey);
    }

    public void clear(String cacheKey) {
        cache.put(cacheKey, null);
    }

    public void clear() {
        cache.clear();
    }

    public static SimpleCacheManager getInstance() {
        if (instance == null) {
            synchronized (monitor) {
                if (instance == null) {
                    instance = new SimpleCacheManager();
                }
            }
        }
        return instance;
    }

}

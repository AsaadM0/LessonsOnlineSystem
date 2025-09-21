package com.example.project.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheAlgo<K, V> implements CacheAlgo<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> cache;

    public LRUCacheAlgo(int capacity) {
        this.capacity = capacity;
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCacheAlgo.this.capacity;
            }
        };
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    @Override
    public int size() {
        return cache.size();
    }
}

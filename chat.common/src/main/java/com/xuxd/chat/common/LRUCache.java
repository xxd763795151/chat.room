package com.xuxd.chat.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 许晓东
 * @date 2021-04-12 0:26
 * @Description: lru cache
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private int capacity = 1000;

    public LRUCache(int initialCapacity, int capacity) {
        super(initialCapacity);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > capacity;
    }
}

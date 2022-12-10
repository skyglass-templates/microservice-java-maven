package com.zhaohuabing.demo;

import org.springframework.http.HttpHeaders;

import java.util.Iterator;
import java.util.Map;

public class HtttpHeaderCarrier implements io.opentracing.propagation.TextMap {
    private HttpHeaders headers;

    public HtttpHeaderCarrier(HttpHeaders headers) {
        this.headers = headers;
    }

    @Override
    public void put(String key, String value) {
        this.headers.set(key, value);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.headers.toSingleValueMap().entrySet().iterator();
    }
}

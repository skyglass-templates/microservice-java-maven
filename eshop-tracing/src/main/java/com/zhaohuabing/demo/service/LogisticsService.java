package com.zhaohuabing.demo.service;

import com.zhaohuabing.demo.HtttpHeaderCarrier;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Huabing Zhao
 */
@RestController
public class LogisticsService {

    @Autowired
    private Tracer tracer;

    @RequestMapping(value = "/transport")
    public String transport(@RequestHeader HttpHeaders headers) {
        SpanContext parent = tracer.extract(Format.Builtin.HTTP_HEADERS, new HtttpHeaderCarrier(headers));
        Span span = tracer.buildSpan("transport").asChildOf(parent).start();
        // Add a random delay to the service
        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
        String user = span.getBaggageItem("user");
        return user + "'s order is on the way!";
    }
}

package com.zhaohuabing.demo.service;

import com.zhaohuabing.demo.HtttpHeaderCarrier;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Huabing Zhao
 */
@RestController
public class DeliveryService {

    @Autowired
    private Tracer tracer;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/arrangeDelivery")
    public String arrangeDelivery(@RequestHeader HttpHeaders headers) {
        SpanContext parent = tracer.extract(Format.Builtin.HTTP_HEADERS, new HtttpHeaderCarrier(headers));
        Span span = tracer.buildSpan("arrangeDelivery").asChildOf(parent).start();
        String result = "";
        try {
            Thread.sleep((long) (Math.random() * 1000));
            HttpHeaders outboundHeaders = new HttpHeaders();
            tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new HtttpHeaderCarrier(outboundHeaders));
            HttpEntity<String> entity = new HttpEntity<>("", outboundHeaders);
            result += restTemplate.exchange("http://logistics:8080/transport", HttpMethod.GET, entity, String.class).getBody();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
        String user = span.getBaggageItem("user");
        return result + "<BR>" + user + "'s order is delivered!";
    }
}

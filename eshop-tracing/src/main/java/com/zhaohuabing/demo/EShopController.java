package com.zhaohuabing.demo;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
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
public class EShopController {
    @Autowired
    private Tracer tracer;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/checkout")
    public String checkout(@RequestHeader HttpHeaders headers) {
        Span span = tracer.buildSpan("checkout").start();
        span.setBaggageItem("user", headers.getFirst("user"));
        String result = "";
        try {
            // Use HTTP GET in this demo. In a real world use case,We should use HTTP POST instead.
            // The three services are bundled in one jar for simplicity. To make it work,define three services in Kubernets.
            HttpHeaders outboundHeaders = new HttpHeaders();
            tracer.inject(span.context(), Format.Builtin.HTTP_HEADERS, new HtttpHeaderCarrier(outboundHeaders));
            HttpEntity<String> entity = new HttpEntity<>("", outboundHeaders);
            result += restTemplate.exchange("http://inventory:8080/createOrder", HttpMethod.GET, entity, String.class).getBody();
            result += "<BR>";
            result += restTemplate.exchange("http://billing:8080/payment", HttpMethod.GET, entity, String.class).getBody();
            result += "<BR>";
            result += restTemplate.exchange("http://delivery:8080/arrangeDelivery", HttpMethod.GET, entity, String.class).getBody();
            return result;
        } catch (Exception ex) {
            Tags.ERROR.set(span, true);
            ex.printStackTrace();
        } finally {
            span.finish();
        }
        return result;
    }
}

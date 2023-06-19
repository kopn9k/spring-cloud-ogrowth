package com.pasha.gatewayserver.filter;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Optional;

@Component
@Order(1)
public class ResponseFilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    private final FilterUtils filterUtils;
    private final Tracer tracer;

    public ResponseFilter(FilterUtils filterUtils, Tracer tracer) {
        this.filterUtils = filterUtils;
        this.tracer = tracer;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String traceId = Optional.ofNullable(tracer.currentSpan())
                .map(Span::context)
                .map(TraceContext::traceId)
                .orElse("null");

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            logger.debug("Adding the correlation id to the outbound headers. {}", traceId);
            exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, traceId);
            logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
        }));
    }
}

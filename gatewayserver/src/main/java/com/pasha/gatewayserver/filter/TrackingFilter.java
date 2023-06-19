package com.pasha.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Order(2)
public class TrackingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

    private final FilterUtils filterUtils;

    public TrackingFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        if (isCorrelationIdPresent(httpHeaders)) {
            logger.debug("tmx-correlation-id found in tracking filter: {}. ",
                    filterUtils.getCorrelationId(httpHeaders));
        } else {
            String correlationId = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange, correlationId);
            logger.debug("tmx-correlation-id generated in tracking filter: {}.", correlationId);
        }

        return chain.filter(exchange);
    }

    private boolean isCorrelationIdPresent(HttpHeaders httpHeaders) {
        return filterUtils.getCorrelationId(httpHeaders) != null;
    }


    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}

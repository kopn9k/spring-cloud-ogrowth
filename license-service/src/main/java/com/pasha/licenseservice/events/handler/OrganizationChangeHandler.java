package com.pasha.licenseservice.events.handler;

import com.pasha.licenseservice.events.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrganizationChangeHandler {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    @Bean
    Consumer<OrganizationChangeModel> logger() {
        return organizationChangeHandler -> logger.debug("Received an {} event for organization id {} and correlation id {}",
                organizationChangeHandler.getAction().toString(), organizationChangeHandler.getOrganizationId(),
                organizationChangeHandler.getCorrelationId());
    }
}

package com.pasha.licenseservice.events.handler;

import com.pasha.licenseservice.events.model.OrganizationChangeModel;
import com.pasha.licenseservice.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class OrganizationChangeHandler {
    private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeHandler.class);

    private final OrganizationRedisRepository repository;

    public OrganizationChangeHandler(OrganizationRedisRepository repository) {
        this.repository = repository;
    }

    @Bean
    Consumer<OrganizationChangeModel> logger() {
        return organizationChangeHandler -> {
            String organizationId = organizationChangeHandler.getOrganizationId();
            switch (organizationChangeHandler.getAction()) {
                case GET -> logger.debug("Received an GET event for organization id {} and correlation id {}",
                        organizationId,
                        organizationChangeHandler.getCorrelationId());
                case CREATED, UPDATED, DELETED -> {
                    logger.debug("Received an {} event for organization id {} and correlation id {}",
                            organizationChangeHandler.getAction(), organizationId,
                            organizationChangeHandler.getCorrelationId());
                    try {
                        repository.deleteById(organizationId);
                    }catch (Exception ex){
                        logger.error("Unable to delete organization {} form Redis. Exception {}", organizationId, ex.getMessage());
                    }
                    logger.debug("Removed organization with id {} from redis cache", organizationId);
                }
            }
        };
    }
}

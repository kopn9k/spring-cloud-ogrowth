package com.pasha.licenseservice.service.client;

import brave.ScopedSpan;
import brave.Tracer;
import com.pasha.licenseservice.model.Organization;
import com.pasha.licenseservice.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
public class OrganizationRestTemplateClient implements OrganizationClient {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    private final RestTemplate restTemplate;
    private final OrganizationRedisRepository repository;
    private final Tracer tracer;

    public OrganizationRestTemplateClient(RestTemplate restTemplate, OrganizationRedisRepository repository, Tracer tracer) {
        this.restTemplate = restTemplate;
        this.repository = repository;
        this.tracer = tracer;
    }

    @Override
    public Organization getOrganization(String organizationId) {
        logger.debug("In Licensing Service.getOrganization");

        Optional<Organization> organizationOptional = getOrganizationFromCache(organizationId);

       if (organizationOptional.isPresent()) {
           logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, organizationOptional);
           return organizationOptional.get();
       }

        logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);
        ResponseEntity<Organization> response = restTemplate.exchange(
                "http://gateway-service:8072/organization/api/v1/organization/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId);

        Organization organization = response.getBody();

        if (organization != null) {
            cacheOrganization(organization);
        }

        return organization;

    }

    private Optional<Organization> getOrganizationFromCache(String organizationId) {
        ScopedSpan newSpan = tracer.startScopedSpan("readLicensingDataFromRedis");
        try {
            return repository.findById(organizationId);
        } catch (Exception ex) {
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache.  Exception {}", organizationId, ex.getMessage());
            return Optional.empty();
        } finally {
            newSpan.tag("peer.service", "redis");
            newSpan.annotate("Client received");
            newSpan.finish();
        }
    }

    private void cacheOrganization(Organization organization) {
        try {
            repository.save(organization);
        }catch (Exception ex){
            logger.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex.getMessage());
        }
    }
}

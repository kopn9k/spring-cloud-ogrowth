package com.pasha.licenseservice.service.client;

import com.pasha.licenseservice.model.Organization;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    private final RestTemplate restTemplate;

    public OrganizationRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        ResponseEntity<Organization> response = restTemplate.exchange(
                "http://organization-service/api/v1/organization/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId);

        return  response.getBody();
    }
}

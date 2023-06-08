package com.pasha.licenseservice.service.client;

import com.pasha.licenseservice.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationRestTemplateClient {
    public Organization getOrganization(String organizationId) {
        return new Organization("default", "default", "default", "default", "default");
    }
}

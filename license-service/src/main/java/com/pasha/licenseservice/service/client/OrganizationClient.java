package com.pasha.licenseservice.service.client;

import com.pasha.licenseservice.model.Organization;

public interface OrganizationClient {
    Organization getOrganization(String organizationId);
}

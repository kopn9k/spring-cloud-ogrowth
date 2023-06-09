package com.pasha.organizationservice.service;

import com.pasha.organizationservice.model.Organization;

public interface OrganizationService {

    Organization getOrganization(String organizationId);

    Organization createOrganization(Organization organization);

    void updateOrganization(Organization organization, String organizationId);

    void deleteOrganization(String organizationId);

}

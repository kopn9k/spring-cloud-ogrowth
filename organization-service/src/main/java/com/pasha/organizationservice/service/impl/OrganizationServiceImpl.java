package com.pasha.organizationservice.service.impl;

import com.pasha.organizationservice.events.source.SimpleSourceBean;
import com.pasha.organizationservice.model.Organization;
import com.pasha.organizationservice.repository.OrganizationRepository;
import com.pasha.organizationservice.service.OrganizationService;
import com.pasha.organizationservice.model.ActionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;
    private final SimpleSourceBean simpleSourceBean;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, SimpleSourceBean simpleSourceBean) {
        this.organizationRepository = organizationRepository;
        this.simpleSourceBean = simpleSourceBean;
    }

    @Override
    public Organization getOrganization(String organizationId) {
        logger.debug("OrganizationServiceImpl.getOrganization() Organization id: {}", organizationId);
        simpleSourceBean.publishOrganizationChange(ActionEnum.GET, organizationId);
        return organizationRepository.findById(organizationId).orElseGet(Organization::new);
    }

    @Override
    public Organization createOrganization(Organization organization) {
        Organization savedOrganization = organizationRepository.save(
                organization.toBuilder()
                        .id(UUID.randomUUID().toString())
                        .build());
        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED, savedOrganization.getId());
        return savedOrganization;
    }

    @Override
    public void updateOrganization(Organization organization, String organizationId) {
        organizationRepository.save(
                organization.toBuilder()
                        .id(organizationId)
                        .build());
        simpleSourceBean.publishOrganizationChange(ActionEnum.UPDATED, organizationId);
    }

    @Override
    public void deleteOrganization(String organizationId) {
        organizationRepository.delete(
                new Organization().toBuilder()
                        .id(organizationId)
                        .build());
        simpleSourceBean.publishOrganizationChange(ActionEnum.DELETED, organizationId);
    }
}

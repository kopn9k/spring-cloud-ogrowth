package com.pasha.organizationservice.service.impl;

import com.pasha.organizationservice.events.source.SimpleSourceBean;
import com.pasha.organizationservice.model.Organization;
import com.pasha.organizationservice.repository.OrganizationRepository;
import com.pasha.organizationservice.service.OrganizationService;
import com.pasha.organizationservice.model.ActionEnum;
import io.micrometer.observation.ObservationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;
    private final SimpleSourceBean simpleSourceBean;

    private final ObservationRegistry observationRegistry;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, SimpleSourceBean simpleSourceBean, ObservationRegistry observationRegistry) {
        this.organizationRepository = organizationRepository;
        this.simpleSourceBean = simpleSourceBean;
        this.observationRegistry = observationRegistry;
    }

    @Override
    public Organization getOrganization(String organizationId) {
        logger.debug("OrganizationServiceImpl.getOrganization() Organization id: {}", organizationId);
        simpleSourceBean.publishOrganizationChange(ActionEnum.GET, organizationId, observationRegistry);
        return organizationRepository.findById(organizationId).orElseGet(Organization::new);
    }

    @Override
    public Organization createOrganization(Organization organization) {
        Organization savedOrganization = organizationRepository.save(
                organization.toBuilder()
                        .id(UUID.randomUUID().toString())
                        .build());
        simpleSourceBean.publishOrganizationChange(ActionEnum.CREATED, savedOrganization.getId(), observationRegistry);
        return savedOrganization;
    }

    @Override
    public void updateOrganization(Organization organization, String organizationId) {
        organizationRepository.save(
                organization.toBuilder()
                        .id(organizationId)
                        .build());
        simpleSourceBean.publishOrganizationChange(ActionEnum.UPDATED, organizationId, observationRegistry);
    }

    @Override
    public void deleteOrganization(String organizationId) {
        organizationRepository.delete(
                new Organization().toBuilder()
                        .id(organizationId)
                        .build());
        simpleSourceBean.publishOrganizationChange(ActionEnum.DELETED, organizationId, observationRegistry);
    }
}

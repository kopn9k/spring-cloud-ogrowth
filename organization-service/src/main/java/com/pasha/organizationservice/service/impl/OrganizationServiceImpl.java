package com.pasha.organizationservice.service.impl;

import com.pasha.organizationservice.model.Organization;
import com.pasha.organizationservice.repository.OrganizationRepository;
import com.pasha.organizationservice.service.OrganizationService;
import com.pasha.organizationservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization getOrganization(String organizationId) {
        logger.debug("OrganizationServiceImpl.getOrganization() Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return organizationRepository.findById(organizationId).orElseGet(Organization::new);
    }

    @Override
    public Organization createOrganization(Organization organization) {
        organizationRepository.save(
                organization.toBuilder()
                        .id(UUID.randomUUID().toString())
                        .build());
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization, String organizationId) {
        organizationRepository.save(
                organization.toBuilder()
                        .id(organizationId)
                        .build());
    }

    @Override
    public void deleteOrganization(String organizationId) {
        organizationRepository.delete(
                new Organization().toBuilder()
                        .id(organizationId)
                        .build());
    }
}

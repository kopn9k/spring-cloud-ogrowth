package com.pasha.organizationservice.controller;

import com.pasha.organizationservice.mapper.OrganizationMapper;
import com.pasha.organizationservice.model.Organization;
import com.pasha.organizationservice.service.OrganizationService;
import com.pasha.organizationservice.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/organization")
public class OrganizationControllerV1 {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationControllerV1.class);

    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;

    public OrganizationControllerV1(OrganizationService organizationService, OrganizationMapper organizationMapper) {
        this.organizationService = organizationService;
        this.organizationMapper = organizationMapper;
    }

    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(@PathVariable(value = "organizationId") String organizationId) {
        logger.debug("OrganizationControllerV1 Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return ResponseEntity.ok(organizationService.getOrganization(organizationId));
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(organizationService.createOrganization(organization));

    }

    @PutMapping(value = "/{organizationId}")
    void updateOrganization(@PathVariable(value = "organizationId") String organizationId,
                            @RequestBody Organization organization) {
        organizationService.updateOrganization(organization, organizationId);

    }

    @DeleteMapping (value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable(value = "organizationId") String organizationId) {
        organizationService.deleteOrganization(organizationId);
    }
}

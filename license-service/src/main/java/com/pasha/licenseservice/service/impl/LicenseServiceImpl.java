package com.pasha.licenseservice.service.impl;

import com.pasha.licenseservice.config.ServiceConfig;
import com.pasha.licenseservice.model.ClientType;
import com.pasha.licenseservice.model.License;
import com.pasha.licenseservice.model.Organization;
import com.pasha.licenseservice.repository.LicenseRepository;
import com.pasha.licenseservice.service.LicenseService;
import com.pasha.licenseservice.service.client.OrganizationDiscoveryClient;
import com.pasha.licenseservice.service.client.OrganizationFeignClient;
import com.pasha.licenseservice.service.client.OrganizationRestTemplateClient;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;
    private final OrganizationRestTemplateClient organizationRestClient;
    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    public LicenseServiceImpl(MessageSource messageSource, LicenseRepository licenseRepository,
                              ServiceConfig serviceConfig, OrganizationRestTemplateClient organizationRestClient,
                              OrganizationFeignClient organizationFeignClient,
                              OrganizationDiscoveryClient organizationDiscoveryClient) {
        this.messageSource = messageSource;
        this.licenseRepository = licenseRepository;
        this.serviceConfig = serviceConfig;
        this.organizationRestClient = organizationRestClient;
        this.organizationFeignClient = organizationFeignClient;
        this.organizationDiscoveryClient = organizationDiscoveryClient;
    }

    @Override
    public License getLicense(String licenceId, String organizationId, Locale locale, ClientType clientType) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(messageSource.getMessage(
                                "license.search.error.message", null, locale), licenceId, organizationId)
                ));

        Organization organization = retrieveOrganization(organizationId, clientType);
        license.setOrganization(organization);

        return licenseWithComment(license);

    }

    @Override
    public List<License> getLicensesByOrganization(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    @Override
    public License createLicence(License license, String organizationId) {
        licenseRepository.save(
                license.toBuilder()
                        .licenseId(UUID.randomUUID().toString())
                        .organizationId(organizationId)
                        .build());
        return licenseWithComment(license);

    }

    @Override
    public License updateLicense(License license, String licenseId, String organizationId) {
        licenseRepository.save(
                license.toBuilder()
                        .licenseId(licenseId)
                        .organizationId(organizationId)
                        .build());
        return licenseWithComment(license);
    }

    @Override
    public String deleteLicense(String licenseId, Locale locale) {
        String responseMessage = null;
        licenseRepository.delete(
                new License().toBuilder()
                        .licenseId(licenseId)
                        .build());
        responseMessage = String.format(messageSource.getMessage(
                "license.delete.message", null, locale), licenseId);
        return responseMessage;
    }

    private License licenseWithComment(License license) {
        return license.toBuilder()
                .comment(serviceConfig.getProperty())
                .build();
    }

    private Organization retrieveOrganization(String organizationId, ClientType clientType) {
        return switch (clientType) {
            case REST, DEFAULT -> {
                System.out.println("I am using the rest client");
                yield organizationRestClient.getOrganization(organizationId);
            }
            case FEIGN -> {
                    System.out.println("I am using the feign client");
                    yield organizationFeignClient.getOrganization(organizationId);
            }
            case DISCOVERY -> {
                System.out.println("I am using the discovery client");
                yield organizationDiscoveryClient.getOrganization(organizationId);
            }
        };
    }
}

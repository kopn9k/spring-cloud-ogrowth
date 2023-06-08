package com.pasha.licenseservice.service.impl;

import com.pasha.licenseservice.config.ServiceConfig;
import com.pasha.licenseservice.model.License;
import com.pasha.licenseservice.repository.LicenseRepository;
import com.pasha.licenseservice.service.LicenseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
public class LicenseServiceImpl implements LicenseService {

    private final MessageSource messageSource;
    private final LicenseRepository licenseRepository;
    private final ServiceConfig serviceConfig;

    public LicenseServiceImpl(MessageSource messageSource, LicenseRepository licenseRepository, ServiceConfig serviceConfig) {
        this.messageSource = messageSource;
        this.licenseRepository = licenseRepository;
        this.serviceConfig = serviceConfig;

    }

    @Override
    public License getLicense(String licenceId, String organizationId, Locale locale) {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenceId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(messageSource.getMessage(
                                "license.search.error.message", null, locale), licenceId, organizationId)
                ));

        return licenseWithComment(license);

    }

    @Override
    public License createLicence(License license) {
        licenseRepository.save(
                license.toBuilder()
                        .licenseId(UUID.randomUUID().toString())
                        .build());
        return licenseWithComment(license);

    }

    @Override
    public License updateLicense(License license, String licenseId) {
        licenseRepository.save(
                license.toBuilder()
                        .licenseId(licenseId)
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
}

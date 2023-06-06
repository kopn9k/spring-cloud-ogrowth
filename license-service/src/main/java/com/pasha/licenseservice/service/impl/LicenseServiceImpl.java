package com.pasha.licenseservice.service.impl;

import com.pasha.licenseservice.model.License;
import com.pasha.licenseservice.service.LicenseService;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
public class LicenseServiceImpl implements LicenseService {

    private MessageSource messageSource;

    public LicenseServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public License getLicense(String licenceId, String organizationId) {
        License license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenceId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");

        return license;

    }

    @Override
    public String createLicence(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(messageSource.getMessage(
                    "license.create.message", null, locale),
                    license);
        }

        return responseMessage;

    }

    @Override
    public String updateLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(messageSource.getMessage(
                    "license.update.message", null, locale),
                    license);
        }
        return responseMessage;
    }

    @Override
    public String deleteLicense(String licenseId, String organizationId, Locale locale) {
        String responseMessage = null;
        responseMessage = String.format(messageSource.getMessage(
                "license.delete.message", null, locale),
                licenseId, organizationId);
        return responseMessage;
    }
}
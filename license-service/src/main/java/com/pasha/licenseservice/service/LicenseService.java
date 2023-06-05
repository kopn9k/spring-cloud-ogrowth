package com.pasha.licenseservice.service;

import com.pasha.licenseservice.model.License;
import java.util.Locale;

public interface LicenseService {

    License getLicense(String licenceId, String organizationId);

    String createLicence(License license, String organizationId, Locale locale);

    String updateLicense(License license, String organizationId, Locale locale);

    String deleteLicense(String licenseId, String organizationId, Locale locale);

}

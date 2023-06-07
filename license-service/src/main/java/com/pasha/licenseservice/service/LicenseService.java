package com.pasha.licenseservice.service;

import com.pasha.licenseservice.model.License;
import java.util.Locale;

public interface LicenseService {

    License getLicense(String licenceId, String organizationId, Locale locale);

    License createLicence(License license);

    License updateLicense(License license);

    String deleteLicense(String licenseId, Locale locale);

}

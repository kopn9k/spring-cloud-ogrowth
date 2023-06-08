package com.pasha.licenseservice.service;

import com.pasha.licenseservice.model.ClientType;
import com.pasha.licenseservice.model.License;

import java.util.List;
import java.util.Locale;

public interface LicenseService {

    License getLicense(String licenceId, String organizationId, Locale locale, ClientType clientType);


    License updateLicense(License license, String licenseId, String organizationId);

    License createLicence(License license, String organizationId);

    String deleteLicense(String licenseId, Locale locale);

    List<License> getLicensesByOrganization(String organizationId);
}

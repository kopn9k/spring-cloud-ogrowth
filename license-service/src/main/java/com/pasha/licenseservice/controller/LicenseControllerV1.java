package com.pasha.licenseservice.controller;

import com.pasha.licenseservice.mapper.LicenseMapper;
import com.pasha.licenseservice.model.License;
import com.pasha.licenseservice.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController
@RequestMapping(value = "api/v1/organization/{organizationId}/license")
public class LicenseControllerV1 {

    private final LicenseService licenseService;
    private final LicenseMapper licenseMapper;

    public LicenseControllerV1(LicenseService licenseService, LicenseMapper licenseMapper) {
        this.licenseService = licenseService;
        this.licenseMapper = licenseMapper;
    }

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable(value = "organizationId") String organizationId,
            @PathVariable(value = "licenseId") String licenseId,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        License license = licenseService.getLicense(licenseId, organizationId, locale);

        return ResponseEntity.ok(license);

    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicence(request));
    }

    @PutMapping(value = "/{licenseId}")
    public ResponseEntity<License> updateLicense(
            @PathVariable(value = "licenseId") String licenseId,
            @RequestBody License license) {
        return ResponseEntity.ok(licenseService.updateLicense(license, licenseId));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable(value = "licenseId") String licenseId,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, locale));

    }
}

package com.pasha.licenseservice.controller;

import com.pasha.licenseservice.model.License;
import com.pasha.licenseservice.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable(value = "organizationId") String organizationId,
            @PathVariable(value = "licenseId") String licenseId) {

        License license = licenseService.getLicense(licenseId, organizationId);

        return ResponseEntity.ok(license);

    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable(value = "organizationId") String organizationId,
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicence(request, organizationId));
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable(value = "organizationId") String organizationId,
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request, organizationId));
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable(value = "organizationId") String organizationId,
            @PathVariable(value = "licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId, organizationId));

    }
}

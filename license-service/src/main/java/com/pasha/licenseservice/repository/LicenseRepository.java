package com.pasha.licenseservice.repository;

import com.pasha.licenseservice.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    List<License> findByOrganizationId(String organizationId);
    Optional<License> findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}

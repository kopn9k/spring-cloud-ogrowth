package com.pasha.licenseservice.repository;

import com.pasha.licenseservice.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRedisRepository extends CrudRepository<Organization, String> {
}

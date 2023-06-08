package com.pasha.licenseservice.service.client;

import com.pasha.licenseservice.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "organization-service")
public interface OrganizationFeignClient {

    @GetMapping(value="/api/v1/organization/{organizationId}", consumes="application/json")
    Organization getOrganization(@PathVariable("organizationId")String organizationId);
}

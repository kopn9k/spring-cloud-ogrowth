package com.pasha.organizationservice.mapper;

import com.pasha.organizationservice.model.Organization;
import com.pasha.organizationservice.model.dto.OrganizationDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
    OrganizationDto map(Organization license);

    @InheritInverseConfiguration
    Organization map(OrganizationDto licenseDto);
}

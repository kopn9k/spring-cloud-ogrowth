package com.pasha.licenseservice.mapper;

import com.pasha.licenseservice.model.License;
import com.pasha.licenseservice.model.dto.LicenseDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface LicenseMapper {
    LicenseDto map(License license);

    @InheritInverseConfiguration
    License map(LicenseDto licenseDto);
}

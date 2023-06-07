package com.pasha.licenseservice.model.dto;

import lombok.Data;

@Data
public class LicenseDto {

        private String licenseId;
        private String description;
        private String organizationId;
        private String productName;
        private String licenseType;
        private String comment;


}

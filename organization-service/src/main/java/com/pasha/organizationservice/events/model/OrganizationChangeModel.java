package com.pasha.organizationservice.events.model;

import com.pasha.organizationservice.model.ActionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrganizationChangeModel {
    private String type;
    private ActionEnum action;
    private String organizationId;
    private String correlationId;

    public OrganizationChangeModel(String type, ActionEnum action, String organizationId, String correlationId) {
        super();
        this.type = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }
}

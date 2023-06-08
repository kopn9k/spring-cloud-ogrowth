package com.pasha.licenseservice.service.client;

import com.pasha.licenseservice.model.Organization;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

//This client was implementing only for demo, not recommended to use
@Component
public class OrganizationDiscoveryClient {

    private final DiscoveryClient discoveryClient;

    public OrganizationDiscoveryClient(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }


    public Organization getOrganization(String organizationId) {

        //wasn't injected for not retrieving spring proxy bean with default client side load balancing
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");

        if (instances.size()==0) return null;
        String serviceUri = String.format("%s/api/v1/organization/%s", instances.get(0).getUri().toString(), organizationId);
        System.out.println("URI --- " + instances.get(0).getUri().toString());

        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}

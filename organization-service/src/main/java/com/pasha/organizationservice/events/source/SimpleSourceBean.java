package com.pasha.organizationservice.events.source;

import com.pasha.organizationservice.events.model.OrganizationChangeModel;
import com.pasha.organizationservice.utils.ActionEnum;
import com.pasha.organizationservice.utils.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    private final StreamBridge streamBridge;

    public SimpleSourceBean(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrganizationChange(ActionEnum action, String organizationId){
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        OrganizationChangeModel change =  new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContext.getCorrelationId());

        streamBridge.send("orgChangeTopic", change);

    }
}
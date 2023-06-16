package com.pasha.organizationservice.events.source;

import com.pasha.organizationservice.events.model.OrganizationChangeModel;
import com.pasha.organizationservice.model.ActionEnum;
import com.pasha.organizationservice.utils.UserContext;
import io.micrometer.observation.ObservationRegistry;
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

    public void publishOrganizationChange(ActionEnum action, String organizationId, ObservationRegistry observationRegistry){
        logger.debug("Sending Kafka message {} for Organization Id: {}", action.toString(), organizationId);
        OrganizationChangeModel change =  new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action,
                organizationId,
                UserContext.getCorrelationId());

       /* Observation.createNotStarted("orgChangeTopicObservation", observationRegistry)
                        .observe(() -> {
                            streamBridge.send("orgChangeTopic", change);
                        });*/

        streamBridge.send("orgChangeTopic", change);
    }
}

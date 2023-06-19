package com.pasha.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import reactor.core.publisher.Hooks;

@SpringBootApplication // actuator/gateway/refresh for automatically route refresh
@RefreshScope
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
		Hooks.enableAutomaticContextPropagation();
	}

}

**Pet project with Spring boot 3**

Technologies: 
* ELK stack + zipkin
* Spring Cloud(Eureka, Gateway, config server, micrometer for tracing)
* kafka + spring cloud stream
* redis as cache, postgres as DB
* resilience4j for circuit breaker, bulkhead, rate limiters, fallback

How to run:
* Prerequisites: (git, java17, maven, docker)

```bash
# Clone this repository
$ git clone https://github.com/kopn9k/spring-cloud-ogrowth.git

# To build the code as a docker image, open a command-line 
# window and execute the following command:
$ mvn clean package spring-boot:build-image

#Go to the docker directory
$ cd docker

# Use docker compose to start all the services
$ docker-compose docker-compose.yml up
```

Some useful links:
* zipkin:http://localhost:9411/zipkin/ (every response have a trace id in response headers for **testing**)
* kibana:http://localhost:5601/
* license-service: curl --location 'http://localhost:8072/license/api/v1/organization/e839ee96-28de-4f67-bb79-870ca89743a0/license/279709ff-e6d5-4a54-8b55-a5c37542025b'
* organization-service: curl --location 'http://localhost:8072/organization/api/v1/organization/d898a142-de44-466c-8c88-9ceb2c2429d3'
package com.rova.transactionservice.components;

import com.rova.transactionservice.config.web.AntMatchersEndpoints;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.Stack;

import static com.rova.transactionservice.enums.Authorities.USER;

@Component
public class DefaultAntMatchersEndpoint {

    @Bean("defaultAntMatchersEndpoints")
    public AntMatchersEndpoints antMatchersEndpoints() {
        AntMatchersEndpoints antMatchersEndpoints = new AntMatchersEndpoints();
        Stack<AntMatchersEndpoints.AntMatchersEndpoint> antMatchersEndpointSet = new Stack<>();
        antMatchersEndpointSet.add(new AntMatchersEndpoints.AntMatchersEndpoint(HttpMethod.GET, "/"));
        antMatchersEndpointSet.add(new AntMatchersEndpoints.AntMatchersEndpoint(HttpMethod.GET, "/swagger-ui/index.html/**"));
        antMatchersEndpointSet.add(new AntMatchersEndpoints.AntMatchersEndpoint("/api/v1/transactions/**", USER.name()));
        antMatchersEndpointSet.add(new AntMatchersEndpoints.AntMatchersEndpoint("/api/v1/transactions/**/", USER.name()));
        antMatchersEndpointSet.add(new AntMatchersEndpoints.AntMatchersEndpoint("/api/v1/accounts", USER.name()));
        antMatchersEndpointSet.add(new AntMatchersEndpoints.AntMatchersEndpoint("/api/v1/accounts/**/balance", USER.name()));

        AntMatchersEndpoints.AntMatchersEndpoint antMatchersEndpoint =
                new AntMatchersEndpoints.AntMatchersEndpoint("/actuator/**");
        antMatchersEndpoint.setPermitAll(true);
        antMatchersEndpointSet.add(antMatchersEndpoint);

        antMatchersEndpoints.setAntMatchersEndpoints(antMatchersEndpointSet);
        return antMatchersEndpoints;
    }
}

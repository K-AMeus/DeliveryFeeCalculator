package com.fujitsu.deliveryfee.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


/**
 * Configuration class for setting up OpenAPI documentation for the Delivery Fee Calculator API.
 * This class utilizes SpringDoc's OpenAPI 3 annotations to define global API information and server details.
 */
@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Delivery Fee Calculator API",
                version = "1.0",
                description = "API for calculating delivery fees",
                contact = @io.swagger.v3.oas.annotations.info.Contact(
                        name = "Karl-Andreas Meus",
                        email = "karl.andreas@meus.ee"
                )

        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenAPIConfig {

}

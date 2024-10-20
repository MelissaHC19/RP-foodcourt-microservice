package com.prgama.foodcourt_microservice.infrastructure.documentation;

import com.prgama.foodcourt_microservice.infrastructure.constants.OpenApiConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title(OpenApiConstants.OPEN_API_TITLE)
                        .version(OpenApiConstants.OPEN_API_VERSION)
                        .description(OpenApiConstants.OPEN_API_DESCRIPTION)
                        .termsOfService(OpenApiConstants.OPEN_API_TERMS_OF_SERVICE)
                        .license(new License().name(OpenApiConstants.OPEN_API_LICENSE_NAME).url(OpenApiConstants.OPEN_API_LICENSE_URL)));
    }
}
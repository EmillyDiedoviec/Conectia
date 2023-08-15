package com.conectia.Conectia.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocConfiguration {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Conectia API")
                        .description("API de uma rede social")
                        .contact(new Contact()
                                .name("Emilly Diedoviec")
                                .email("emillydiedoviec@gmail.com")));
    }
}

package com.aguilar.luis.proyectobase.app.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(regex("/api/v1.*"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Documentación API")
                .description("Esta es la documentación de las actividades del dia 9, 10 y 11")
                .version("1.3")
                .contact(new Contact("Luis Aguilar", "https://banregio.workplace.com/profile.php?id=100084010296675", "luis.aguilar@banregio.com"))
                .build();
    }

}

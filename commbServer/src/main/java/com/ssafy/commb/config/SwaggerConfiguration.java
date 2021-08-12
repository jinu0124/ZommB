package com.ssafy.commb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * @ Swagger 설정 확인 : http://localhost:8080/v2/api-docs
     * @ Swagger -UI 확인 : http://localhost:8080/swagger-ui.html
     */

    private final String version = "V1";
    private final String title = "CommB API " + version;

    @Value("${security.accesstoken}")
    private String accessToken;

    @Bean
    public Docket api() {
        List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("OK").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생").responseModel(new ModelRef("Error")).build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다").build());
        return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
                .apiInfo(apiInfo()).groupName(version).select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.commb.controller"))
                .paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,responseMessages)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()));
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    // API에 대한 설명
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title)
                .description("<h3>SSAFY API Reference for Developers</h3>Swagger CommB API<br>")
                .contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
                .license("SSAFY License")
                .licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
                .version("1.0").build();

    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", accessToken, "header");
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}


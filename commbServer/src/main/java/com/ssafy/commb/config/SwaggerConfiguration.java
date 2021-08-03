package com.ssafy.commb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 스웨거
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

//	Swagger 설정 확인
//	http://localhost:8080/v2/api-docs
//	Swagger-UI 확인
//	http://localhost:8080/swagger-ui.html

    private String version = "V1";
    private String title = "CommB API " + version;

    @Bean
    public Docket api() {

        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        ParameterBuilder bParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("access-token") //헤더 이름
                .description("access-token") //설명
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        bParameterBuilder.name("refresh-token") //헤더 이름
                .description("refresh-token") //설명
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        List<Parameter> headParameters = new ArrayList<>();
        headParameters.add(aParameterBuilder.build());
        headParameters.add(bParameterBuilder.build());

        List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
        responseMessages.add(new ResponseMessageBuilder().code(200).message("OK").build());
        responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생").responseModel(new ModelRef("Error")).build());
        responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다").build());
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(headParameters)
                .consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
                .apiInfo(apiInfo()).groupName(version).select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.commb.controller"))
                .paths(PathSelectors.any()).build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,responseMessages);
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

}


package com.course.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("course")
                .apiInfo(webApiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.course"))
                //只显示api路径下的页面
                //.paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }


    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("API-Test")
                .description("Testing different Mapping method")
                .version("1.0")
                .contact(new Contact("course", "http://course.com", "course.com"))
                .build();
    }
}

package com.example.commmon;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/resources/")
//                .addResourceLocations("classpath:/static/images");
        registry.addResourceHandler("/images/**").
                addResourceLocations("file:///D:/workspace/unit-un-demo/unit-university/src/main/resources/static/images/");
        //如果不知道如何以file开头就用浏览器打开该图片
    }
}

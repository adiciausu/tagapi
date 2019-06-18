package com.cegeka.tag.tagapi.config;

import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableSpringDataWebSupport
@Configuration
@ComponentScan("com.cegeka.tag.tagapi")
public class WebConfiguration extends WebMvcConfigurationSupport {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
    resolver.setFallbackPageable(PageRequest.of(0, 100));
    argumentResolvers.add(resolver);

    super.addArgumentResolvers(argumentResolvers);
  }
}

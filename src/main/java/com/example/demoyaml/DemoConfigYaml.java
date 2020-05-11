package com.example.demoyaml;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class DemoConfigYaml extends WebMvcConfigurationSupport {

  private static final List<MediaType> YAML_MEDIA_TYPES = List.of(
    MediaType.valueOf("text/yaml"), MediaType.valueOf("text/yml"),
    MediaType.valueOf("text/x-yaml"), MediaType.valueOf("text/vnd-yaml"),
    MediaType.valueOf("application/yaml"), MediaType.valueOf("application/x-yaml"));

  @Override
  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper yamlMapper = new YAMLMapper();
    MappingJackson2HttpMessageConverter yamlConverter =
      new MappingJackson2HttpMessageConverter(yamlMapper);
    yamlConverter.setSupportedMediaTypes(YAML_MEDIA_TYPES);
    converters.add(yamlConverter);
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    for (MediaType mediaType : YAML_MEDIA_TYPES) {
      configurer.mediaType(mediaType.getSubtype(), mediaType);
    }
    configurer.defaultContentType(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);
  }
}

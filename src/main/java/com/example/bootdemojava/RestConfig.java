package com.example.bootdemojava;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestConfig implements WebMvcConfigurer {

    private static final List<MediaType> YAML_MEDIA_TYPES = List.of(
            MediaType.valueOf("text/yaml"), MediaType.valueOf("text/yml"),
            MediaType.valueOf("text/x-yaml"), MediaType.valueOf("text/vnd-yaml"),
            MediaType.valueOf("application/yaml"), MediaType.valueOf("application/x-yaml"));

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
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
    }
}

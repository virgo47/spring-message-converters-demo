package com.example.demo2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This configuration is picked by MVC auto-configuration and added to it.
 * This means default converters will be provided too by auto-configured {@code WebMvcConfigurer}.
 * If we excluded {@code WebMvcAutoConfiguration} in {@code @SpringBootApplication},
 * this would be found as a bean, but not processed as {@code WebMvcConfigurer} -
 * and custom converters would not be available.
 */
@Configuration
public class DemoConfig2 implements WebMvcConfigurer {

  private static final List<MediaType> YAML_MEDIA_TYPES = List.of(
    MediaType.valueOf("text/yaml"), MediaType.valueOf("text/yml"),
    MediaType.valueOf("text/x-yaml"), MediaType.valueOf("text/vnd-yaml"),
    MediaType.valueOf("application/yaml"), MediaType.valueOf("application/x-yaml"));

  private static final MediaType CRAZY1 = MediaType.valueOf("crazy/1");

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper yamlMapper = new YAMLMapper();
    MappingJackson2HttpMessageConverter yamlConverter =
      new MappingJackson2HttpMessageConverter(yamlMapper);
    yamlConverter.setSupportedMediaTypes(YAML_MEDIA_TYPES);
    converters.add(yamlConverter);

    // crazy converter
    converters.add(new Crazy1Converter<>("first", String.class));
    converters.add(new Crazy1Converter<>("second", Object.class));
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    for (MediaType mediaType : YAML_MEDIA_TYPES) {
      configurer.mediaType(mediaType.getSubtype(), mediaType);
    }
    configurer.mediaType(CRAZY1.getSubtype(), CRAZY1);
    configurer.defaultContentType(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);
  }

  private static class Crazy1Converter<T> extends AbstractHttpMessageConverter<T> {

    private final String name;
    private final Class<T> supportedClass;

    protected Crazy1Converter(String name, Class<T> supportedClass) {
      super(CRAZY1);
      this.name = name;
      this.supportedClass = supportedClass;
    }

    @Override
    protected boolean supports(Class clazz) {
      return supportedClass.isAssignableFrom(clazz);
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
      throws HttpMessageNotReadableException {
      throw new UnsupportedOperationException("This converter can only write objects, not read");
    }

    @Override
    protected void writeInternal(T o, HttpOutputMessage outputMessage)
      throws HttpMessageNotWritableException, IOException {
      StreamUtils.copy("Converted by " + name + " to " + o.getClass().getName() + ": " + o.toString(),
        StandardCharsets.UTF_8, outputMessage.getBody());
    }
  }
}

package com.example.demo2c;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration declaring our controllers as beans.
 * With @EnableWebMvc we have to inject them and add manually to the list of converters.
 */
@Configuration
public class DemoConfig2c implements WebMvcConfigurer {

  private static final MediaType CRAZY1 = MediaType.valueOf("crazy/1");

  @Autowired
  private List<Crazy1Converter<?>> crazy1Converters;

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    System.out.println("configureMessageConverters.size(): " + converters.size());
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    System.out.println("extendMessageConverters.size(): " + converters.size());
    converters.addAll(crazy1Converters);
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.mediaType(CRAZY1.getSubtype(), CRAZY1);
    // without this XML is default
    configurer.defaultContentType(CRAZY1, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML);
  }

  @Bean
  public Crazy1Converter<String> crazyStringConverter() {
    return new Crazy1Converter<>("first", String.class);
  }

  @Bean
  public Crazy1Converter<Object> crazyObjectConverter() {
    return new Crazy1Converter<>("second", Object.class);
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

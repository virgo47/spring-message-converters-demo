package com.example.demo2d;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration declaring our controllers as beans.
 * With auto-configuration we don't have to do anything else.
 */
@Configuration
public class DemoConfig2d implements WebMvcConfigurer {

  private static final MediaType CRAZY1 = MediaType.valueOf("crazy/1");

  @Bean
  public Crazy1Converter<String> crazyStringConverter() {
    return new Crazy1Converter<>("first", String.class);
  }

  @Bean
  public Crazy1Converter<Object> crazyObjectConverter() {
    return new Crazy1Converter<>("second", Object.class);
  }

  // Only needed if we want to adjust default content type order
  // or add support for media type via path extension or a query parameter.
  /*
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.defaultContentType(MediaType.APPLICATION_JSON);
  }
  */

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

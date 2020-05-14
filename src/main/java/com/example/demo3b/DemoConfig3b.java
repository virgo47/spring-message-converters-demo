package com.example.demo3b;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class DemoConfig3b extends WebMvcConfigurationSupport {

  private static final MediaType CRAZY1 = MediaType.valueOf("crazy/1");

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.clear();

    // crazy converter
    converters.add(new Crazy1Converter<>("first", String.class));
    converters.add(new Crazy1Converter<>("second", Object.class));

    // optionally - but after our converters!
    addDefaultHttpMessageConverters(converters);
  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
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

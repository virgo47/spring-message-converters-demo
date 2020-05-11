package com.example.demo2b;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 * {@code @EnableWebMvc} on the main class (also a configuration) disables {@code WebMvcAutoConfiguration}.
 * With this annotation we declared we want to take the full responsibility for the configuration.
 * Whether default converters are added depends on whether {@link #configureContentNegotiation}
 * or {@link #extendHandlerExceptionResolvers} is used - see their javadocs for details.
 */
@Configuration
public class DemoConfig2b implements WebMvcConfigurer {

  private static final MediaType CRAZY1 = MediaType.valueOf("crazy/1");

  @Autowired
  private MappingJackson2HttpMessageConverter jsonConverter;

  @Override
//  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    System.out.println("configureMessageConverters.size(): " + converters.size());

    // crazy converter
    converters.add(new Crazy1Converter<>("first", String.class));
    converters.add(new Crazy1Converter<>("second", Object.class));
    converters.add(jsonConverter);
    Crazy1Converter<Object> fallbackConverter = new Crazy1Converter<>("fallback", Object.class);
    fallbackConverter.setSupportedMediaTypes(List.of(MediaType.ALL));
    converters.add(fallbackConverter);

  }

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
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
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
      return false;
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

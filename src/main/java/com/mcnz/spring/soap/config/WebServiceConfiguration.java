package com.mcnz.spring.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfiguration {

  @Bean
  public static ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    
    return new ServletRegistrationBean<>(servlet, "/ws/*");
  }

  @Bean
  public static XsdSchema productSchema() {
    return new SimpleXsdSchema(new ClassPathResource("product.xsd"));
  }

  @Bean(name = "product")
  public static DefaultWsdl11Definition productWsdl11Definition(XsdSchema productSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    
    wsdl11Definition.setPortTypeName("ProductPort");
    wsdl11Definition.setLocationUri("/ws");  
    wsdl11Definition.setTargetNamespace("http://soap.jee.mcnz.com/"); 
    wsdl11Definition.setSchema(productSchema); 
    
    return wsdl11Definition;
  }
}

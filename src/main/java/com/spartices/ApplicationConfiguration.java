package com.spartices;

import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.service.ModelImageService;
import org.flowable.ui.modeler.service.ModelServiceImpl;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FlowableModelerAppProperties.class)
@ComponentScan(basePackages = {
        "org.flowable.ui.modeler.repository",
        "org.flowable.ui.common.repository",
        "org.flowable.ui.common.tenant" })
public class ApplicationConfiguration {

    @Bean
    public ServletRegistrationBean modelerApiServlet(ApplicationContext applicationContext) {
        AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        dispatcherServletConfiguration.setParent(applicationContext);
        dispatcherServletConfiguration.register(ApiDispatcherServletConfiguration.class);
        DispatcherServlet servlet = new DispatcherServlet(dispatcherServletConfiguration);
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/api/editor/*");
        registrationBean.setName("Flowable Modeler App API Servlet");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> modelerAppServlet(ApplicationContext applicationContext, ObjectProvider<MultipartConfigElement> multipartConfig) {
        AnnotationConfigWebApplicationContext dispatcherServletConfiguration = new AnnotationConfigWebApplicationContext();
        dispatcherServletConfiguration.setParent(applicationContext);
        dispatcherServletConfiguration.register(AppDispatcherServletConfiguration.class);
        DispatcherServlet servlet = new DispatcherServlet(dispatcherServletConfiguration);
        ServletRegistrationBean<DispatcherServlet> registrationBean = new ServletRegistrationBean<>(servlet, "/modeler-app/*");
        registrationBean.setName("Flowable Modeler App Servlet");
        registrationBean.setLoadOnStartup(1);
        registrationBean.setAsyncSupported(true);
        multipartConfig.ifAvailable(registrationBean::setMultipartConfig);
        return registrationBean;
    }

//    @Bean
//    public WebMvcConfigurer modelerApplicationWebMvcConfigurer() {
//        return new WebMvcConfigurer() {
//
//            @Override
//            public void addViewControllers(@NonNull ViewControllerRegistry registry) {
//
//                if (!ClassUtils.isPresent("org.flowable.ui.task.conf.ApplicationConfiguration", getClass().getClassLoader())) {
//                    // If the task application is not present, then the root should be mapped to admin
//                    registry.addViewController("/").setViewName("redirect:/modeler/");
//                }
//                registry.addViewController("/modeler").setViewName("redirect:/modeler/");
//                registry.addViewController("/modeler/").setViewName("forward:/modeler/index.html");
//            }
//        };
//    }


    // The services are shared between the api and app rest modules
    @Bean
    public ModelService modelerModelService() {
        return new ModelServiceImpl();
    }

    @Bean
    public ModelImageService modelerModelImageService() {
        return new ModelImageService();
    }

    @Bean
    public FlowableModelQueryService modelerModelQueryService() {
        return new FlowableModelQueryService();
    }

}
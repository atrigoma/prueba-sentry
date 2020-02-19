package io.sentry.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    private static final Logger logger = LogManager.getLogger("example.Application");

    /*
    Register a HandlerExceptionResolver that sends all exceptions to Sentry
    and then defers all handling to the other HandlerExceptionResolvers.

    You should only register this if you are not using a logging integration,
    otherwise you may double report exceptions.
     */
    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new io.sentry.spring.SentryExceptionResolver();
    }

    /*
    Register a ServletContextInitializer that installs the SentryServletRequestListener
    so that Sentry events contain HTTP request information.

    This should only be necessary in Spring Boot applications. "Classic" Spring
    should automatically load the `io.sentry.servlet.SentryServletContainerInitializer`.
     */
    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new io.sentry.spring.SentryServletContextInitializer();
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        int x = 1 / 0;

        return "Hello World!";
    }

    @GetMapping("/customer/{id}")
    @ResponseBody
    String customer(@PathVariable String id) {
        String result= new String("Hello world");
        System.out.println("contenido de id: " + id);
        if (id == "0"){
            int x = 1 / 0;            
        }
        else if( id.equals("1")){
            logger.debug("Debug message - 1");
            logger.info("Info message - 1");
            logger.warn("Warn message - 1");
            logger.error("Error message - 1");
            result="Hello - 1";
        }
        else if ( id.equals("2")){
            logger.debug("Debug message - 2");
            logger.info("Info message - 2");
            logger.warn("Warn message - 2");
            logger.error("Error message - 2");
            result="Hello - 2";

        }
        else{
            logger.debug("Debug message - otro");
            logger.info("Info message - otro");
            logger.warn("Warn message - otro");
            logger.error("Error message - otro");
            result="Hello - otro";
        }

        return result;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

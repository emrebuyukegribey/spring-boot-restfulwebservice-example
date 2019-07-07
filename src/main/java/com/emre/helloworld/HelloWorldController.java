package com.emre.helloworld;

import com.emre.SpringBootDemoApplication;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

//Controller
@RestController
public class HelloWorldController {

    //GET
    //URI - /hello-world
    //method - "Hello World"
    @GetMapping(path="/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path="/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/hello-world/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accpet-Language", required = false) Locale locale) {
        SpringBootDemoApplication springBootDemoApplication = new SpringBootDemoApplication();
        return springBootDemoApplication.messageSource().getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}

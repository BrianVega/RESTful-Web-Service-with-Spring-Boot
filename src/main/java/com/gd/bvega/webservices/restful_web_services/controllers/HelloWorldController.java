package com.gd.bvega.webservices.restful_web_services.controllers;

import com.gd.bvega.webservices.restful_web_services.beans.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello world");
    }

    // Path parameters
    // /users/{id}/todos/{id} => /users/1/todos/100
    // /hello-world/path-variable-{name}
    @GetMapping(path = "/hello-world/path-variable/{name}")
    public HelloWorldBean HelloWorldWithPathParameters(@PathVariable String name) {
        return new HelloWorldBean("Hello " + name);
    }

    @GetMapping(path = "/hello-world/internationalized")
    public String HelloWorldWithInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
    }
}

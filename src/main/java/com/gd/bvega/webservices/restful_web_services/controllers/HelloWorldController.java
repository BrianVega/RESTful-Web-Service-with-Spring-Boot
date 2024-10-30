package com.gd.bvega.webservices.restful_web_services.controllers;

import com.gd.bvega.webservices.restful_web_services.beans.HelloWorldBean;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

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

}

package com.mrn.demohelloworld.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    // URI - /helloworld
    //    // GET
    // @RequestMapping(method = RequestMethod.GET, path = "/helloworld")
    @GetMapping("/helloworld")
    public String helloWorld() {
        return "<h1>Hello World!!!!</h1>";
    }

    @GetMapping("/helloworld1")
    public UserDetails helloWorldBean() {
        return new UserDetails("Me", "You", "Zorile City");
    }
}

package com.mrn.demohelloworld.hello;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private final ResourceBundleMessageSource messageSource;

    public HelloWorldController(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

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

    @GetMapping("/hello-int")
    public String getMessagesInI18NFormat(@RequestHeader(name="Accept-Language", required = false) String locale) {
        return messageSource.getMessage("label.hello",null, new Locale(locale));
    }

    @GetMapping("/hello-int2")
    public String getMessagesInI18NFormat2() {
        return messageSource.getMessage("label.hello",null, LocaleContextHolder.getLocale());
    }
}

package io.aetherit.ats.ws.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @RequestMapping({"/"})
    public String index() {
        return "hello";
    }
}

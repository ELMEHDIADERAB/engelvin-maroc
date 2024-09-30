package com.example.engelvinmaroc.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommandeErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "denied";
    }
}

package com.example.externalapi.app;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class WebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

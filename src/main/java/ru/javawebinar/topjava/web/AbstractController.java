package ru.javawebinar.topjava.web;

import org.springframework.web.bind.annotation.GetMapping;

public abstract class AbstractController {
    @GetMapping("/")
    public String root() {
        return "index";
    }
}

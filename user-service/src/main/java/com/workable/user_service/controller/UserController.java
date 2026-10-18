package com.workable.user_service.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("/test")
    public String test() {
        return "User Service is working!";
    }
    @GetMapping("/secure")
    public String secure() {
        return "You are authenticated!";
    }
}
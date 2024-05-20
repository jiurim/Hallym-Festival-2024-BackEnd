package com.hallymfestival.HallymFestival2024BackEnd.domain.manager.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController implements ErrorController {
    @CrossOrigin(origins = "https://hallym-festival-admin.com", maxAge = 3600)
    @GetMapping({"/","/error"})
    public String index(){
        return "index.html";
    }
}
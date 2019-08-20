package com.yardox.training.controller;

import com.yardox.training.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    NewsService newsService;

    @GetMapping({"/","/hello"})
    public String hello(Model model,@RequestParam(value="name", required=false, defaultValue="World") String name){
        model.addAttribute("name",newsService.parse() );
        return "hello";
    }
}

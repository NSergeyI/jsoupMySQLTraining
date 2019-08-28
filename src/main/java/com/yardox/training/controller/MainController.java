package com.yardox.training.controller;

import com.yardox.training.service.NewsService;
import com.yardox.training.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    NewsService newsService;

    @Autowired
    ParseService parseService;

    @GetMapping("/parse")
    public String parse(){
        parseService.startParse();
        return "parse";
    }

    @GetMapping({"/","/main"})
    public String hello(Model model){
        model.addAttribute("news",newsService.getData() );
        return "main";
    }
}

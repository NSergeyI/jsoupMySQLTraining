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
    public String parse(Model model){
        parseService.startParse();
        model.addAttribute("news",newsService.getData() );
        return "main";
    }

    @GetMapping({"/","/main"})
    public String hello(Model model){
        model.addAttribute("news",newsService.getData() );
        return "main";
    }

    @GetMapping("/json")
    public String json(Model model){
        model.addAttribute("json", newsService.getJsonData());
        return "json";
    }
}

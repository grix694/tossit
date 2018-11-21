package com.rix.tossit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rix.tossit.repository.UserRepository;
import com.rix.tossit.svc.UserSvc;

@Controller
public class HomeController {
	
	@Autowired
	UserSvc userSvc;
	
	@Autowired
	UserRepository repo;
	
	
    @RequestMapping("/")
    public String index(Model model) {
    	model.addAttribute("users", repo.findAll());
        return "index";
    }
}

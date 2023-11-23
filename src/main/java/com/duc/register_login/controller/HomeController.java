package com.duc.register_login.controller;

import com.duc.register_login.entity.User;
import com.duc.register_login.repository.UserRepository;
import com.duc.register_login.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @GetMapping("/signin")
    public String login() {
        return "login";
    }

//    @GetMapping("/user/profile")
//    public String profile(Principal p, Model model) {
//        String email = p.getName();
//        User user = userRepository.findByEmail(email);
//        model.addAttribute("user", user);
//        return "profile";
//    }
//    @GetMapping("/user/home")
//    public String home() {
//        return "home";
//    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session, Model m) {

        // System.out.println(user);

        User u = userService.saveUser(user);

        if (u != null) {
            // System.out.println("save sucess");
            session.setAttribute("msg", "Register successfully");

        } else {
            // System.out.println("error in server");
            session.setAttribute("msg", "Something wrong server");
        }
        return "redirect:/register";
    }
}

package com.duc.register_login.controller;

import com.duc.register_login.entity.User;
import com.duc.register_login.exception.UserNotFoundException;
import com.duc.register_login.repository.UserRepository;
import com.duc.register_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userRepository.findByEmail(email);
            m.addAttribute("user", user);
        }

    }
    @GetMapping("/home")
    public String home() {
        return "index";
    }
    @GetMapping("/profile")
    public String profile() {
        return "admin_profile";
    }
    @GetMapping("/manage")
    public String manage(Model model) {
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "manage";
    }
    @PostMapping("/manage/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes ra) {
        Optional<User> usernew = userRepository.findById(user.getId());
        usernew.get().setName(user.getName());
        usernew.get().setName(user.getEmail());
        usernew.get().setName(user.getRole());
        userService.save(usernew.get());
        ra.addFlashAttribute("message", "The user has been saved successfully");
        return "redirect:/admin/manage";
    }
    @GetMapping("/manage/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            User user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            return "user_form";
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/manage";
        }
    }
    @GetMapping("/manage/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            userService.delete(id);
            ra.addFlashAttribute("message", "The user ID " + id + " has been deleted");
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/manage";
    }
}

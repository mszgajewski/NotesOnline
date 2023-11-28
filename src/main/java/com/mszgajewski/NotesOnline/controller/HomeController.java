package com.mszgajewski.NotesOnline.controller;

import com.mszgajewski.NotesOnline.entity.User;
import com.mszgajewski.NotesOnline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, Model model, HttpSession httpSession){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        User registered = userRepository.save(user);

        if (registered!=null){
            httpSession.setAttribute("msg", "Zarejestrowano!");
        } else {
            httpSession.setAttribute("msg", "Błąd rejestracji!");
        }
        return "redirect:/signup";
    }
}
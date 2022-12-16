package com.mszgajewski.NotesOnline.controller;

import com.mszgajewski.NotesOnline.entity.Notes;
import com.mszgajewski.NotesOnline.entity.User;
import com.mszgajewski.NotesOnline.repository.NotesRepository;
import com.mszgajewski.NotesOnline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    @GetMapping("/home")
    public String home(){
        return "user/home";
    }

    @GetMapping("/add_notes")
    public String addNotes(){
        return "user/add_notes";
    }

    @GetMapping("/view_notes/{page}")
    public String viewNotes(@PathVariable int page, Model model, Principal principal){

        String email = principal.getName();
        User user = userRepository.findByEmail(email);

        Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
        Page<Notes> notes = notesRepository.findNotesByUser(user.getId(), pageable);

        model.addAttribute("pageNo", page);
        model.addAttribute("totalPage", notes.getTotalPages());
        model.addAttribute("Notes", notes);
        model.addAttribute("totalElement", notes.getTotalElements());

        return "user/view_notes";
    }

    @GetMapping("/edit_notes/{id}")
    public String editNotes(@PathVariable int id, Model model){
        Optional<Notes> n = notesRepository.findById(id);
        if (n!= null){
            Notes notes = n.get();
            model.addAttribute("notes", notes);
        }
        return "user/edit_notes";
    }

    @GetMapping("/view_profile")
    public String viewProfile(){
        return "user/view_profile";
    }

    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        model.addAttribute("user",user);
    }

    @PostMapping("/save_notes")
    public String saveNotes(@ModelAttribute Notes notes, HttpSession httpSession, Principal principal){
        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        notes.setUser(user);

        Notes n = notesRepository.save(notes);
        if (n!=null){
            httpSession.setAttribute("msg", "Dodano notatkę");
        } else {
            httpSession.setAttribute("msg", "Błąd!");
        }
        return "redirect:/user/add_notes";
    }

    @PostMapping("/update_notes")
    public String updateNotes(@ModelAttribute Notes notes, HttpSession httpSession, Principal principal){

        String email = principal.getName();
        User user = userRepository.findByEmail(email);
        notes.setUser(user);
        Notes updateNotes = notesRepository.save(notes);

        if (updateNotes!=null){
            httpSession.setAttribute("msg", "Dodano notatkę");
        } else {
            httpSession.setAttribute("msg", "Błąd!");
        }
        return "redirect:/user/view_notes/0";
    }

    @GetMapping("/delete_notes/{id}")
    public String deleteNotes(@PathVariable int id, HttpSession httpSession){
        Optional<Notes> notes = notesRepository.findById(id);
        if (notes!=null){
            notesRepository.delete(notes.get());
            httpSession.setAttribute("msg", "Usunięto");
        }

        return "redirect:/user/view_notes/0";
    }
}

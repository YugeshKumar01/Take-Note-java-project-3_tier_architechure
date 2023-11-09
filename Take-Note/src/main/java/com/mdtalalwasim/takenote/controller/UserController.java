package com.mdtalalwasim.takenote.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mdtalalwasim.takenote.entity.Notes;
import com.mdtalalwasim.takenote.entity.User;
import com.mdtalalwasim.takenote.repository.UserRepository;
import com.mdtalalwasim.takenote.service.NotesService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user")//this will only accessible when user is login, as we mention in securityConfig class.
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NotesService notesService;
	
	
	//everytime if user is login 
	//@ModelAttribute help to access the below method to every where, for the login user 
	@ModelAttribute
	public User getUser(Principal principal, Model model) {
		String email = principal.getName();
		User user = this.userRepository.findByEmail(email);
		
		model.addAttribute("user",user); //binding to view.
		
		return user;
	}
	
	
	
	@GetMapping("/add-notes")
	public String addNotes() {
		return "add-notes";
	}
	
	@GetMapping("/view-notes")
	public String viewNotes() {
		return "view-notes";
	}
	
	@GetMapping("/edit-notes")
	public String editNotes() {
		return "edit-notes";
	}
	
	@PostMapping("/save-notes")
	public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal principal, Model model) {
		notes.setCreatedAt(LocalDate.now());
		notes.setUser(getUser(principal, model));
		
		Notes saveNotes = this.notesService.saveNotes(notes);
		
		if(saveNotes != null) {
			session.setAttribute("message", "Note Save Succefully");
		}else {
			session.setAttribute("message", "Something wrong on server");
		}
		
	
		return "redirect:/user/add-notes";
	}
	
	
	
}

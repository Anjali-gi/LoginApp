package com.userlogin.controller;

import com.userlogin.entity.User;
import com.userlogin.service.UserService;
import com.userlogin.validation.InputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private InputValidation inputValidation;


	// GET mapping for login page
	@GetMapping("/")
	public String loginPage() {
		return "login";
		// returns login.html
	}

	// POST mapping for login
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
		User user = userService.validateUser(username, password); // Validate user credentials
		if (user != null) {
			model.addAttribute("user", user); // If valid, add user to model
			return "redirect:/dashboard?id=" + user.getId(); // Redirect to dashboard page with ID
		} else {
			model.addAttribute("error", "Invalid credentials!"); // Error message for invalid login
			return "login"; // Return to login page
		}
	}

	// GET mapping for signup page
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new User());
		return "signup"; // returns signup.html
	}

	// POST mapping for signup
	@PostMapping("/signup")
	public String signup(@ModelAttribute User user, Model model) {

		boolean isValid=inputValidation.validateUser(user);



		userService.saveUser(user); // Save the user in the database
		model.addAttribute("message", "User registered successfully!"); // Success message
		return "login"; // Redirect to login page
	}





	//private String url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjav_8SkShQnVJXRKQVgAGxScQ1ptL6UREzA&s";
	//private String url="/images/john.jpg";


	// GET mapping for dashboard page
	@GetMapping("/dashboard")
	public String dashboard(@RequestParam(required = false) Long id, Model model) throws InterruptedException {
		// Check if ID is missing or invalid
		if (id == null) {
			model.addAttribute("error", "Invalid user ID!");
			return "login"; // Redirect to login page if ID is invalid
		}

		User user = userService.findById(id).orElse(null); // Get user by ID
		if (user != null) {
			model.addAttribute("user", user);


			 String url="/images/"+user.getUsername()+".jpg";

			model.addAttribute("image",url);


			Thread.sleep(2000);



			return "dashboard"; // Return to dashboard.html
		} else {
			model.addAttribute("error", "User not found!"); // Error if user not found
			return "login"; // Redirect to login page
		}
	}

	// POST mapping for updating user info (including profile image upload)
	@PostMapping("/update")
	public String updateUser(
			@ModelAttribute User user,
//			@RequestParam("profileImage") MultipartFile file,
			Model model) {
		User existingUser = userService.findById(user.getId()).orElse(null);

		if (existingUser != null) {
			try {
				// Update editable fields
				existingUser.setFirstName(user.getFirstName());
				existingUser.setLastName(user.getLastName());
				existingUser.setEmail(user.getEmail());
				existingUser.setPassword(user.getPassword());
				existingUser.setGender(user.getGender());
				existingUser.setCountry(user.getCountry());
				existingUser.setAddress(user.getAddress());
				existingUser.setPhoneNumber(user.getPhoneNumber());
				existingUser.setDob(user.getDob()  );



				// Save updated user
				userService.saveUser(existingUser);
				model.addAttribute("message", "User updated successfully!");


			} catch (Exception e) {
				model.addAttribute("error", "Error while uploading the profile image: " + e.getMessage());
			}
		} else {
			model.addAttribute("error", "User not found!");
		}
		return "dashboard"; // Return to dashboard.html
	}
}

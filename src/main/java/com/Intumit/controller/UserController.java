package com.Intumit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Intumit.model.User;
import com.Intumit.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService service;

	@GetMapping("login")
	public String login(Model model) {
		model.addAttribute("user", new User());

		return "login";
	}

	@PostMapping("/login")
	public String login(User IUser, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (service.hasUser(IUser.getName())) {
			if (service.isPasswordCorrect(IUser.getName(), IUser.getPassword())) {
				User user = service.getUserByName(IUser.getName());
				HttpSession session = request.getSession();
				session.setAttribute("user_id", user.getUser_id());
				session.setAttribute("name", user.getName());
				session.setAttribute("isAdministrator", user.getIsAdministrator());
				
				redirectAttributes.addFlashAttribute("message", "登入成功");
				return "redirect:/viewNotice";
			}
			redirectAttributes.addFlashAttribute("message", "密碼錯誤");
			return "redirect:/login";
		}
		redirectAttributes.addFlashAttribute("message", "使用者不存在");
		return "redirect:/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "成功登出");
        return "redirect:/viewNotice";
	}

	@GetMapping("/register")
	public String register(Model model) {
		User user = new User();
		user.setIsAdministrator(false);
		model.addAttribute("user", user);

		return "register";
	}

	@PostMapping("/register")
	public String register(User user, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (service.hasUser(user.getName())) {
			redirectAttributes.addFlashAttribute("message", "使用者名稱已被註冊");
			return "redirect:/register";
		}
		user.setIsAdministrator(true);
		if (service.saveUser(user)) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", user.getUser_id());
			session.setAttribute("name", user.getName());
			session.setAttribute("isAdministrator", user.getIsAdministrator());
			redirectAttributes.addFlashAttribute("message", "註冊成功");
			return "redirect:/viewNotice";
		}
		redirectAttributes.addFlashAttribute("message", "註冊失敗");
		return "redirect:/register";
	}
}

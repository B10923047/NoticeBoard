package com.Intumit.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Intumit.model.Notice;
import com.Intumit.model.User;
import com.Intumit.service.NoticeService;
import com.Intumit.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class NoticeController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private UserService userService;

	@GetMapping({"/", "/viewNotice"})
	public String viewNotice(Model model, HttpServletRequest request) {
		model.addAttribute("noticeList", noticeService.getAllNotice());
		model.addAttribute("userList", userService.getAllUser());
		return "viewNotice";
	}

	@GetMapping("/notice/{id}")
	public String notice(@PathVariable Long id, Model model) {
		Notice notice = noticeService.getNoticeById(id);
		User user = userService.getUserById(notice.getUser_id());
		model.addAttribute("name", user.getName());
		model.addAttribute("notice", noticeService.getNoticeById(id));
		return "notice";
	}

	@GetMapping("/addNotice")
	public String addNotice(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("name") != null) {
			LocalDate today = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			String todayAsString = today.format(formatter);

			Long user_id = (Long) session.getAttribute("user_id");

			Notice notice = new Notice();
			notice.setRelease_date(todayAsString);
			notice.setUser_id(user_id);

			model.addAttribute("notice", notice);

			return "addNotice";
		}
		return "redirect:/login";
	}

	@PostMapping("/addNotice")
	public String addNotice(Notice notice, User user, RedirectAttributes redirectAttributes) {
		notice.setUser_id(user.getUser_id());
		if (noticeService.saveOrUpdateNotice(notice)) {
			redirectAttributes.addFlashAttribute("message", "成功發佈");
			return "redirect:/viewNotice";
		}

		redirectAttributes.addFlashAttribute("message", "發佈失敗");
		return "redirect:/addNotice";
	}

	@GetMapping("/editNotice/{id}")
	public String editNotice(@PathVariable Long id, Model model) {
		model.addAttribute("notice", noticeService.getNoticeById(id));
		return "editNotice";
	}

	@PostMapping("/editNotice")
	public String editNotice(Notice notice, RedirectAttributes redirectAttributes) {
		if (noticeService.saveOrUpdateNotice(notice)) {
			redirectAttributes.addFlashAttribute("message", "編輯成功");
			return "redirect:/viewNotice";
		}

		redirectAttributes.addFlashAttribute("message", "編輯失敗");
		return "redirect:/editNotice	/" + notice.getNotice_id();
	}

	@GetMapping("/deleteNotice/{id}")
	public String deleteNotice(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		if (noticeService.deleteNotice(id)) {
			redirectAttributes.addFlashAttribute("message", "刪除成功");
			return "redirect:/viewNotice";
		}

		redirectAttributes.addFlashAttribute("message", "刪除失敗");
		return "redirect:/viewNotice";
	}
}

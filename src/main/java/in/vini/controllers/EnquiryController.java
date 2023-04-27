package in.vini.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.vini.binding.DashboardResponse;
import in.vini.binding.EnquiryForm;
import in.vini.services.EnquiryServiceImpl;
import in.vini.services.UserServiceImpl;

@Controller
public class EnquiryController {
	
	@Autowired
	private EnquiryServiceImpl enqService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		
		Integer user = (Integer)session.getAttribute("userId");
		
		DashboardResponse response = enqService.getDashboardData(user);
		
		model.addAttribute("dashboardData" ,response);
		
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		
		List<String> courseNames = enqService.getCourseNames();
		
		List<String> enqStatus = enqService.getEnqStatus();
		
		EnquiryForm form = new EnquiryForm();
		
		
		model.addAttribute("courseName",courseNames);
		model.addAttribute("statusNames",enqStatus);
		model.addAttribute("form",form);
		
		return "add-enquiry";
	}
	
	
	
	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("form") EnquiryForm form,Model model) {
		
		 boolean status = enqService.upsertEnquiry(form);
		
		if(status) {
			model.addAttribute("msg","data added succesfully");
		}else {
			model.addAttribute("errMsg","failed to add data");
		}
		
		return "add-enquiry";
	}

	
	
	@GetMapping("/enquiries")
	public String viewEnquiryPage() {
		return "view-enquiry";
	}
}


















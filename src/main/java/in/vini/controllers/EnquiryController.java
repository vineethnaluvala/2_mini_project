package in.vini.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.vini.binding.DashboardResponse;
import in.vini.binding.EnquiryForm;
import in.vini.binding.EnquirySearchCriteria;
import in.vini.entity.StudentEnquiriesEntity;
import in.vini.repository.StudentEnquiriesRepository;
import in.vini.services.EnquiryServiceImpl;
import in.vini.services.UserServiceImpl;

@Controller
public class EnquiryController {

	
	@Autowired
	private EnquiryServiceImpl enqService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private StudentEnquiriesRepository enqRepo;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {

		Integer user = (Integer) session.getAttribute("userId");

		DashboardResponse response = enqService.getDashboardData(user);

		model.addAttribute("dashboardData", response);

		return "dashboard";
	}

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		initForm(model);
		return "add-enquiry";
	}

	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("form") EnquiryForm form, Model model) {

		boolean status = enqService.upsertEnquiry(form);

		if (status) {
			model.addAttribute("msg", "data added succesfully");
		} else {
			model.addAttribute("errMsg", "failed to add data");
		}

		return "add-enquiry";
	}

	private void initForm(Model model) {
		List<String> courseNames = enqService.getCourseNames();

		List<String> enqStatus = enqService.getEnqStatus();

		EnquiryForm form = new EnquiryForm();

		model.addAttribute("courseName", courseNames);
		model.addAttribute("statusNames", enqStatus);
		model.addAttribute("form", form);

	}

	@GetMapping("/enquiries")
	public String viewEnquiryPage(Model model) {
		initForm(model);

		// model.addAttribute("searchForm", new EnquirySearchCriteria());

		List<StudentEnquiriesEntity> enquiries = enqService.getEnquiries();

		model.addAttribute("enquiries", enquiries);

		return "view-enquiry";
	}

	@GetMapping("/filter-enquiries")
	public String filterEnquiry(@RequestParam String cname, @RequestParam String mode, @RequestParam String status,
			Model model) {

		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourse(cname);
		criteria.setClassMode(mode);
		criteria.setEnqStatus(status);

		Integer user = (Integer) session.getAttribute("userId");
		List<StudentEnquiriesEntity> filteredEnquiries = enqService.getFilteredEnquiries(criteria, user);

		model.addAttribute("enquiries", filteredEnquiries);
		return "filterd-enquiries";

	}

	@GetMapping("/enqu")
	public String enquiry(Model model)
	{
		initForm(model);
		EnquiryForm enqForm=new EnquiryForm();
		if(session.getAttribute("enq")!=null)
		{
		   StudentEnquiriesEntity enq = (StudentEnquiriesEntity) session.getAttribute("enq");
		   BeanUtils.copyProperties(enq,enqForm);
		   session.removeAttribute("enq");
		   
		}
		model.addAttribute("form", enqForm);
		return "add-enquiry";
	}

	@GetMapping("/edit/{id}")
	public String editEnquiry(@PathVariable("id") Integer id) {
		 StudentEnquiriesEntity enq= enqService.getEnq(id);
		session.setAttribute("enq", enq);
		session.setAttribute("enqid", id);
		return "redirect:/enqu";
	}

}

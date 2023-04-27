package in.vini.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vini.binding.DashboardResponse;
import in.vini.binding.EnquiryForm;
import in.vini.binding.EnquirySearchCriteria;
import in.vini.entity.CourseEntity;
import in.vini.entity.EnquiryStatusEnquiry;
import in.vini.entity.StudentEnquiriesEntity;
import in.vini.entity.UserDetailsEntity;
import in.vini.repository.CoursesRepository;
import in.vini.repository.EnquiryStatusRepository;
import in.vini.repository.StudentEnquiriesRepository;
import in.vini.repository.UserDetailsRepository;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDetailsRepository userRepo;
	@Autowired
	private CoursesRepository courseRepo;
	@Autowired
	private EnquiryStatusRepository enquiryRepo;
	@Autowired
	private StudentEnquiriesRepository studentRepo;
	
	@Autowired
	private HttpSession session;
	
	public List<String> getCourseNames() {
		
		List<CourseEntity> findAll = courseRepo.findAll();
		
		List<String> names = new ArrayList<>();
		
		for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		
		return names;
	}

	
	public List<String> getEnqStatus() {
		
		List<EnquiryStatusEnquiry> findAll = enquiryRepo.findAll();
		
		List<String> status = new ArrayList<>();
		
		for(EnquiryStatusEnquiry entity : findAll) {
			status.add(entity.getStatusName());
		}
		
		return status;
	}

	public boolean upsertEnquiry(EnquiryForm form) {

		StudentEnquiriesEntity entity = new StudentEnquiriesEntity();
		
		BeanUtils.copyProperties(form, entity);
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		UserDetailsEntity userEntity = userRepo.findById(userId).get();
		
		entity.setUser(userEntity);
		
		studentRepo.save(entity);
		
		return true;
	}
	

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();
		
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetailsEntity entity = findById.get();
			List<StudentEnquiriesEntity> enquiries = entity.getEnquiries();
			
			Integer totalSize = enquiries.size();

			Integer enroll = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lost = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();
			
			response.setTotalEnquires(totalSize);
			response.setEnrolled(enroll);
			response.setLost(lost);
			

		}

		return response;
	}


	@Override
	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enquiryId) {
		// TODO Auto-generated method stub
		return null;
	}

}














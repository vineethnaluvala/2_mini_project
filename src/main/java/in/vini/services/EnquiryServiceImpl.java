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

		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}

		return names;
	}

	public List<String> getEnqStatus() {

		List<EnquiryStatusEnquiry> findAll = enquiryRepo.findAll();

		List<String> status = new ArrayList<>();

		for (EnquiryStatusEnquiry entity : findAll) {
			status.add(entity.getStatusName());
		}

		return status;
	}

	public boolean upsertEnquiry(EnquiryForm form) {

		StudentEnquiriesEntity entity = new StudentEnquiriesEntity();

		BeanUtils.copyProperties(form, entity);

		Integer userId = (Integer) session.getAttribute("userId");

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

			Integer lost = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost")).collect(Collectors.toList())
					.size();

			response.setTotalEnquires(totalSize);
			response.setEnrolled(enroll);
			response.setLost(lost);

		}

		return response;
	}

	@Override
	public List<StudentEnquiriesEntity> getEnquiries() {

		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			List<StudentEnquiriesEntity> enquiries = userDetailsEntity.getEnquiries();

			return enquiries;
		}

		return null;

	}

	@Override
	public List<StudentEnquiriesEntity> getFilteredEnquiries(EnquirySearchCriteria criteria, Integer userId) {

		Optional<UserDetailsEntity> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetailsEntity userDetailsEntity = findById.get();
			List<StudentEnquiriesEntity> enquiries = userDetailsEntity.getEnquiries();

			if (null != criteria.getCourse() & !"".equals(criteria.getCourse())) {

				enquiries = enquiries.stream().filter(e -> e.getCourseName().equals(criteria.getCourse()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getEnqStatus() & !"".equals(criteria.getEnqStatus())) {

				enquiries = enquiries.stream().filter(e -> e.getEnqStatus().equals(criteria.getEnqStatus()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getClassMode() & !"".equals(criteria.getClassMode())) {

				enquiries = enquiries.stream().filter(e -> e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
			}

			return enquiries;
		}

		return null;
	}

	public StudentEnquiriesEntity getEnq(Integer enqId)
	{
		Optional<StudentEnquiriesEntity> enq = studentRepo.findById(enqId);
	     return enq.get();
	}

	
	@Override
	public String updateEnq(Integer enqid, EnquiryForm formObj) {
		
		Optional<StudentEnquiriesEntity> enq = studentRepo.findById(enqid);
		if(enq.isPresent())
		{
			StudentEnquiriesEntity enqEntity = enq.get();
			enqEntity.setStudentName(formObj.getStudentName());
			enqEntity.setStudentPhno(formObj.getStudentPhno());
			enqEntity.setClassMode(formObj.getClassMode());
			enqEntity.setCourseName(formObj.getCourseName());
			enqEntity.setEnqStatus(formObj.getEnqStatus());
			
			studentRepo.save(enqEntity);
			return "Updated";
		}
		
		return "Failed";
	}

}

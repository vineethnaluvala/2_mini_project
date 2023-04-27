package in.vini.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.vini.entity.CourseEntity;
import in.vini.entity.EnquiryStatusEnquiry;
import in.vini.repository.CoursesRepository;
import in.vini.repository.EnquiryStatusRepository;

@Component
public class DataLoader  implements ApplicationRunner{

	@Autowired
	private CoursesRepository courseRepo;
	
	@Autowired
	private EnquiryStatusRepository enqRepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {

		courseRepo.deleteAll();
		
		CourseEntity c = new CourseEntity();
		c.setCourseName("Java");
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Python");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("AWS");
		
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("C++");
		
		List<CourseEntity> asList = Arrays.asList(c,c1,c2,c3);
		
		courseRepo.saveAll(asList);
		
		enqRepo.deleteAll();
		
		EnquiryStatusEnquiry e = new EnquiryStatusEnquiry();
		e.setStatusName("New");
		
		EnquiryStatusEnquiry e1 = new EnquiryStatusEnquiry();
		e1.setStatusName("Enrolled");
		
		EnquiryStatusEnquiry e2 = new EnquiryStatusEnquiry();
		e2.setStatusName("Lost");
		
		List<EnquiryStatusEnquiry> asList2 = Arrays.asList(e,e1,e2);
		
		enqRepo.saveAll(asList2);
	}

}














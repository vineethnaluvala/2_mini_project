package in.vini.services;

import java.util.List;

import org.springframework.stereotype.Service;

import in.vini.binding.DashboardResponse;
import in.vini.binding.EnquiryForm;
import in.vini.binding.EnquirySearchCriteria;
import in.vini.entity.StudentEnquiriesEntity;

@Service
public interface EnquiryService {

	public List<String> getCourseNames();

	public List<String> getEnqStatus();

	public DashboardResponse getDashboardData(Integer userId);

	public boolean upsertEnquiry(EnquiryForm form);

	public List<StudentEnquiriesEntity> getEnquiries();


	public List<StudentEnquiriesEntity> getFilteredEnquiries(EnquirySearchCriteria criteria,Integer userId);
	
	public StudentEnquiriesEntity getEnq(Integer enqId);
	
	public String updateEnq(Integer enqid, EnquiryForm formObj);
	
	
}

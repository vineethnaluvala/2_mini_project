package in.vini.services;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import in.vini.binding.DashboardResponse;
import in.vini.binding.EnquiryForm;
import in.vini.binding.EnquirySearchCriteria;

@Service
public interface EnquiryService {

	public List<String> getCourseNames();

	public List<String> getEnqStatus();

	public DashboardResponse getDashboardData(Integer userId);

	public String upsertEnquiry(EnquiryForm form);

	public List<EnquiryForm> getEnquiries(Integer userId, EnquirySearchCriteria criteria);

	public EnquiryForm getEnquiry(Integer enquiryId);

}

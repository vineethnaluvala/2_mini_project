package in.vini.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EnquiryStatusEnquiry {

	@Id
	private Integer statusId;
	private String statusName;

}

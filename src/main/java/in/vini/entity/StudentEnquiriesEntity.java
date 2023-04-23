
package in.vini.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Data
public class StudentEnquiriesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer enquiryId;
	private String studentName;
	private Integer phoneNumber;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	@CreatedDate
	private LocalDate createdDate;
	@UpdateTimestamp
	private LocalDate updatedDate;
	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "userId") private Integer userId;
	 */
	
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "userId")
	 */
	@ManyToOne(targetEntity = UserDetailsEntity.class)
    private Integer user;


}





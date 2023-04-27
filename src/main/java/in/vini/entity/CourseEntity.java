package in.vini.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CourseEntity {
	@Id
	@GeneratedValue
	private Integer courseId;
	private String courseName;

}

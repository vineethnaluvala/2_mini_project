package in.vini.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class CourseEntity {
	@Id
	private Integer courseId;
	private String courseName;

}

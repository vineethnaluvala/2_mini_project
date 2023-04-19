package in.vini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vini.entity.CourseEntity;

public interface CoursesRepository extends JpaRepository<CourseEntity, Integer>{

}

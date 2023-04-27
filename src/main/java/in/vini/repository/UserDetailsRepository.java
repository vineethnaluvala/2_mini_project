package in.vini.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.vini.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer>{

	public UserDetailsEntity findByEmail(String email);
	
	public UserDetailsEntity findByEmailAndPassword(String email, String password);
}

package com.sl.lms.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sl.lms.domain.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
	 @Query("SELECT CONCAT(u.name, ' ', u.lastName) FROM User u WHERE u.email = ?1")
	 Optional<String> getUserFullName(String emailId);
}

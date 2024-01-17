package com.school.sba.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.sba.entity.User;
import com.school.sba.enumuration.UserRole;

public interface UserRepositary extends JpaRepository<User, Integer>{

	boolean existsByUserRole(UserRole role);
}
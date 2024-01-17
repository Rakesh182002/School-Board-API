package com.school.sba.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.school.sba.entity.User;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.util.ResponseStructure;

public interface UserService {

	ResponseEntity<ResponseStructure<UserResponse>> saveUser( UserRequest userRequest);

	ResponseEntity<ResponseStructure<String>> deleteUserById(int id);

	ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId);
}
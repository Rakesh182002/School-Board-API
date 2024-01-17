package com.school.sba.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.sba.entity.User;
import com.school.sba.enumuration.UserRole;
import com.school.sba.exception.DuplicateEntryException;
import com.school.sba.exception.InvalidUserException;
import com.school.sba.exception.UserAlreadyDeletedException;
import com.school.sba.exception.UserNotFoundException;
import com.school.sba.repositary.UserRepositary;
import com.school.sba.requestdto.UserRequest;
import com.school.sba.responsedto.UserResponse;
import com.school.sba.service.UserService;
import com.school.sba.util.ResponseStructure;

@Service
public class UserServiceImpl implements UserService{
	static boolean admin=false;
	@Autowired
	private UserRepositary userRepo;
	@Autowired
	private ResponseStructure<UserResponse> responceStructure;
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userRequest) {
		if(userRequest.getUserRole()==UserRole.ADMIN)
		{
			if(admin==false)
			{
				admin=true;
				if(userRepo.existsByUserRole(userRequest.getUserRole())==false)
				{
					try {
						User user = userRepo.save(mapToUser(userRequest));
						 responceStructure.setData(mapToUserResponce(user));
						 responceStructure.setMessage("User Saved Successfully");
						 responceStructure.setStatus(HttpStatus.CREATED.value());
					} catch (Exception e) {
						e.printStackTrace();
						throw new DuplicateEntryException("Change UserName And Email and try again");
					}
				}
				else
				{
					throw new InvalidUserException("Admin can be only one Person");
				}
					
			}
			else
			{
				throw new InvalidUserException("Admin can be only one Person");
			}
		}
		else {
				try {
					User user = userRepo.save(mapToUser(userRequest));
					 responceStructure.setData(mapToUserResponce(user));
					 responceStructure.setMessage("User Saved Successfully");
					 responceStructure.setStatus(HttpStatus.CREATED.value());
				} catch (Exception e) {
					throw new DuplicateEntryException("Change UserName And Email and try again");
				}	
		}
		return new ResponseEntity<ResponseStructure<UserResponse>>(responceStructure,HttpStatus.CREATED);
	}
	private UserResponse mapToUserResponce(User user) {
		return  UserResponse.
				builder().
				userId(user.getUserId()).
				userName(user.getUserName()).
				userFirstName(user.getUserFirstName()).
				userLastName(user.getUserLastName()).
				userEmail(user.getUserEmail()).
				userRole(user.getUserRole()).
				userContactNo(user.getUserContactNo()).build();
		
		 
	}
	private User mapToUser(UserRequest userRequest) {
		return User.builder().
				userName(userRequest.getUserName()).
				userFirstName(userRequest.getUserFirstName()).
				userLastName(userRequest.getUserLastName())
				.userPassword(userRequest.getUserPassword()).
				userContactNo(userRequest.getUserContactNo())
				.userEmail(userRequest.getUserEmail())
				.userRole(userRequest.getUserRole())
				.userIsDeleted(false)
				.build();
	}
	private User getUserById(int id)
	{
		User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("USER NOT FOUND"));
		if(user.isUserIsDeleted()==true)
			throw new UserAlreadyDeletedException("USER ALREADY DELETED");
		else
		return user;
		
	}
	public User updateUser(User user)
	{
		return user;
		
	}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> deleteUserById(int id) {
		User user = getUserById(id);
//		userRepo.delete(user);
		user.setUserIsDeleted(true);
		userRepo.save(user);
		 ResponseStructure<String> responceStructure = new ResponseStructure<>();
		 responceStructure.setStatus(HttpStatus.OK.value());
		 responceStructure.setMessage("Deleted successfully");
		 responceStructure.setData("USER IS DELETED");
		return new ResponseEntity<ResponseStructure<String>>(responceStructure,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUserById(int userId) {
		User user = getUserById(userId);
		 responceStructure.setData(mapToUserResponce(user));
		 responceStructure.setMessage("User Fetched Successfully");
		 responceStructure.setStatus(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<UserResponse>>(responceStructure,HttpStatus.OK);
	}

}

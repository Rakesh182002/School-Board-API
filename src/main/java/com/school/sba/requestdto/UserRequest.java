package com.school.sba.requestdto;

import org.springframework.stereotype.Component;

import com.school.sba.enumuration.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserRequest {
	

	@NotBlank(message = "USERNAME IS MANDATORY")
	private String userName;

	 @Size(min = 8,max = 20,message = "pasword must be 8 to 20 character")
	 @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
	            message = "Password must have 1 uppercase, 1 lowercase, 1 number, 1 special character, and be at least 8 characters long")
	private String userPassword;
	 @NotBlank(message = "FirstName is mandatory")
	private String userFirstName;
	private String userLastName;
	private long userContactNo;
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	@NotBlank(message = "emaail canot be blank")
	private String userEmail;
	@NotNull(message = "User Role is mandatory")
	private UserRole userRole;

}

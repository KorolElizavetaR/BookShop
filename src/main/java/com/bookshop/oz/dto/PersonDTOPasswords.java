package com.bookshop.oz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import com.bookshop.oz.model.LocationPoint;
import com.bookshop.oz.validator.annotation.PasswordMatches;
import com.bookshop.oz.validator.annotation.UniqueEmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@Accessors(chain = true)
public class PersonDTOPasswords {
	@NotNull(message = "Пароль не может быть пустым")
	@NotEmpty
	private String password;
	private String confirmPassword;
}

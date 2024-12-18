package com.bookshop.oz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import com.bookshop.oz.validator.annotation.PasswordMatches;
import com.bookshop.oz.validator.annotation.UniqueEmail;
import com.bookshop.oz.validator.annotation.UniquePhone;

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
public class PersonDTORegister {
	@NotNull(message = "Имя не должно быть пустым.")
	@NotEmpty(message = "Имя не должно быть пустым.")
	@Size(max = 50, message = "Имя не должно быть не больше 50 символов.")
	private String firstName;

	@Size(max = 50, message = "Фамилия должна быть не больше 50 символов.")
	private String lastName;

	@Email(message = "Некорректный email.")
	@NotNull(message = "Email не может быть пустым.")
	@Size(max = 100, message = "Email не может быть больше 100 символов.")
	@UniqueEmail
	private String email;

	@Pattern(regexp = "^$|\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}$", message = "Номер телефона должен быть в формате +375(XX)XXX-XX-XX.")
	@UniquePhone
	private String phone;

	@NotNull(message = "Пароль не может быть пустым")
	@NotEmpty
	private String password;

	private String confirmPassword;
}

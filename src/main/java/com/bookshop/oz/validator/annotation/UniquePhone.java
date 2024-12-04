package com.bookshop.oz.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bookshop.oz.validator.UniqueEmailValidator;
import com.bookshop.oz.validator.UniquePhoneValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniquePhoneValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhone {
	String message() default "Такой номер телефона уже используется в системе";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

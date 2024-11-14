package com.bookshop.oz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.bookshop.oz.model.LocationPoint;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
	private Integer personId;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String bpassword;
	private LocationPoint locationPoint;
}

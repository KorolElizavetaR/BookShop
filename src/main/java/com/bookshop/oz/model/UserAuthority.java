package com.bookshop.oz.model;

import com.bookshop.oz.model.enumeration.Authority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_authorities")
public class UserAuthority {
	@Id
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
	@NotNull(message = "Person ID cannot be null")
	@ToString.Exclude
	private Person person;

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "user_authority", nullable = false)
	@NotNull(message = "User authority cannot be null")
	private Authority userAuthority;
}

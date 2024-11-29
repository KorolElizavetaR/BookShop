package com.bookshop.oz.model;

import java.io.Serializable;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import com.bookshop.oz.model.enumeration.Authority;
import com.bookshop.oz.model.pk.UserAuthorityId;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_authorities")
@IdClass(UserAuthorityId.class)
public class UserAuthority {
	@Id
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
	@NotNull(message = "Person ID cannot be null")
	@ToString.Exclude
	private Person person;

	@Id
//	@Enumerated(EnumType.STRING)
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name = "user_authority", nullable = false)
	@NotNull(message = "User authority cannot be null")
	private Authority userAuthority;
}

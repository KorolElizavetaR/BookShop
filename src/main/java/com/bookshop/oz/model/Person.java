package com.bookshop.oz.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer personId;

    @NotNull(message = "Имя не должно быть пустым.")
    @Size(max = 50, message = "Имя не должно быть не больше 50 символов.")
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Size(max = 50, message = "Фамилия должна быть не больше 50 символов.")
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email(message = "Некорректный email.")
    @NotNull(message = "Email не может быть пустым.")
    @Size(max = 100, message = "Email не может быть больше 100 символов.")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}", message = "Номер телефона должен быть в формате +375(XX)XXX-XX-XX.")
    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    @NotNull(message = "Пароль не может быть пустым")
    @Column(name = "bpassword", nullable = false)
    private String bpassword;

    @ManyToOne
    @JoinColumn(name = "current_location", referencedColumnName = "location_id", insertable = false, updatable = false)
    private LocationPoint locationPoint;
    
    @OneToMany (mappedBy = "person")
    private List <UserAuthority> autorities;
}
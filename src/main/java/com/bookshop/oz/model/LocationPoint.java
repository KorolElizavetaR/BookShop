package com.bookshop.oz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location_points")
@Accessors(chain = true)
public class LocationPoint {
	@Id
    @Column(name = "location_id", length = 5)
    @Pattern(regexp = "\\d{5}", message = "ID локации обязано быть пятизначным номером.")
    private String locationId;

    @NotNull(message = "City cannot be null")
    @Size(max = 20, message = "City name must be 20 characters or less")
    @Column(name = "city", length = 20, nullable = false)
    @Pattern (regexp = "[А-ЯЁ][-А-яЁё]+", message = "Название города должно начинаться с большой буквы и не содержать в себе цифр.")
    private String city;

    @NotNull(message = "Адрес не может быть пустым.")
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "is_storage", nullable = false)
    private Boolean isStorage = false;
}

package com.p_school.entity;

import java.time.LocalDateTime;

import com.p_school.enums.InstituteStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "institutes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Institute {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "institute_id", nullable = false, updatable = false)
	private int instituteId;

	@Column(name = "institute_name", nullable = false, length = 150)
	private String instituteName;

	@Column(name = "email", nullable = false, unique = true, length = 150)
	private String email;

	@Column(name = "phone_number", nullable = false, length = 15)
	private String phoneNumber;

	@Column(name = "address", nullable = false, length = 500)
	private String address;

	@Column(name = "city", nullable = false, length = 100)
	private String city;

	@Column(name = "state", nullable = false, length = 100)
	private String state;

	@Column(name = "country", nullable = false, length = 100)
	private String country;

	@Column(name = "postal_code", nullable = false, length = 10)
	private String postalCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private InstituteStatus status;

	@Column(name = "is_deleted", nullable = false)
	private Boolean deleted = false;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	private User createdBy;
}
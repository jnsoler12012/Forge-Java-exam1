package com.nicolas.exam.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NonNull
	@NotBlank
	private String firstName;
	@NonNull
	@NotBlank
	private String lastName;
	@Email(message = "Email must be valid")
	private String email;
	@Size(min = 8, max = 255)
	private String password;
	@Transient
	private String passwordConfirmation;
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private List<Role> roles;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Package> packages = new ArrayList<Package>();

	

	@PrePersist
	public void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = new Date();
	}

}

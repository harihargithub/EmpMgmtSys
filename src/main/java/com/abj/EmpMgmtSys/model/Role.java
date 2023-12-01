package com.abj.EmpMgmtSys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@EqualsAndHashCode(exclude = "users")
@NoArgsConstructor

public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String role;

	public Role(String role) {
		this.role = role;
	}

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "role_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
	private Set<User> users = new HashSet<>();

}

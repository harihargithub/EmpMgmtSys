package com.abj.EmpMgmtSys.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String username;
	private String password;

	@ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void addRole(Role role) {
		this.roles.add(role);
		role.getUsers().add(this);
	}
}
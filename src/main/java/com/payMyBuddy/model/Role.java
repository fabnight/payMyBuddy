package com.payMyBuddy.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role", schema = "public")
public class Role implements Serializable {
	 
		private static final long serialVersionUID = 1L;
	 
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
		private Integer id;
	 
		@Column(name = "role_name", length = 65)
		private String roleName;
	 
		public Role() {
	 
		}
	 
		public Role(Integer valueOf, AppUser user) {
			// TODO Auto-generated constructor stub
		}

		public Integer getId() {
			return id;
		}
	 
		public void setId(Integer id) {
			this.id = id;
		}
	 
		public String getRoleName() {
			return roleName;
		}
	 
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
	 
	}

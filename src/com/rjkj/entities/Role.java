package com.rjkj.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.rjkj.entities.IdEntity;
import com.google.common.collect.Lists;
@Entity
@Table(name="sys_role")
public class Role extends IdEntity {
	
	/** 鎻忚堪  */
	private static final long serialVersionUID = -5537665695891354775L;
	

	@Column(nullable=false, length=128)
	private String roleName;
	
	@Column(length=128)
	private String roleCode;
	
	@Column(length=256)
	private String roleRemark;
	
	@Column(nullable=true, length=4)
	private String status;
	
	@Column(nullable=true, length=512)
	private String wkflows;
	
	@OneToMany(mappedBy="role", cascade={CascadeType.REMOVE}, orphanRemoval=true)
	private List<UserRole> userRoles = new ArrayList<UserRole>(0);
	
	@OneToMany(mappedBy="role", cascade={CascadeType.REMOVE}, orphanRemoval=true)
	private List<RoleModule> roleModule = Lists.newArrayList();

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleRemark() {
		return roleRemark;
	}

	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWkflows() {
		return wkflows;
	}

	public void setWkflows(String wkflows) {
		this.wkflows = wkflows;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<RoleModule> getRoleModule() {
		return roleModule;
	}

	public void setRoleModule(List<RoleModule> roleModule) {
		this.roleModule = roleModule;
	}
	
	
	
}

package com.rjkj.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.validation.constraints.NotNull;


import com.google.common.collect.Lists;
import com.rjkj.entities.IdEntity;

@Entity
@Table(name="sys_module")
public class Module extends IdEntity  {

	
	private static final long serialVersionUID = -6926690440815291509L;
	
	public final static String PERMISSION_CREATE = "save";
	
	public final static String PERMISSION_READ = "view";
	
	public final static String PERMISSION_UPDATE = "edit";
	
	public final static String PERMISSION_DELETE = "delete";
	
	public final static String PERMISSION_BATCHDEL = "batchdel";
	
	public final static String PERMISSION_SEARCH = "search";
	

	
	@Column
	private Integer orderNum;
	
	@Column(nullable=false, length=256)
	private String modName;
	
	@Column(length=128)
	private String sn;
	
	@Column(length=128)
	private String permission;
	
	@Column(length=512)
	private String url;
	
	@Column(length=512)
	private String modBrief;
	
	@Column(nullable=false, length=4)
	private String nodeType;
	
	@Column(nullable=true, length=4)
	private String status;

	@ManyToOne
	@JoinColumn(name="parentId")
	private Module parent;
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<Module> children = Lists.newArrayList();

	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="module")
	private List<RoleModule> roleModule = Lists.newArrayList();

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModBrief() {
		return modBrief;
	}

	public void setModBrief(String modBrief) {
		this.modBrief = modBrief;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public List<Module> getChildren() {
		return children;
	}

	public void setChildren(List<Module> children) {
		this.children = children;
	}

	public List<RoleModule> getRoleModule() {
		return roleModule;
	}

	public void setRoleModule(List<RoleModule> roleModule) {
		this.roleModule = roleModule;
	}
	
	
	
	
}

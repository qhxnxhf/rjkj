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

import com.google.common.collect.Lists;


@Entity
@Table(name = "sys_organize")
public class Organize extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false, length=256)
	private String orgName;
	
	@Column(length=64)
	private String orgCode;
	
	@Column(length=2)
	private Integer orderNum;
	
	@Column(length=255)
	private String orgIcon;
	
	@Column(length=512)
	private String orgBrief;
	
	@Column(nullable=false, length=4)
	private String nodeType;
	
	@Column(nullable=true, length=4)
	private String status;
	
	@Column(nullable=true, length=128)
	private String inheritNb;
	

	@ManyToOne
	@JoinColumn(name="parentId")
	private Organize parent;
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<Organize> children = Lists.newArrayList();
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="org")
	private List<User> users = Lists.newArrayList();
	
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrgIcon() {
		return orgIcon;
	}

	public void setOrgIcon(String orgIcon) {
		this.orgIcon = orgIcon;
	}

	public String getOrgBrief() {
		return orgBrief;
	}

	public void setOrgBrief(String orgBrief) {
		this.orgBrief = orgBrief;
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

	public Organize getParent() {
		return parent;
	}

	public void setParent(Organize parent) {
		this.parent = parent;
	}

	public List<Organize> getChildren() {
		return children;
	}

	public void setChildren(List<Organize> children) {
		this.children = children;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


	public String getInheritNb() {
		return inheritNb;
	}

	public void setInheritNb(String inheritNb) {
		this.inheritNb = inheritNb;
	}
	
	

	

}

package com.rjkj.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

@Entity
@Table(name = "sys_dic")
public class Dic  extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1168402405828267121L;

	/**
	 * 
	 */

	@Column(nullable=false, length=64)
	private String dicName;
	
	@Column(length=2)
	private Integer orderNum = 1;
	
	@Column(nullable=true,length=64)
	private String dicKey;
	
	@Column(nullable=true,length=128)
	private String value1;
	
	@Column(nullable=true,length=128)
	private String value2;
	
	@Column(nullable=true, length=128)
	private String value3;

	@Column(length=255)
	private String dicBrief;
	
	@Column(nullable=false, length=32)
	private String nodeType;
	
	@Column(nullable=false, length=32)
	private String status;

	@ManyToOne
	@JoinColumn(name="parentId")
	private Dic parent;
	
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<Dic> children = Lists.newArrayList();

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getDicKey() {
		return dicKey;
	}

	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getDicBrief() {
		return dicBrief;
	}

	public void setDicBrief(String dicBrief) {
		this.dicBrief = dicBrief;
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

	public Dic getParent() {
		return parent;
	}

	public void setParent(Dic parent) {
		this.parent = parent;
	}

	public List<Dic> getChildren() {
		return children;
	}

	public void setChildren(List<Dic> children) {
		this.children = children;
	}

	
	
}

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
@Table(name = "jk_sxtj")
public class JkSxtj extends IdEntity{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8311391487800569024L;

	@Column(nullable=false, length=128)
	private String typeName;//类别名称
	
	@Column(nullable=true,length=512)
	private String typeBrief;//筛选类别说明
	
	@Column(nullable=true,length=64)
	private String typeSql;//SQL筛选语句
	
	@Column(nullable=false, length=4)
	private String nodeType;
	
	@Column(length=2)
	private Integer orderNum;
	
	@Column(nullable=true, length=4)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="sxtjId")
	private JkSxtj parent;
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<JkSxtj> children = Lists.newArrayList();

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeBrief() {
		return typeBrief;
	}

	public void setTypeBrief(String typeBrief) {
		this.typeBrief = typeBrief;
	}

	public String getTypeSql() {
		return typeSql;
	}

	public void setTypeSql(String typeSql) {
		this.typeSql = typeSql;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JkSxtj getParent() {
		return parent;
	}

	public void setParent(JkSxtj parent) {
		this.parent = parent;
	}

	public List<JkSxtj> getChildren() {
		return children;
	}

	public void setChildren(List<JkSxtj> children) {
		this.children = children;
	}
	
	

}

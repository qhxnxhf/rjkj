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
@Table(name = "jk_tjxm")
public class JkTjxm extends IdEntity{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6077548231135965507L;

	@Column(nullable=false, length=128)
	private String name;//中文名称
	
	@Column(nullable=true,length=64)
	private String shortName;//英文缩写
	
	@Column(nullable=true, length=32)
	private String escValue;//转义符
	
	@Column(nullable=true)
	private Double refValueH;//参考高值
	
	@Column(nullable=true)
	private Double refValueL;//参考低值
	
	@Column(nullable=true)
	private Double sxValueH;//筛选参考高值
	
	@Column(nullable=true)
	private Double sxValueL;//筛选参考低值
	
	@Column(nullable=true,length=64)
	private String type1Value;//一类参考值
	
	@Column(nullable=true,length=64)
	private String type2Value;//二类参考值
	
	@Column(nullable=true,length=64)
	private String type3Value;//三类参考值
	
	@Column(nullable=true,length=32)
	private String tjUnit;//计量单位
	
	@Column(nullable=true,length=32)
	private String mappingField;//字段映射
	
	@Column(nullable=false, length=4)
	private String nodeType;//节点类型
	
	@Column(length=2)
	private Integer orderNum;//节点序号
	
	@Column(nullable=true, length=4)
	private String status;//状态
	
	
	
	@ManyToOne
	@JoinColumn(name="parentId")
	private JkTjxm parent;
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<JkTjxm> children = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Double getRefValueH() {
		return refValueH;
	}

	public void setRefValueH(Double refValueH) {
		this.refValueH = refValueH;
	}

	public Double getRefValueL() {
		return refValueL;
	}

	public void setRefValueL(Double refValueL) {
		this.refValueL = refValueL;
	}

	public String getTjUnit() {
		return tjUnit;
	}

	public void setTjUnit(String tjUnit) {
		this.tjUnit = tjUnit;
	}

	public String getMappingField() {
		return mappingField;
	}

	public void setMappingField(String mappingField) {
		this.mappingField = mappingField;
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

	public JkTjxm getParent() {
		return parent;
	}

	public void setParent(JkTjxm parent) {
		this.parent = parent;
	}

	public List<JkTjxm> getChildren() {
		return children;
	}

	public void setChildren(List<JkTjxm> children) {
		this.children = children;
	}

	public String getType1Value() {
		return type1Value;
	}

	public void setType1Value(String type1Value) {
		this.type1Value = type1Value;
	}

	public String getType2Value() {
		return type2Value;
	}

	public void setType2Value(String type2Value) {
		this.type2Value = type2Value;
	}

	public String getType3Value() {
		return type3Value;
	}

	public void setType3Value(String type3Value) {
		this.type3Value = type3Value;
	}

	public String getEscValue() {
		return escValue;
	}

	public void setEscValue(String escValue) {
		this.escValue = escValue;
	}

	public Double getSxValueH() {
		return sxValueH;
	}

	public void setSxValueH(Double sxValueH) {
		this.sxValueH = sxValueH;
	}

	public Double getSxValueL() {
		return sxValueL;
	}

	public void setSxValueL(Double sxValueL) {
		this.sxValueL = sxValueL;
	}

	
	

}

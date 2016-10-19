package com.rjkj.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "jk_ycxm")
public class JkYcxm extends IdEntity{		
	/**
	 * 
	 */
	private static final long serialVersionUID = 7100570672165163329L;

	@Column(nullable=false, length=64)
	private String name;
	
	@Column(nullable=true)
	private Long tjrId;//体检人ID
	
	@Column(nullable=true)
	private Integer tjAges;//体检人年龄
	
	@Column(nullable=true)
	private Long deptId;//单位ID
	
	@Column(nullable=true,length=64)
	private String tjpc;//体检批次
	
	@Column(nullable=true,length=64)
	private String tjType;//体检类别
	
	@Column(nullable=true,length=16)
	private String sex;//性别
	
	@Column(nullable=true)
	private Date tjDate;//体检日期
	
	@Column(nullable=true,length=64)
	private String cardId;//体检人身份证
	
	@ManyToOne
	@JoinColumn(name="tjxmId")
	private JkTjxm tjxm;//体检项目
	
	@Column(nullable=true)
	private Long tjxxId;//体检信息ID
	
	@Column(nullable=true)
	private Double tjValue;//体检值

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTjrId() {
		return tjrId;
	}

	public void setTjrId(Long tjrId) {
		this.tjrId = tjrId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getTjpc() {
		return tjpc;
	}

	public void setTjpc(String tjpc) {
		this.tjpc = tjpc;
	}

	public Date getTjDate() {
		return tjDate;
	}

	public void setTjDate(Date tjDate) {
		this.tjDate = tjDate;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public JkTjxm getTjxm() {
		return tjxm;
	}

	public void setTjxm(JkTjxm tjxm) {
		this.tjxm = tjxm;
	}

	public Long getTjxxId() {
		return tjxxId;
	}

	public void setTjxxId(Long tjxxId) {
		this.tjxxId = tjxxId;
	}

	public Double getTjValue() {
		return tjValue;
	}

	public void setTjValue(Double tjValue) {
		this.tjValue = tjValue;
	}

	public String getTjType() {
		return tjType;
	}

	public void setTjType(String tjType) {
		this.tjType = tjType;
	}

	public Integer getTjAges() {
		return tjAges;
	}

	public void setTjAges(Integer tjAges) {
		this.tjAges = tjAges;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	

}

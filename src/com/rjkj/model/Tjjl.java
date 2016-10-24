package com.rjkj.model;

import java.util.Date;

public class Tjjl {
	
	protected Long id;//ID id
	
	private String deptName;//单位名称  txx_18
	
	private String cardId;//体检人身份证 xx_5
	
	private String medicareId;//医保号 xx_4
	
	private Date tjDate;//体检日期 txx_22
	
	private String tjpc;//体检批次 gxx_6
	
	private String tjType;//体检类型 txx_21
	
	private String hospital;//体检医院 txx_15
	
	private String tjjl;//体检结论 txx_14
	
	private String jdjl;//鉴定结论 txx_20
	
	private String jkjl;//鉴定结论 txx_24
	
	private String qrf;//确认否 txx_16

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getMedicareId() {
		return medicareId;
	}

	public void setMedicareId(String medicareId) {
		this.medicareId = medicareId;
	}

	public Date getTjDate() {
		return tjDate;
	}

	public void setTjDate(Date tjDate) {
		this.tjDate = tjDate;
	}

	public String getTjpc() {
		return tjpc;
	}

	public void setTjpc(String tjpc) {
		this.tjpc = tjpc;
	}

	public String getTjType() {
		return tjType;
	}

	public void setTjType(String tjType) {
		this.tjType = tjType;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getTjjl() {
		return tjjl;
	}

	public void setTjjl(String tjjl) {
		this.tjjl = tjjl;
	}

	public String getJdjl() {
		return jdjl;
	}

	public void setJdjl(String jdjl) {
		this.jdjl = jdjl;
	}

	public String getJkjl() {
		return jkjl;
	}

	public void setJkjl(String jkjl) {
		this.jkjl = jkjl;
	}

	public String getQrf() {
		return qrf;
	}

	public void setQrf(String qrf) {
		this.qrf = qrf;
	}
	
	
	
	

}

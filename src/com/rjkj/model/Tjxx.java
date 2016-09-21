package com.rjkj.model;

import java.util.Date;

public class Tjxx {
	
	protected Long id;//体检ID
	
	private Long tjrId;//体检人ID
	
	private String cardId;//体检人身份证
	
	private String medicareId;//医保号
	
	private Date tjDate;//体检日期
	
	private String tjpc;//体检批次
	
	private String tjType;//体检批次
	
	private Long tjxId;//体检项目ID
	
	private String tjkm;//体检科目名称
	
	private String tjx;//体检项目名称
	
	private String tjjg;//体检结果

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTjrId() {
		return tjrId;
	}

	public void setTjrId(Long tjrId) {
		this.tjrId = tjrId;
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

	public String getTjType() {
		return tjType;
	}

	public void setTjType(String tjType) {
		this.tjType = tjType;
	}

	public Long getTjxId() {
		return tjxId;
	}

	public void setTjxId(Long tjxId) {
		this.tjxId = tjxId;
	}

	public String getTjkm() {
		return tjkm;
	}

	public void setTjkm(String tjkm) {
		this.tjkm = tjkm;
	}

	public String getTjx() {
		return tjx;
	}

	public void setTjx(String tjx) {
		this.tjx = tjx;
	}

	public String getTjjg() {
		return tjjg;
	}

	public void setTjjg(String tjjg) {
		this.tjjg = tjjg;
	}

	public String getTjpc() {
		return tjpc;
	}

	public void setTjpc(String tjpc) {
		this.tjpc = tjpc;
	}
	
	

}

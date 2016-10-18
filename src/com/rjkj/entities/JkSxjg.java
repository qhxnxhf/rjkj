package com.rjkj.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "jk_sxjg")
public class JkSxjg extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6398250094756338569L;

	@Column(nullable=false, length=64)
	private String name;
	
	@Column(nullable=true)
	private Long tjrId;//体检人ID
	
	@Column(nullable=true)
	private Long deptId;//单位ID
	
	@Column(nullable=true,length=64)
	private String tjpc;//体检批次
	
	@Column(nullable=true)
	private Date tjDate;//体检日期
	
	@Column(nullable=true,length=64)
	private String cardId;//体检人身份证
	
	@Column(nullable=true)
	private Double bmi;
	
	@Column(nullable=true)
	private Double zongdan;
	
	@Column(nullable=true)
	private Double dimi;	
	
	@Column(nullable=true)
	private Double kfxt;	
	
	@Column(nullable=true)
	private Double shzhya;
	
	@Column(nullable=true)
	private Double gaomi;
	
	@Column(nullable=true)
	private Double gysz;
	
	@Column(nullable=true)
	private Double fev1;
	
	@Column(nullable=true)
	private Double fev1fvc;
	
	@Column(nullable=true)
	private Double age;
	
	@Column(nullable=true)
	private Double guanxb;
	
	@Column(nullable=true)
	private Double xueshi;
	
	@Column(nullable=true)
	private Double naocu;
	
	@Column(nullable=true)
	private Double zhongliu;
	
	@Column(nullable=true, length=4)
	private String type1;
	
	@Column(nullable=true, length=4)
	private String type2;
	
	@Column(nullable=true, length=4)
	private String type3;
	
	@Column(nullable=true, length=256)
	private String sxResult;

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

	public Double getBmi() {
		return bmi;
	}

	public void setBmi(Double bmi) {
		this.bmi = bmi;
	}

	public Double getZongdan() {
		return zongdan;
	}

	public void setZongdan(Double zongdan) {
		this.zongdan = zongdan;
	}

	public Double getDimi() {
		return dimi;
	}

	public void setDimi(Double dimi) {
		this.dimi = dimi;
	}

	public Double getShzhya() {
		return shzhya;
	}

	public void setShzhya(Double shzhya) {
		this.shzhya = shzhya;
	}

	public Double getGaomi() {
		return gaomi;
	}

	public void setGaomi(Double gaomi) {
		this.gaomi = gaomi;
	}

	public Double getGysz() {
		return gysz;
	}

	public void setGysz(Double gysz) {
		this.gysz = gysz;
	}

	public Double getFev1() {
		return fev1;
	}

	public void setFev1(Double fev1) {
		this.fev1 = fev1;
	}

	public Double getFev1fvc() {
		return fev1fvc;
	}

	public void setFev1fvc(Double fev1fvc) {
		this.fev1fvc = fev1fvc;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public Double getGuanxb() {
		return guanxb;
	}

	public void setGuanxb(Double guanxb) {
		this.guanxb = guanxb;
	}

	public Double getXueshi() {
		return xueshi;
	}

	public void setXueshi(Double xueshi) {
		this.xueshi = xueshi;
	}

	public Double getNaocu() {
		return naocu;
	}

	public void setNaocu(Double naocu) {
		this.naocu = naocu;
	}

	public Double getZhongliu() {
		return zhongliu;
	}

	public void setZhongliu(Double zhongliu) {
		this.zhongliu = zhongliu;
	}

	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getType3() {
		return type3;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public String getSxResult() {
		return sxResult;
	}

	public void setSxResult(String sxResult) {
		this.sxResult = sxResult;
	}

	public Double getKfxt() {
		return kfxt;
	}

	public void setKfxt(Double kfxt) {
		this.kfxt = kfxt;
	}
	
	
	

}

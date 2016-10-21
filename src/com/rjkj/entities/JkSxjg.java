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

	@Column(nullable=true, length=64)
	private String name;//姓名
	
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
	
	@Column(nullable=true,length=64)
	private String tjType;//体检类别
	
	@Column(nullable=true,length=16)
	private String sex;//性别
	
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
	private Integer age;
	
	@Column(nullable=true)
	private Double guanxb;
	
	@Column(nullable=true)
	private Double xueshi;
	
	@Column(nullable=true)
	private Double naocu;
	
	@Column(nullable=true)
	private Double xjqx;
	
	@Column(nullable=true)
	private Double xnxg;
	
	@Column(nullable=true)
	private Double tnbs;
	
	@Column(nullable=true)
	private Double zhongliu;
	
	@Column(nullable=true)
	private Long type1;
	
	@Column(nullable=true)
	private Long type2;
	
	@Column(nullable=true)
	private Long type3;
	
	@Column(nullable=true)
	private Long type4;
	
	@Column(nullable=true)
	private Long type5;
	
	@Column(nullable=true)
	private Long type6;
	
	@Column(nullable=true)
	private Long type7;
	
	@Column(nullable=true)
	private Long type8;
	
	@Column(nullable=true)
	private Long type9;
	
	@Column(nullable=true)
	private Long type10;
	
	@Column(nullable=true)
	private Long type11;
	
	@Column(nullable=true)
	private Long type12;
	
	@Column(nullable=true)
	private Long type13;
	
	@Column(nullable=true)
	private Long type14;
	
	@Column(nullable=true)
	private Long type15;
	
	@Column(nullable=true)
	private Long type16;
	
	@Column(nullable=true)
	private Long type17;
	
	@Column(nullable=true)
	private Long type18;
	
	@Column(nullable=true)
	private Long type19;
	
	@Column(nullable=true)
	private Long type20;
	
	
	@Column(nullable=true, length=256)
	private String sxResult;
	
	@Column(nullable=true)
	private Long indexId;//体检信息ID

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

	

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
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

	public String getTjType() {
		return tjType;
	}

	public void setTjType(String tjType) {
		this.tjType = tjType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}

	public Double getXjqx() {
		return xjqx;
	}

	public void setXjqx(Double xjqx) {
		this.xjqx = xjqx;
	}

	public Double getXnxg() {
		return xnxg;
	}

	public void setXnxg(Double xnxg) {
		this.xnxg = xnxg;
	}

	public Double getTnbs() {
		return tnbs;
	}

	public void setTnbs(Double tnbs) {
		this.tnbs = tnbs;
	}

	public Long getType1() {
		return type1;
	}

	public void setType1(Long type1) {
		this.type1 = type1;
	}

	public Long getType2() {
		return type2;
	}

	public void setType2(Long type2) {
		this.type2 = type2;
	}

	public Long getType3() {
		return type3;
	}

	public void setType3(Long type3) {
		this.type3 = type3;
	}

	public Long getType4() {
		return type4;
	}

	public void setType4(Long type4) {
		this.type4 = type4;
	}

	public Long getType5() {
		return type5;
	}

	public void setType5(Long type5) {
		this.type5 = type5;
	}

	public Long getType6() {
		return type6;
	}

	public void setType6(Long type6) {
		this.type6 = type6;
	}

	public Long getType7() {
		return type7;
	}

	public void setType7(Long type7) {
		this.type7 = type7;
	}

	public Long getType8() {
		return type8;
	}

	public void setType8(Long type8) {
		this.type8 = type8;
	}

	public Long getType9() {
		return type9;
	}

	public void setType9(Long type9) {
		this.type9 = type9;
	}

	public Long getType10() {
		return type10;
	}

	public void setType10(Long type10) {
		this.type10 = type10;
	}

	public Long getType11() {
		return type11;
	}

	public void setType11(Long type11) {
		this.type11 = type11;
	}

	public Long getType12() {
		return type12;
	}

	public void setType12(Long type12) {
		this.type12 = type12;
	}

	public Long getType13() {
		return type13;
	}

	public void setType13(Long type13) {
		this.type13 = type13;
	}

	public Long getType14() {
		return type14;
	}

	public void setType14(Long type14) {
		this.type14 = type14;
	}

	public Long getType15() {
		return type15;
	}

	public void setType15(Long type15) {
		this.type15 = type15;
	}

	public Long getType16() {
		return type16;
	}

	public void setType16(Long type16) {
		this.type16 = type16;
	}

	public Long getType17() {
		return type17;
	}

	public void setType17(Long type17) {
		this.type17 = type17;
	}

	public Long getType18() {
		return type18;
	}

	public void setType18(Long type18) {
		this.type18 = type18;
	}

	public Long getType19() {
		return type19;
	}

	public void setType19(Long type19) {
		this.type19 = type19;
	}

	public Long getType20() {
		return type20;
	}

	public void setType20(Long type20) {
		this.type20 = type20;
	}
	
	
	

}

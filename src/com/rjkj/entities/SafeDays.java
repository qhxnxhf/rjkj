package com.rjkj.entities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="cms_safedays")
public class SafeDays extends IdEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6662326261491434157L;
	
	@Column(nullable=true)
	private Integer orderNum = 1;//栏目序号

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="dicId")
	private Dic dic;
	
	@Column(length=64)
	private String title;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orgId")
	private Organize org;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginTime;	
	
	@Transient
	private Integer days;
	
	@Column(length=512)
	private String brief;
	
	@Column(nullable=true, length=16)
	private String allowed;//访问限定
	
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Dic getDic() {
		return dic;
	}

	public void setDic(Dic dic) {
		this.dic = dic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Organize getOrg() {
		return org;
	}

	public void setOrg(Organize org) {
		this.org = org;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getDays() {
		days=this.daysBetween(beginTime,new Date());
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	
	
	public String getAllowed() {
		return allowed;
	}

	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	public  int daysBetween(Date date1,Date date2){  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date1);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(date2);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
          
       return Integer.parseInt(String.valueOf(between_days));         
    }  

}

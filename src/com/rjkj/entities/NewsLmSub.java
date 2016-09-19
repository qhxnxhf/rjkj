package com.rjkj.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "cms_newslm_sub")
public class NewsLmSub extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2158461542500679797L;

	@ManyToOne
	@JoinColumn(name="lmId")
	private NewsLm lm;//订阅栏目
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;//订阅人
	
	@Column(nullable=true, length=4)
	private String status;//状态
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;//日期
	
	@Column(nullable=true, length=256)
	private String remark;//备注

	public NewsLm getLm() {
		return lm;
	}

	public void setLm(NewsLm lm) {
		this.lm = lm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}

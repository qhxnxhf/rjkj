package com.rjkj.entities;

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
import com.rjkj.entities.IdEntity;

@Entity
@Table(name="sys_user")
public class User extends IdEntity {


	private static final long serialVersionUID = -4277639149589431277L;
	
	@Column(nullable=false, length=128)
	private String loginName;
	
	@Column(length=64)
	private String password;
	
	@Transient
	private String plainPassword;
	
	@Column(length=32)
	private String salt;
	
	@Column(nullable=false, length=128)
	private String userName;
	
	@Column(nullable=true, length=4)
	private String sex;
	
	@Column(length=255)
	private String userPhoto;
	
	@Column(length=512)
	private String userBrief;
	
	@Column(length=128)
	private String mobile;
	
	@Column(length=128)
	private String email;
	
	@Column(length=64)
	private String qq;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;	
	
	 
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date validityTime;
	
	@Column(length=512)
	private String roles;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orgId")
	private Organize org;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date loginTime;
	
	@Column(length=256)
	private String loginIp;
	
	@Column(length=512)
	private String allowIp;		

	@Column(nullable=false, length=16)
	private String status = "1";

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getUserBrief() {
		return userBrief;
	}

	public void setUserBrief(String userBrief) {
		this.userBrief = userBrief;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(Date validityTime) {
		this.validityTime = validityTime;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public Organize getOrg() {
		return org;
	}

	public void setOrg(Organize org) {
		this.org = org;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAllowIp() {
		return allowIp;
	}

	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}
	
	

}

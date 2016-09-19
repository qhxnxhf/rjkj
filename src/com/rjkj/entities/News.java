package com.rjkj.entities;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Lob;
import javax.persistence.CascadeType;
import com.google.common.collect.Lists;


@Entity
@Table(name = "cms_news")
public class News  extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6365883836142397209L;
	
	@Column(nullable=true, length=128)
	private String process_instance_id;

	@Column(nullable=true)
	private Integer orderNum;//排序号
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="lmId")
	private NewsLm lm;//所属栏目
	
	@Column(nullable=true)
	private Integer showType;//展示方式，如：头条，焦点等
	
	@Column(nullable=true)
	private Integer mesgType;//信息类别，如:图片新闻，图文新闻，视频新闻等
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="orgId")
	private Organize org;//发布单位
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	private User user;//发布人
	
	@Column(nullable=true, length=255)
	private String newsTitle;//标题
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pubDate;//发布日期
	
	@Column(nullable=true, length=64)
	private String author;//作者
	
	@Column(nullable=true, length=128)
	private String origin;//信息来源
	
	@Column(nullable=true, length=256)
	private String newsBrief;//正文简介
	
	@Column(nullable=true, length=128)
	private String keyWd;//关键字
	
	
	
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="newsBody",columnDefinition="CLOB", nullable=true)
	//@Column(name="newsBody",nullable=true)
	private String newsBody;//正文
	
	@Column(nullable=true)
	private Integer count1;//预留1
	
	@Column(nullable=true)
	private Integer count2;//预留2
	
	@Column(nullable=true)
	private Integer count3;//预留3
	
	@Column(nullable=true)
	private Integer count4;//预留4
	
	@Column(nullable=true)
	private Integer count5;//预留5
	
	@Column(nullable=true)
	private Integer readCount;//点击量
	
	@Column(nullable=true, length=256)
	private String icoPath;//新闻配图，在一些栏目中可直接显示
	
	@Column(nullable=true, length=256)
	private String filePath;//正文内容静态化文件路径
	
	@Column(nullable=true, length=16)
	private String allowed;//访问限定
	
	@Column(nullable=true, length=16)
	private String status;//发布状态
	
	@Column(nullable=false)
	private Integer newslock;//锁定状态(0：不锁定;1：锁定[可编辑]；2：锁定[只读])
	
	@Column(nullable=true, length=512)
	private String remark;//备注
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="news")
	private List<NewsAttach> attach = Lists.newArrayList();
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="news")
	private List<NewsSend> receiver = Lists.newArrayList();
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="news")
	private List<NewsComment> comment = Lists.newArrayList();

	public String getProcess_instance_id() {
		return process_instance_id;
	}

	public void setProcess_instance_id(String process_instance_id) {
		this.process_instance_id = process_instance_id;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public NewsLm getLm() {
		return lm;
	}

	public void setLm(NewsLm lm) {
		this.lm = lm;
	}

	public Integer getShowType() {
		return showType;
	}

	public void setShowType(Integer showType) {
		this.showType = showType;
	}

	public Integer getMesgType() {
		return mesgType;
	}

	public void setMesgType(Integer mesgType) {
		this.mesgType = mesgType;
	}

	public Organize getOrg() {
		return org;
	}

	public void setOrg(Organize org) {
		this.org = org;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	

	public String getNewsBrief() {
		return newsBrief;
	}

	public void setNewsBrief(String newsBrief) {
		this.newsBrief = newsBrief;
	}

	public String getKeyWd() {
		return keyWd;
	}

	public void setKeyWd(String keyWd) {
		this.keyWd = keyWd;
	}

	

	public String getNewsBody() {
		return newsBody;
	}

	public void setNewsBody(String newsBody) {
		this.newsBody = newsBody;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public String getIcoPath() {
		return icoPath;
	}

	public void setIcoPath(String icoPath) {
		this.icoPath = icoPath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAllowed() {
		return allowed;
	}

	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<NewsAttach> getAttach() {
		return attach;
	}

	public void setAttach(List<NewsAttach> attach) {
		this.attach = attach;
	}

	public List<NewsSend> getReceiver() {
		return receiver;
	}

	public void setReceiver(List<NewsSend> receiver) {
		this.receiver = receiver;
	}

	public List<NewsComment> getComment() {
		return comment;
	}

	public void setComment(List<NewsComment> comment) {
		this.comment = comment;
	}

	public Integer getNewslock() {
		return newslock;
	}

	public void setNewslock(Integer newslock) {
		this.newslock = newslock;
	}

	public Integer getCount1() {
		return count1;
	}

	public void setCount1(Integer count1) {
		this.count1 = count1;
	}

	public Integer getCount2() {
		return count2;
	}

	public void setCount2(Integer count2) {
		this.count2 = count2;
	}

	public Integer getCount3() {
		return count3;
	}

	public void setCount3(Integer count3) {
		this.count3 = count3;
	}

	public Integer getCount4() {
		return count4;
	}

	public void setCount4(Integer count4) {
		this.count4 = count4;
	}

	public Integer getCount5() {
		return count5;
	}

	public void setCount5(Integer count5) {
		this.count5 = count5;
	}

	
	

}

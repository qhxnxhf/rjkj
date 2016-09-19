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
@Table(name = "cms_newslm")
public class NewsLm  extends IdEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1216452218422901181L;

	@Column(nullable=false, length=64)
	private String lmName;//栏目名称
	
	@Column(length=255)
	private String lmBrief;//栏目介绍
	
	@Column(nullable=false,length=4)
	private String nodeType;//节点类型
	
	@Column(nullable=true,length=128)
	private String lmIcon;//栏目图标
	
	@Column(length=2)
	private Integer orderNum = 1;//栏目序号
	
	@Column(nullable=true, length=255)
	private String linkUrl;//当栏目为节点类型为链接时的URL地址
	
	@Column(nullable=true, length=32)
	private String target;//inframe名称
	
	@Column(length=2)
	private Integer moduleId;//栏目内容展示模板ID
	
	@Column(nullable=true, length=128)
	private String moduleName;//栏目内容展示模板名称
	
	@Column(nullable=true, length=255)
	private String orgIdsMl;//栏目定义操作授权
	
	@Column(nullable=true, length=255)
	private String orgIdsWj;//内容发布授权
	
	@Column(nullable=false, length=4)
	private String status;//栏目状态
	
	@ManyToOne
	@JoinColumn(name="parentId")
	private NewsLm parent;//父节点
	
	@OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy="parent")
	private List<NewsLm> children = Lists.newArrayList();

	public String getLmName() {
		return lmName;
	}

	public void setLmName(String lmName) {
		this.lmName = lmName;
	}

	public String getLmBrief() {
		return lmBrief;
	}

	public void setLmBrief(String lmBrief) {
		this.lmBrief = lmBrief;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getLmIcon() {
		return lmIcon;
	}

	public void setLmIcon(String lmIcon) {
		this.lmIcon = lmIcon;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getOrgIdsMl() {
		return orgIdsMl;
	}

	public void setOrgIdsMl(String orgIdsMl) {
		this.orgIdsMl = orgIdsMl;
	}

	public String getOrgIdsWj() {
		return orgIdsWj;
	}

	public void setOrgIdsWj(String orgIdsWj) {
		this.orgIdsWj = orgIdsWj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NewsLm getParent() {
		return parent;
	}

	public void setParent(NewsLm parent) {
		this.parent = parent;
	}

	public List<NewsLm> getChildren() {
		return children;
	}

	public void setChildren(List<NewsLm> children) {
		this.children = children;
	}
	
	

}

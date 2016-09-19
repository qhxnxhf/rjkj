/*
 * @MessageResult.java
 *
 * Copyright (c) 2011-2011 MOR, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of MOR,
 * Inc. ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with MOR.
 */
package com.rjkj.util;

import javax.xml.bind.annotation.XmlType;

/*
 * 所以service返回类
 * success是成功状态默认是true
 * message是返回信息默认是""
 * T是返回类型
 */
@XmlType(name = "MessageResult", namespace = "http://webservice.dzsw.mor.org")
public class MessageResult {
	boolean isSuccess = true;
	String message = "操作成功";
	Object object = null;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "MessageResult [isSuccess=" + isSuccess + ", message=" + message + ", object=" + object + "]";
	}
	
}

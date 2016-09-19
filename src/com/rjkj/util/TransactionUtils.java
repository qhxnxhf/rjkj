/*
 * @TransactionUtils.java
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

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.rjkj.util.MessageResult;

/**
 * TransactionUtils Description.
 * 
 * @version Change History:
 * @version <1> Eric (Nov 16, 2011) create this file.
 */
public class TransactionUtils {
	public static void throwRollBackException(Exception e) {
		throw new RuntimeException(e);
	}

	public static void throwRollBackException(MessageResult mr) {
		if (!mr.isSuccess())
			throw new RuntimeException(mr.getMessage());
	}

	/**
	 * Roll back if MessageResult return isSuccess = false.
	 * only used in the outest method, just like transactionInteceptor
	 * @param mr the mr
	 */
	public static void rollBack(MessageResult mr) {
		if (!mr.isSuccess()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}

	/**
	 * set Roll back flag.
	 * only used in the outest method, just like transactionInteceptor
	 */
	public static void rollBack() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}

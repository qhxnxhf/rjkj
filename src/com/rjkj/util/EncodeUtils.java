/*
 * @EncodeUtils.java
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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;



/**
 * 各种格式的编码加码工具类.
 * 
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * 
 * @author calvin
 */
/**
 * EncodeUtils Description. import from springside3.3.4
 * 
 * @version Change History:
 * @version  <1> Eric (Nov 21, 2011) create this file. 
 */
public class EncodeUtils {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	/**
	 * Hex编码.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String base64Encode(byte[] input) {
		return new String(Base64.encodeBase64(input));
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String input) {
		try {
			return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String input) {
		try {
			return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	/**
	 * Html 转码.
	 */
	public static String htmlEscape(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String htmlUnescape(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 */
	public static String xmlEscape(String xml) {
		return StringEscapeUtils.escapeXml(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String xmlUnescape(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}
	
	/**
	 * Md5 encode (本系统的md5加密)
	 * 
	 * @param rawPass 密码明文
	 *            the password
	 * @return the string 加密后的密文
	 */
	@SuppressWarnings("deprecation")
	public static String md5Encode(String rawPass){
		PasswordEncoder encoder = SpringApplicationContextUtils.getBean("md5Encoder",PasswordEncoder.class);
		SaltSource salt =SpringApplicationContextUtils.getBean("salt",SaltSource.class);
		return encoder.encodePassword(rawPass, salt.getSalt(null));
//		return rawPass;//空实现前期调试时使用
	}
	
	/**
	 * 必须是数字和字母组合
	 * @param pwd
	 * @return
	 */
	public static MessageResult checkPwdFormat(String pwd){
		MessageResult mr = new MessageResult();
		if(null == pwd || "".equals(pwd)){
			mr.setSuccess(false);
			mr.setMessage("密码不能为空！");
			return mr;
		}
		if(pwd.length() < 6){
			mr.setSuccess(false);
			mr.setMessage("密码长度不能少于6位！");
			return mr;
		}
		mr.setSuccess((!Pattern.compile("[^a-z]{1,}").matcher(pwd).matches()
				|| !Pattern.compile("[^A-Z]{1,}").matcher(pwd).matches())
				&& !Pattern.compile("[^0-9]{1,}").matcher(pwd).matches());
		if(mr.isSuccess()){
			mr.setMessage("验证通过！");
		}else{
			mr.setMessage("密码必须是由数字和字母组合！");
		}
		return mr;
	}
}

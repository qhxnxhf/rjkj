package com.rjkj.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Sets;
import com.rjkj.dao.ModuleDao;
import com.rjkj.dao.UserDao;
import com.rjkj.entities.Module;
import com.rjkj.entities.User;

public class ShiroDbRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory
			.getLogger(ShiroDbRealm.class);

	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	// 是否启用超级管理员
	protected boolean activeRoot = false;

	// 是否使用验证码
	protected boolean useCaptcha = false;
	
	@Autowired
	protected UserDao userService;
	
	@Autowired
	protected ModuleDao moduleService;

	//protected ImageCaptchaService imageCaptchaService;

	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对 描述
	 */
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				ALGORITHM);
		matcher.setHashIterations(INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	// TODO 对认证进行缓存处理
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		List<User> users = userService.findByLoginName(token.getUsername(), "");
		
		if (users != null && users.size()>0) {
			User user=users.get(0);
			if (user.getStatus().equals("4")||user.getStatus().equals("1")) {
				throw new DisabledAccountException();
			}

			
			byte[] salt =Hex.decode(user.getSalt());
			//byte[] salt =user.getSalt().getBytes();

			ShiroUser shiroUser = new ShiroUser(user.getId(),
					user.getId()+"", user);
			// 这里可以缓存认证
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(),
					ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String obj=getName();
		Collection<?> collection = principals.fromRealm(getName());
		if (collection==null||collection.size()==0) {
			return null;
		}
		//if (Collections3.isEmpty(collection)) {
		////	return null;
		//}
		ShiroUser shiroUser = (ShiroUser) collection.iterator().next();

		List<Module> modules = userService.findModuleByUser(shiroUser.getUser(), "", " AND o.module.nodeType='y' ");
		

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Collection<String> stringPermissions = Sets.newHashSet();
		
		for (Module module : modules) {
			String permission = module.getPermission();
			stringPermissions.add(permission);
		}
		
		info.addStringPermissions(stringPermissions);

		return info;
	}

	

	public static class HashPassword {
		public String salt;
		public String password;
	}

	public static HashPassword encryptPassword(String plainPassword) {
		
		HashPassword result = new HashPassword();
		try{		
			SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();  
			randomNumberGenerator.setSeed("123".getBytes()); 
			//randomNumberGenerator.setDefaultNextBytesSize(8);
			String salt = randomNumberGenerator.nextBytes().toHex();
			salt=salt.substring(0, 8);		
			String hashPassword = new SimpleHash(ALGORITHM, plainPassword, salt,INTERATIONS).toString(); 			
			result.salt = Hex.encodeToString(salt.getBytes());
			result.password = hashPassword;
			
		}catch(Exception e){
			System.out.print(e);
		}
		return result;
	}
	
	/**
	 * 
	 * 验证密码
	 * 
	 * @param plainPassword
	 *            明文密码
	 * @param password
	 *            密文密码
	 * @param salt
	 *            盐值
	 * @return
	 */
	public static boolean validatePassword(String plainPassword,
			String password, String salt) {		
		byte[] bb=Hex.decode(salt.getBytes());
		String salt2 = new String(bb);
		String hashPassword = new SimpleHash(ALGORITHM, plainPassword, salt2,INTERATIONS).toString(); 
		return password.equals(hashPassword);
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 设置 isActiveRoot 的值
	 * 
	 * @param isActiveRoot
	 */
	public void setActiveRoot(boolean activeRoot) {
		this.activeRoot = activeRoot;
	}

	/**
	 * 设置 useCaptcha 的值
	 * 
	 * @param useCaptcha
	 */
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	

/**
	public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
		this.imageCaptchaService = imageCaptchaService;
	}
**/
	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		private Long id;
		private String loginName;
		private String ipAddress;
		private User user;

		public ShiroUser() {

		}

		/**
		 * 构造函数
		 * 
		 * @param id
		 * @param loginName
		 * @param email
		 * @param createTime
		 * @param status
		 */
		public ShiroUser(Long id, String loginName, User user) {
			this.id = id;
			this.loginName = loginName;
			this.user = user;
		}

		/**
		 * 返回 id 的值
		 * 
		 * @return id
		 */
		public Long getId() {
			return id;
		}

		/**
		 * 返回 loginName 的值
		 * 
		 * @return loginName
		 */
		public String getLoginName() {
			return loginName;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		/**
		 * 返回 user 的值
		 * 
		 * @return user
		 */
		public User getUser() {
			return user;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}
	}
}
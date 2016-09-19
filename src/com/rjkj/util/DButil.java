package com.rjkj.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


public class DButil {
	private static Logger logger = Logger.getLogger(DButil.class);
	/**
	 * jdbc插入数据 获取执行sql
	 * @param bean
	 * @param tableName
	 * @param isExecNull  为true时  表示bean中属性值为空也执行否则不执行
	 * @return
	 */
	public static String getTableInsertSql(Object bean,String tableName){
		String sql = "";
		try {
			String sqlProps = "";
			String sqlvlues = "";
			Field[] fields = bean.getClass().getDeclaredFields();
			int index = 1;
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true); // 取得对private属性的访问权限
				String attrName = fields[i].getName();
				
				Object val = fields[i].get(bean);
				
				if(StringUtil.isNotEmpty(val)){
					try {
						sqlProps += A2_a(attrName)+",";
						sqlvlues += getValueSql(val)+",";
						index++;
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Set field failed in Field ["+attrName+"]["+A2_a(attrName)+"] .");
					}
				}
			}
			sqlProps = sqlProps.substring(0, sqlProps.length()-1);
			sqlvlues = sqlvlues.substring(0, sqlvlues.length()-1);
			sql = "insert into "+tableName+"("+sqlProps+") values("+sqlvlues+")";
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return sql;
	}
	
	
	
	/**
	 * jdbc更新数据 获取 执行sql
	 * @param bean
	 * @param tableName
	 * @param conditionSql
	 * @param isExecNull  为true时  表示bean中属性值为空也执行否则不执行
	 * @return
	 */
	public static String getTableUpdateSql(Object bean,String tableName,String conditionSql,boolean isExecNull){
		String sql = "";
		try {
			String sqlUpdatePros  = "";
			Field[] fields = bean.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true); // 取得对private属性的访问权限
				String attrName = fields[i].getName();
				
				Object val = fields[i].get(bean);
				if(StringUtil.isNotEmpty(val) || StringUtil.isEmpty(val) && isExecNull){
					try {
						sqlUpdatePros += A2_a(attrName)+"="+getValueSql(val)+",";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			sqlUpdatePros = sqlUpdatePros.substring(0, sqlUpdatePros.length()-1);

			sql = "update "+tableName+" set "+sqlUpdatePros+" where "+conditionSql;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * jdbc更新数据 获取 执行sql
	 * @param bean
	 * @param tableName
	 * @param conditionSql
	 * @param isExecNull  为true时  表示bean中属性值为空也执行否则不执行
	 * @return
	 */
	public static String getTableUpdateSql(Object oldBean,Object newBean,String tableName,String conditionSql){
		String sql = "";
		try {
			String sqlUpdatePros  = "";
			Field[] fields = oldBean.getClass().getDeclaredFields();
			Field[] newFields = newBean.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true); // 取得对private属性的访问权限
				newFields[i].setAccessible(true);
				String attrName = fields[i].getName();
				
				Object val = fields[i].get(oldBean);
				Object newVal = newFields[i].get(newBean);
				if(StringUtil.isNotEmpty(val) && !val.equals(newVal) || StringUtil.isNotEmpty(newVal) && !newVal.equals(val)){
					try {
						sqlUpdatePros += A2_a(attrName)+"="+getValueSql(newVal)+",";
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			sqlUpdatePros = sqlUpdatePros.substring(0, sqlUpdatePros.length()-1);
			sql = "update "+tableName+" set "+sqlUpdatePros+" where "+conditionSql;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * @param value
	 */
	public static String getValueSql(Object value){
		if(!StringUtil.isNotEmpty(value)){
			return "null";
		}
		
		if(value instanceof String){
			return "'"+value+"'";
		}else if(value instanceof java.util.Date){
			String date = DateUtils.sdfDate2.format(value);
			return "to_date('"+date+"', 'yyyy-mm-dd hh24:mi:ss')";
		}
		
		return value+"";
		
	}
	
	
	public static String A2_a(String name){
		StringBuilder sb = new StringBuilder(name);
		for(int i = 0; i < sb.length(); i++){
			char c = sb.charAt(i);
			if(c>=65 && c<=90){
				sb.delete(i, i+1);
				sb.insert(i, "_"+(char)(c+32));
			}
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param bean
	 * @param map 
	 */
	public static void buildMapBean(Object bean, Map<String,Object> map){
		if (bean==null ||  map==null || map.size()==0) {
			return;
		}
		
		Map<String,Object> newMap = new HashMap<String,Object>();
		Set<String> keys = map.keySet();
		for(String key:keys){
			Object value = map.get(key);
			newMap.put(key.replace("_", "").toLowerCase(), value);
		}
		
		String prop = "";
		Object param = null;
		try {
			Class cls = bean.getClass();
	        Method[] ms = cls.getMethods();
			for(int i=0; i<ms.length;i++){
		        	String name = ms[i].getName();
		            if(name.startsWith("set")) {
		                Class[] cc = ms[i].getParameterTypes();
		                if(cc.length==1) {
		                    String type = cc[0].getName();
		                    try {
		                        // get property name:
		                        prop = Character.toLowerCase(name.charAt(3)) + name.substring(4).toLowerCase();
		                        // get parameter value:
		                        param = newMap.get(prop);
		                        if(param!=null && !param.equals("")) {
		                            //ms[i].setAccessible(true);
		                            if(type.equals("java.lang.String")) {
		                                ms[i].invoke(bean, new Object[] {param});
		                            }
		                            else if(type.equals("int") || type.equals("java.lang.Integer")) {
		                                ms[i].invoke(bean, new Object[] {Integer.parseInt(param.toString())});
		                            }
		                            else if(type.equals("long") || type.equals("java.lang.Long")) {
		                                ms[i].invoke(bean, new Object[] {Long.parseLong(param.toString())});
		                            }
		                            else if(type.equals("double") || type.equals("java.lang.Double")) {
		                                ms[i].invoke(bean, new Object[] {Double.parseDouble(param.toString())});
		                            }
		                            else if(type.equals("boolean") || type.equals("java.lang.Boolean")) {
		                                ms[i].invoke(bean, new Object[] { Boolean.valueOf(param.toString()) });
		                            }
		                            else if(type.equals("short") || type.equals("java.lang.Short")) {
		                                ms[i].invoke(bean, new Object[] { Short.valueOf(param.toString()) });
		                            }
		                            else if(type.equals("float") || type.equals("java.lang.Float")) {
		                                ms[i].invoke(bean, new Object[] { Float.valueOf(param.toString()) });
		                            }
		                            else if(type.equals("java.util.Date")) {
		                            	
		                                Date date = DateUtils.getdate(param.toString());
		                                if(date!=null)
		                                    ms[i].invoke(bean, new Object[] {date});
		                            }
		                        }
		                    }
		                    catch(Exception e) {
		                	logger.error("Set field failed in Field ["+prop+"]["+type+"] Value ["+param+"].");
		                    }
		                }
		            }
		        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		String tt="Apr,27,2015 11:29";
		SimpleDateFormat sdf=new SimpleDateFormat("MMM,dd,yyyy hh:mm");
		Date date;
		try {
			date = sdf.parse(tt);
			System.out.println(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

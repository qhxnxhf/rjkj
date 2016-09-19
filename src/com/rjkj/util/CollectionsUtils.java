package com.rjkj.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CollectionsUtils {
	/*
	 * 删除Collection中空值
	 */
	@SuppressWarnings("unchecked")
	static public <T extends Collection> T removeNull(T t) {
		Iterator<T> it = t.iterator();
		while (it.hasNext()) {
			T temp = it.next();
			if (temp == null)
				it.remove();
		}
		return t;
	}
    /**
     * 删除重复的内容
     * @param <T>
     * @param c
     * @return
     */
    public static <T> List<T> removeDuplicate(List<T> c){
    	Set<T> set=new HashSet<T>();
    	List<T> temp=new ArrayList<T>();
    	for(T t:c){
    		set.add(t);
    	}
    	for (T t : set)
            temp.add(t);
    	return temp;
    }
	/**
	 * 传入一个List<Map>或者list<Object>，map的key，object为属性名，需要匹配的字符串，匹配模式 匹配模式可以传 font
	 * back frontAndBack null,标识模糊匹配，null为不模糊 value查找的字符串,不区分大小写
	 * 
	 * @return List, 符合value的LIST
	 */
	public static  List getListByValue(List list, String key, String value, String matchModel) {
		List listTemp = new ArrayList();
		Map map = null;
		String regex = null;
		if (matchModel == null) {
			regex = "(?i)" + value;
		} else if (matchModel.equals("front")) {
			regex = ".*(?i)" + value;
		} else if (matchModel.equals("back")) {
			regex = "(?i)" + value + ".*";
		} else if (matchModel.equals("frontAndBack")) {
			regex = ".*(?i)" + value + ".*";
		} else {
			throw new java.lang.RuntimeException();
		}

		String temp = null;
		for (int index = list.size() - 1; index >= 0; index--) {
			if (list.get(index) instanceof Map)
				map = (Map) list.get(index);
			else
				map = ClassTools.buildClassMap(list.get(index));
			temp = String.valueOf(map.get(key));

			if (temp.matches(regex)) {
				listTemp.add(list.get(index));
			}
		}
		return listTemp;
	}

	@SuppressWarnings("unchecked")
	public static List sort(List list, String key, String order) {
		ComparatorList comparatorList = new ComparatorList(key, order);
		List temp=deepClone(list);//防止传入的list是经过Collections.unmodifiableList处理的list
		Collections.copy(temp, list);
		Collections.sort(temp, comparatorList);

		return temp;
	}

	public static class ComparatorList implements Comparator {
		String key, order;
		Map m1, m2;
		int flag = 1;// 空值排序

		public ComparatorList(String key, String order) {
			this.key = key;
			this.order = order;
		}

		@Override
		public int compare(Object o1, Object o2) {
			if (this.order.equals("desc"))
				flag = -1;
			// 检查是否为Map，如果不是转换为Map
			if (o1 instanceof Map) {
				m1 = (Map) (o1);
				m2 = (Map) (o2);
			} else {
				m1 = ClassTools.buildClassMap(o1);
				m2 = ClassTools.buildClassMap(o2);
			}
			// 如果比较字段为Date类型
			if (m1.get(key) instanceof Date) {
				return (int) (((Date) m1.get(key)).getTime() - ((Date) m2.get(key)).getTime()) * flag;
			}
			// 如果比较字段为int类型
			// if(m1.get(key) instanceof Integer){
			// return m1.get(key)-m2.get(key);
			// }

			// 其余按转字符串处理

			return String.valueOf(m1.get(key)).compareTo(String.valueOf(m2.get(key))) * flag;
		}
	}

	/**
	 * Deep clone. 深度copy一个list集合
	 * 
	 * @param source
	 *            the source
	 * @return the list
	 * @description
	 * @author Eric (Nov 19, 2011)
	 * @version Change History:
	 * @version <1> Nov 19, 2011 Eric Modification purpose.
	 */
	public static List<Map> deepClone(List<Map> source) {
		if (null == source || source.size() == 0)
			return new ArrayList<Map>(0);
		List<Map> dest = new ArrayList<Map>(Arrays.asList(new HashMap[source.size()]));
		Collections.copy(dest, source);
		return dest;
	}

	/**
	 * Clone a list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the source
	 * @return List<T> 返回类型
	 * @Title: clone
	 * @Description: this method can replace the deepClone method (clone一个列表)
	 */
	public static <T> List<T> clone(List<T> source) {
		if (null == source || source.size() == 0)
			return new ArrayList<T>(0);
		List<T> dest = new ArrayList<T>((Collection<? extends T>) Arrays.asList(new Object[source.size()]));
		Collections.copy(dest, source);
		return dest;
	}

	/**
	 * Clone.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the source
	 * @param cloneNum
	 *            the clone num
	 * @return List<T>
	 * @title: clone
	 * @description: clone 限制的(cloneNum)结果集
	 * @version Change History:
	 * @version <1> Eric Dec 4, 2011 create this method
	 */
	public static <T> List<T> clone(List<T> source, int cloneNum) {
		if (null == source || source.size() == 0)
			return new ArrayList<T>(0);
		int realCloneNum = source.size() > cloneNum ? cloneNum : source.size();
		List<T> dest = new ArrayList<T>((Collection<? extends T>) Arrays.asList(new Object[realCloneNum]));
		if (realCloneNum < source.size()) {
			List<T> filterSource = new ArrayList<T>();
			for (int i = 0; i < realCloneNum; i++) {
				filterSource.add(source.get(i));
			}
			Collections.copy(dest, filterSource);
		} else
			Collections.copy(dest, source);
		return dest;
	}

	/**
	 * To list map.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param source
	 *            the source
	 * @return the list
	 */
	public static <T> List<Map> toListMap(List<T> source) {
		List<Map> result = new ArrayList<Map>();
		for (T item : source) {
			try {
				result.add(ClassTools.getMapOfClass(item));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	* @title: oneFilterList
	* @description: 提取此方法,让PYM的search result排序优先
	* @param tmpSourceList
	* @param q
	* @param filterCols
	* @param isPYM
	* @param searchCols
	* @return   
	* @version <1> Eric  19 Feb 2012  create this method
	*/ 
	private static List<Map> oneFilterList(List<Map<String, Object>> tmpSourceList, String q, Map<String, String> filterCols, boolean isPYM,
			Map<String, String> searchCols) {
		String pym = filterCols.get("PYM");
		List<Map> resultList = new ArrayList<Map>();
		for (Map item : tmpSourceList) {
			boolean isMatch = false;
			/*
			 * 循环sourceList中key的集合（旧key保存在filterCols中,以键的形式存在），得到sourceList的值
			 * 并与条件q进行匹配，如果条件为空或发现有匹配成功的值,刚退出循环
			 * 如果条件为空，刚直接将值以新key返回，性能会好一点（此if判断不要也不影响功能)
			 */
			if (StringUtil.isNotEmpty(q)) {
				if (isPYM) {
					String value = "";
					if (item.get(pym) != null) {
						value = item.get(pym).toString();
						isMatch = isSearch(value, q);
					}
				} else {
					for (Map.Entry<String, String> entry : searchCols.entrySet()) {
						String key = entry.getKey();
						String value = "";
						if (item.get(key) != null) {
							value = item.get(key).toString();
							isMatch = isSearch(value, q);
						}
						if (isMatch || StringUtil.isEmpty(q))
							break;
					}
				}
			}
			/*
			 * 如果匹配成功或条件为空，则将此次循环的值用新key拼装好后存入resultList中
			 * 新key保存在filterCols中，以值的形式存在
			 */
			if (isMatch || StringUtil.isEmpty(q)) {
//				Map<String, Object> m = new HashMap<String, Object>();
//				for (Map.Entry<String, String> entry : filterCols.entrySet()) {
//					m.put(entry.getValue(), item.get(entry.getKey()));
//				}
				//直接装载row item,不过滤字段
				resultList.add(item);
			}
		}// end for
		return resultList;
	}

	/**
	 * 将SourceList转换为符合匹配条件q并指定key的List.
	 * 
	 * @param data
	 *            : 待转换的List<Map>
	 * @param q
	 *            : 匹配的条件
	 * @param filterCols
	 *            : SourceList中的Key与转换成List<Map>中的key的集合
	 *            filterCols.put("源字段名称","新字段字称")
	 * @return 匹配了条件q 并 指定了新key的List<Map>
	 * @version <1> 2011/11/11 Hayden : create.
	 * @version <2> 2011/12/18 Hayden : will update a. add a key list for
	 *          compare b. update compare way ,use regex. c. order result list .
	 */
	public static List<Map> filterList(List<Map<String, Object>> data, String q, Map<String, String> filterCols) {
		List<Map> resultList = new ArrayList<Map>();
		// process empty q;
		if (data != null && StringUtil.isEmpty(q)) {
			resultList.addAll(data);
		} else if (data != null) {
			String pym = filterCols.get("PYM");
			if (StringUtil.isNotEmpty(pym)) {
				List<Map<String, Object>> tmpSourceList = clone(data);
				Map<String, String> cloneFilterCols = new HashMap<String, String>();
				cloneFilterCols.putAll(filterCols);
				cloneFilterCols.remove("PYM");
				resultList.addAll(oneFilterList(tmpSourceList, q, filterCols, true, cloneFilterCols));
				tmpSourceList.removeAll(resultList);
				resultList.addAll(oneFilterList(tmpSourceList, q, filterCols, false, cloneFilterCols));
				tmpSourceList.clear();
			} else {
				resultList.addAll(oneFilterList(data, q, filterCols, false, filterCols));
			}
		}// end if(sourceList != null){
		// add 自动提示下拉选项的缺省现实条数 by eric 2011/12/4
		List<Map> showResultMap=CollectionsUtils.clone(resultList,50);
		//过滤结果集的column according to filterCols
		List<Map> returnListMap=new ArrayList<Map>();
		for (Map item : showResultMap) {
			Map<String, Object> m = new HashMap<String, Object>();
			for (Map.Entry<String, String> entry : filterCols.entrySet()) {
				m.put(entry.getValue(), item.get(entry.getKey()));
			}
			returnListMap.add(m);
		}
		//清空临时变量
		resultList.clear();
		showResultMap.clear();
		return returnListMap;
	}

	/**
	 * 根据条件匹配查询源，决定是否根据条件来查询
	 * 一般用于：根据拼音码、电报码、汉字、TMISM查询，而输入的条件可能是拼音码、电报码、汉字或TMISM码。
	 * 
	 * @param searchSource
	 *            ：查询源（拼音码、电报码、汉字、TMISM码）
	 * @param searchCondition
	 *            ：　查询条件
	 * @version <1> 2011-11-3 hayden : created.
	 */
	private static boolean isSearch(String searchSource, String searchCondition) {
		boolean flag = false;
		// 1. 判断输入不为空或""
		// 2. 判断查询源中包含条件,全大写
		// 3. 判断查询源中包含条件,全小写（或者）
		if (StringUtil.isNotEmpty(searchCondition)) {
			for (int i = 0; i < searchSource.length(); i++) {
				if (searchSource.toLowerCase().startsWith(searchCondition.toLowerCase())) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/xxzx/jeesite">JeeSite</a> All rights reserved.
 */
package com.xxzx.jeesite.common.utils.excel.fieldtype;

import com.xxzx.jeesite.common.utils.StringUtils;
import com.xxzx.jeesite.modules.sys.entity.Area;
import com.xxzx.jeesite.modules.sys.utils.UserUtils;

/**
 * 字段类型转换
 * @author xxzx
 * @version 2013-03-10
 */
public class AreaType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		for (Area e : UserUtils.getAreaList()){
			if (StringUtils.trimToEmpty(val).equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	/**
	 * 获取对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((Area)val).getName() != null){
			return ((Area)val).getName();
		}
		return "";
	}
}

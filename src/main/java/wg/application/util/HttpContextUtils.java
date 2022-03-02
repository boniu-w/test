/**
 * Copyright (c) 2016-2019  All rights reserved.
 *
 * https://www.7-me.net
 *
 * 版权所有，侵权必究！
 */

package wg.application.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpContextUtils {

	/************************************************************************
	 * @author: wg
	 * @description: 在任意地方获取 HttpServletRequest
	 * @params:
	 * @return:
	 * @createTime: 14:25  2022/3/2
	 * @updateTime: 14:25  2022/3/2
	 ************************************************************************/
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getDomain(){
		HttpServletRequest request = getHttpServletRequest();
		StringBuffer url = request.getRequestURL();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
	}

	public static String getOrigin(){
		HttpServletRequest request = getHttpServletRequest();
		return request.getHeader("Origin");
	}
}

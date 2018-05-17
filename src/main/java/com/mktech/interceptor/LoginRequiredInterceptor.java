/**
 * @author Chnyge Lin
 */
package com.mktech.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.ClearCommand;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mktech.dao.SnUserDao;
import com.mktech.dao.SnUserTicketDao;
import com.mktech.entity.HostHolder;
import com.mktech.entity.SnUser;
import com.mktech.entity.SnUserTicket;

/**
 * @author Chnyge Lin
 *
 */
public class LoginRequiredInterceptor implements HandlerInterceptor {
	
	@Resource
	private HostHolder hostHolder;
	
	/**
	 * 请求后拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	
		
	}

	/**
	 * 请求时拦截(渲染之前)
	 */
	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object, ModelAndView modelAndView) throws Exception {
	
	}

	/**
	 * 请求前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object arg2) throws Exception {
		if(hostHolder.getUser()==null){
//			httpServletResponse.sendRedirect("/login.do?next="+httpServletRequest.getRequestURL());
		}
		return true;
	}
	
	
}

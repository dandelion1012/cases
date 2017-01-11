package com.cases.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ResponseContentInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String retrContentType = request.getParameter("_con_type");
			MyHttpMessageConverter.setMTTL(retrContentType);
			if("json".equals(retrContentType)){
				request.setAttribute("accept", "application/json");
			}else if("xml".equals(retrContentType)){
				request.setAttribute("accept", "application/xml");
			}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
		// TODO Auto-generated method stub
//		Map<String, Object> model = mv.getModel();
//		ModelMap mm = mv.getModelMap();
		System.out.println(mv);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(handler);
	}

}

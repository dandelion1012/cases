package com.cases.interceptors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.util.concurrent.RateLimiter;

public class MyHandlerInterceptor implements HandlerInterceptor {
	private Map<String, RateLimiter> rlm = new ConcurrentHashMap<String, RateLimiter>();
	private ReentrantLock lock = new ReentrantLock();
	private RateLimiter getRateLimiter(String key){
		RateLimiter rl = rlm.get(key);
		if(rl == null){
			lock.lock();
			try {
				if(!rlm.containsKey(key)){
					rl = RateLimiter.create(10.0/60.0);
					rlm.put(key, rl);
				}
			} finally{
				lock.unlock();
			}
			
		}
		return rl;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String key = request.getRemoteAddr();
		getRateLimiter(key).acquire();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}

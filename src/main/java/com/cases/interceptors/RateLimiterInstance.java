package com.cases.interceptors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterInstance {
	private static RateLimiterInstance instance = new RateLimiterInstance();
	private RateLimiterInstance(){
		super();
	}
	public static RateLimiterInstance getInstance(){
		return instance;
	}
	private Map<String, RateLimiter> rlm = new ConcurrentHashMap<String, RateLimiter>();
	private ReentrantLock lock = new ReentrantLock();
	public RateLimiter getRateLimiter(String key){
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
}

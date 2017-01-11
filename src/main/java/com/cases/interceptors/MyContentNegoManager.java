package com.cases.interceptors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationStrategy;

public class MyContentNegoManager extends ContentNegotiationManager {

	public MyContentNegoManager() {
		super(convertToColl(new ArrayList<ContentNegotiationStrategy>()));
		// TODO Auto-generated constructor stub
	}

	public MyContentNegoManager(Collection<ContentNegotiationStrategy> strategies) {
		super(convertToColl(strategies));
		// TODO Auto-generated constructor stub
	}
 
	public MyContentNegoManager(ContentNegotiationStrategy... strategies) {
		super(convertToColl(Arrays.asList(strategies)).toArray(new ContentNegotiationStrategy[0]));
		// TODO Auto-generated constructor stub
	}
	private static Collection<ContentNegotiationStrategy> convertToColl(Collection<ContentNegotiationStrategy> strategies){
		List<ContentNegotiationStrategy> list = new ArrayList<ContentNegotiationStrategy>(strategies);
		list.add(new MyContentStrategy());
		return list;
	}
}

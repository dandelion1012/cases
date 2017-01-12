package com.cases.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.context.request.NativeWebRequest;

public class MyContentStrategy implements ContentNegotiationStrategy {

	@Override
	public List<MediaType> resolveMediaTypes(NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException {
		String retrContentType = webRequest.getParameter("_content_type");
		if(retrContentType != null){
			 MyHttpMessageConverter.setMTTL(retrContentType);
		}
		List<MediaType> list = new ArrayList<MediaType>();
		if("json".equals(retrContentType)){
			list.add(MediaType.APPLICATION_JSON);
		}else if("xml".equals(retrContentType)){
			list.add(MediaType.APPLICATION_XML);
		}else{
//			list.add(MediaType.ALL);
		}
		return list;
	}

}

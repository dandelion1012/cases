package com.cases.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

public class MyHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	private static class MyMappingJacksonHttpMessageConverter extends MappingJacksonHttpMessageConverter{
		public Object publicRead(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
			return super.readInternal(clazz, inputMessage);
		}
		public void publicWrite(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			super.writeInternal(object, outputMessage);
		}
	}
	private static class MyJaxb2RootElementHttpMessageConverter extends Jaxb2RootElementHttpMessageConverter{
		public Object publicRead(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
			return super.readInternal(clazz, inputMessage);
		}
		public void publicWrite(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			super.writeInternal(object, outputMessage);
		}
	}
	private MyMappingJacksonHttpMessageConverter mjhmc = new MyMappingJacksonHttpMessageConverter();
	private MyJaxb2RootElementHttpMessageConverter jrehmc = new MyJaxb2RootElementHttpMessageConverter();
	
	private static ThreadLocal<String> mtTL = new ThreadLocal<String>();
	
	public static void setMTTL (String mt){
		mtTL.set(mt);
	}
	@Override
	protected boolean supports(Class clazz) {
		// should not be called, since we override canRead/Write instead
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean canRead(Class clazz, MediaType mediaType) {
		String contType = mtTL.get();
		if("json".equals(contType)){
			return mjhmc.canRead(clazz, mediaType);
		}else if("xml".equals(contType)){
			return jrehmc.canRead(clazz, mediaType);
		}
		return false;
	}
	@Override
	public boolean canWrite(Class clazz, MediaType mediaType) {
		String contType = mtTL.get();
		if("json".equals(contType)){
			return mjhmc.canWrite(clazz, mediaType);
		}else if("xml".equals(contType)){
			return jrehmc.canWrite(clazz, mediaType);
		}
		return false;
	}
	
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		String contType = mtTL.get();
		List<MediaType> list = new ArrayList<MediaType>();
		if("json".equals(contType)){
			list.add(MediaType.APPLICATION_JSON);
		}else if("xml".equals(contType)){
			list.add(MediaType.APPLICATION_XML);
		}else{
			list.add(MediaType.ALL);
		}
		return list;
	}
	@Override
	protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		String contType = mtTL.get();
		if("json".equals(contType)){
			mjhmc.publicRead(clazz, inputMessage);
		}else if("xml".equals(contType)){
			jrehmc.publicRead(clazz, inputMessage);
		}
		return null;
		
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		String contType = mtTL.get();
		if("json".equals(contType)){
			mjhmc.publicWrite(t, outputMessage);
		}else if("xml".equals(contType)){
			jrehmc.publicWrite(t, outputMessage);
		}

	}

}

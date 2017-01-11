package com.cases.interceptors;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.AbstractXmlHttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;

public class MyHttpMessageConverter extends AbstractHttpMessageConverter {
	private MappingJacksonHttpMessageConverter mjhmc = new MappingJacksonHttpMessageConverter();
	private Jaxb2RootElementHttpMessageConverter jrehmc = new Jaxb2RootElementHttpMessageConverter();
	
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
		Method m = null;
		if("json".equals(contType)){
			try {
				m = mjhmc.getClass().getDeclaredMethod("readInternal", Class.class, HttpInputMessage.class);
				m.setAccessible(true);
				return m.invoke(mjhmc, clazz, inputMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("xml".equals(contType)){
			try {
				m = jrehmc.getClass().getDeclaredMethod("readInternal", Class.class, HttpInputMessage.class);
				m.setAccessible(true);
				return m.invoke(jrehmc, clazz, inputMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}

	@Override
	protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		String contType = mtTL.get();
		Method m = null;
		if("json".equals(contType)){
			try {
				m = mjhmc.getClass().getDeclaredMethod("writeInternal", Object.class, HttpOutputMessage.class);
				m.setAccessible(true);
				m.invoke(mjhmc, t, outputMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("xml".equals(contType)){
			try {
				m = AbstractXmlHttpMessageConverter.class.getDeclaredMethod("writeInternal", Object.class, HttpOutputMessage.class);
				m.setAccessible(true);
				m.invoke(jrehmc, t, outputMessage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}

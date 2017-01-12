package com.cases.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="person")
public class Person implements Serializable {	
	private static final long serialVersionUID = 7522321496316671267L;
	@XmlElement(name="id")
	public int id ;
	@XmlElement(name="name")
	public String name ;
	@XmlElement(name="age")
	public int age;
}

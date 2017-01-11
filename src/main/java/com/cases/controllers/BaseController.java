package com.cases.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cases.vo.Person;

@Controller
public class BaseController {

	@RequestMapping(value = "/")
	public String loginui() {
		return "index";
	}
	@RequestMapping(value = "/getPerson")
	@ResponseBody
	public Person getPerson(){
		Person per = new Person();
		per.name = "tom";
		per.age = 19;
		return per;
	}
	@RequestMapping(value = "/getJack")
	@ResponseBody
	public Person getPerson2(){
		Person per = new Person();
		per.name = "jack";
		per.age = 25;
		return per;
	}
}

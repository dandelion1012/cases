package com.cases.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cases.service.PersonService;
import com.cases.vo.Person;

@Controller
public class BaseController {
	@Resource
	private PersonService personService = null;
	
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
	@RequestMapping(value="/queryAllPerson", produces="application/json;charse=utf-8" )
	@ResponseBody
	public List<Person> queryAllPerson(){
		return personService.queryAllPerson();
	}
	@RequestMapping(value="/createPerson/{name}/{age}", produces="application/json;charse=utf-8" )
	@ResponseBody
	public Person createPerson(@PathVariable String name, @PathVariable int age){
		Person per = new Person();
		per.name = name;
		per.age = age;
		personService.insertPerson(per);
		return per;
	}
	@RequestMapping(value="/insertAndUpdate/{name}/{age}/{inc}", produces="application/json;charse=utf-8" )
	@ResponseBody
	public Person insertAndUpdatePerson(@PathVariable String name, @PathVariable int age, @PathVariable int inc){
		Person per = new Person();
		per.name = name;
		per.age = age;
		personService.insertAndUpdateAge(per, inc);
		return per;
	}
}

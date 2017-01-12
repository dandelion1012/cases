package com.cases.service;

import java.util.List;

import com.cases.vo.Person;

public interface PersonService {
	public void insertPerson(Person p) ;
	public Person queryPersonByID(int id);
	public List<Person> queryAllPerson();
	public void deletePersonByID(int id);
	public void updatePerson(Person p);
	public Person insertAndUpdateAge(Person p,  int inc);
}

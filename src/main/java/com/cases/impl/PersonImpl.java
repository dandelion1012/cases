package com.cases.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cases.dao.PersonDAO;
import com.cases.service.PersonService;
import com.cases.vo.Person;
@Service
@Transactional
public class PersonImpl implements PersonService {
	@Resource
	private PersonDAO personDao = null;
	@Override
	public void insertPerson(Person p) {
		personDao.insertPerson(p);

	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Person queryPersonByID(int id) {
		return personDao.queryPerson(id);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public List<Person> queryAllPerson() {
		return personDao.queryAllPerson();
	}

	@Override
	public void deletePersonByID(int id) {
		personDao.deletePersonByID(id);

	}

	@Override
	public void updatePerson(Person p) {
		personDao.updatePerson(p);

	}

	
}

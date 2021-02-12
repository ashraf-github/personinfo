package com.personinfo.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.personinfo.api.data.Person;
import com.personinfo.entity.PersonEntity;
import com.personinfo.persistence.PersonDao;

@Singleton
public class PersonServiceImpl implements PersonService {

	@Inject
	private PersonDao PersonDao;
	
	public Person retrieve(Long id) {
		return entityToPerson(PersonDao.retrieve(id));
	}

    public void delete(Long id) {
        PersonDao.delete(id);
    }

	public void save(Person Person) {
		PersonDao.save(PersonToEntity(Person));
	}

	// =========== Helpers ================

	private Person entityToPerson(PersonEntity entity) {
		Person person = new Person();

		if (entity != null) {
			person.setFirstName(entity.getFirstName());
			person.setLastName(entity.getLastName());
		}

		return person;
	}

	private PersonEntity PersonToEntity(Person person) {
		PersonEntity entity = new PersonEntity();

		if (person != null) {
			entity.setFirstName(person.getFirstName());
			entity.setLastName(person.getLastName());
		}

		return entity;
	}

	public void save(PersonService person) {
		// TODO Auto-generated method stub
		
	}
}

package com.personinfo.service;

import com.personinfo.api.data.Person;

public interface PersonService {

	public Person retrieve(Long id);

    public void delete(Long id);
	
	public void save(Person person);
}

package com.personinfo.persistence;

import com.personinfo.entity.PersonEntity;

public interface PersonDao {
	
	public PersonEntity retrieve(Long id);

    public void delete(Long id);
	
	public void save(PersonEntity Person);
}

package com.personinfo.config;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.personinfo.persistence.PersonDao;
import com.personinfo.persistence.PersonDaoImpl;
import com.personinfo.service.PersonService;
import com.personinfo.service.PersonServiceImpl;

public class ApplicationBinder extends AbstractBinder {
	
    @Override
    protected void configure() {
    	// services
        bind(PersonServiceImpl.class).to(PersonService.class).in(Singleton.class);
        
        // dao
        bind(PersonDaoImpl.class).to(PersonDao.class).in(Singleton.class);
    }
}
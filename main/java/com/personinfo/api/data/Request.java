package com.personinfo.api.data;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {

	private static final long serialVersionUID = -5183069099661541090L;
	
	private List<Person> person;

	public List<Person> getPerson() {
		return person;
	}

	public void setPerson(List<Person> person) {
		this.person = person;
	}

}

package com.personinfo.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.personinfo.api.data.Person;
import com.personinfo.service.PersonService;

@Path(value = "person")
public class PersonResource {

	private final PersonService personService;

	@Inject
	public PersonResource(PersonService personService) {
		this.personService = personService;
	}

	@GET
	@Path(value = "retrieve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("id") Long id) {
		return personService.retrieve(id);
	}

    @POST
    @Path(value = "delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePerson(Long id) {
        personService.delete(id);

        return Response.status(Status.OK).entity("person has been successfully deleted").type(MediaType.APPLICATION_JSON).build();
    }
	
	@POST
	@Path(value = "save")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response savePerson(Person person) {
		personService.save(person);
		
		return Response.status(Status.OK).entity("person has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
}

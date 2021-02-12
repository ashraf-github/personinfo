package au.com.example.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.personinfo.api.PersonResource;
import com.personinfo.api.data.Person;
import com.personinfo.service.PersonService;

public class PersonResourceTest extends JerseyTest {

	@Mock
	private PersonService serviceMock;

	/**
	 * This is executed only once, not before each test.
	 * 
	 * This will enable Mockito Annotations to be used.
	 * This will enable log traffic and message dumping.
	 * This will register the Injectable Provider to the ResourceConfiguration which will
	 * allow for the mock objects and jersey test to be linked.
	 */
	@Override
	protected Application configure() {
		MockitoAnnotations.initMocks(this);
		
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		
		ResourceConfig config = new ResourceConfig(PersonResource.class);
		config.register(new InjectableProvider());

		return config;
	}

	/**
	 * Invoke the retrieve Person and check the http response is 200.
	 */
	@Test
	public void testPersonRetrieveResponse() {
		
		when(serviceMock.retrieve(Mockito.anyLong())).thenReturn(getMockPerson());

		Response response = target("Person/retrieve/1").request().get();

		Person Person = response.readEntity(Person.class);
		
		assertEquals(200, response.getStatus());
		assertEquals("1", Person.getAge().toString());
		assertEquals("Robert", Person.getFirstName());
		assertEquals("Leggett", Person.getLastName());
	}

    /**
     * Invoke the delete Person and check the http response is 200.
     */
    @Test
    public void testPersonDeleteResponse() {

        Entity<String> PersonId = Entity.entity(getMockPerson().getAge(), MediaType.APPLICATION_JSON_TYPE);

		doNothing().when(serviceMock).save(Mockito.any(Person.class));

        Response response = target("Person/delete").request().post(PersonId);

        assertEquals(200, response.getStatus());
        assertEquals("Person has been successfully deleted", response.readEntity(String.class));
    }
	
	/**
	 * Invoke the save Person and check the http response is 200.
	 */
	@Test
	public void testPersonSaveResponse() {
		
	    Entity<Person> Person = Entity.entity(getMockPerson(), MediaType.APPLICATION_JSON_TYPE);
	    
		doNothing().when(serviceMock).save(Mockito.any(Person.class));

		Response response = target("Person/save").request().post(Person);

		assertEquals(200, response.getStatus());
		assertEquals("Person has been successfully saved", response.readEntity(String.class));
	}

	// ======= Mocking ==========
	
	/**
	 * Mock object that will be returned
	 * 
	 * @return the Person object
	 */
	private Person getMockPerson() {
		Person Person = new Person();
		Person.setFirstName("Robert");
		Person.setLastName("Leggett");
		
		return Person;
	}

	/**
	 * Create an Injectable Provider that with bind this factory to the Person service.
	 * When the provide is invoked a mock service object will be returned.
	 * When dispose is invoked the mock service object will be assigned null.
	 * 
	 * @author Robert Leggett
	 *
	 */
	class InjectableProvider extends AbstractBinder implements Factory<PersonService> {
		
		@Override
		protected void configure() {
			bindFactory(this).to(PersonService.class).in(Singleton.class);
		}

		public PersonService provide() {
			return serviceMock;
		}

		public void dispose(PersonService service) {
			serviceMock = null;
		}
	}
}

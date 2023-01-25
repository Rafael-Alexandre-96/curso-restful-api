package br.com.curso.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.curso.model.Person;

@Service
public class PersonServices {
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public Person findById(String id) {
		logger.info("Finding new person!");
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFistName("Rafael");
		person.setLastName("Alexandre");
		person.setAddress("Rua das Oliveiras");
		person.setGender("Male");
		return person;
	}
	
	public List<Person> findAll() {
		logger.info("Finding all people!");
		List<Person> persons = new ArrayList<>();
		for(int i = 0; i  < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons;
	}
	
	public Person create(Person person) {
		logger.info("Creating one person!");
		return person;
	}
	
	public Person update(Person person) {
		logger.info("Updating one person!");
		return person;
	}
	
	public void delete(String id) {
		logger.info("Deleting one person!");
	}
	
	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFistName("Name " + i);
		person.setLastName("Last " + i);
		person.setAddress("Rua " + i);
		person.setGender("Male " + i);
		return person;
	}
	
}

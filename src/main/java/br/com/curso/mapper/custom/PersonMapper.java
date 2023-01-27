package br.com.curso.mapper.custom;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import br.com.curso.data.vo.v2.PersonVOV2;
import br.com.curso.model.Person;

@Service
public class PersonMapper {
	
	public PersonVOV2 convertEntityToVO(Person person) {
		PersonVOV2 vo = new PersonVOV2();
		vo.setId(person.getId());
		vo.setAddress(person.getAddress());
		vo.setFirstName(person.getFirstName());
		vo.setLastName(person.getLastName());
		vo.setGender(person.getGender());
		vo.setBirthDay(OffsetDateTime.now());
		return vo;
	}
	
	public Person convertVOToEntity(PersonVOV2 personVOV2) {
		Person entity = new Person();
		entity.setId(personVOV2.getId());
		entity.setAddress(personVOV2.getAddress());
		entity.setFirstName(personVOV2.getFirstName());
		entity.setLastName(personVOV2.getLastName());
		entity.setGender(personVOV2.getGender());
		//entity.setBirthDay(OffsetDateTime.now());
		return entity;
	}
}

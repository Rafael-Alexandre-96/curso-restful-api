package br.com.curso.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.curso.data.vo.v1.PersonVO;
import br.com.curso.data.vo.v2.PersonVOV2;
import br.com.curso.exceptions.ResourceNotFoundException;
import br.com.curso.mapper.DozerMapper;
import br.com.curso.mapper.custom.PersonMapper;
import br.com.curso.model.Person;
import br.com.curso.repositories.PersonRepository;

@Service
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PersonMapper mapper;
	
	public PersonVO findById(Long id) {
		logger.info("Finding new person!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public List<PersonVO> findAll() {
		logger.info("Finding all people!");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO create(PersonVO personVO) {
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(personVO, Person.class);
		var vo = repository.save(entity);
		return DozerMapper.parseObject(vo, PersonVO.class);
	}
	
	public PersonVOV2 createV2(PersonVOV2 personVOV2) {
		logger.info("Creating one person! - V2");
		var entity = mapper.convertVOToEntity(personVOV2);
		var vo = repository.save(entity);
		return mapper.convertEntityToVO(vo);
	}
	
	public PersonVO update(PersonVO personVO) {
		logger.info("Updating one person!");
		
		var entity = repository.findById(personVO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		entity.setFirstName(personVO.getFirstName());
		entity.setLastName(personVO.getLastName());
		entity.setAddress(personVO.getAddress());
		entity.setGender(personVO.getGender());
		
		var vo = repository.save(entity);
		return DozerMapper.parseObject(vo, PersonVO.class);
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
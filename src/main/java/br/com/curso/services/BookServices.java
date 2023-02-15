package br.com.curso.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.curso.controllers.BookController;
import br.com.curso.data.vo.v1.BookVO;
import br.com.curso.exceptions.RequiredObjectIsNullException;
import br.com.curso.exceptions.ResourceNotFoundException;
import br.com.curso.mapper.DozerMapper;
import br.com.curso.model.Book;
import br.com.curso.repositories.BookRepository;

@Service
public class BookServices {
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	@Autowired
	PagedResourcesAssembler<BookVO> assembler;
	
	public BookVO findById(Long id) {
		logger.info("Finding new book!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
		logger.info("Finding all books!");
		var bookPage = repository.findAll(pageable);
		var bookVosPage = bookPage.map(book -> DozerMapper.parseObject(book, BookVO.class));
		bookVosPage.map(book -> book.add(linkTo(methodOn(BookController.class).findById(book.getKey())).withSelfRel()));
		Link link = linkTo(methodOn(BookController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(bookVosPage, link);
	}
	
	public BookVO create(BookVO bookVO) {
		if (bookVO == null) throw new RequiredObjectIsNullException();
		logger.info("Creating one book!");
		var entity = DozerMapper.parseObject(bookVO, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO bookVO) {
		if (bookVO == null) throw new RequiredObjectIsNullException();
		logger.info("Updating one book!");
		var entity = repository.findById(bookVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		entity.setAuthor(bookVO.getAuthor());
		entity.setLaunchDate(bookVO.getLaunchDate());
		entity.setPrice(bookVO.getPrice());
		entity.setTitle(bookVO.getTitle());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);;
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one book!");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		repository.delete(entity);
	}
}
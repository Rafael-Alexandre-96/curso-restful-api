package br.com.curso.unittests.mapper.mocks;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.curso.data.vo.v1.BookVO;
import br.com.curso.model.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookVO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 10; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList() {
        List<BookVO> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            books.add(mockVO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(OffsetDateTime.parse("2023-01-2" + number + "T00:00:00-03:00"));
        book.setPrice(10D * number);
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

    public BookVO mockVO(Integer number) {
        BookVO book = new BookVO();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(OffsetDateTime.parse("2023-01-2" + number + "T00:00:00-03:00"));
        book.setPrice(10D * number);
        book.setKey(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }

}

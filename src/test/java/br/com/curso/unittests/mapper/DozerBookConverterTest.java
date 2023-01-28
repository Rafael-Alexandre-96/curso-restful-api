package br.com.curso.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.curso.data.vo.v1.BookVO;
import br.com.curso.mapper.DozerMapper;
import br.com.curso.model.Book;
import br.com.curso.unittests.mapper.mocks.MockBook;

public class DozerBookConverterTest {
    
    MockBook inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() {
        BookVO output = DozerMapper.parseObject(inputObject.mockEntity(), BookVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Author Test0", output.getAuthor());
        assertEquals(OffsetDateTime.parse("2023-01-20T00:00:00-03:00"), output.getLaunchDate());
        assertEquals(0D, output.getPrice());
        assertEquals("Title Test0", output.getTitle());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<BookVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), BookVO.class);
        BookVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertEquals(OffsetDateTime.parse("2023-01-20T00:00:00-03:00"), outputZero.getLaunchDate());
        assertEquals(0D, outputZero.getPrice());
        assertEquals("Title Test0", outputZero.getTitle());
        
        BookVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertEquals(OffsetDateTime.parse("2023-01-27T00:00:00-03:00"), outputSeven.getLaunchDate());
        assertEquals(70D, outputSeven.getPrice());
        assertEquals("Title Test7", outputSeven.getTitle());
    }

    @Test
    public void parseVOToEntityTest() {
        Book output = DozerMapper.parseObject(inputObject.mockVO(), Book.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Author Test0", output.getAuthor());
        assertEquals(OffsetDateTime.parse("2023-01-20T00:00:00-03:00"), output.getLaunchDate());
        assertEquals(0D, output.getPrice());
        assertEquals("Title Test0", output.getTitle());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Book> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Book.class);
        Book outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertEquals(OffsetDateTime.parse("2023-01-20T00:00:00-03:00"), outputZero.getLaunchDate());
        assertEquals(0D, outputZero.getPrice());
        assertEquals("Title Test0", outputZero.getTitle());
        
        Book outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertEquals(OffsetDateTime.parse("2023-01-27T00:00:00-03:00"), outputSeven.getLaunchDate());
        assertEquals(70D, outputSeven.getPrice());
        assertEquals("Title Test7", outputSeven.getTitle());
    }
}

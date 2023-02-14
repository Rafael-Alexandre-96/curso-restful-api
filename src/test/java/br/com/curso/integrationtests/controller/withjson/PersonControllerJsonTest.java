package br.com.curso.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.curso.configs.TestConfigs;
import br.com.curso.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.curso.integrationtests.vo.AccountCredentialsVO;
import br.com.curso.integrationtests.vo.PersonVO;
import br.com.curso.integrationtests.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonVO();
	}
	
	@Test
	@Order(0)
	void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
				.when().post().then().statusCode(200).extract().body().as(TokenVO.class).getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

	@Test
	@Order(1)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
						.spec(specification)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_RAFAEL)
						.body(person)
						.when().post().then().statusCode(201).extract().body().asString();
		
		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
		person = createdPerson;
		
		Assertions.assertNotNull(createdPerson);
		Assertions.assertNotNull(createdPerson.getId());
		Assertions.assertNotNull(createdPerson.getFirstName());
		Assertions.assertNotNull(createdPerson.getLastName());
		Assertions.assertNotNull(createdPerson.getAddress());
		Assertions.assertNotNull(createdPerson.getGender());
		Assertions.assertTrue(createdPerson.getId() > 0);
		Assertions.assertEquals("Richard", createdPerson.getFirstName());
		Assertions.assertEquals("Stallman", createdPerson.getLastName());
		Assertions.assertEquals("New York", createdPerson.getAddress());
		Assertions.assertEquals("Male", createdPerson.getGender());
	}
	
	@Test
	@Order(2)
	void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
						.spec(specification)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_OUTRO)
						.body(person)
						.when().post().then().statusCode(403).extract().body().asString();
		
		Assertions.assertNotNull(content);
		Assertions.assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(3)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
						.spec(specification)
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_RAFAEL)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.pathParam("id", person.getId())
						.when().get("{id}").then().statusCode(200).extract().body().asString();
		
		PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
		person = createdPerson;
		
		Assertions.assertNotNull(createdPerson);
		Assertions.assertNotNull(createdPerson.getId());
		Assertions.assertNotNull(createdPerson.getFirstName());
		Assertions.assertNotNull(createdPerson.getLastName());
		Assertions.assertNotNull(createdPerson.getAddress());
		Assertions.assertNotNull(createdPerson.getGender());
		Assertions.assertTrue(createdPerson.getId() > 0);
		Assertions.assertEquals("Richard", createdPerson.getFirstName());
		Assertions.assertEquals("Stallman", createdPerson.getLastName());
		Assertions.assertEquals("New York", createdPerson.getAddress());
		Assertions.assertEquals("Male", createdPerson.getGender());
	}
	
	@Test
	@Order(4)
	void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given()
						.spec(specification)
						.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_OUTRO)
						.contentType(TestConfigs.CONTENT_TYPE_JSON)
						.pathParam("id", person.getId())
						.when().get("{id}").then().statusCode(403).extract().body().asString();
		
		Assertions.assertNotNull(content);
		Assertions.assertEquals("Invalid CORS request", content);
	}
	
	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York");
		person.setGender("Male");
	}
}
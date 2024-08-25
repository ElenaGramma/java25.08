package telran.cars.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static telran.dto.State.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import telran.dto.Car;
import telran.dto.Model;
import telran.service.IRentCompany;
@WebMvcTest
@RequiredArgsConstructor
class RentCompanyAdministratorControllerTest {
	

	@MockBean
	IRentCompany service;

	@Autowired
	MockMvc mock;
	@Autowired
	ObjectMapper mapper;

	@Test
	void testAddCar() throws  Exception {
		Car car1=new Car("12","red","BMW");
		mock.perform(post("/car/add").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(car1)))
				.andExpect(status().isOk());
	}

	@Test
	void testAddModel() throws Exception, Exception {
		Model model1=new Model();
		mock.perform(post("/model/add").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(model1)))
				.andExpect(status().isOk());
	}

	@Test
	void testRemoveCar() throws Exception {
		mock.perform(delete("/car/remove?regNumber=12")).andExpect(status().isOk());
	}

	@Test
	void testRemoveModel() throws Exception {
		mock.perform(delete("/model/remove?modelName=BMW")).andExpect(status().isOk());
	}

}

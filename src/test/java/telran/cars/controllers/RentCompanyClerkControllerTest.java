package telran.cars.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import telran.dto.Driver;
import telran.dto.Model;
import telran.service.IRentCompany;

@WebMvcTest
@RequiredArgsConstructor
class RentCompanyClerkControllerTest {

	@MockBean
	IRentCompany service;

	@Autowired
	MockMvc mock;
	@Autowired
	ObjectMapper mapper;
	@Test
	void testAddDriver() throws JsonProcessingException, Exception {
		Driver driver1=new Driver(1L, "driver", 25, "0532489663");
		mock.perform(post("/driver/add").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(driver1)))
				.andExpect(status().isOk());
	}

//	@Test
//	void testRentCar() {
//		
//	}

////	@Test
////	void testReturnCar() {
//
////	}

	@Test
	void testGetCarsByModel() throws JsonProcessingException, Exception {
		String model="BMW";
		Car car=new Car("12", "red", model);
		when(service.getCarsByModel(model)).thenReturn(new ArrayList<Car>(List.of(car)));
		mock.perform(get("/cars/{modelName}",model).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(model)))
				.andExpect(status().isOk());
	}

}

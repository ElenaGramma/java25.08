package telran.cars.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
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
import telran.service.IRentCompany;

@WebMvcTest
@RequiredArgsConstructor
class RentCompanyTechnicianControllerTest {
	@MockBean
	IRentCompany service;

	@Autowired
	MockMvc mock;
	@Autowired
	ObjectMapper mapper;

//	@Test
//	void testGetCarsByDriver() throws JsonProcessingException, Exception {
//		Long licenseId=1L;
//		Car car=new Car("12", "red", "BMW");
//		when(service.getCarsByDriver(licenseId)).thenReturn(new HashSet<Car>());
//		mock.perform(get("/driver/{licenseId}/cars").contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString()))
//				.andExpect(status().isOk());
//	}
//
//	@Test
//	void testGetDriversByCar() {
//		
//	}
//
//	@Test
//	void testGetRentRecordsAtDates() {
//		
//	}

}

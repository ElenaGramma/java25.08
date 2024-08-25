package telran.cars.controllers;

import static org.assertj.core.api.Assertions.linesOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import telran.dto.Driver;
import telran.dto.Model;
import telran.service.IRentCompany;

@WebMvcTest
@RequiredArgsConstructor
class RentCompanyStatisticControllerTest {

	@MockBean
	IRentCompany service;

	@Autowired
	MockMvc mock;
	@Autowired
	ObjectMapper mapper;

	@Test
	void testGetMostActiveDrivers() throws Exception {
		List<Driver> res = new ArrayList<Driver>(List.of(new Driver()));

		when(service.getMostActiveDrivers()).thenReturn(res);
		String actual = mock.perform(get("http://localhost:8080/drivers/active")).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		assertEquals(mapper.writeValueAsString(res), actual);
	}

	@Test
	void testGetMostPopularModels() throws Exception {
		List<String> res=new ArrayList<String>();
		when(service.getMostPopularCarModels(LocalDate.of(2024, 3, 12) , LocalDate.of(2024, 3, 22), 22, 45)).thenReturn(res);
		String actual = mock.perform(get("http://localhost:8080/models/popular/{dateFrom}/{dateTo}/{ageFrom}/{ageTo}"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		assertEquals(mapper.writeValueAsString(res), actual);
	}

	@Test
	void testGetMostProfitableCarModels() throws Exception {
		fail("Not yet implemented");
	}

}

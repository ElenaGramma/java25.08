package telran.cars.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.dto.Car;
import telran.dto.Driver;
import telran.dto.Model;
import telran.service.IRentCompany;
import telran.service.RentCompanyEmbedded;

class RentCompaniStatistics {
	static final String MODEL = "model";
	static final String CAR = "car";
	IRentCompany company = new RentCompanyEmbedded();
	int[] years = { 2000, 1995, 1950, 1945 };
	int[] prices = { 100, 100, 100, 100, 1000 };
	LocalDate fromDate = LocalDate.of(1900, 1, 1);
	LocalDate toDate = LocalDate.of(2030, 1, 1);
	LocalDate rentDate = LocalDate.of(2020, 1, 1);
	int rentDays = 5;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		createModels();
		createCars();
		createDrivers();
		rentReturn();
	}

	private void rentReturn() {
		int[] drivers = { 1, 1, 2, 2, 3, 3, 4, 4, 1 };
		String[] cars = { CAR + 0, CAR + 1, CAR + 0, CAR + 1, CAR + 2, CAR + 3, CAR + 2, CAR + 3, CAR + 4 };
		for (int i = 0; i < drivers.length; i++) {
			company.rentCar(cars[i], drivers[i], rentDate, rentDays);
			company.returnCar(cars[i], drivers[i], rentDate.plusDays(rentDays), 0, 100);
			rentDate = rentDate.plusDays(rentDays + 1);
			
		}
	}

	private void createDrivers() {
		for (int i = 0; i < years.length; i++) {
			company.addDriver(new Driver(i+1, "name", years[i], "phone"));
		}
	}

	private void createCars() {
		for (int i = 0; i < prices.length; i++) {
			company.addCar(new Car(CAR + i, "color", MODEL + i));
		}

	}

	private void createModels() {
		for (int i = 0; i < prices.length; i++) {
			company.addModel(new Model(MODEL + i, "company", "country", 50, prices[i]));
		}

	}

	@Test
	void testgetMostPopularCarModels() {
		int ageYoungFrom=rentDate.getYear()-years[0];
		int ageYoungTo=rentDate.getYear()-years[1]+1;
		int ageOldFrom=rentDate.getYear()-years[0];
		int ageOldTo=rentDate.getYear()-years[1]+1;
		List<String> res=company.getMostPopularCarModels(fromDate, toDate, ageYoungFrom, ageYoungTo);
		assertEquals(2, res.size());
		assertTrue(res.contains(MODEL+0));
		assertTrue(res.contains(MODEL+1));
		 res=company.getMostPopularCarModels(fromDate, toDate, ageOldFrom, ageOldTo);
		assertEquals(2, res.size());
		assertTrue(res.contains(MODEL+2));
		assertTrue(res.contains(MODEL+3));
		res=company.getMostPopularCarModels(fromDate, LocalDate.of(2010, 1, 1), ageOldFrom, ageOldTo);
	assertTrue(res.isEmpty());
	}
	@Test
	void testGetMostProfitableCarModels() {
		List<String> res=company.getMostProfitableCarModels(toDate, fromDate);
		assertEquals(1, res.size());
		assertTrue(res.contains(MODEL+4));
		res=company.getMostProfitableCarModels(toDate,  LocalDate.of(2010, 1, 1));
		assertTrue(res.isEmpty());
	}
	@Test
	void testGetMostActiveDrivers() {
		List<Driver> res=company.getMostActiveDrivers();
		assertEquals(1, res.size());
		assertEquals(1, res.get(0).getLicenseId());
		
		
	}

}

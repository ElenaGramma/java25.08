package telran.cars.test;

import static org.junit.jupiter.api.Assertions.*;
import static telran.dto.CarsReturnCode.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import telran.dto.Car;
import telran.dto.Driver;
import telran.dto.Model;
import telran.service.IRentCompany;

import java.time.LocalDate;


@SpringBootTest
class RentCompanyEmbeddedTest {
	
	static final String MODEL_NAME="BMW x5";
	static final String REG_NUMBER="123";
	static final long LICENSE_ID=123;
	static Model model=new Model(MODEL_NAME,"BMW","Germany",50,100);
	static Car car = new Car(REG_NUMBER, "red", MODEL_NAME);
	static Driver driver = new Driver(LICENSE_ID, "Max", 1980, "223322223");
	
	
	static final LocalDate RENT_DAY=LocalDate.of(2020, 10, 10);
	static IRentCompany company;
	
	@Autowired
	ApplicationContext context;
	
//	@BeforeAll
//	static void setUpBeforeClass()throws Exception{
//		company=new RentCompanyEmbedded();
//		company.addModel(model);
//		company.addCar(car);
//		company.addDriver(driver);
//		company.rentCar(REG_NUMBER, LICENSE_ID, RENT_DAY, 10);
//		((RentCompanyEmbedded)company).save(FILE_NAME);
//	}
	@BeforeEach
	void setUp() throws Exception {
		company=context.getBean("RentCompanyServiceTest",IRentCompany.class);
		company.addModel(model);
		company.addCar(car);
		company.addDriver(driver);
		company.rentCar(REG_NUMBER, LICENSE_ID, RENT_DAY, 10);
		
	}
	

	@Test
	void testAddModel() {
		assertEquals(WRONG_DATA, company.addModel(null));
		assertEquals(MODEL_EXISTS, company.addModel(model));
		assertEquals(OK, company.addModel(new Model(MODEL_NAME+"1","BMW","Germany",50,100)));
	}

	@Test
	void testAddCar() {
		assertEquals(WRONG_DATA, company.addCar(null));
		assertEquals(NO_MODEL, company.addCar(new Car(REG_NUMBER+"1", "red", MODEL_NAME+"1")));
		assertEquals(CAR_EXISTS, company.addCar(car));
		assertEquals(OK, company.addCar(new Car(REG_NUMBER+"1", "red", MODEL_NAME)));
		
	}

	@Test
	void testAddDriver() {
		assertEquals(WRONG_DATA, company.addDriver(null));
		assertEquals(DRIVER_EXISTS, company.addDriver(driver));
		assertEquals(OK, company.addDriver(new Driver(LICENSE_ID+1, "Max", 1980, "223322223")));
	}

	@Test
	void testGetModel() {
		assertNull(company.getModel(null));
		assertNull(company.getModel(MODEL_NAME+"1"));
		assertEquals(model, company.getModel(MODEL_NAME));
	}

	@Test
	void testGetCar() {
		assertNull(company.getCar(null));
		assertNull(company.getCar(REG_NUMBER+"1"));
		assertEquals(car, company.getCar(REG_NUMBER));
	}

	@Test
	void testGetDriver() {
		assertNull(company.getDriver(0));
		assertNull(company.getDriver(-1));
		assertNull(company.getDriver(LICENSE_ID+1));
		assertEquals(driver, company.getDriver(LICENSE_ID));
	}
	@Test
	void testRentCar() {
		
		assertEquals(WRONG_DATA,company.rentCar(null, LICENSE_ID, RENT_DAY, 10));
		assertEquals(WRONG_DATA,company.rentCar(REG_NUMBER, 0, RENT_DAY, 10));
		assertEquals(WRONG_DATA,company.rentCar(REG_NUMBER, LICENSE_ID, null, 10));
		assertEquals(WRONG_DATA,company.rentCar(REG_NUMBER, LICENSE_ID, RENT_DAY, 0));
		assertEquals(NO_CAR,company.rentCar(REG_NUMBER+"1", LICENSE_ID, RENT_DAY, 10));
		assertEquals(NO_DRIVER,company.rentCar(REG_NUMBER, LICENSE_ID+1, RENT_DAY, 10));
		assertEquals(CAR_IN_USE,company.rentCar(REG_NUMBER, LICENSE_ID, RENT_DAY, 10));
		Car car1=new Car(REG_NUMBER+"1", "red", MODEL_NAME);
		company.addCar(car1);
		car1.setFlagRemoved(true);
		assertEquals(CAR_REMOVED,company.rentCar(REG_NUMBER+"1", LICENSE_ID, RENT_DAY, 10));
		
	}

}

package telran.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import telran.dto.Car;
import telran.dto.CarsReturnCode;
import telran.dto.Driver;
import telran.dto.RemovedCarData;
import telran.dto.ReturnCarData;
import telran.service.IRentCompany;

@RestController
@Slf4j
public class RentCompanyClerkController {
	
	private <T> void logging(String methodName, T data)
	{
		log.debug(String.format("%s: receiver data: {}", methodName), data);
	}
	@Autowired
	IRentCompany service;
	
	@PostMapping("/driver/add")
	CarsReturnCode addDriver(@RequestBody Driver driver) {
		logging("addDriver", driver);
		return service.addDriver(driver);
	}
	@PostMapping("/car/rent")
	CarsReturnCode rentCar(@RequestBody @Valid ReturnCarData rcd) {
		logging("rentCar", rcd);
		return service.rentCar(rcd.getRegNumber(), rcd.getLicenseId(), rcd.getReturnDate(), rcd.getTankPercent());
	}
	@GetMapping("/car/return")
	RemovedCarData returnCar(@RequestBody @Valid ReturnCarData rcd) {
		logging("returnCar", rcd);
		return service.returnCar(rcd.getRegNumber(), rcd.getLicenseId(), 
				rcd.getReturnDate(), rcd.getdamagesPercent(), rcd.getTankPercent() );
	}
	@GetMapping("/cars/{modelName}")
	List<Car> getCarsByModel(@RequestBody @Valid String modelName) {
		logging("getCarsByModel", modelName);
		return service.getCarsByModel(modelName);
	}

}

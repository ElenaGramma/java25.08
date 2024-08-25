package telran.controllers;

import static telran.service.RentCompanyLocks.lockGetCar;
import static telran.service.RentCompanyLocks.lockGetDriver;
import static telran.service.RentCompanyLocks.lockGetModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import telran.dto.Car;
import telran.dto.CarsReturnCode;
import telran.dto.Driver;
import telran.dto.Model;
import telran.dto.RemovedCarData;
import telran.service.IRentCompany;

@RestController
@Slf4j
public class RentCompanyAdministratorController {
	private <T> void logging(String methodName, T data)
	{
		log.debug(String.format("%s: receiver data: {}", methodName), data);
	}
	@Autowired
	IRentCompany service;
	
	@PostMapping("/car/add")
	CarsReturnCode addCar(@RequestBody Car car) {
		logging("addCar", car);
		return service.addCar(car);
	}
	@PostMapping("/model/add")
	CarsReturnCode addModel(@RequestBody Model model) {
		logging("addModel", model);
		return service.addModel(model);
	}
	@DeleteMapping("/car/remove/{regNumber}")
	RemovedCarData removeCar(@RequestParam @Valid @PathVariable String regNumber) {
		logging("removeCar", regNumber);
		return service.removeCar(regNumber);
	}
	@DeleteMapping("/model/remove/{modelName}")
	List<RemovedCarData> removeModel(@RequestParam @Valid @PathVariable String modelName){
		logging("removeModel", modelName);
		return service.removeModel(modelName);
	}
	@GetMapping("/model/{modelName}")
	Model getModel(@PathVariable String modelName) {
		logging("getModel", modelName);
		return service.getModel(modelName);
	}
	@GetMapping("/car/{regNumber}")
	 Car getCar(@PathVariable String regNumber) {
		logging("getCar", regNumber);

		return service.getCar(regNumber);
	}

	@GetMapping("/driver/{licenseId}")
	  Driver getDriver(@PathVariable long licenseId) {
		logging("getDriver", licenseId);
		  return service.getDriver(licenseId);
		}

}

package telran.controllers;

import static telran.api.RentCompanyErrorMessage.DATE_IS_NULL;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import telran.dto.Car;
import telran.dto.Driver;
import telran.dto.RentRecord;
import telran.service.IRentCompany;

@RestController
@Slf4j
public class RentCompanyTechnicianController {
	private <T> void logging(String methodName, T data)
	{
		log.debug(String.format("%s: receiver data: {}", methodName), data);
	}
	@Autowired
	IRentCompany service;
	
	@GetMapping("/driver/{licenseId}/cars")
	Set<Car> getCarsByDriver(@PathVariable @NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "/[^0-9]/") long licenseId){
		logging("getCarsByDriver", licenseId);
		return service.getCarsByDriver(licenseId);
	}
	@GetMapping("/car/{regNumber}/drivers")
	Set<Driver> getDriversByCar(@PathVariable @Valid String regNumber){
		logging("getDriversByCar", regNumber);
		return service.getDriversByCar(regNumber);
	}
	@GetMapping("/records/{from}/{to}")
	List<RentRecord> getRentRecordsAtDates(@PathVariable @Valid String from, String to){
		logging("getRentRecordsAtDates", new RentRecord());
		LocalDate from1=LocalDate.parse(from);
		LocalDate to1=LocalDate.parse(to);
		return service.getRentRecordsAtDates(from1, to1);
	}

}











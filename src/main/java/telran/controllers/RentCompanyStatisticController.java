package telran.controllers;



import static telran.api.RentCompanyErrorMessage.DATE_IS_NOT_PAST;
import static telran.api.RentCompanyErrorMessage.DATE_IS_NULL;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import telran.dto.Driver;
import telran.dto.StatisticsData;
import telran.service.IRentCompany;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@Slf4j
public class RentCompanyStatisticController {

	private <T> void logging(String methodName, T data)
	{
		log.debug(String.format("%s: receiver data: {}", methodName), data);
	}
	@Autowired
	IRentCompany service;
	
	@GetMapping("drivers/active")
	List<Driver> getMostActiveDrivers(){
		logging("getMostActiveDrivers", null);
		return service.getMostActiveDrivers();
	}
//	@DateTimeFormat(iso = ISO.DATE)
	@GetMapping("/models/popular/")
	List<String> getMostPopularModels(@RequestBody @Valid StatisticsData data){
		
		logging("getMostActiveDrivers", new StatisticsData(data.getFromDate(),data.getToDate(),data.getFromAge(),data.getToAge()));
		return service.getMostPopularCarModels(data.getFromDate(),data.getToDate(),data.getFromAge(),data.getToAge());
	}
	
	@DateTimeFormat(iso = ISO.DATE)
	@GetMapping("/models/rofitable/{dateFrom}/{dateTo}")
	List<String> getMostProfitableCarModels(@PathVariable @NotNull(message =  DATE_IS_NULL)
	@Past(message = DATE_IS_NOT_PAST)
	@Pattern(regexp = "\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|(3[01]))") String dateFrom, @PathVariable @NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|(3[01]))") String dateTo){
		LocalDate fromDate=LocalDate.parse(dateFrom);
		LocalDate toDate=LocalDate.parse(dateTo);
		logging("getMostProfitableCarModels", new StatisticsData(fromDate,toDate,0,0));
		return service.getMostProfitableCarModels(fromDate, fromDate);
	}
	
	
}

package telran.dto;

import static telran.api.RentCompanyErrorMessage.DATE_IS_NOT_PAST;
import static telran.api.RentCompanyErrorMessage.DATE_IS_NULL;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class ReturnCarData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6349205440109688553L;

	@NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "/[^0-9]/")
	private String regNumber;
	@NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "/[^0-9]/")
	private Long licenseId;
	@NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|(3[01]))")
	private LocalDate returnDate;
	private int damagesPercent;
	private int tankPercent;
	public ReturnCarData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReturnCarData(String regNumber, Long licenseId, LocalDate returnDate, int damagesPercent, int tankPercent) {
		super();
		this.regNumber = regNumber;
		this.licenseId = licenseId;
		this.returnDate = returnDate;
		this.damagesPercent = damagesPercent;
		this.tankPercent = tankPercent;
	}
	public String getRegNumber() {
		return regNumber;
	}
	public Long getLicenseId() {
		return licenseId;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public int getdamagesPercent() {
		return damagesPercent;
	}
	public int getTankPercent() {
		return tankPercent;
	}
	
}

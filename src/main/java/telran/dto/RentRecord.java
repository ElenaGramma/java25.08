package telran.dto;

import static telran.api.RentCompanyErrorMessage.DATE_IS_NULL;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class RentRecord  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8199361554075981936L;
	private String regNumber;
	private long licenseId;
	@NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|(3[01]))")
	private LocalDate rentDate;
	@NotNull(message =  DATE_IS_NULL)
	@Pattern(regexp = "\\d{4}-((0[1-9])|(1[0-2]))-((0[1-9])|([12][0-9])|(3[01]))")
	private LocalDate returnDate;
	private int rentDays;
	private int damagesPercent;
	private int tankPersent;
	private double cost;
	public RentRecord() {
		super();
	
	}
	public RentRecord(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
		super();
		this.regNumber = regNumber;
		this.licenseId = licenseId;
		this.rentDate = rentDate;
		this.rentDays = rentDays;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public int getDamagesPercent() {
		return damagesPercent;
	}
	public void setDamagesPercent(int damagesPercent) {
		this.damagesPercent = damagesPercent;
	}
	public int getTankPersent() {
		return tankPersent;
	}
	public void setTankPersent(int tankPersent) {
		this.tankPersent = tankPersent;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public String getRegNumber() {
		return regNumber;
	}
	public long getLicenseId() {
		return licenseId;
	}
	public LocalDate getRentDate() {
		return rentDate;
	}
	public int getRentDays() {
		return rentDays;
	}
	@Override
	public String toString() {
		return "RentRecord [regNumber=" + regNumber + ", licenseId=" + licenseId + ", rentDate=" + rentDate
				+ ", returnDate=" + returnDate + ", rentDays=" + rentDays + ", damagesPercent=" + damagesPercent
				+ ", tankPersent=" + tankPersent + ", cost=" + cost + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + damagesPercent;
		result = prime * result + (int) (licenseId ^ (licenseId >>> 32));
		result = prime * result + ((regNumber == null) ? 0 : regNumber.hashCode());
		result = prime * result + ((rentDate == null) ? 0 : rentDate.hashCode());
		result = prime * result + rentDays;
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
		result = prime * result + tankPersent;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentRecord other = (RentRecord) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (damagesPercent != other.damagesPercent)
			return false;
		if (licenseId != other.licenseId)
			return false;
		if (regNumber == null) {
			if (other.regNumber != null)
				return false;
		} else if (!regNumber.equals(other.regNumber))
			return false;
		if (rentDate == null) {
			if (other.rentDate != null)
				return false;
		} else if (!rentDate.equals(other.rentDate))
			return false;
		if (rentDays != other.rentDays)
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
			return false;
		if (tankPersent != other.tankPersent)
			return false;
		return true;
	}
	
	
	
	

}

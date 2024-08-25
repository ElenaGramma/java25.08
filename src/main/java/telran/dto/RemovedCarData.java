package telran.dto;

import java.io.Serializable;
import java.util.List;

public class RemovedCarData  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7682375091125886841L;

	private Car car;
	private List<RentRecord> removedRecords;
	public RemovedCarData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RemovedCarData(Car car, List<RentRecord> removedRecords) {
		super();
		this.car = car;
		this.removedRecords = removedRecords;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public List<RentRecord> getRemovedRecords() {
		return removedRecords;
	}
	public void setRemovedRecords(List<RentRecord> removedRecords) {
		this.removedRecords = removedRecords;
	}
	@Override
	public String toString() {
		return "RemovedCarData [car=" + car + ", removedRecords=" + removedRecords + "]";
	}
	
	
}

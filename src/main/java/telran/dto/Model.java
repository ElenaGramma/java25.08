package telran.dto;

import java.io.Serializable;

public class Model  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3629860314527912835L;
	
	private String modelName;
	private String company;
	private String country;
	private int gasTank;
	private int priceDay;
	public Model() {
		super();
		
	}
	public Model(String modelName, String company, String country, int gasTank, int priceDay) {
		super();
		this.modelName = modelName;
		this.company = company;
		this.country = country;
		this.gasTank = gasTank;
		this.priceDay = priceDay;
	}
	public int getPriceDay() {
		return priceDay;
	}
	public void setPriceDay(int priceDay) {
		this.priceDay = priceDay;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getModelName() {
		return modelName;
	}
	public String getCompany() {
		return company;
	}
	public String getCountry() {
		return country;
	}
	public int getGasTank() {
		return gasTank;
	}
	@Override
	public String toString() {
		return "Model [modelName=" + modelName + ", company=" + company + ", country=" + country + ", gasTank="
				+ gasTank + ", priceDay=" + priceDay + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
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
		Model other = (Model) obj;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		return true;
	}
	
	

}

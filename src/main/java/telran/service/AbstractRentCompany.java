package telran.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import jakarta.annotation.PostConstruct;

@ManagedResource
public abstract class AbstractRentCompany implements IRentCompany{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1697398309445055205L;
//	protected int  finePercent=15;
//	protected int gasPric=10;
//	public AbstractRentCompany() {
//		super();
//		this.finePercent=finePercent;
//		this.gasPric=gasPric;
//		
//	}
	@Value("${fine-percent:15}")
	protected int  finePercent;
	@Value("${gas-price:10}")
	protected int gasPrice;
	
	
	@PostConstruct
	void setting() {
		setFinePercent(finePercent);
		setGasPrice(gasPrice);
	}
	@ManagedAttribute
	public int getFinePercent() {
		return finePercent;
	}
	@ManagedAttribute
	public void setFinePercent(int finePercent) {
		this.finePercent = finePercent;
	}
	@ManagedAttribute
	public int getGasPrice() {
		return gasPrice;
	}
	@ManagedAttribute
	public void setGasPrice(int gasPrice) {
		this.gasPrice = gasPrice;
	}
	protected double  computeCost(int rentPrice, int rentDays,
				 int delay,int tankPercent,int tankVolume) {
		double cost=rentPrice*rentDays;
		if(delay>0) {
			double temp=rentPrice*(double)(1+finePercent/100)*delay;
			cost+=temp;
		}
		if(tankPercent<100) {
			double temp=tankVolume*(double)((100-tankPercent)/100)*gasPrice;
			cost+=temp;
		}
					return cost;	
	}
	
	

}

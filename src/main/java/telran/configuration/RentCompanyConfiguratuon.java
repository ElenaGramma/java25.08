package telran.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import telran.service.IRentCompany;
import telran.service.RentCompanyEmbedded;

@Configuration
public class RentCompanyConfiguratuon {

	@Value("${file-name:company.data}")
	private String fileName;
	
	@Bean
	@Scope("prototype")
	IRentCompany getCompany() {
		
		return RentCompanyEmbedded.restoreFromFile(fileName);
		
	}
}

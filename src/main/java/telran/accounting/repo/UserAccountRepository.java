package telran.accounting.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.accounting.entites.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String>{

}

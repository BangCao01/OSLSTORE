package orangeschool.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import orangeschool.model.Customer;


public interface UserRepository extends JpaRepository<Customer, Integer> {
	Customer findByUsername(String _username);
	Customer findByCustomerID(Integer _id);
	Customer findByPhonenumber(String _phonenumber);
}
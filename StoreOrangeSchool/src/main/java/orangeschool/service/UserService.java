package orangeschool.service;


import orangeschool.model.Customer;

public interface UserService {
    void save(Customer _user);
    Customer findById(Integer _id);
    Customer findByUsername(String _username);
    boolean match(Customer _user, String _password);
    
}
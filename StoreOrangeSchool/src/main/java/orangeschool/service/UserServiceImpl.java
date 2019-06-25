package orangeschool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import orangeschool.repository.UserRepository;
import orangeschool.model.Customer;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Customer _user) {
        _user.setPassword(bCryptPasswordEncoder.encode(_user.getPassword()));
        //_admin.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(_user);
    }

    @Override
    public Customer findById(Integer _id)
    {
    	return this.userRepository.findByCustomerID(_id);
    }
    
    @Override
    public Customer findByUsername(String _username) {
    	Customer user =  userRepository.findByUsername(_username);
        return user;
    }
    
    public boolean match(Customer _user, String _password)
    {
    	return bCryptPasswordEncoder.encode(_password) == _user.getPassword();
    }
}
package orangeschool.service;


import orangeschool.model.Admin;;

public interface AdminService {
    void save(Admin _admin);

    Admin findByUsername(String _username);
    boolean match(Admin _admin, String _password);
    
}
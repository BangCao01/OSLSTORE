package orangeschool.form;

public class SignUpForm {

	private String username;

    private String password;
    
    private String confirmPassword;
    
    private String phonenumber;
    
	
    public void setUsername(String _username)
    {
    	this.username = _username;
    }
    
    public String getUsername()
    {
    	return this.username;
    }
    
    public void setPassword(String _password)
    {
    	this.password = _password;
    }
    
    public String getPassword()
    {
    	return this.password;
    }
    
    public void setPhonenumber(String _phonenumber)
    {
    	this.phonenumber = _phonenumber;
    }
    
    public String getPhonenumber()
    {
    	return this.phonenumber;
    }
    
    public void setConfirmPassword(String _confirmPassword)
    {
    	this.confirmPassword = _confirmPassword;
    }
    
    public String getConfirmPassword()
    {
    	return this.confirmPassword;
    }
    
}

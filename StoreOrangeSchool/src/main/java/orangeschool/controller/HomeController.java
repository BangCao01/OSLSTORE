package orangeschool.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import orangeschool.form.*;
import orangeschool.model.Category;
import orangeschool.model.Customer;
import orangeschool.WebUtil;
import orangeschool.service.CategoryService;
import orangeschool.service.SecurityService;
import orangeschool.service.TopicService;
import orangeschool.service.UserService;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/home") // This
public class HomeController extends BaseController{
//	@Autowired 
//	private AdminRepository userRepository;
//	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;
    @Autowired
	private CategoryService categoryService;
	
	
    @RequestMapping(value = { "/index" }, method = RequestMethod.GET)
    public String index(Model model, Principal _principal) {
 
    	List<Category> mathCategories = categoryService.findWithOrderInRange(49, 62);
    	List<Category> elaCategories = categoryService.findWithOrderInRange(100, 113);
    	
    	model.addAttribute("topics", MergeCategory(mathCategories, elaCategories));
        model.addAttribute("menucode", this.menucode);
        this.setAccessCode(model, _principal);
        return "home/index";
    }
    
    private List<CategoryThumb> MergeCategory(List<Category> _math, List<Category> _ela)
    {
    	List<CategoryThumb> ret = new ArrayList<CategoryThumb>();
    	
    	for(int i =0; i < _math.size(); i++ )
    	{
    		CategoryThumb tmp = new CategoryThumb();
    		tmp.ela = _ela.get(i);
    		tmp.math = _math.get(i);
    		tmp.name = tmp.math.getName();
    		ret.add(tmp);
    		
    	}
    	
    	return ret;
    	
    }
    
    @RequestMapping(value = { "/404" }, method = RequestMethod.GET)
    public String forbitden(Model model, Principal _principal) {
 
        
        return "forbidden";
    }
    
    
    @RequestMapping(value = { "/signin" }, method = RequestMethod.GET)
	public String showSignInPage(Model model) {
	 
	        SignInForm theForm = new SignInForm();
	        model.addAttribute("signInForm", theForm);
	 
	        return "home/signin";
	}
    @RequestMapping(value = { "/signin" }, method = RequestMethod.POST)
    public String saveSignInForm(Model model, //
            @ModelAttribute("signInForm") SignInForm signInForm, BindingResult bindingResult) {
 
    	
    	
        if (this.IsValidate(signInForm))
        {
        	return "redirect:/home/index";
        }
        
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("signInForm", signInForm);
        return "home/signin";
        
        
    }
    
	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String showSignUpPage(Model model) {
	 
	        SignUpForm theForm = new SignUpForm();
	        model.addAttribute("signUpForm", theForm);
	 
	        return "home/signup";
	}
	
	
	
	 @RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
	    public String saveSignUpForm(Model model, //
	            @ModelAttribute("signupForm") SignUpForm signupForm, BindingResult bindingResult) {
	 
		    

	        if (this.IsValidate(signupForm)) {
	        	
	        	String username = signupForm.getUsername();
		        String password = signupForm.getPassword();
		        String phonenumber = signupForm.getPhonenumber();
		        Customer n = new Customer();
	    		n.setUsername(username);
	    		n.setPassword(password);
	    		n.setStatus(1);
	    		n.setPhonenumber(phonenumber);
	    		n.setCreateDate(WebUtil.GetTime());
	    		n.setExpireDate(WebUtil.GetTime());
		        userService.save(n);

		        securityService.autoLogin(signupForm.getUsername(), signupForm.getConfirmPassword());
			 
		        return "redirect:/home/index";
	        	
	        }

	        
	        model.addAttribute("errorMessage", errorMessage);
	        model.addAttribute("signUpForm", signupForm);
	        return "home/signup";
	    }
	
	
	 
	 @RequestMapping(value = "/logout", method = RequestMethod.GET)
	    public String logoutSuccessfulPage(Model model,HttpServletRequest request, 
	            HttpServletResponse response) {
	        model.addAttribute("title", "Logout");
	        
	        try {
		        HttpSession session = request.getSession(false);
		        if (session != null) {

		            session.invalidate();
		        }
				
		        SecurityContextHolder.clearContext();
		        return "redirect:/home/signin";

		    } catch (Exception e) {
		        //logger.log(LogLevel.INFO, "Problem logging out.");
				System.out.println("inside catch\n\n");

		    	return "ERROR"+e.getMessage();
		    }
	        
	        
	        
	        
	    }
	 
	 @RequestMapping(value = "/403", method = RequestMethod.GET)
	    public String accessDenied(Model model, Principal principal) {
	 
	        if (principal != null) {
	            User loginedUser = (User) ((Authentication) principal).getPrincipal();
	 
	            String userInfo = WebUtil.toString(loginedUser);
	 
	            model.addAttribute("userInfo", userInfo);
	 
	            String message = "Hi " + principal.getName() //
	                    + "<br> You do not have permission to access this page!";
	            model.addAttribute("message", message);
	 
	        }
	 
	        return "403page";
	    }
	

	 
	 @RequestMapping(value = "/upload/{f1}/{f2}/{f3}/{filename}")
	 @ResponseBody
	 public byte[] getImage(
			 @PathVariable(value = "f1") String f1,
			 @PathVariable(value = "f2") String f2,
			 @PathVariable(value = "f3") String f3,
			 @PathVariable(value = "filename") String filename,
			 HttpServletRequest request) throws IOException {
	        
		    String uploadRootPath = request.getServletContext().getRealPath("upload");
	        File serverFile = new File(uploadRootPath
	        		+File.separator + f1 
	        		+File.separator + f2
	        		+File.separator + f3
	        		+File.separator + filename
	        		);

	        return Files.readAllBytes(serverFile.toPath());
	    }
	 
	 private boolean IsValidate(SignUpForm _theForm)
	 {
	
		    if (_theForm.getUsername().length() < 6 || _theForm.getUsername().length() > 32) {
	            this.errorMessage = "Size.userForm.username";
	            return false;
	        }
	        if (userService.findByUsername(_theForm.getUsername()) != null) {
	        	this.errorMessage = "Duplicate.userForm.username";
	        	return false;
	        }

	        
	        if (_theForm.getPassword().length() < 6 || _theForm.getPassword().length() > 32) {
	            this.errorMessage = "Size.userForm.password";
	            return false;
	        }

	        if (!_theForm.getConfirmPassword().equals(_theForm.getPassword())) {
	            this.errorMessage = "Diff.userForm.passwordConfirm";
	            return false;
	        }
		 
		 return true;
	 }
	 
	 private boolean IsValidate(SignInForm _theForm)
	 {
		 
		 if (_theForm.getUsername().length() < 6 || _theForm.getUsername().length() > 32) {
			 this.errorMessage = "Size.userForm.username";
			 return false;
	        }
	        
	        Customer user = userService.findByUsername(_theForm.getUsername());
	        if (user == null) {
	            this.errorMessage = "Duplicate.userForm.username";
	            return false;
	        }
	        
	        if (_theForm.getPassword().length() < 6 || _theForm.getPassword().length() > 32) {
	        	this.errorMessage = "Size.userForm.password";
	        	return false;
	        }
	        
	        if(user != null && userService.match(user, _theForm.getPassword()))
	        {
	        	this.errorMessage = "Incorrect.userFrom.password";
	        	return false;
	        }
		 return true;
	 }

}

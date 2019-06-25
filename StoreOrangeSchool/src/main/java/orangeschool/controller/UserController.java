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
import java.security.Principal;

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
import orangeschool.model.Customer;
import orangeschool.WebUtil;
import orangeschool.service.SecurityService;
import orangeschool.service.UserService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/user") // This
public class UserController extends BaseController {
//	@Autowired 
//	private AdminRepository userRepository;
//	
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String showDetailPage(Model model, Principal _principal) {

		model.addAttribute("message", message);
		model.addAttribute("menucode", this.menucode);
		Customer user = this.getUser(_principal);
		model.addAttribute("user", user);
		User loginedUser = (User) ((Authentication) _principal).getPrincipal();
		this.setAccessCode(model, _principal);

		return "user/detail";
	}

	@RequestMapping(value = { "/edit" }, method = RequestMethod.GET)
	public String showEditPage(Model model, Principal _principal) {

		Customer user = this.getUser(_principal);
		UserEditForm theForm = new UserEditForm();
		user.setUsername(user.getUsername());
		user.setPhonenumber(user.getPhonenumber());
		user.setEmail(user.getEmail());
		user.setAddress(user.getAddress());
		model.addAttribute("userEditForm", theForm);

		return "user/edit";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.POST)
	public String saveEditForm(@PathVariable("id") int id, Model model,
			@ModelAttribute("userEditForm") UserEditForm userEditForm) {

		if (this.IsValidate(userEditForm)) {
			
			String email = userEditForm.getEmail();
			String address = userEditForm.getAddress();
			String phonenumber = userEditForm.getPhonenumber();
			Customer user = this.userService.findById(id);
			user.setAddress(address);
			user.setPhonenumber(phonenumber);
			user.setEmail(email);
			user.setUpdateDate(WebUtil.GetTime());
			this.userService.save(user);
			return "redirect:/user/detail/"+ id;
		}

		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("userEditForm", userEditForm);
		return "user/edit";

	}

	private boolean IsValidate(UserEditForm _theForm) {

		if (_theForm.getPhonenumber().length() > 0
				&& (_theForm.getPhonenumber().length() < 10 || _theForm.getPhonenumber().length() > 15)) {
			this.errorMessage = "Invalid phone number";
			return false;
		}

		if (_theForm.getEmail().length() < 4) {
			this.errorMessage = "Invalid email";
			return false;
		}

		return true;
	}

}

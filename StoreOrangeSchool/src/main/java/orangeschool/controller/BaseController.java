package orangeschool.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;


import orangeschool.model.Customer;
import orangeschool.service.UserService;
import orangeschool.WebUtil;

public class BaseController {
	@Value("${welcome.message}")
	protected String message;

	@Value("${error.message}")
	protected String errorMessage;
	@Autowired
	protected UserService userService;
	public static String GetTime() {
//    	TimeZone.setDefault(TimeZone.getTimeZone("CET"));
//    	Calendar calendar = Calendar.getInstance();
//    	calendar.set(2018, Calendar.AUGUST, 1, 12, 0);
//    	Date date = calendar.getTime();
		LocalDate localDate = LocalDate.now(ZoneId.of("GMT+07:00"));
		LocalTime time = LocalTime.now();
		LocalDateTime ldt = LocalDateTime.now();
		String dateandtime = ldt.toString();// localDate.toString()+":"+ time.toString();
		// System.out.print(" ttttttttttttttt:" + dateandtime);
		return dateandtime;
	}
	
	
	
	protected boolean checkForbidden(Principal _principal,  MenuCode _item)
	{
		
		Customer _admin = this.getUser(_principal);
		if(!this.checkPermission(_admin, _item, null))
			return true;
		return false;
	}

	protected  boolean checkPermission(Customer _user, MenuCode _item, Action _action)
	{
		boolean ret =true;
		if(_user == null)
		{
			ret = false;
		}
		
		
		return ret;
	}
	
	protected Customer getUser(Principal _principal)
	{
		if(_principal == null)
			return null;
		
		User loginedUser = (User) ((Authentication) _principal).getPrincipal();
		Customer _author = this.userService.findByUsername(loginedUser.getUsername());
		return _author;
	}
	
	protected void setAccessCode(Model model, Principal _principal)
	{
		
		//Customer _admin = getAdminUser(_principal);
		model.addAttribute("accesscodes", getAccessCode(null));
	}
	
	protected List<String> getAccessCode(Customer _user)
	{
		List<String> ret = new ArrayList();
		ret.add("Home");
		
		ret.add("Math");
		ret.add("Ela");
		ret.add("Flashcard");
		ret.add("Story");
		ret.add("Category");
		/*
		if(checkPermission(_user, MenuCode.Math, null))
		{
			ret.add("Math");
		}
		if(checkPermission(_user, MenuCode.Ela, null))
		{
			ret.add("Ela");
		}
		if(checkPermission(_user, MenuCode.Flashcard, null))
		{
			ret.add("Flashcard");
		}
		if(checkPermission(_user, MenuCode.Story, null))
		{
			ret.add("Story");
		}
		
		if(checkPermission(_user, MenuCode.Category, null))
		{
			ret.add("Category");
		}
		
		if(checkPermission(_user, MenuCode.Topic, null))
		{
			ret.add("Topic");
		}
		*/
		ret.add("Purchase");
		ret.add("Login");
		ret.add("Logout");
		return ret;
	}
	

	protected void setMessageAt(Model model, MessageCode _messageCode)
	{
		if(_messageCode == MessageCode.Add)
			message = WebUtil.addSuccessfull;
		else if(_messageCode == MessageCode.Edit)
			message = WebUtil.editSuccessfull;
		else if(_messageCode == MessageCode.Delete)
			message = WebUtil.deleteSuccessfull;
		else if(_messageCode == MessageCode.Add)
			message = WebUtil.addSuccessfull;
		else if(_messageCode == MessageCode.Detail)
		{
			model.addAttribute("message", message);
			message = null;
		}
		else if(_messageCode == MessageCode.Index)
		{
			if (message != null && !message.equalsIgnoreCase(WebUtil.deleteSuccessfull))
				message = null;
			model.addAttribute("message", message);
			message = null;
		}
	}

	protected void setSelectedMenu(Model model, MenuCode _menuItem) {
    	model.addAttribute("menucode", _menuItem.toString());
	}
	
	protected void setSelectedActionMenu(Model model, ActionMenuCode _menuItem) {
    	model.addAttribute("actioncode", _menuItem.toString());
	}


	public enum MessageCode{
		Edit,Add,Delete,Index, Detail
		
	}
	public enum Action{
		List,View, Add, Edit, Delete
	}
	public enum MenuCode {
		Home,User,Flashcard, Story, Math,Ela, Images, Sounds, Texts, Asset,Category,
		Topic, Translator, Product,Transaction,Result, Logout

	}
	public enum ActionMenuCode{
		List, Add, Pending, Approved, TopicEla,TopicMath,TopicStory
	}
	public enum AccessCode{
		Admin,SubAdmin,MathEditor,StoryEditor, ElaEditor, Translator
	}

	protected String menucode = "Home";
}

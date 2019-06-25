package orangeschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

import orangeschool.service.TopicService;
import orangeschool.model.Category;
import orangeschool.model.Topic;
import orangeschool.service.CategoryService;
import orangeschool.service.MathSubjectService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/math") // This
public class MathsubjectController extends BaseController {
    private Integer type = 2;
	@Autowired
	private MathSubjectService mathSubjectService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TopicService topicService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String mathIndex(Model model, Principal _principal) {
		
		model.addAttribute("type", this.type);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		List<Category> categories = categoryService.findWithOrderInRange(49, 62);
		model.addAttribute("mathCategories", categories);
		
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/index";
	}
	private void process(List<Category> _categories)
	{
		for(int i =0; i < _categories.size(); i++)
		{
			Category item = _categories.get(i);
	        String des = item.getDescription();
	        
		}
	}

	@RequestMapping(value = { "/index/{category}" }, method = RequestMethod.GET)
	public String showTopicByTypePage(
			@PathVariable("category") int _category, 
			Model model,
			Principal _principal) {

		
		model.addAttribute("categories", categoryService.findWithOrderInRange(49, 62));
		model.addAttribute("category", _category);
		Category category = categoryService.findById(_category);
		List<Topic> Maths = topicService.findByTypeParent(type, null);
		List<Topic> SubTopics = null;
		if(Maths.size() >0)
		SubTopics = topicService.findByTypeCategoryParent(type, category, Maths.get(0));
		
		model.addAttribute("topics", SubTopics);
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setAccessCode(model, _principal);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		
		return "math/grade";
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Math)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("mathSubject", mathSubjectService.findById(id));
		this.setSelectedMenu(model, MenuCode.Math);
		this.setAccessCode(model, _principal);
		return "math/detail";
	}

	

}

package orangeschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMethod;
import java.security.Principal;
import java.util.List;

import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.controller.BaseController.MessageCode;
import orangeschool.model.Category;
import orangeschool.model.Topic;
import orangeschool.service.CategoryService;
import orangeschool.service.ImageContentService;
import orangeschool.service.SoundContentService;
import orangeschool.service.EnglishtestService;
import orangeschool.service.TextContentService;
import orangeschool.service.TopicService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/ela") // This
public class EnglishtestController extends BaseController {
	private Integer type = 1;
	@Autowired
	private EnglishtestService englishtestService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TopicService topicService;
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(Model model,Principal _principal) {
		
		
		model.addAttribute("type", this.type);
		List<Category> categories = categoryService.findWithOrderInRange(100, 113);
		model.addAttribute("elaCategories", categories);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		return "ela/index";
		
		
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Ela)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("englishtest", englishtestService.findWithId(id));
		this.setSelectedMenu(model, MenuCode.Ela);
		this.setAccessCode(model, _principal);
		return "ela/detail";
	}

	
	@RequestMapping(value = { "/index/{category}" }, method = RequestMethod.GET)
	public String showTopicByTypePage(
			@PathVariable("category") int _category, 
			Model model,
			Principal _principal) {

		
		
		model.addAttribute("categories", categoryService.findWithOrderInRange(100, 113));
		model.addAttribute("category",_category);
		Category category = categoryService.findById(_category);
		List<Topic> Elas = topicService.findByTypeParent(type, null);
		List<Topic> SubTopics = null;
		if(Elas.size() >0)
		SubTopics = topicService.findByTypeCategoryParent(type, category, Elas.get(0));
		
		
		model.addAttribute("categoryName",category.getName());
		
		model.addAttribute("topics", SubTopics);
		this.setMessageAt(model, MessageCode.Index);
		this.setSelectedMenu(model, MenuCode.Topic);
		this.setAccessCode(model, _principal);
		ActionMenuCode actioncode = (type == 1)? ActionMenuCode.TopicEla:(type == 2)? ActionMenuCode.TopicMath:ActionMenuCode.TopicStory;
		this.setSelectedActionMenu(model, actioncode);
		
		return "ela/grade";
	}
	
	

}

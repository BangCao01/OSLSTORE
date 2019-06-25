package orangeschool.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import orangeschool.service.CategoryService;
import orangeschool.service.ImageContentService;
import orangeschool.service.StoryService;
import orangeschool.service.TextContentService;

@Controller
@RequestMapping(path = "/story") // This
public class StoryController extends BaseController {

	@Autowired
	private StoryService storyService;
	@Autowired
	private ImageContentService imageContentService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TextContentService textContentService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String mathIndex(Model model, Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}

		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("stories", storyService.findAll());
		this.menucode = "Story";
		model.addAttribute("menucode", this.menucode);
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setAccessCode(model, _principal);
		return "story/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {

		if (this.checkForbidden(_principal, MenuCode.Story)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("storyID", id);
		this.setAccessCode(model, _principal);
		model.addAttribute("story", storyService.findWithId(id));
		this.setSelectedMenu(model, MenuCode.Story);
		return "story/detail";
	}

	
}

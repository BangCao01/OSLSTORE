package orangeschool.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import orangeschool.service.AssetService;
import orangeschool.service.FlashcardService;
import orangeschool.service.TextContentService;

@Controller
@RequestMapping(path = "/flashcard") // This
public class FlashcardController extends BaseController {

	@Autowired
	private FlashcardService flashcardService;
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private TextContentService textContentService;

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showIndexPage(
			Model model,
			Principal _principal
			) {
		
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("flashcards", flashcardService.findAll());
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Flashcard)) {
			return "redirect:/home/404";
		}
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("flashcard", flashcardService.findWithId(id));
		this.setSelectedMenu(model, MenuCode.Flashcard);
		this.setAccessCode(model, _principal);
		return "flashcard/detail";
	}

	
}

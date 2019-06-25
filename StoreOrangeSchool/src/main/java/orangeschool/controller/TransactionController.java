package orangeschool.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import orangeschool.WebUtil;
import orangeschool.controller.BaseController.ActionMenuCode;
import orangeschool.controller.BaseController.MenuCode;
import orangeschool.form.TransactionForm;
import orangeschool.model.Transaction;
import orangeschool.model.Customer;
import orangeschool.model.Product;
import orangeschool.model.TextContent;
import orangeschool.service.ProductService;
import orangeschool.service.TransactionService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/transaction") // This
public class TransactionController extends BaseController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showCategoryPage(
			Model model,
			Principal _principal
			) {
		
		Customer user = this.getUser(_principal);
		
		if (this.checkForbidden(_principal, MenuCode.Transaction)
				
				) {
			return "redirect:/home/404";
		}
		
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		model.addAttribute("transactions", transactionService.findByCustomerID(user.getId()));
		this.setSelectedMenu(model, MenuCode.Transaction);
		this.setAccessCode(model, _principal);
		return "transaction/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(
			@PathVariable("id") int id, 
			Model model,
			Principal _principal) {
		
		Customer user = this.getUser(_principal);
		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		Transaction transaction = transactionService.findByTransactionID(id);
		String id1 = transaction.getCustomerID().toString();
		String id2 = user.getId().toString();
		if(!(id1.equalsIgnoreCase(id2)))
		{
			String log = id1 +" id " + id2;
			System.out.println(log);
			return "redirect:/home/404";
		}
		
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("transaction", transaction);
		this.setSelectedMenu(model, MenuCode.Transaction);
		this.setAccessCode(model, _principal);
		return "transaction/detail";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String doAddPost(
			@ModelAttribute("transactionForm") TransactionForm _transactionForm,
			Model model,
			Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Transaction)) {
			return "redirect:/home/404";
		}
		
		Customer customer = this.getUser(_principal);
		
		if(this.IsValidate(_transactionForm))
		{
			Product product = productService.findById(_transactionForm.getProductID());
			
			
			Transaction trans = transactionService.findByCustomerAndProduct(customer,product);
			if(trans == null)
			{
				trans = new Transaction();
				trans.setCustomer(customer);
				trans.setProduct(product);
				trans.setStatus(1);// not yet approved.
				trans.setTransValue(product.getPrice().toString());
				trans.setCreateDate(WebUtil.GetTime());
				trans.setExpireDate(WebUtil.GetTimeTo(product.getPeriod()));
				transactionService.save(trans);
				String billcode = trans.getId().toString() + product.getId();
				trans.setBillcode(billcode);
				transactionService.save(trans);
			}
			this.setMessageAt(model, MessageCode.Add);
			return "redirect:/transaction/detail/"+ trans.getId();
		}
		
		errorMessage = "Failed to buy the product";
		model.addAttribute("transaction", _transactionForm);
		model.addAttribute("errorMessage", errorMessage);
		this.setSelectedMenu(model, MenuCode.Transaction);
	
		this.setAccessCode(model, _principal);
		return "product/detail";
	}


	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String showDeletePage(@PathVariable("id") int id, Model model,Principal _principal) {
		if (this.checkForbidden(_principal, MenuCode.Category)) {
			return "redirect:/home/404";
		}
		try {
			transactionService.deleteById(id);
		} catch (Exception ex) {

		}
		this.setMessageAt(model, MessageCode.Delete);
		return "redirect:/transaction/index";
	}

	private boolean IsValidate(TransactionForm _theForm) {
		
		if (_theForm.getProductID() <= 0) {
			errorMessage = "Product field must be an integer";
			return false;
		}

		return true;
	}

	
 
}

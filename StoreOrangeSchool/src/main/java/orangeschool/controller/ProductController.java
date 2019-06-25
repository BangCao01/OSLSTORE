package orangeschool.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import orangeschool.form.PackForm;
import orangeschool.form.TransactionForm;
import orangeschool.model.Customer;
import orangeschool.model.Product;
import orangeschool.model.Transaction;
import orangeschool.service.ProductService;
import orangeschool.service.TransactionService;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/product") // This
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private TransactionService transactionService;
	
	

	

	@RequestMapping(value = { "/index" }, method = RequestMethod.GET)
	public String showProductsPage(Model model, Principal _principal) {
//		if (this.checkForbidden(_principal, MenuCode.Product)) {
//			return "redirect:/home/404";
//		}
		
		Customer user = this.getUser(_principal);
		List<Transaction> transactions = null;
		if(user!=null)
		{
			transactions= transactionService.findByCustomerID(user.getId());
			System.out.println("size of transactions :" +transactions.size());
		}
		
		model.addAttribute("packs", this.getPacks(transactions, productService.findAll()));
		this.setSelectedActionMenu(model, ActionMenuCode.List);
		this.setMessageAt(model, MessageCode.Index);
		
		this.setSelectedMenu(model, MenuCode.Product);
		this.setAccessCode(model, _principal);
		return "product/index";
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String showDetailPage(@PathVariable("id") int id, Model model, Principal _principal) {
//		if (this.checkForbidden(_principal, MenuCode.Product)) {
//			return "redirect:/home/404";
//		}
		TransactionForm theForm = new TransactionForm();
		theForm.setProductID(id);
		this.setMessageAt(model, MessageCode.Detail);
		model.addAttribute("product", productService.findById(id));
		model.addAttribute("transactionForm",theForm);
		this.setSelectedMenu(model, MenuCode.Product);
		this.setAccessCode(model, _principal);
		return "product/detail";
	}

	private int getPackStatus(Integer _productID, List<Transaction> _transactions)
	{
		int ret = 0;
		if(_transactions !=null)
		for(int i =0; i < _transactions.size();i++)
		{
			Transaction transaction = _transactions.get(i);
			String id1 = transaction.getProductID().toString();
			String id2 = _productID.toString();
		      if(id1.equalsIgnoreCase(id2))
		      {
		    	  ret = transaction.getStatus();
		    	  break;
		      }
		}
		
		return ret;
		
	}
	private List<PackForm> getPacks(List<Transaction> _transactions, List<Product> _products)
	{
		List<PackForm> ret = new ArrayList<PackForm>();
		for(int i =0; i < _products.size();i++)
		{
			Product p = _products.get(i);
			PackForm tmp = new PackForm();
			tmp.setProduct(p);
			tmp.setStatus(this.getPackStatus(p.getId(),_transactions));
			ret.add(tmp);
		}
		
		
		return ret;
		
	}
}

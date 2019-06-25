package orangeschool.form;

import orangeschool.model.Product;

public class PackForm {
	private Product product;
    private Integer status;
	
    public PackForm()
    {
    	product = null;
    	status = 0;
    }
    
    
    public void setProduct(Product _product)
    {
    	this.product = _product;
    }
    
    public Product getProduct()
    {
    	return this.product;
    }
    
    public void setStatus(Integer _status)
    {
    	this.status = _status;
    }
    
    public Integer getStatus()
    {
    	return this.status;
    }
   
   
}

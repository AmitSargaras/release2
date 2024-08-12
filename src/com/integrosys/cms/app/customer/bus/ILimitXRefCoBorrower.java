package com.integrosys.cms.app.customer.bus;

import java.io.Serializable;

public interface ILimitXRefCoBorrower extends Serializable{
	
	
	    public String getCoBorrowerId();
	    public String getCoBorrowerName();
	  
	    public void setCoBorrowerId(String coBorrowerId);
	    public void setCoBorrowerName(String coBorrowerName);
	   
    
	public long getId();
	public void setId(long id);
	
	public long getXRefID();
	public void setXRefID(long xrefID);
}

package com.integrosys.cms.app.customer.bus;

public class OBLimitXRefCoBorrower implements  ILimitXRefCoBorrower{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	    private long XRefID;
	    private long id;
	
	    private String coBorrowerId;
	    private String coBorrowerName;
	    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getXRefID() {
		return XRefID;
	}
	public void setXRefID(long xRefID) {
		XRefID = xRefID;
	}
	public String getCoBorrowerId() {
		return coBorrowerId;
	}
	public void setCoBorrowerId(String coBorrowerId) {
		this.coBorrowerId = coBorrowerId;
	}
	public String getCoBorrowerName() {
		return coBorrowerName;
	}
	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}
	
}

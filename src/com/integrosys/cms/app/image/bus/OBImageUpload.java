package com.integrosys.cms.app.image.bus;

import com.integrosys.cms.ui.image.IImageUpload;


public class OBImageUpload implements IImageUpload {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long custID;
	
	private String CustName = "";

	private String CustCIF = "";

	/**
	 * @return the custID
	 */
	public Long getCustID() {
		return custID;
	}

	/**
	 * @param custID the custID to set
	 */
	public void setCustID(Long custID) {
		this.custID = custID;
	}

	/**
	 * @return the custName
	 */
	public String getCustName() {
		return CustName;
	}

	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		CustName = custName;
	}

	/**
	 * @return the custCIF
	 */
	public String getCustCIF() {
		return CustCIF;
	}

	/**
	 * @param custCIF the custCIF to set
	 */
	public void setCustCIF(String custCIF) {
		CustCIF = custCIF;
	}

	
}

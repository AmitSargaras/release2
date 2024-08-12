/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/LawSearchResultItem.java,v 1.1 2003/07/03 01:34:30 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.CustomerTypeResultItem;

/**
 * This class implements the ICheckList
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/03 01:34:30 $ Tag: $Name: $
 */
public class LawSearchResultItem implements Serializable {
	private String lawCode = null;

	private String lawDesc = null;

	private CustomerTypeResultItem[] customerTypeList = null;

	LawSearchResultItem(String aLawCode, String aLawDesc) {
		setLawCode(aLawCode);
		setLawDesc(aLawDesc);
	}

	public String getLawCode() {
		return this.lawCode;
	}

	public String getLawDesc() {
		return this.lawDesc;
	}

	public CustomerTypeResultItem[] getCustomerTypeList() {
		return this.customerTypeList;
	}

	public void setLawCode(String aLawCode) {
		this.lawCode = aLawCode;
	}

	public void setLawDesc(String aLawDesc) {
		this.lawDesc = aLawDesc;
	}

	public void setCustomerTypeList(CustomerTypeResultItem[] aList) {
		this.customerTypeList = aList;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/GroupSearchCriteria.java,v 1.1 2003/09/03 12:08:49 lakshman Exp $
 */
package com.integrosys.cms.app.customer.bus;

//ofa
import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the Customer.
 * 
 * @author $Author: lakshman $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 12:08:49 $ Tag: $Name: $
 */
public class GroupSearchCriteria extends SearchCriteria {
	private String _groupName = null;

	private String _groupID = null;

	private ITrxContext ctx;

	public ITrxContext getCtx() {
		return ctx;
	}

	public void setCtx(ITrxContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * 
	 * Default Constructor
	 */
	public GroupSearchCriteria() {
	}

	// Getters

	/**
	 * Get the legal name
	 * 
	 * @return String
	 */
	public String getGroupName() {
		return _groupName;
	}

	/**
	 * Get the legal ID
	 * 
	 * @return String
	 */
	public String getGroupID() {
		return _groupID;
	}

	// Setters

	/**
	 * Set the legal name
	 * 
	 * @param value is of type String
	 */
	public void setGroupName(String value) {
		_groupName = value;
	}

	/**
	 * Set the legal ID
	 * 
	 * @param value is of type String
	 */
	public void setGroupID(String value) {
		_groupID = value;
	}
}

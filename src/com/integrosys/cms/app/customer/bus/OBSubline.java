/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupDao;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBSubline implements ISubline {
	private long _sublineID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	


	private String amount;
	
	private long partyId;
	
	private IPartyGroup partyGroup;
	
	private IPartyGroupProxyManager partyGroupProxy;
	
	private long LEID;
	
	/**
	 * Default Constructor
	 */
	public OBSubline() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBSubline(ISystem value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	

	public long getSublineID() {
		return _sublineID;
	}

	public void setSublineID(long sublineID) {
		_sublineID = sublineID;
	}


	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public long getPartyId() {
		return partyId;
	}

	public void setPartyId(long partyId) {
		this.partyId = partyId;
	}

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	
	
	public ICMSCustomer getPartyGroup() {
		long partyId = getPartyId();
		if (partyId==0) {
			return null;
		}
		
			// OBPartyGroup party = null;;
			
				//IPartyGroupDao sublineParty = (IPartyGroupDao)BeanHouse.get("partyGroupDao");
			 ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			 ICMSCustomer customer = null;
			    List cust = customerDAO.searchCustomerByCustomerId(String.valueOf(partyId));
			    if(cust!=null && cust.size() > 0)
			    {
				 customer = (ICMSCustomer)cust.get(0);
			    }
			return	customer;
	}

	public void setPartyGroup(ICMSCustomer partyGroup) {
		if (null == partyGroup) {
			setPartyGroup(null);
		}
		else {
			setPartyId(partyGroup.getCustomerID());
		}
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	
	public long getLEID() {
		return LEID;
	}

	
	public void setLEID(long LEID) {
		this.LEID = LEID;
		
	}
}
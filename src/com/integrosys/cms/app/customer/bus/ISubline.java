/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.partygroup.bus.OBPartyGroup;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface ISubline extends java.io.Serializable {
	
	public long getSublineID();

	public void setSublineID(long sublineID);

	public String getAmount() ;

	public void setAmount(String amount);
	
	public long getPartyId(); 

	public void setPartyId(long partyId);

    public long getLEID();
	
	public void setLEID(long value);
	
	//public ICMSCustomer getPartyGroup();

	//public void setPartyGroup(ICMSCustomer partyGroup) ;
}
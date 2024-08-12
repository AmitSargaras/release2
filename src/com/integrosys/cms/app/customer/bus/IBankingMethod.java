/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface IBankingMethod extends java.io.Serializable {
	
	public long getBankingMethodID();

	public void setBankingMethodID(long bankingMethodID);

	public long getBankId();

	public void setBankId(long bankId);

	
	public String getNodal();

	public void setNodal(String nodal);
	
	public String getLead();

	public void setLead(String lead);

	public String getBankType();

	public void setBankType(String bankType);
	
	public long getLEID();
		
	public void setLEID(long value);
	
	public void setEmailID(String emailID);
	public String getEmailID();
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getCustomerIDForBankingMethod();
	public void setCustomerIDForBankingMethod(String customerIDForBankingMethod);

	public String getRevisedEmailIds() ;
	public void setRevisedEmailIds(String revisedEmailIds) ;
	

}
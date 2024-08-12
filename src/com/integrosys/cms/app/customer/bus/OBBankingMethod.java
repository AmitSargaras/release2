/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankDao;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBBankingMethod implements IBankingMethod {
	private long _bankingMethodID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
	private long customerId;

	private String nodal;
	
	private String lead;
	
	private long bankId;
	
	private String bankType;
	
	private IOtherBank otherbank;
	
	private IOtherBank systembank;
	
	private long LEID;
	
	private ISystemBankProxyManager systemBankProxy;
	
	private String emailID;
	
	private String status;
	
	private String customerIDForBankingMethod;
	
	private String revisedEmailIds;
	

	public String getCustomerIDForBankingMethod() {
		return customerIDForBankingMethod;
	}

	public void setCustomerIDForBankingMethod(String customerIDForBankingMethod) {
		this.customerIDForBankingMethod = customerIDForBankingMethod;
	}

	public String getRevisedEmailIds() {
		return revisedEmailIds;
	}

	public void setRevisedEmailIds(String revisedEmailIds) {
		this.revisedEmailIds = revisedEmailIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public ISystemBankProxyManager getSystemBankProxy() {
		return systemBankProxy;
	}

	public void setSystemBankProxy(ISystemBankProxyManager systemBankProxy) {
		this.systemBankProxy = systemBankProxy;
	}

	private IOtherBankProxyManager otherBankProxyManager;
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public IOtherBranch getOtherbranch() {
		long bankId = getBankId();
		if (bankId == 0) {
			return null;
		}
		
			 OBOtherBranch bank = null;;
			
				IOtherBranchDAO otherBank = (IOtherBranchDAO)BeanHouse.get("otherBranchDAO");
			return	otherBank.getOtherBranchById(bankId);
	}

	public void setOtherbranch(IOtherBranch otherbranch) {
		if (null == otherbranch) {
			setOtherbranch(null);
		}
		else {
			setBankId(otherbranch.getId());
		}
	}

	/**
	 * Default Constructor
	 */
	public OBBankingMethod() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBBankingMethod(IBankingMethod value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	
	public long getBankingMethodID() {
		return _bankingMethodID;
	}

	public void setBankingMethodID(long bankingMethodID) {
		_bankingMethodID = bankingMethodID;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getNodal() {
		return nodal;
	}

	public void setNodal(String nodal) {
		this.nodal = nodal;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		/*IOtherBank OtherBank = new OBOtherBank();
		   		
			IOtherBankDAO otherBank = (IOtherBankDAO)BeanHouse.get("otherBankDao");
			IOtherBank bank = 	otherBank.getOtherBankById(bankId);		
			setOtherbank(bank);*/
		this.bankId = bankId;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public ISystemBank getSystembank() {
		long bankId = getBankId();
		if (bankId == 0) {
			return null;
		}
		
			 OBSystemBank bank = null;;
			
				ISystemBankDao systemBank = (ISystemBankDao)BeanHouse.get("systemBankDao");
			return	systemBank.getSystemBankById(bankId);
	}

	public void setSystembank(ISystemBank systembank) {
		if (null == systembank) {
			setSystembank(null);
		}
		else {
			setBankId(systembank.getId());
		}
	}

	
	public long getLEID() {
		return LEID;
	}

	
	public void setLEID(long LEID) {
		this.LEID = LEID;
		
	}
	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	
}
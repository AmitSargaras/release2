/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.FinderException;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankDao;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBCriFac implements ICriFac {
	private long _criFacID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	
	
	private long LEID;

	private  String facilityFor;

	private  String facilityName;

	private  String facilityAmount;
	
	private  String lineNo;
	
	private  String serialNo;
	
	private  String estateType;
	
	private  String commRealEstateType;
	
	private  String prioritySector;


	/**
	 * Default Constructor
	 */
	public OBCriFac() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBCriFac(ICriFac value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	
	public long getCriFacID() {
		return _criFacID;
	}

	public void setCriFacID(long criFacID) {
		_criFacID = criFacID;
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

	public String getFacilityFor() {
		return facilityFor;
	}

	public void setFacilityFor(String facilityFor) {
		this.facilityFor = facilityFor;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityAmount() {
		return facilityAmount;
	}

	public void setFacilityAmount(String facilityAmount) {
		this.facilityAmount = facilityAmount;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getEstateType() {
		return estateType;
	}

	public void setEstateType(String estateType) {
		this.estateType = estateType;
	}

	public String getCommRealEstateType() {
		return commRealEstateType;
	}

	public void setCommRealEstateType(String commRealEstateType) {
		this.commRealEstateType = commRealEstateType;
	}

	public String getPrioritySector() {
		return prioritySector;
	}

	public void setPrioritySector(String prioritySector) {
		this.prioritySector = prioritySector;
	}

}
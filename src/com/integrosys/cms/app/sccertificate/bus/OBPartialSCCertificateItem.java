/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/OBPartialSCCertificateItem.java,v 1.5 2004/08/13 11:02:28 mcchua Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class that provides the implementation for IPartialSCCertificateItem
 * 
 * @author $Author: mcchua $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/13 11:02:28 $ Tag: $Name: $
 */
public class OBPartialSCCertificateItem extends OBSCCertificateItem implements IPartialSCCertificateItem, Cloneable {
	private boolean partialSCCIssuedInd = false;

	private Date issuedDate = null;

	private HashMap checkListMap = null;

	public OBPartialSCCertificateItem() {
	}

	public OBPartialSCCertificateItem(String aLimitType, long aLimitID) {
		super(aLimitType, aLimitID);
	}

	/**
	 * Get Partial SCC issued indicator
	 * @return boolean - true if partial SCC is issued and false otherwise
	 */
	public boolean getIsPartialSCCIssued() {
		return this.partialSCCIssuedInd;
	}

	/**
	 * Get the issued date
	 * @return Date - the Partial SCC issued date
	 */
	public Date getIssuedDate() {
		return this.issuedDate;
	}

	/**
	 * Get the checklist map
	 * @return HashMap - the checklist map
	 */
	public HashMap getCheckListMap() {
		return this.checkListMap;
	}

	/**
	 * Get the checklist status for a collateral
	 * @param aCollateralID of long type
	 * @return String - the checklist status
	 */
	public String retrieveCheckListStatus(long aCollateralID) {
		return (String) checkListMap.get(new Long(aCollateralID));
	}

	/**
	 * Set Partial SCC issued indicator
	 * @param anIsPartialSCCIssuedInd of boolean type
	 */
	public void setIsPartialSCCIssued(boolean anIsPartialSCCIssuedInd) {
		this.partialSCCIssuedInd = anIsPartialSCCIssuedInd;
	}

	/**
	 * Set the partial scc issued date
	 * @param anIssuedDate of Date type
	 */
	public void setIssuedDate(Date anIssuedDate) {
		this.issuedDate = anIssuedDate;
	}

	/**
	 * Set the checklist map
	 * @param aCheckListMap of HashMap type
	 */
	public void setCheckListMap(HashMap aCheckListMap) {
		this.checkListMap = aCheckListMap;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public Object clone() {
		return super.clone();
	}
}

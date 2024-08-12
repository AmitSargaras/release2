/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/IPartialSCCertificateItem.java,v 1.2 2004/08/06 13:45:22 mcchua Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import java.util.Date;
import java.util.HashMap;

/**
 * This interface defines the list of attributes that is required for partial
 * SCC
 * 
 * @author $Author: mcchua $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/06 13:45:22 $ Tag: $Name: $
 */
public interface IPartialSCCertificateItem extends ISCCertificateItem {
	/**
	 * Get Partial SCC issued indicator
	 * @return boolean - true if partial SCC is issued and false otherwise
	 */
	public boolean getIsPartialSCCIssued();

	/**
	 * Get the issued date
	 * @return Date - the Partial SCC issued date
	 */
	public Date getIssuedDate();

	/**
	 * Get the checklist map
	 * @return HashMap - the checklist map
	 */
	public HashMap getCheckListMap();

	/**
	 * Get the checklist status for a collateral
	 * @param aCollateralID of long type
	 * @return String - the checklist status
	 */
	public String retrieveCheckListStatus(long aCollateralID);

	/**
	 * Set the partial SCC issued indicator
	 * @param anIsPartialSCCIssuedInd of boolean type
	 */
	public void setIsPartialSCCIssued(boolean anIsPartialSCCIssuedInd);

	/**
	 * Set the partial scc issued date
	 * @param anIssuedDate of Date type
	 */
	public void setIssuedDate(Date anIssuedDate);

	/**
	 * Set the checklist map
	 * @param aCheckListMap of HashMap type
	 */
	public void setCheckListMap(HashMap aCheckListMap);

	public Object clone();
}

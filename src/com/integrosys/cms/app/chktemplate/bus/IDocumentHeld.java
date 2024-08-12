/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IDocumentHeld.java,v 1.5 2006/08/29 09:00:27 jzhai Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that is required for audit
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/08/29 09:00:27 $ Tag: $Name: $
 */
public interface IDocumentHeld extends Serializable {
	public long getCheckListID();

	public String getCategory();

	public String getSubCategory();

	public String getLegalID();

	public String getLegalName();

	public IDocumentHeldItem[] getDocHeldItems();

	public void setCheckListID(long checkListID);

	public void setCategory(String category);

	public void setSubCategory(String subCategory);

	public void setLegalID(String legalID);

	public void setLegalName(String legalName);

	public void setDocHeldItems(IDocumentHeldItem[] documentHeldItems);

	// only if this is a security checklist
	public ICollateral getCollateral();

	public void setCollateral(ICollateral col);

	public ArrayList getCoCustomerList();

	public void setCoCustomerList(ArrayList coCustomerList);

	// only if this is a pledgor checklist
	public long getPledgorID();

	public void setPledgorID(long pledgorID);

	public List getPledgorSecurityPledged();

	public void setPledgorSecurityPledged(List securityPledgedList);

	public long getCustomerID();

	public void setCustomerID(long customerID);

	public IBookingLocation getCheckListLocation();

	public void setCheckListLocation(IBookingLocation checkListLocation);

    public String getCheckListStatus();

    public void setCheckListStatus(String checkListStatus);
}

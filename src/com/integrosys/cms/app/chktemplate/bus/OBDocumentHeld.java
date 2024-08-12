/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBDocumentHeld.java,v 1.6 2006/08/29 08:59:44 jzhai Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.chktemplate.bus.IDocumentHeld;
import com.integrosys.cms.app.chktemplate.bus.IDocumentHeldItem;

/**
 * This business object defines the list of methods required for documents held.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/29 08:59:44 $ Tag: $Name: $
 */
public class OBDocumentHeld implements IDocumentHeld {
	private long checkListID = ICMSConstant.LONG_INVALID_VALUE;

	private String category = null;

	private String subCategory = null;

	private String legalID = null;

	private String legalName = null;

	private long customerID = ICMSConstant.LONG_INVALID_VALUE;

	private IBookingLocation checkListLocation;

	private IDocumentHeldItem[] documentHeldItems = null;

	// only if this is a pledgor checklist
	private long pledgorID = ICMSConstant.LONG_INVALID_VALUE;

	private List pledgorSecuritiesPledged;

	// only if this is a security checklist
	private ICollateral collateral = null;

	private ArrayList coCustomerList;

    private String checkListStatus = null;

	public OBDocumentHeld() {
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	public String getCategory() {
		return this.category;
	}

	public String getSubCategory() {
		return this.subCategory;
	}

	public String getLegalID() {
		return this.legalID;
	}

	public String getLegalName() {
		return this.legalName;
	}

	public IDocumentHeldItem[] getDocHeldItems() {
		return this.documentHeldItems;
	}

	public ICollateral getCollateral() {
		return ((category != null) && ICMSConstant.DOC_TYPE_SECURITY.equals(category)) ? this.collateral : null;
	}

	public long getPledgorID() {
		return ((subCategory != null) && ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) ? this.pledgorID
				: ICMSConstant.LONG_INVALID_VALUE;
	}

	public List getPledgorSecurityPledged() {
		return ((subCategory != null) && ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) ? pledgorSecuritiesPledged
				: null;
	}

    public String getCheckListStatus() {
        return checkListStatus;
    }

    public void setCheckListID(long checkListID) {
		this.checkListID = checkListID;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public void setDocHeldItems(IDocumentHeldItem[] documentHeldItems) {
		this.documentHeldItems = documentHeldItems;
	}

	public void setCollateral(ICollateral collateral) {
		if ((category != null) && ICMSConstant.DOC_TYPE_SECURITY.equals(category)) {
			this.collateral = collateral;
		}
	}

	public ArrayList getCoCustomerList() {
		return coCustomerList;
	}

	public void setCoCustomerList(ArrayList coCustomerList) {
		this.coCustomerList = coCustomerList;
	}

	public void setPledgorID(long pledgorID) {
		if ((subCategory != null) && ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) {
			this.pledgorID = pledgorID;
		}
	}

	public void setPledgorSecurityPledged(List securityPledgedList) {
		if ((subCategory != null) && ICMSConstant.CHECKLIST_PLEDGER.equals(subCategory)) {
			this.pledgorSecuritiesPledged = securityPledgedList;
		}
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public IBookingLocation getCheckListLocation() {
		return checkListLocation;
	}

	public void setCheckListLocation(IBookingLocation checkListLocation) {
		this.checkListLocation = checkListLocation;
	}

    public void setCheckListStatus(String checkListStatus) {
        this.checkListStatus = checkListStatus;
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

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/DocumentHeldSearchCriteria.java,v 1.4 2006/03/22 12:25:51 hmbao Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is a helper class that will contains all the search criteria required by
 * the document held.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/03/22 12:25:51 $ Tag: $Name: $
 */
public class DocumentHeldSearchCriteria extends SearchCriteria {
	public static String CATEGORY_NON_BORROWER = "NON_BORROWER";

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long checklistID = ICMSConstant.LONG_INVALID_VALUE;

	private String category;

	private String subCategory;

	private boolean allReqDoc = false;

    private boolean completedOnly = true;

	private ITrxContext trxContext;

	private ILimitProfile limitProfile;

	private String searchCategory;

	public String getSearchCategory() {
		return searchCategory;
	}

	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}

	/**
	 * Default Constructor
	 */
	public DocumentHeldSearchCriteria() {
	}

	/**
	 * Get limit profile ID.
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Set limit profile ID.
	 * 
	 * @param limitProfileID - long
	 */
	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * Get sub profile ID.
	 * 
	 * @return long
	 */
	public long getSubProfileID() {
		return subProfileID;
	}

	/**
	 * Set sub profile ID.
	 * 
	 * @param subProfileID - long
	 */
	public void setSubProfileID(long subProfileID) {
		this.subProfileID = subProfileID;
	}

	/**
	 * Get checklist ID.
	 * 
	 * @return long
	 */
	public long getCheckListID() {
		return checklistID;
	}

	/**
	 * Set checklist ID.
	 * 
	 * @param checklistID - long
	 */
	public void setCheckListID(long checklistID) {
		this.checklistID = checklistID;
	}

	/**
	 * Get document category. Either ICMSConstant.DOC_TYPE_CC or
	 * ICMSConstant.DOC_TYPE_SECURITY.
	 * 
	 * @return String
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Set document category. Either ICMSConstant.DOC_TYPE_CC or
	 * ICMSConstant.DOC_TYPE_SECURITY.
	 * 
	 * @param category - String
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Get checklist sub-category. Either ICMSConstant.CHECKLIST_MAIN_BORROWER,
	 * ICMSConstant.CHECKLIST_CO_BORROWER, ICMSConstant.CHECKLIST_PLEDGER or
	 * ICMSConstant.CHECKLIST_NON_BORROWER.
	 * 
	 * @return String
	 */
	public String getSubCategory() {
		return subCategory;
	}

	/**
	 * Set checklist sub-category. Either ICMSConstant.CHECKLIST_MAIN_BORROWER,
	 * ICMSConstant.CHECKLIST_CO_BORROWER, ICMSConstant.CHECKLIST_PLEDGER or
	 * ICMSConstant.CHECKLIST_NON_BORROWER.
	 * 
	 * @param subCategory of type String
	 */
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public boolean isAllReqDoc() {
		return allReqDoc;
	}

	public void setAllReqDoc(boolean allReqDoc) {
		this.allReqDoc = allReqDoc;
	}

	public ITrxContext getTrxContext() {
		return trxContext;
	}

	public void setTrxContext(ITrxContext trxContext) {
		this.trxContext = trxContext;
	}

	public ILimitProfile getLimitProfile() {
		return limitProfile;
	}

	public void setLimitProfile(ILimitProfile limitProfile) {
		this.limitProfile = limitProfile;
	}

    public boolean isCompletedOnly() {
        return completedOnly;
    }

    public void setCompletedOnly(boolean completedOnly) {
        this.completedOnly = completedOnly;
    }
}

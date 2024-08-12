/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CommodityDealSearchCriteria.java,v 1.7 2005/08/05 12:34:29 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the commodity deal.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/05 12:34:29 $ Tag: $Name: $
 */
public class CommodityDealSearchCriteria extends SearchCriteria {
	private String dealNo;

	private long customerID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isPendingOfficerApproval;

	private String trxID;

	private ITrxContext trxContext;

	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isSearchByColID = false;

	/**
	 * Default Constructor
	 */
	public CommodityDealSearchCriteria() {
	}

	/**
	 * Get transaction context.
	 * 
	 * @return String
	 */
	public ITrxContext getTrxContext() {
		return trxContext;
	}

	/**
	 * Set transaction context.
	 * 
	 * @param trxContext of type ITrxContext
	 */
	public void setTrxContext(ITrxContext trxContext) {
		this.trxContext = trxContext;
	}

	/**
	 * Get commodity deal number.
	 * 
	 * @return String
	 */
	public String getDealNo() {
		return dealNo;
	}

	/**
	 * Set commodity deal number.
	 * 
	 * @param dealNo of type String
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	/**
	 * Get cms sub profile id.
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return customerID;
	}

	/**
	 * Set cms sub profile id.
	 * 
	 * @param customerID of type long
	 */
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	/**
	 * Get limit profile id.
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * Set limit profile id.
	 * 
	 * @param limitProfileID of type long
	 */
	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * Get transaction id.
	 * 
	 * @return String
	 */
	public String getTrxID() {
		return trxID;
	}

	/**
	 * Set transaction id.
	 * 
	 * @param trxID of type String
	 */
	public void setTrxID(String trxID) {
		this.trxID = trxID;
	}

	/**
	 * A flag to indicate that the search is from FAM Task Re-assignment.
	 * 
	 * @return boolean
	 */
	public boolean isPendingOfficerApproval() {
		return isPendingOfficerApproval;
	}

	/**
	 * Set the flag that the search is from FAM Task Re-assignment.
	 * 
	 * @param pendingOfficerApproval of type boolean
	 */
	public void setPendingOfficerApproval(boolean pendingOfficerApproval) {
		isPendingOfficerApproval = pendingOfficerApproval;
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * If the search is by collateral id.
	 * 
	 * @return boolean
	 */
	public boolean isSearchByColID() {
		return isSearchByColID;
	}

	/**
	 * Set if the search is by collateral id.
	 * 
	 * @param searchByColID of type boolean
	 */
	public void setSearchByColID(boolean searchByColID) {
		isSearchByColID = searchByColID;
	}
}

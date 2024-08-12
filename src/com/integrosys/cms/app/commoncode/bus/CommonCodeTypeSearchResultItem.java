package com.integrosys.cms.app.commoncode.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeSearchResultItem implements Serializable {
	private String trxID = null;

	private String trxStatus = null;

	private ICommonCodeType item = null;

	/**
	 * Get the trx ID
	 * @return String - the trx ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the trx status
	 * @return String - the trx status
	 */
	public String getTrxStatus() {
		return this.trxStatus;
	}

	public ICommonCodeType getCommonCodeType() {
		return this.item;
	}

	/**
	 * Get the item ID
	 * @return long - the item ID
	 */
	public long getCommonCategoryId() {
		if (getCommonCodeType() != null) {
			return getCommonCodeType().getCommonCategoryId();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the item code
	 * @return String - the item code
	 */
	public String getCommonCategoryCode() {
		if (getCommonCodeType() != null) {
			return getCommonCodeType().getCommonCategoryCode();
		}
		return null;
	}

	/**
	 * Get the item description
	 * @return String - the item description
	 */
	public String getCommonCategoryName() {
		if (getCommonCodeType() != null) {
			return getCommonCodeType().getCommonCategoryName();
		}
		return null;
	}

	/**
	 * Set the trx ID.
	 * @param aTrxID - String
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the trx status.
	 * @param aTrxStatus - String
	 */
	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	/**
	 * Set the item
	 * @param anIItem - IItem
	 */
	public void setItem(ICommonCodeType anIItem) {
		this.item = anIItem;
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

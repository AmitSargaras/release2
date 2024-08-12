/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.chktemplate.bus;

// java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class implements the ICheckList
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class DocumentSearchResultItem implements Serializable, Comparable {

	private static final long serialVersionUID = 5398068867790828776L;

	private String trxID = null;

	private String trxStatus = null;

	private IItem item = null;

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

//	public String getLoanApplicationType() {
//		if (getItem() != null) {
//			String appType = getItem().getLoanApplicationType();
//			if (!"0".equals(appType)) {
//				return appType;
//			}
//			return null;
//		}
//		return null;
//	}

	public String getDocVersion() {
		if (getItem() != null) {
			return getItem().getDocumentVersion();
		}
		return null;
	}

	public boolean getIsForBorrower() {
		if (getItem() != null) {
			return getItem().getIsForBorrower();
		}
		return false;
	}

	public boolean getIsForPledgor() {
		if (getItem() != null) {
			return getItem().getIsForPledgor();
		}
		return false;
	}

	public String getIsPreApprove() {
		String isPreApprove = ICMSConstant.FALSE_VALUE;
		if (getItem() != null) {
			if (getItem().getIsPreApprove()) {
				isPreApprove = ICMSConstant.TRUE_VALUE;
			}
		}
		return isPreApprove;
	}
	
	public String getTenureType() {
		String tenureType = "";
		if (getItem() != null) {
			if (getItem().getTenureType()!=null) {
				tenureType =getItem().getTenureType() ;
			}
		}
		return tenureType;
	}
	public String getStatementType() {
		String statementType = "";
		if (getItem() != null) {
			if (getItem().getStatementType()!=null) {
				statementType =getItem().getStatementType() ;
			}
		}
		return statementType;
	}
	
	public String getIsRecurrent() {
		String isRecurrent = "";
		if (getItem() != null) {
			if (getItem().getIsRecurrent()!=null) {
				isRecurrent =getItem().getIsRecurrent() ;
			}
		}
		return isRecurrent;
	}
	public String getRating() {
		String rating = "";
		if (getItem() != null) {
			if (getItem().getRating()!=null) {
				rating =getItem().getRating() ;
			}
		}
		return rating;
	}
	public String getSegment() {
		String segment = "";
		if (getItem() != null) {
			if (getItem().getSegment()!=null) {
				segment =getItem().getSegment() ;
			}
		}
		return segment;
	}
	public String getTotalSancAmt() {
		String totalSancAmt= "";
		if (getItem() != null) {
			if (getItem().getTotalSancAmt()!=null) {
				totalSancAmt =getItem().getTotalSancAmt() ;
			}
		}
		return totalSancAmt;
	}
	public String getClassification() {
		String classification = "";
		if (getItem() != null) {
			if (getItem().getClassification()!=null) {
				classification =getItem().getClassification() ;
			}
		}
		return classification;
	}
	public String getGuarantor() {
		String guarantor = "";
		if (getItem() != null) {
			if (getItem().getGuarantor()!=null) {
				guarantor =getItem().getGuarantor() ;
			}
		}
		return guarantor;
	}
	
	public String getStatus() {
		String status = "";
		if (getItem() != null) {
			if (getItem().getStatus()!=null) {
				status =getItem().getStatus();
			}
		}
		return status;
	}
	public String getDeprecated() {
		String deprecated = "";
		if (getItem() != null) {
			if (getItem().getDeprecated()!=null) {
				deprecated =getItem().getDeprecated() ;
			}
		}
		return deprecated;
	}
	
	public int getTenureCount() {
		int tenureCount = 0;
		if (getItem() != null) {
			tenureCount =getItem().getTenureCount() ;
		}
		return tenureCount;
	}

	public IItem getItem() {
		return this.item;
	}

	/**
	 * Get the item ID
	 * @return long - the item ID
	 */
	public long getItemID() {
		if (getItem() != null) {
			return getItem().getItemID();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the item code
	 * @return String - the item code
	 */
	public String getItemCode() {
		if (getItem() != null) {
			return getItem().getItemCode();
		}
		return null;
	}

	/**
	 * Get the item description
	 * @return String - the item description
	 */
	public String getItemDesc() {
		if (getItem() != null) {
			return getItem().getItemDesc();
		}
		return null;
	}

	/**
	 * Get the expiry date
	 * @return Date - the expiry date
	 */
	public Date getExpiryDate() {
		if (getItem() != null) {
			return getItem().getExpiryDate();
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
	public void setItem(IItem anIItem) {
		this.item = anIItem;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("DocumentSearchResultItem [");
		buf.append("item=");
		buf.append(item);
		buf.append(", trxID=");
		buf.append(trxID);
		buf.append(", trxStatus=");
		buf.append(trxStatus);
		buf.append("]");
		return buf.toString();
	}

	public int compareTo(Object other) {
		String otherItemCode = ((other == null) || ((DocumentSearchResultItem) other).getItem() == null) ? null
				: ((DocumentSearchResultItem) other).getItem().getItemCode();

		if (this.getItemCode() == null) {
			return (otherItemCode == null) ? 0 : -1;
		}

		return (otherItemCode == null) ? 1 : this.getItemCode().compareTo(otherItemCode);
	}
}

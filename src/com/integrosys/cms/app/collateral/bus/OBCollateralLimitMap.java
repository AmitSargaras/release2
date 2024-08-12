/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateralLimitMap.java,v 1.15 2006/08/14 04:11:40 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents collateral limit.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/08/14 04:11:40 $ Tag: $Name: $
 */
public class OBCollateralLimitMap implements ICollateralLimitMap {

	private static final long serialVersionUID = 5232874498848304313L;

	private long chargeID = ICMSConstant.LONG_INVALID_VALUE;

	private long collateralID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String sciLimitID;

	private String limitType;

	private long sciSysGenID = ICMSConstant.LONG_MIN_VALUE;

	private long sciLimitProfileID = ICMSConstant.LONG_MIN_VALUE;

	private long cmsLimitProfileId = ICMSConstant.LONG_MIN_VALUE;

	private long sciSubProfileID = ICMSConstant.LONG_MIN_VALUE;

	private String sciLegalEntityID;

	private String sciSecurityID;

	private long sciPledgorID = ICMSConstant.LONG_MIN_VALUE;

	private boolean isCollateralPool;

	private boolean isSpecificTrx;

	private double cashReqPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private boolean isAppliedLimitAmtIncluded;

	private boolean isReleasedLimitAmtIncluded;

	private Amount appliedLimitAmount;

	private Amount releasedLimitAmount;

	private int priorityRanking = ICMSConstant.INT_INVALID_VALUE;

	private String sciStatus;

	private ISubLimit[] subLimitArray;

	// CR035
	private String coBorrowerLEID;

	private long coBorrowerSubProfileID;

	private long coBorrowerLimitID;

	private String sCICoBorrowerLimitID;

	private String customerCategory;

	private Amount pledgeAmount;

	private double pledgeAmountPercentage;

	private Amount drawAmount;

	private double drawAmountPercentage;

	private Character changeIndicator;

	private String sourceID;
	

	private String lmtSecurityCoverage;
	
	private String cpsSecurityId;

	//

	/**
	 * Default Constructor.
	 */
	public OBCollateralLimitMap() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralLimitMap
	 */
	public OBCollateralLimitMap(ICollateralLimitMap obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get applied limit amount for asset based general charge security.
	 * 
	 * @return Amount
	 */
	public Amount getAppliedLimitAmount() {
		return appliedLimitAmount;
	}

	/**
	 * Get cash requirement percentage.
	 * 
	 * @return double
	 */
	public double getCashReqPct() {
		return cashReqPct;
	}

	/**
	 * Get charge id.
	 * 
	 * @return long
	 */
	public long getChargeID() {
		return chargeID;
	}

	/**
	 * @return the cmsLimitProfileId
	 */
	public long getCmsLimitProfileId() {
		return cmsLimitProfileId;
	}

	public String getCoBorrowerLEID() {
		return coBorrowerLEID;
	}

	public long getCoBorrowerLimitID() {
		return coBorrowerLimitID;
	}

	public long getCoBorrowerSubProfileID() {
		return coBorrowerSubProfileID;
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public Amount getDrawAmount() {
		return drawAmount;
	}

	public double getDrawAmountPercentage() {
		return drawAmountPercentage;
	}

	/**
	 * Get if the applied limit amount is to be included in calculations for
	 * total applied limit amount for a collateral.
	 * 
	 * @return boolean
	 */
	public boolean getIsAppliedLimitAmountIncluded() {
		return isAppliedLimitAmtIncluded;
	}

	/**
	 * Get if the limit allows collateral pool.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralPool() {
		return isCollateralPool;
	}

	/**
	 * Get if the released limit amount is to be included in calculations for
	 * total released limit amount for a collateral.
	 * 
	 * @return boolean
	 */
	public boolean getIsReleasedLimitAmountIncluded() {
		return isReleasedLimitAmtIncluded;
	}

	/**
	 * Get if the limit allows specific transaction.
	 * 
	 * @return boolean
	 */
	public boolean getIsSpecificTrx() {
		return isSpecificTrx;
	}

	/**
	 * Get limit id.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Get limit product type.
	 * 
	 * @return String
	 */
	public String getLimitType() {
		return limitType;
	}

	public Amount getPledgeAmount() {
		return pledgeAmount;
	}

	public double getPledgeAmountPercentage() {
		return pledgeAmountPercentage;
	}

	/**
	 * Get limit priority ranking within a collateral.
	 * 
	 * @return int
	 */
	public int getPriorityRanking() {
		return priorityRanking;
	}

	/**
	 * Get released limit amount for asset based general charge security.
	 * 
	 * @return Amount
	 */
	public Amount getReleasedLimitAmount() {
		return releasedLimitAmount;
	}

	public String getSCICoBorrowerLimitID() {
		return sCICoBorrowerLimitID;
	}

	/**
	 * Get main profile id from SCI.
	 * 
	 * @return String
	 */
	public String getSCILegalEntityID() {
		return sciLegalEntityID;
	}

	/**
	 * Get limit id from SCI.
	 * 
	 * @return String
	 */
	public String getSCILimitID() {
		return sciLimitID;
	}

	/**
	 * Get limit profile id from SCI.
	 * 
	 * @return long
	 */
	public long getSCILimitProfileID() {
		return sciLimitProfileID;
	}

	/**
	 * Get pledgor id from SCI.
	 * 
	 * @return long
	 */
	public long getSCIPledgorID() {
		return sciPledgorID;
	}

	/**
	 * Get security id from SCI.
	 * 
	 * @return String
	 */
	public String getSCISecurityID() {
		return sciSecurityID;
	}

	/**
	 * Get update status indicator from SCI.
	 * 
	 * @return String
	 */
	public String getSCIStatus() {
		return sciStatus;
	}

	/**
	 * Get customer id from SCI.
	 * 
	 * @return long
	 */
	public long getSCISubProfileID() {
		return sciSubProfileID;
	}

	/**
	 * Get system generated id from SCI.
	 * 
	 * @return long
	 */
	public long getSCISysGenID() {
		return sciSysGenID;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.collateral.bus.ICollateralLimitMap#getSubLimit()
	 */
	public ISubLimit[] getSubLimit() {
		return subLimitArray;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(chargeID);
		return hash.hashCode();
	}

	/**
	 * Set applied limit amount for asset based general charge security.
	 * 
	 * @param appliedLimitAmt of type Amount
	 */
	public void setAppliedLimitAmount(Amount appliedLimitAmt) {
		this.appliedLimitAmount = appliedLimitAmt;
	}

	/**
	 * Set cash requirement percentage.
	 * 
	 * @param cashReqPct of type double
	 */
	public void setCashReqPct(double cashReqPct) {
		this.cashReqPct = cashReqPct;
	}

	/**
	 * Set charge id.
	 * 
	 * @param chargeID of type long
	 */
	public void setChargeID(long chargeID) {
		this.chargeID = chargeID;
	}

	/**
	 * @param cmsLimitProfileId the cmsLimitProfileId to set
	 */
	public void setCmsLimitProfileId(long cmsLimitProfileId) {
		this.cmsLimitProfileId = cmsLimitProfileId;
	}

	public void setCoBorrowerLEID(String coBorrowerLEID) {
		this.coBorrowerLEID = coBorrowerLEID;
	}

	public void setCoBorrowerLimitID(long coBorrowerLimitID) {
		this.coBorrowerLimitID = coBorrowerLimitID;
	}

	// CR035

	public void setCoBorrowerSubProfileID(long coBorrowerSubProfileID) {
		this.coBorrowerSubProfileID = coBorrowerSubProfileID;
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public void setDrawAmount(Amount drawAmount) {
		this.drawAmount = drawAmount;
	}

	public void setDrawAmountPercentage(double drawAmountPercentage) {
		this.drawAmountPercentage = drawAmountPercentage;
	}

	/**
	 * Set if the applied limit amount is to be included in calculations for
	 * total applied limit amount for a collateral.
	 * 
	 * @param isIncluded - boolean
	 */
	public void setIsAppliedLimitAmountIncluded(boolean isIncluded) {
		this.isAppliedLimitAmtIncluded = isIncluded;
	}

	/**
	 * Set if the limit allows collateral pool.
	 * 
	 * @param isCollateralPool of type boolean
	 */
	public void setIsCollateralPool(boolean isCollateralPool) {
		this.isCollateralPool = isCollateralPool;
	}

	/**
	 * Set if the released limit amount is to be included in calculations for
	 * total released limit amount for a collateral.
	 * 
	 * @param isIncluded - boolean
	 */
	public void setIsReleasedLimitAmountIncluded(boolean isIncluded) {
		this.isReleasedLimitAmtIncluded = isIncluded;
	}

	/**
	 * Set if the limit allows specific transaction.
	 * 
	 * @param isSpecificTrx of type boolean
	 */
	public void setIsSpecificTrx(boolean isSpecificTrx) {
		this.isSpecificTrx = isSpecificTrx;
	}

	/**
	 * Set limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	/**
	 * Set limit type.
	 * 
	 * @param limitType of type String
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public void setPledgeAmount(Amount pledgeAmount) {
		this.pledgeAmount = pledgeAmount;
	}

	public void setPledgeAmountPercentage(double pledgeAmountPercentage) {
		this.pledgeAmountPercentage = pledgeAmountPercentage;
	}

	/**
	 * Set limit priority ranking within a collateral.
	 * 
	 * @param priorityRanking of type int
	 */
	public void setPriorityRanking(int priorityRanking) {
		this.priorityRanking = priorityRanking;
	}

	/**
	 * Set released limit amount for asset based general charge security.
	 * 
	 * @param releasedLimitAmt of type Amount
	 */
	public void setReleasedLimitAmount(Amount releasedLimitAmt) {
		this.releasedLimitAmount = releasedLimitAmt;
	}

	public void setSCICoBorrowerLimitID(String sCICoBorrowerLimitID) {
		this.sCICoBorrowerLimitID = sCICoBorrowerLimitID;
	}

	/**
	 * Set main profile id from SCI.
	 * 
	 * @param sciLegalEntityID of type String
	 */
	public void setSCILegalEntityID(String sciLegalEntityID) {
		this.sciLegalEntityID = sciLegalEntityID;
	}

	/**
	 * Set SCI limit id.
	 * 
	 * @param sciLimitID of type String
	 */
	public void setSCILimitID(String sciLimitID) {
		this.sciLimitID = sciLimitID;
	}

	/**
	 * Set limit profile id from SCI.
	 * 
	 * @param sciLimitProfileID of type long
	 */
	public void setSCILimitProfileID(long sciLimitProfileID) {
		this.sciLimitProfileID = sciLimitProfileID;
	}

	/**
	 * Set pledgor id from SCI.
	 * 
	 * @param sciPledgorID of type long
	 */
	public void setSCIPledgorID(long sciPledgorID) {
		this.sciPledgorID = sciPledgorID;
	}

	/**
	 * Set security id from SCI.
	 * 
	 * @param sciSecurityID of type String
	 */
	public void setSCISecurityID(String sciSecurityID) {
		this.sciSecurityID = sciSecurityID;
	}

	/**
	 * Set update status indicator from SCI.
	 * 
	 * @param sciStatus of type String
	 */
	public void setSCIStatus(String sciStatus) {
		this.sciStatus = sciStatus;
	}

	/**
	 * Set customer id from SCI.
	 * 
	 * @param sciSubProfileID of type long
	 */
	public void setSCISubProfileID(long sciSubProfileID) {
		this.sciSubProfileID = sciSubProfileID;
	}

	/**
	 * Set system generated id from SCI.
	 * 
	 * @param sciSysGenID of type long
	 */
	public void setSCISysGenID(long sciSysGenID) {
		this.sciSysGenID = sciSysGenID;
	}

	public void setSubLimit(ISubLimit[] subLimitArray) {
		this.subLimitArray = subLimitArray;
	}

	public Character getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(Character changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String getSourceID() {
		return sourceID;
	}

	public String getLmtSecurityCoverage() {
		return lmtSecurityCoverage;
	}

	public void setLmtSecurityCoverage(String lmtSecurityCoverage) {
		this.lmtSecurityCoverage = lmtSecurityCoverage;
	}

	public String getCpsSecurityId() {
		return cpsSecurityId;
	}

	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCollateralLimitMap)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	//
	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("CollateralLimitMap: ");
		buf.append("Limit Profile Id").append("[").append(cmsLimitProfileId).append("]");
		buf.append("CMS Limit Id").append("[").append(limitID).append("]");
		buf.append("CMS Collateral Id").append("[").append(collateralID).append("]");
		buf.append("Limit Id").append("[").append(sciLimitID).append("]");
		buf.append("Collateral Id").append("[").append(sciSecurityID).append("]");
		buf.append("status").append("[").append(sciStatus).append("]");

		return buf.toString();
	}

}
/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralLimitMap.java,v 1.14 2006/08/14 04:11:40 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;

/**
 * This interface represents collateral limit.
 * 
 * @author nkumar
 * @author Chong Jun Yong
 * @since 2006/08/14 04:11:40
 */
public interface ICollateralLimitMap extends Serializable {

	/**
	 * To indicate the usage of charge information of the limit security linkage
	 * is Percentage (%)
	 */
	public final static char CHARGE_INFO_PERCENTAGE_USAGE = ICollateral.CHARGE_INFO_PERCENTAGE_USAGE.charValue();

	/**
	 * To indicate the usage of charge information of the limit security linkage
	 * is Amount ($)
	 */
	public final static char CHARGE_INFO_AMOUNT_USAGE = ICollateral.CHARGE_INFO_AMOUNT_USAGE.charValue();

	/**
	 * Get applied limit amount for asset based general charge security.
	 * 
	 * @return Amount
	 */
	public Amount getAppliedLimitAmount();

	/**
	 * Get cash requirement percentage.
	 * 
	 * @return double
	 */
	public double getCashReqPct();

	public Character getChangeIndicator();

	/**
	 * Get charge id.
	 * 
	 * @return long
	 */
	public long getChargeID();

	public long getCmsLimitProfileId();

	public String getCoBorrowerLEID();

	public long getCoBorrowerLimitID();

	public long getCoBorrowerSubProfileID();

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	public String getCustomerCategory();

	public Amount getDrawAmount();

	public double getDrawAmountPercentage();

	/**
	 * Get if the applied limit amount is to be included in calculations for
	 * total applied limit amount for a collateral.
	 * 
	 * @return boolean
	 */
	public boolean getIsAppliedLimitAmountIncluded();

	/**
	 * Get if the limit allows collateral pool.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralPool();

	/**
	 * Get if the released limit amount is to be included in calculations for
	 * total released limit amount for a collateral.
	 * 
	 * @return boolean
	 */
	public boolean getIsReleasedLimitAmountIncluded();

	/**
	 * Get if the limit allows specific transaction.
	 * 
	 * @return boolean
	 */
	public boolean getIsSpecificTrx();

	/**
	 * Get limit id.
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Get limit product type.
	 * 
	 * @return String
	 */
	public String getLimitType();

	public Amount getPledgeAmount();

	public double getPledgeAmountPercentage();

	/**
	 * Get limit priority ranking within a collateral.
	 * 
	 * @return int
	 */
	public int getPriorityRanking();

	/**
	 * Get released limit amount for asset based general charge security.
	 * 
	 * @return Amount
	 */
	public Amount getReleasedLimitAmount();

	public String getSCICoBorrowerLimitID();

	/**
	 * Get main profile id from SCI.
	 * 
	 * @return String
	 */
	public String getSCILegalEntityID();

	/**
	 * Get limit id from SCI.
	 * 
	 * @return String
	 */
	public String getSCILimitID();

	/**
	 * Get limit profile id from SCI.
	 * 
	 * @return long
	 */
	public long getSCILimitProfileID();

	/**
	 * Get pledgor id from SCI.
	 * 
	 * @return long
	 */
	public long getSCIPledgorID();

	/**
	 * Get security id from SCI.
	 * 
	 * @return String
	 */
	public String getSCISecurityID();

	/**
	 * Get update status indicator from SCI.
	 * 
	 * @return String
	 */
	public String getSCIStatus();

	/**
	 * Get customer id from SCI.
	 * 
	 * @return long
	 */
	public long getSCISubProfileID();

	/**
	 * Get system generated id from SCI.
	 * 
	 * @return long
	 */
	public long getSCISysGenID();

	public String getSourceID();

	public ISubLimit[] getSubLimit();

	/**
	 * Set applied limit amount for asset based general charge security.
	 * 
	 * @param appliedLimitAmt of type Amount
	 */
	public void setAppliedLimitAmount(Amount appliedLimitAmt);

	/**
	 * Set cash requirement percentage.
	 * 
	 * @param cashReqPct of type double
	 */
	public void setCashReqPct(double cashReqPct);

	//

	public void setChangeIndicator(Character changeIndicator);

	/**
	 * Set charge id.
	 * 
	 * @param chargeID of type long
	 */
	public void setChargeID(long chargeID);

	public void setCmsLimitProfileId(long cmsLimitProfileId);

	public void setCoBorrowerLEID(String coBorrowerLEID);

	public void setCoBorrowerLimitID(long coBorrowerLimitID);

	public void setCoBorrowerSubProfileID(long coBorrowerSubProfileID);

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID);

	public void setCustomerCategory(String customerCategory);

	public void setDrawAmount(Amount drawAmount);

	public void setDrawAmountPercentage(double drawAmountPercentage);
	

	public abstract String getLmtSecurityCoverage();

	public abstract void setLmtSecurityCoverage(String lmtSecurityCoverage);
	
	public abstract String getCpsSecurityId();

	public abstract void setCpsSecurityId(String cpsSecurityId);

	/**
	 * Set if the applied limit amount is to be included in calculations for
	 * total applied limit amount for a collateral.
	 * 
	 * @param isIncluded - boolean
	 */
	public void setIsAppliedLimitAmountIncluded(boolean isIncluded);

	/**
	 * Set if the limit allows collateral pool.
	 * 
	 * @param isCollateralPool of type boolean
	 */
	public void setIsCollateralPool(boolean isCollateralPool);

	/**
	 * Set if the released limit amount is to be included in calculations for
	 * total released limit amount for a collateral.
	 * 
	 * @param isIncluded - boolean
	 */
	public void setIsReleasedLimitAmountIncluded(boolean isIncluded);

	/**
	 * Set if the limit allows specific transaction.
	 * 
	 * @param isSpecificTrx of type boolean
	 */
	public void setIsSpecificTrx(boolean isSpecificTrx);

	/**
	 * Set limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID);

	/**
	 * Set limit type.
	 * 
	 * @param limitType of type String
	 */
	public void setLimitType(String limitType);

	public void setPledgeAmount(Amount pledgeAmount);

	public void setPledgeAmountPercentage(double pledgeAmountPercentage);

	/**
	 * Set limit priority ranking within a collateral.
	 * 
	 * @param priorityRanking of type int
	 */
	public void setPriorityRanking(int priorityRanking);

	/**
	 * Set released limit amount for asset based general charge security.
	 * 
	 * @param releasedLimitAmt of type Amount
	 */
	public void setReleasedLimitAmount(Amount releasedLimitAmt);

	public void setSCICoBorrowerLimitID(String sCICoBorrowerLimitID);

	/**
	 * Set main profile id from SCI.
	 * 
	 * @param sciLegalEntityID of type String
	 */
	public void setSCILegalEntityID(String sciLegalEntityID);

	/**
	 * Set SCI limit id.
	 * 
	 * @param sciLimitID of type String
	 */
	public void setSCILimitID(String sciLimitID);

	/**
	 * Set limit profile id from SCI.
	 * 
	 * @param sciLimitProfileID of type long
	 */
	public void setSCILimitProfileID(long sciLimitProfileID);

	/**
	 * Set pledgor id from SCI.
	 * 
	 * @param sciPledgorID of type long
	 */
	public void setSCIPledgorID(long sciPledgorID);

	/**
	 * Set security id from SCI.
	 * 
	 * @param sciSecurityID of type String
	 */
	public void setSCISecurityID(String sciSecurityID);

	/**
	 * Set update status indicator from SCI.
	 * 
	 * @param sciStatus of type String
	 */
	public void setSCIStatus(String sciStatus);

	/**
	 * Set customer id from SCI.
	 * 
	 * @param sciSubProfileID of type long
	 */
	public void setSCISubProfileID(long sciSubProfileID);

	/**
	 * Set system generated id from SCI.
	 * 
	 * @param sciSysGenID of type long
	 */
	public void setSCISysGenID(long sciSysGenID);

	public void setSourceID(String sourceID);

	public void setSubLimit(ISubLimit[] subLimitArray);
}
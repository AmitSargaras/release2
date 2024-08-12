/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateItemBean.java,v 1.9 2005/12/20 02:59:14 hmbao Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for ccc item Information
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/12/20 02:59:14 $ Tag: $Name: $
 */
public abstract class EBCCCertificateItemBean implements EntityBean, ICCCertificateItem {
	private static final String[] EXCLUDE_METHOD = new String[] { "getCCCertItemID", "getCCCertItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCCCertificateItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPCCCertItemID();

	public abstract String getIsDeletedIndStr();

	public abstract Long getCMPCCCertID();

	public abstract Long getCMPLimitID();

	public abstract String getActivatedAmtCurrency();

	public abstract double getActivatedAmt();

	public abstract String getApprovedLimitAmtCcy();

	public abstract BigDecimal getApprovedLimitAmt();

	public abstract String getBkgLoctnCtry();

	public abstract String getBkgLoctnOrg();

	public abstract String getCleanLimitInd();

	public abstract long getCCCertItemRef();

    public abstract String getLimitType();

    public abstract Date getApprovalDate();

    public abstract String getProductDesc();

    public abstract String getOuterLimitRef();

    public abstract String getCoBorrowerLegalID();

    public abstract String getCoBorrowerName();

    public abstract Date getMaturityDate();

	public abstract void setCMPCCCertItemID(Long aCCCertItemID);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCMPCCCertID(Long aCCCertID);

	public abstract void setCMPLimitID(Long aLimtiID);

	public abstract void setLimitType(String aLimitType);

	public abstract void setActivatedAmtCurrency(String anActivatedAmtCurrency);

	public abstract void setActivatedAmt(double anActivatedLimit);

	public abstract void setApprovedLimitAmtCcy(String approvedLimitAmtCcy);

	public abstract void setApprovedLimitAmt(BigDecimal approvedLimitAmt);

	public abstract void setBkgLoctnCtry(String bkgLoctnCtry);

	public abstract void setBkgLoctnOrg(String bkgLoctnOrg);

	public abstract void setCleanLimitInd(String cleanLimitInd);

    public abstract void setCCCertItemRef(long aSCCertItemRef);

    public abstract void setApprovalDate(Date approvalDate);

    public abstract void setProductDesc(String productDesc);

    public abstract void setOuterLimitRef(String outerLimitRef);

    public abstract void setCoBorrowerLegalID(String coBorrowerLegalID);

    public abstract void setCoBorrowerName(String coBorrowerName);

    public abstract void setMaturityDate(Date maturiyDate);

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the ccc item ID
	 * @return long - the long value of the ccc item
	 */
	public long getCCCertItemID() {
		if (getCMPCCCertItemID() != null) {
			return getCMPCCCertItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the approved limit amount.
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount() {
		if ((getApprovedLimitAmtCcy() != null) && (getApprovedLimitAmt() != null)) {
			return new Amount(getApprovedLimitAmt(), new CurrencyCode(getApprovedLimitAmtCcy()));
		}
		return null;
	}

	/**
	 * Get the limit booking location.
	 * 
	 * @return IBookingLocation - the limit booking location
	 */
	public IBookingLocation getLimitBookingLocation() {
		OBBookingLocation bkg = new OBBookingLocation();
		bkg.setCountryCode(getBkgLoctnCtry());
		bkg.setOrganisationCode(getBkgLoctnOrg());
		return bkg;
	}

	/**
	 * Helper method to get the delete indicator
	 * @return boolean - true if it is to be deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		if ((getIsDeletedIndStr() != null) && getIsDeletedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public Amount getActivatedAmount() {
		String ccy = getActivatedAmtCurrency();
		double amt = getActivatedAmt();
		return new Amount(amt, ccy);
	}

	public String getCheckListStatus() {
		return null;
	}

	public long getLimitID() {
		if (getCMPLimitID() != null) {
			return getCMPLimitID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Not implemented
	 */
	public String getLimitRef() {
		return null;
	}

	public boolean isInnerLimit() {
		return false;
	}

	public boolean isCleanType() {
		return false;
	}

	public long getOuterLimitID() {
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getOuterLimitProfileID() {
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public boolean getIsInnerOuterSameBCA() {
		return true;
	}

	/**
	 * Get actual limit expiry date.
	 * 
	 * @return Date
	 */
	public Date getLimitExpiryDate() {
		return null;
	}

	/**
	 * Set the approved limit amount.
	 * 
	 * @param approvedLimitAmount of Amount type
	 */
	public void setApprovedLimitAmount(Amount approvedLimitAmount) {
		setApprovedLimitAmt(approvedLimitAmount == null ? null : approvedLimitAmount.getAmountAsBigDecimal());
		setApprovedLimitAmtCcy(approvedLimitAmount == null ? null : approvedLimitAmount.getCurrencyCode());
	}

	/**
	 * Set the limit booking location.
	 * 
	 * @param bkgLoctn of IBookingLocation type
	 */
	public void setLimitBookingLocation(IBookingLocation bkgLoctn) {
		setBkgLoctnCtry(bkgLoctn == null ? null : bkgLoctn.getCountryCode());
		setBkgLoctnOrg(bkgLoctn == null ? null : bkgLoctn.getOrganisationCode());
	}

	/**
	 * Helper method to set the ccc item ID
	 * @param aCCCertItemID - long
	 */
	public void setCCCertItemID(long aCCCertItemID) {
		setCMPCCCertItemID(new Long(aCCCertItemID));
	}

	/**
	 * Helper method to set delete indicator
	 * @param anIsDeletedInd - boolean
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		if (anIsDeletedInd) {
			setIsDeletedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsDeletedIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Set the activated amount
	 * @param anActivatedAmount of Amount type
	 */
	public void setActivatedAmount(Amount anActivatedAmount) {
		if (null != anActivatedAmount) {
			setActivatedAmt(anActivatedAmount.getAmount());
			setActivatedAmtCurrency(anActivatedAmount.getCurrencyCode());
		}
		else {
			setActivatedAmt(0);
			setActivatedAmtCurrency(getApprovedLimitAmtCcy());
		}
	}

	public void setCheckListStatus(String aCheckListStatus) {
		// do nothing
	}

	public void setIsCleanTypeInd(boolean anIsCleanTypeInd) {
	}

	public void setLimitRef(String aLimitRef) {
		// do nothing
	}

	public void setOuterLimitID(long outerLimitID) {
	}

	public void setOuterLimitProfileID(long outerLimitProfileID) {
	}

	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA) {
	}

	public void setLimitExpiryDate(Date limitExpiryDate) {
	}

	// *****************************************************
	/**
	 * Create a ccc item Information
	 * @param anICCCertificateItem of ICCCertificateItem type
	 * @return Long - the ccc item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICCCertificateItem anICCCertificateItem) throws CreateException {
		if (anICCCertificateItem == null) {
			throw new CreateException("ICCCertificateItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setCCCertItemID(pk);
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anICCCertificateItem
					.getCCCertItemRef()) {
				setCCCertItemRef(pk);
			}
			else {
				setCCCertItemRef(anICCCertificateItem.getCCCertItemRef());
			}
			AccessorUtil.copyValue(anICCCertificateItem, this, EXCLUDE_METHOD);
			setCleanLimitInd(anICCCertificateItem.isCleanType() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
			setCMPLimitID(new Long(anICCCertificateItem.getLimitID()));
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * @param anICCCertificateItem of ICCCertificateItem type
	 */
	public void ejbPostCreate(ICCCertificateItem anICCCertificateItem) throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return ICCCertificateItem
	 */
	public ICCCertificateItem getValue() {
		ICCCertificateItem value = new OBCCCertificateItem(getLimitType(), getLimitID());
		AccessorUtil.copyValue(this, value);
		value
				.setIsCleanTypeInd((getCleanLimitInd() != null) && getCleanLimitInd().equals(ICMSConstant.TRUE_VALUE) ? true
						: false);
		return value;

	}

	/**
	 * Persist a ccc item information
	 * 
	 * @param ccc - ICCCertificateItem
	 */
	public void setValue(ICCCertificateItem ccc) {
		AccessorUtil.copyValue(ccc, this, EXCLUDE_METHOD);
		setCleanLimitInd(ccc.isCleanType() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CC_CERTIFICATE_ITEM;
	}
}
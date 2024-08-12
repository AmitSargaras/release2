/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDNItemBean.java,v 1.17 2005/10/24 02:41:33 lyng Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for ddn item Information
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.17 $
 * @since $Date: 2005/10/24 02:41:33 $ Tag: $Name: $
 */
public abstract class EBDDNItemBean implements EntityBean, IDDNItem, Cloneable {
	private static final String[] EXCLUDE_METHOD = new String[] { "getDDNItemID", "getDDNItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBDDNItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPDDNItemID();

	public abstract String getIsDeletedIndStr();

	public abstract String getIsDDNIssuedIndStr();

	public abstract Long getCMPDDNID();

	public abstract Long getCMPLimitID();

	public abstract String getDDNAmtCurrency();

	public abstract BigDecimal getDDNAmt();

	public abstract String getApprovedLimitAmtCcy();

	public abstract BigDecimal getApprovedLimitAmt();

	public abstract String getBkgLoctnCtry();

	public abstract String getBkgLoctnOrg();

	public abstract String getSecurityIDs();

	public abstract String getSecurityTypes();

	public abstract String getProductType();

	public abstract Date getApprovalLimitDate();

	public abstract String getOutLimitRef();

	public abstract String getCoBorrowLegalID();

	public abstract String getCoBorrowName();

	public abstract long getDDNItemRef();

    public abstract long getDocNumber();

    public abstract String getDocCode();

    public abstract String getDocDesc();

    public abstract Date getDateDefer();

    public abstract Date getDateOfReturn();

    public abstract String getDocStatus();

    public abstract String getActionParty();

    public abstract Date getTheApprovalDate();

    public abstract String getApprovedBy();

    public abstract String getLimitType();

    public abstract Date getIssuedDate();

    public abstract Date getMaturityDate();

	public abstract void setCMPDDNItemID(Long aSCCertItemID);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setIsDDNIssuedIndStr(String anIsDDNIssuedIntStr);

	public abstract void setCMPDDNID(Long aSCCertID);

	public abstract void setCMPLimitID(Long aLimtiID);

	//public abstract void setLimitType(String aLimitType);

	public abstract void setDDNAmtCurrency(String anDDNAmtCurrency);

	public abstract void setDDNAmt(BigDecimal anDDNdLimit);

	public abstract void setApprovedLimitAmtCcy(String approvedLimitAmtCcy);

	public abstract void setApprovedLimitAmt(BigDecimal approvedLimitAmt);

	public abstract void setBkgLoctnCtry(String bkgLoctnCtry);

	public abstract void setBkgLoctnOrg(String bkgLoctnOrg);

	public abstract void setSecurityIDs(String securityIDs);

	public abstract void setSecurityTypes(String securityTypes);

	public abstract void setApprovalLimitDate(Date limitDate);

	public abstract void setProductType(String type);

	public abstract void setOutLimitRef(String ref);

	public abstract void setCoBorrowLegalID(String id);

	public abstract void setCoBorrowName(String name);

    public abstract void setDocNumber(long docNumber);

    public abstract void setDocCode(String docCode);

    public abstract void setDocDesc(String docDesc);

    public abstract void setDateDefer(Date dateDefer);

    public abstract void setDateOfReturn(Date dateOfReturn);

    public abstract void setDocStatus(String docStatus);

    public abstract void setActionParty(String actionParty);

    public abstract void setTheApprovalDate(Date theApprovalDate);

    public abstract void setApprovedBy(String approvedBy);

    public abstract void setDDNItemRef(long dDNItemRef);

    public abstract void setLimitType(String limitType);

    public abstract void setIssuedDate(Date issuedDate);

    public abstract void setMaturityDate(Date maturityDate);

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the ddn item ID
	 * @return long - the long value of the scc item
	 */
	public long getDDNItemID() {
		if (getCMPDDNItemID() != null) {
			return getCMPDDNItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
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

	/**
	 * Helper method to get the DDN is issued indicator
	 * @return boolean - true if ddn is issued and false otherwise
	 */
	public boolean getIsDDNIssuedInd() {
		if ((getIsDDNIssuedIndStr() != null) && getIsDDNIssuedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Get DDN Limit amount.
	 * 
	 * @return Amount
	 */
	public Amount getDDNAmount() {
		if ((getDDNAmt() != null) && (getDDNAmtCurrency() != null)) {
			return new Amount(getDDNAmt(), new CurrencyCode(getDDNAmtCurrency()));
		}
		return null;
	}

	public Date getApprovalDate() {
		return null;
	}

	public boolean isInnerLimit() {
		return false;
	}

	public boolean isCleanType() {
		return false;
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

	public DDNCollateralInfo[] getDDNCollateralInfoList() {
		return null;
	}

	public IBookingLocation getLimitBookingLocation() {
		return null;
	}

	public String getProductDesc() {
		return null;
	}

	public Amount getApprovedLimitAmount() {
		return null;
	}

	public Amount getActivatedAmount() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public HashMap getCheckListMap() {
		return null;
	}

	public boolean getIsLimitExistingInd() {
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

	public long getCoBorrowerCustID() {
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getLimitStatus() {
		return null;
	}

	public Date getLimitExpiryDate() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public String retrieveCheckListStatus(long aCollateralID) {
		return null;
	}

	/**
	 * Helper method to set the ddn item ID
	 * @param aDDNItemID - long
	 */
	public void setDDNItemID(long aDDNItemID) {
		setCMPDDNItemID(new Long(aDDNItemID));
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
	 * Helper method to set ddn issued indicator
	 * @param anIsDDNIssuedInd - boolean
	 */
	public void setIsDDNIssuedInd(boolean anIsDDNIssuedInd) {
		if (anIsDDNIssuedInd) {
			setIsDDNIssuedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsDDNIssuedIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Set the DDN Limit amount.
	 * 
	 * @param ddnAmount of Amount type
	 */
	public void setDDNAmount(Amount ddnAmount) {
		if (ddnAmount != null) {
			setDDNAmt(ddnAmount.getAmountAsBigDecimal());
			setDDNAmtCurrency(ddnAmount.getCurrencyCode());
		}
		else {
			setDDNAmt(null);
			setDDNAmtCurrency(null);
		}
	}

	public void setLimitRef(String aLimitRef) {
		// do nothing
	}

	public void setIsLimitExistingInd(boolean anIsLimitExistingInd) {
		// do nothing
	}

	public void setDDNCollateralInfoList(DDNCollateralInfo[] aDDNCollateralInfoList) {
		// do nothing
	}

	public void setLimitBookingLocation(IBookingLocation aLimitBookingLocation) {
		// do nothing
	}

	public void setProductDesc(String aProductDesc) {
		// do nothing
	}

	public void setApprovedLimitAmount(Amount anApprovedLimitAmount) {
		// do nothing
	}

	public void setActivatedAmount(Amount anActivatedAmount) {
		// do nothing
	}

	public void setCheckListMap(HashMap aCheckListMap) {
		// do nothing
	}

	public void setIsCleanTypeInd(boolean aCleanTypeInd) {
		// do nothing
	}

	public void setOuterLimitRef(String ref) {

	}

	public void setCoBorrowerLegalID(String id) {

	}

	public void setCoBorrowerName(String name) {

	}

	public String getOuterLimitRef() {
		return null;
	}

	public String getCoBorrowerLegalID() {
		return null;
	}

	public String getCoBorrowerName() {
		return null;
	}

	public void setOuterLimitID(long outerLimitID) {
	}

	public void setOuterLimitProfileID(long outerLimitProfileID) {
	}

	public void setIsInnerOuterSameBCA(boolean isInnerOuterSameBCA) {
	}

	public void setCoBorrowerCustID(long coBorrowerCustID) {
	}

	public void setLimitStatus(String limitStatus) {
	}

	public void setLimitExpiryDate(Date limitExpiryDate) {
	}

	// *****************************************************
	/**
	 * Create a ddn item Information
	 * @param anIDDNItem of IDDNItem type
	 * @return Long - the ddn item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IDDNItem anIDDNItem) throws CreateException {
		if (anIDDNItem == null) {
			throw new CreateException("IDDNItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setDDNItemID(pk);
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anIDDNItem.getDDNItemRef()) {
				setDDNItemRef(pk);
			}
			else {
				setDDNItemRef(anIDDNItem.getDDNItemRef());
			}
			AccessorUtil.copyValue(anIDDNItem, this, EXCLUDE_METHOD);
			setCMPLimitID(new Long(anIDDNItem.getLimitID()));
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * @param anIDDNItem of IDDNItem type
	 */
	public void ejbPostCreate(IDDNItem anIDDNItem) throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return IDDNItem
	 */
	public IDDNItem getValue() {
		IDDNItem value = new OBDDNItem(getLimitType(), getLimitID());
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a ddn item information
	 * 
	 * @param anIDDNItem of IDDNItem type
	 */
	public void setValue(IDDNItem anIDDNItem) {
		AccessorUtil.copyValue(anIDDNItem, this, EXCLUDE_METHOD);
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
		return ICMSConstant.SEQUENCE_DDN_ITEM;
	}

	public Object clone() {
		return null;
	}

}
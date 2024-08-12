/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBSCCertificateItemBean.java,v 1.14 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;

/**
 * This entity bean represents the persistence for scc item Information
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.14 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */
public abstract class EBSCCertificateItemBean implements EntityBean, ISCCertificateItem, Cloneable {
	private static final String[] EXCLUDE_METHOD = new String[] { "getSCCertItemID", "getSCCertItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBSCCertificateItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPSCCertItemID();

	public abstract String getIsDeletedIndStr();

	public abstract Long getCMPSCCertID();

	public abstract Long getCMPLimitID();

	public abstract String getActivatedAmtCurrency();

	public abstract double getActivatedAmt();

	public abstract String getApprovedLimitAmtCcy();

	public abstract BigDecimal getApprovedLimitAmt();

	public abstract String getBkgLoctnCtry();

	public abstract String getBkgLoctnOrg();

	public abstract String getSecurityIDs();

	public abstract String getSecurityTypes();

	public abstract long getSCCertItemRef();

    public abstract Date getExpAvalDT();

    public abstract String getDistAmtCCY();

    public abstract BigDecimal getDistAmt();

    public abstract String getEnforceAmtCCY();

    public abstract BigDecimal getEnforceAmt();

    public abstract String getPaymntInstruc();

    public abstract String getLimitType();

    public abstract Date getApprovalDate();

    public abstract String getProductDesc();

    public abstract String getOuterLimitRef();

    public abstract String getCoBorrowerLegalID();

    public abstract String getCoBorrowerName();

    public abstract Date getMaturityDate();

	public abstract void setCMPSCCertItemID(Long aSCCertItemID);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCMPSCCertID(Long aSCCertID);

	public abstract void setCMPLimitID(Long aLimtiID);

	public abstract void setLimitType(String aLimitType);

	public abstract void setActivatedAmtCurrency(String anActivatedAmtCurrency);

	public abstract void setActivatedAmt(double anActivatedLimit);

	public abstract void setApprovedLimitAmtCcy(String approvedLimitAmtCcy);

	public abstract void setApprovedLimitAmt(BigDecimal approvedLimitAmt);

	public abstract void setBkgLoctnCtry(String bkgLoctnCtry);

	public abstract void setBkgLoctnOrg(String bkgLoctnOrg);

	public abstract void setSecurityIDs(String securityIDs);

	public abstract void setSecurityTypes(String securityTypes);

    public abstract void setExpAvalDT(Date expAvalDT);

    public abstract void setDistAmtCCY(String distAmtCCY);

    public abstract void setDistAmt(BigDecimal distAmt);

    public abstract void setEnforceAmtCCY(String enforceAmtCCY);

    public abstract void setEnforceAmt(BigDecimal enforceAmt);

    public abstract void setPaymntInstruc(String paymntInstruc);

    public abstract void setSCCertItemRef(long aSCCertItemRef);

    public abstract void setApprovalDate(Date approvalDate);

    public abstract void setProductDesc(String productDesc);

    public abstract void setOuterLimitRef(String outerLimitRef);

    public abstract void setCoBorrowerLegalID(String coBorrowerLegalID);

    public abstract void setCoBorrowerName(String coBorrowerName);

    public abstract void setMaturityDate(Date maturiyDate);

    public Date getExpiryAvailabilityDate() {
        return getExpAvalDT() == null ? null : getExpAvalDT();
    }

    public Amount getDisbursementAmount() {
        if (getDistAmtCCY() != null && getDistAmt() != null) {
            return new Amount(getDistAmt(), new CurrencyCode(getDistAmtCCY()));
        }
        return null;
    }

    public Amount getEnforceAmount() {
        if (getEnforceAmtCCY() != null && getEnforceAmt() != null) {
            return new Amount(getEnforceAmt(), new CurrencyCode(getEnforceAmtCCY()));
        }
        return null;
    }

    public String getPaymentInstruction() {
        return getPaymntInstruc() == null ? "" : (getPaymntInstruc().length() == 0 ? "" : getPaymntInstruc().trim());
    }

    public void setExpiryAvailabilityDate(Date expiryAvailabilatyDate) {
        setExpAvalDT(expiryAvailabilatyDate);
    }

    public void setDisbursementAmount(Amount disbursementAmount) {
        setDistAmtCCY(disbursementAmount == null ? null : disbursementAmount.getCurrencyCode());
        setDistAmt(disbursementAmount == null ? null : disbursementAmount.getAmountAsBigDecimal());
    }

    public void setEnforceAmount(Amount enforceAmount) {
        setEnforceAmtCCY(enforceAmount == null ? null : enforceAmount.getCurrencyCode());
        setEnforceAmt(enforceAmount == null ? null : enforceAmount.getAmountAsBigDecimal());
    }

    public void setPaymentInstruction(String paymentInstruction) {
        setPaymntInstruc(paymentInstruction == null ? "" : paymentInstruction);
    }

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the scc item ID
	 * @return long - the long value of the scc item
	 */
	public long getSCCertItemID() {
		if (getCMPSCCertItemID() != null) {
			return getCMPSCCertItemID().longValue();
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

	public Amount getActivatedAmount() {
		String ccy = getActivatedAmtCurrency();
		double amt = getActivatedAmt();
		return new Amount(amt, ccy);
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

	public String getCheckListStatus() {
		return null;
	}

	/*
	 * public ILimit getLimit() { return null; }
	 */

	public long getLimitID() {
		if (getCMPLimitID() != null) {
			return getCMPLimitID().longValue();
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
	 * Get the list of collateral tied to the limit.
	 * 
	 * @return ICollateralAllocation[] - the list of collateral tied to the
	 *         limit
	 */
	public ICollateralAllocation[] getCollateralAllocations() {
		String secIds = getSecurityIDs();
		ArrayList list = new ArrayList();
		if (secIds != null) {
			StringTokenizer ids = new StringTokenizer(secIds, ",");
			StringTokenizer types = new StringTokenizer(getSecurityTypes(), ",");
			while (ids.hasMoreElements()) {
				OBCollateralAllocation alloc = new OBCollateralAllocation();
				OBCollateral col = new OBCollateral();
				OBCollateralSubType subtype = new OBCollateralSubType();
				subtype.setTypeName(types.nextToken());
				col.setSCISecurityID(ids.nextToken());
				col.setCollateralType(subtype);
				col.setCollateralSubType(subtype);
				alloc.setCollateral(col);
				list.add(alloc);
			}
		}
		return (OBCollateralAllocation[]) list.toArray(new OBCollateralAllocation[0]);
	}

	/**
	 * Not implemented
	 */
	public String getLimitRef() {
		return null;
	}

	public boolean getIsLimitExistingInd() {
		return false;
	}

	public boolean isInnerLimit() {
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

	public boolean isCleanType() {
		return false;
	}

	public Date getLimitExpiryDate() {
		return null;
	}

	/**
	 * Helper method to set the scc item ID
	 * @param aSCCertItemID - long
	 */
	public void setSCCertItemID(long aSCCertItemID) {
		setCMPSCCertItemID(new Long(aSCCertItemID));
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

	/**
	 * Set the list of collaterals tied to the limit.
	 * 
	 * @param alloc of ICollateralAllocation type
	 */
	public void setCollateralAllocations(ICollateralAllocation[] alloc) {
		int count = alloc == null ? 0 : alloc.length;
		String secIDs = "", secTypes = "";
		int lastRecIndex = count - 1;
		for (int i = 0; i < count; i++) {
			secIDs = secIDs + alloc[i].getCollateral().getSCISecurityID();
			secTypes = secTypes + alloc[i].getCollateral().getCollateralType().getTypeName();
			if (i != lastRecIndex) {
				secIDs = secIDs + ",";
				secTypes = secTypes + ",";
			}
		}
		setSecurityIDs(secIDs);
		setSecurityTypes(secTypes);
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

	public void setCheckListStatus(String aCheckListStatus) {
		// do nothing
	}

	public void setLimitRef(String aLimitRef) {
	}

	public void setIsLimitExistingInd(boolean anIsLimitExistingInd) {
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

	public void setIsCleanTypeInd(boolean anIsCleanTypeInd) {
	}

	/*
	 * public void setLimit(ILimit anILimit) { //do nothing }
	 */

	// *****************************************************
	/**
	 * Create a scc item Information
	 * @param anISCCertificateItem of ISCCertificateItem type
	 * @return Long - the scc item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ISCCertificateItem anISCCertificateItem) throws CreateException {
		if (anISCCertificateItem == null) {
			throw new CreateException("ISCCertificateItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setSCCertItemID(pk);
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anISCCertificateItem
					.getSCCertItemRef()) {
				setSCCertItemRef(pk);
			}
			else {
				setSCCertItemRef(anISCCertificateItem.getSCCertItemRef());
			}
			AccessorUtil.copyValue(anISCCertificateItem, this, EXCLUDE_METHOD);
			setCMPLimitID(new Long(anISCCertificateItem.getLimitID()));
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * @param anISCCertificateItem of ISCCertificateItem type
	 */
	public void ejbPostCreate(ISCCertificateItem anISCCertificateItem) throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return ISCCertificateItem
	 */
	public ISCCertificateItem getValue() {
		ISCCertificateItem value = new OBSCCertificateItem(getLimitType(), getLimitID());
		AccessorUtil.copyValue(this, value);
		return value;

	}

	/**
	 * Persist a scc item information
	 * 
	 * @param anISCCertificateItem - ISCCertificateItem
	 */
	public void setValue(ISCCertificateItem anISCCertificateItem) {
		AccessorUtil.copyValue(anISCCertificateItem, this, EXCLUDE_METHOD);
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
		return ICMSConstant.SEQUENCE_SC_CERTIFICATE_ITEM;
	}

	public Object clone() {
		return null;
	}

}
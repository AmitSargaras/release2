/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/guarantee/EBGuaranteeCollateralBean.java,v 1.3 2006/04/10 07:06:57 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.guarantee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.EBLineDetailLocal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.EBLineDetailLocalHome;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.EBFeeDetailsLocal;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.EBFeeDetailsLocalHome;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.OBFeeDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for guarantee types.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/04/10 07:06:57 $ Tag: $Name: $
 */
public abstract class EBGuaranteeCollateralBean extends EBCollateralDetailBean implements IGuaranteeCollateral {
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get amount of guarantee.
	 * 
	 * @return Amount
	 */
	public Amount getGuaranteeAmount() {
		return new Amount(getEBGuaranteeAmount(), getGuaranteeCcyCode());
	}

	/**
	 * Set amount of guarantee.
	 * 
	 * @param guaranteeAmount of type Amount
	 */
	public void setGuaranteeAmount(Amount guaranteeAmount) {
		if (guaranteeAmount != null) {
			setEBGuaranteeAmount(guaranteeAmount.getAmountAsDouble());
		}
	}

	/**
	 * Get minimal FSV.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalFSV() {
		if (getEBMinimalFSV() != null) {
			return new Amount(getEBMinimalFSV().doubleValue(), getMinimalFSVCcyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set minimal FSV.
	 * 
	 * @param minimalFSV of type Amount
	 */
	public void setMinimalFSV(Amount minimalFSV) {
		if (minimalFSV != null) {
			setEBMinimalFSV(new Double(minimalFSV.getAmountAsDouble()));
		}
		else {
			setEBMinimalFSV(null);
		}
	}

	public int getSecuredPortion() {
		if (getEBSecuredPortion() == null) {
			return ICMSConstant.INT_INVALID_VALUE;
		}
		else {
			return getEBSecuredPortion().intValue();
		}
	}

	public Amount getSecuredAmountOrigin() {
		if (getEBSecuredAmountOrigin() != null)
			return new Amount(getEBSecuredAmountOrigin().doubleValue(), getGuaranteeCcyCode());
		return null;
	}

	public int getUnsecuredPortion() {
		if (getEBUnsecuredPortion() == null) {
			return ICMSConstant.INT_INVALID_VALUE;
		}
		else {
			return getEBUnsecuredPortion().intValue();
		}
	}

	// public Amount getGuaranteeAmount() {
	// return new Amount(getEBGuaranteeAmount(), getGuaranteeCcyCode());
	// }

	public abstract Collection getFeeDetailsCMR();

	public abstract void setFeeDetailsCMR(Collection feeDetailsCMR);

	public Amount getUnsecuredAmountOrigin() {
		if (getEBUnsecuredAmountOrigin() != null)
			return new Amount(getEBUnsecuredAmountOrigin().doubleValue(), getGuaranteeCcyCode());
		return null;
	}

	public Amount getSecuredAmountCalc() {
		if (getEBSecuredAmountCalc() != null)
			return new Amount(getEBSecuredAmountCalc().doubleValue(), getGuaranteeCcyCode());
		return null;
	}

	public Amount getUnsecuredAmountCalc() {
		if (getEBUnsecuredAmountCalc() != null)
			return new Amount(getEBUnsecuredAmountCalc().doubleValue(), getGuaranteeCcyCode());
		return null;
	}

	public Amount getGuaranteeAmtCalc() {
		if (getEBGuaranteeAmtCalc() != null)
			return new Amount(getEBGuaranteeAmtCalc().doubleValue(), getGuaranteeCcyCode());
		return null;
	}

	public void setSecuredPortion(int securedPortion) {
		if (securedPortion < 0)
			setEBSecuredPortion(null);
		else
			setEBSecuredPortion(new Integer(securedPortion));
	}

	public void setSecuredAmountOrigin(Amount securedAmountOrigin) {
		if (securedAmountOrigin != null) {
			setEBSecuredAmountOrigin(new Double(securedAmountOrigin.getAmountAsDouble()));
		}
		else
			setEBSecuredAmountOrigin(null);
	}

	public void setUnsecuredPortion(int unsecuredPortion) {
		if (unsecuredPortion < 0)
			setEBUnsecuredPortion(null);
		else
			setEBUnsecuredPortion(new Integer(unsecuredPortion));
	}

	public void setUnsecuredAmountOrigin(Amount unsecuredAmountOrigin) {
		if (unsecuredAmountOrigin != null) {
			setEBUnsecuredAmountOrigin(new Double(unsecuredAmountOrigin.getAmountAsDouble()));
		}
		else
			setEBUnsecuredAmountOrigin(null);
	}

	public void setSecuredAmountCalc(Amount securedAmountCalc) {
		if (securedAmountCalc != null) {
			setEBSecuredAmountCalc(new Double(securedAmountCalc.getAmountAsDouble()));
		}
		else
			setEBSecuredAmountCalc(null);
	}

	public void setUnsecuredAmountCalc(Amount unsecuredAmountCalc) {
		if (unsecuredAmountCalc != null) {
			setEBUnsecuredAmountCalc(new Double(unsecuredAmountCalc.getAmountAsDouble()));
		}
		else
			setEBUnsecuredAmountCalc(null);
	}

	public void setGuaranteeAmtCalc(Amount guaranteeAmtCalc) {
		if (guaranteeAmtCalc != null) {
			setEBGuaranteeAmtCalc(new Double(guaranteeAmtCalc.getAmountAsDouble()));
		}
		else
			setEBGuaranteeAmtCalc(null);
	}

	public char getICCRulesComplied() {
		if (getIsICCRulesComplied() != null)
			return getIsICCRulesComplied().charValue();
		return ' ';
	}

	public void setICCRulesComplied(char iccRulesComplied) {
		setIsICCRulesComplied(new Character(iccRulesComplied));
	}

	public char getURDComplied() {
		if (getIsURDComplied() != null)
			return getIsICCRulesComplied().charValue();
		return ' ';
	}

	public void setURDComplied(char urdComplied) {
		setIsURDComplied(new Character(urdComplied));
	}

	public char getUCP600Complied() {
		if (getIsUCP600Complied() != null)
			return getIsUCP600Complied().charValue();
		return ' ';
	}

	public void setUCP600Complied(char ucp600Complied) {
		setIsUCP600Complied(new Character(ucp600Complied));
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract double getEBGuaranteeAmount();

	public abstract void setEBGuaranteeAmount(double ebGuaranteeAmount);

	public abstract Double getEBMinimalFSV();

	public abstract void setEBMinimalFSV(Double eBMinimalFSV);

	public abstract String getHoldingPeriod();

	public abstract void setHoldingPeriod(String holdingPeriod);

	public abstract String getHoldingPeriodTimeUnit();

	public abstract void setHoldingPeriodTimeUnit(String holdingPeriodTimeUnit);

	public abstract String getClaimPeriod();

	public abstract void setClaimPeriod(String claimPeriod);

	public abstract String getClaimPeriodUnit();

	public abstract void setClaimPeriodUnit(String claimPeriodUnit);

	public abstract Integer getEBSecuredPortion();

	public abstract void setEBSecuredPortion(Integer securedPortion);

	public abstract Double getEBSecuredAmountOrigin();

	public abstract void setEBSecuredAmountOrigin(Double securedAmountOrigin);

	public abstract Integer getEBUnsecuredPortion();

	public abstract void setEBUnsecuredPortion(Integer unsecuredPortion);

	public abstract Double getEBUnsecuredAmountOrigin();

	public abstract void setEBUnsecuredAmountOrigin(Double unsecuredAmountOrigin);

	public abstract Double getEBSecuredAmountCalc();

	public abstract void setEBSecuredAmountCalc(Double securedAmountCalc);

	public abstract Double getEBUnsecuredAmountCalc();

	public abstract void setEBUnsecuredAmountCalc(Double unsecuredAmountCalc);

	public abstract Double getEBGuaranteeAmtCalc();

	public abstract void setEBGuaranteeAmtCalc(Double guaranteeAmtCalc);

	public ICollateral getValue(ICollateral collateral) {
		ICollateral col = super.getValue(collateral);
		
		IGuaranteeCollateral guaranteeCol = (IGuaranteeCollateral) col;
		try {
			guaranteeCol.setLineDetails(retrieveLineDetails());
		} catch (CollateralException e) {
			throw new EJBException("fail to retrieve guarantee collateral detail for collateral id ["
					+ collateral.getCollateralID() + "] host id [" + collateral.getSCISecurityID() + "]", e);
		}
		
		return col;
	}

	public void setValue(ICollateral collateral) {

		AccessorUtil.copyValue(collateral, this, EXCLUDE_METHOD);
		try {
			setFeeDetailsInfoRef(((IGuaranteeCollateral) collateral).getFeeDetails(), false);
		}
		catch (CreateException e) {
			throw new EJBException("fail to create fee details, collateral id [" + collateral.getCollateralID() + "]",
					e);
		}
		try {
			updateLineDetail(((IGuaranteeCollateral) collateral).getLineDetails(), collateral.getCollateralID());
		} catch (CollateralException e) {
			throw new EJBException("fail to create line details, collateral id [" + collateral.getCollateralID() + "]",
					e);
		}
	}

	public IFeeDetails[] getFeeDetails() {
		Iterator i = getFeeDetailsCMR().iterator();
		ArrayList arrList = new ArrayList();
		while (i.hasNext()) {
			EBFeeDetailsLocal theEjb = (EBFeeDetailsLocal) i.next();
			IFeeDetails feeDetails = theEjb.getValue();
			if (!ICMSConstant.STATE_DELETED.equals(feeDetails.getStatus())) {
				if (feeDetails.getAmountFee() != null) {
					feeDetails.getAmountFee().setCurrencyCode(getCurrencyCode());
				}
				if (feeDetails.getAmountCGC() != null) {
					feeDetails.getAmountCGC().setCurrencyCode(getCurrencyCode());
				}
				arrList.add(feeDetails);
			}
		}
		return (OBFeeDetails[]) arrList.toArray(new OBFeeDetails[0]);
	}

	public void setFeeDetails(IFeeDetails[] feeDetails) {
	}

	protected void setFeeDetailsInfoRef(IFeeDetails[] feeDetailsList, boolean isAdd) throws CreateException {
		if ((feeDetailsList == null) || (feeDetailsList.length == 0)) {
			removeAllFeeDetails();
			return;
		}

		EBFeeDetailsLocalHome ejbHome = getEBFeeDetailsLocalHome();

		Collection c = getFeeDetailsCMR();

		if (isAdd) {
			for (int i = 0; i < feeDetailsList.length; i++) {
//				System.out.println("EBCollateralBean : added  feeDetailsList " + feeDetailsList[i]);
				c.add(ejbHome.create(feeDetailsList[i]));
			}
			return;
		}

		if (c.size() == 0) {
			for (int i = 0; i < feeDetailsList.length; i++) {
				c.add(ejbHome.create(feeDetailsList[i]));
			}
			return;
		}

		removeFeeDetails(c, feeDetailsList);

		Iterator iterator = c.iterator();
		ArrayList newDeposit = new ArrayList();

		for (int i = 0; i < feeDetailsList.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBFeeDetailsLocal theEjb = (EBFeeDetailsLocal) iterator.next();
				IFeeDetails value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (feeDetailsList[i].getRefID() == value.getRefID()) {
					theEjb.setValue(feeDetailsList[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newDeposit.add(feeDetailsList[i]);
			}
			iterator = c.iterator();
		}

		iterator = newDeposit.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IFeeDetails) iterator.next()));
		}
	}

	private void removeAllFeeDetails() {
		Collection c = getFeeDetailsCMR();
		Iterator iterator = c.iterator();
		while (iterator.hasNext()) {
			EBFeeDetailsLocal theEjb = (EBFeeDetailsLocal) iterator.next();
			deleteFeeDetails(theEjb);
		}
	}

	private void removeFeeDetails(Collection coll, IFeeDetails[] feeDetailsList) {
		Iterator iterator = coll.iterator();

		while (iterator.hasNext()) {
			EBFeeDetailsLocal theEjb = (EBFeeDetailsLocal) iterator.next();
			IFeeDetails feeDetails = theEjb.getValue();
			if ((feeDetails.getStatus() != null) && feeDetails.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < feeDetailsList.length; i++) {
				if (feeDetailsList[i].getRefID() == feeDetails.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteFeeDetails(theEjb);
			}
		}
	}

	private void deleteFeeDetails(EBFeeDetailsLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	protected EBFeeDetailsLocalHome getEBFeeDetailsLocalHome() {
		EBFeeDetailsLocalHome ejbHome = (EBFeeDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FEEDETAILS_LOCAL_JNDI, EBFeeDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBFeeDetailsLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param collateral of type ICollateral
	 * @throws CreateException on error creating references to collateral
	 */
	public void ejbPostCreate(ICollateral collateral) throws CreateException {
		setFeeDetailsInfoRef(((IGuaranteeCollateral) collateral).getFeeDetails(), true);
		try {
			updateLineDetail(((IGuaranteeCollateral) collateral).getLineDetails(), collateral.getCollateralID());
		} catch (CollateralException e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}
	
	public ILineDetail[] getLineDetails() {
		return null;
	}
	
	public void setLineDetails(ILineDetail[] lLineDetails) {
		
	}
	
	public static Comparator<ILineDetail> orderByLineDetailID = new Comparator<ILineDetail>() {

		public int compare(ILineDetail o1, ILineDetail o2) {
			return Long.valueOf(o1.getLineDetailID()).compareTo(o2.getLineDetailID());
		}
	}; 
	
	private ILineDetail[] retrieveLineDetails() throws CollateralException {

		try {
			Collection c = getCMRLineDetails();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList<ILineDetail> aList = new ArrayList<ILineDetail>();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLineDetailLocal local = (EBLineDetailLocal) i.next();
					ILineDetail ob = local.getValue();
					aList.add(ob);
				}
				ILineDetail[] lineDetails = aList.toArray(new ILineDetail[0]);
				Arrays.sort(lineDetails, orderByLineDetailID);
				
				return lineDetails;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
	
	}
	
	private void deleteLineDetails(List deleteList) throws CollateralException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			
		}
		
		try {
			Collection c = getCMRLineDetails();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBLineDetailLocal local = (EBLineDetailLocal) i.next();
				c.remove(local);
				local.remove();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
		
	}
	
	private void createLineDetails(List createList,long collateralId) throws CollateralException {
		if ((null == createList) || (createList.size() == 0)) {
			return;
		}
		Collection c = getCMRLineDetails();
		Iterator i = createList.iterator();
		try {
			EBLineDetailLocalHome home = getEBLocalLineDetail();
			while (i.hasNext()) {
				ILineDetail ob = (ILineDetail) i.next();
				if(ob!=null){
					DefaultLogger.debug(this, "Creating LineItem ID: " + ob.getLineDetailID());
					ob.setCollateralID(collateralId);
					EBLineDetailLocal local = home.create(ob);
					c.add(local);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CollateralException) {
				throw (CollateralException) e;
			}
			else {
				throw new CollateralException("Caught Exception: " + e.toString());
			}
		}
	}
	
	protected EBLineDetailLocalHome getEBLocalLineDetail() throws CollateralException {
		EBLineDetailLocalHome home = (EBLineDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LINE_DETAIL_LOCAL_JNDI, EBLineDetailLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBLineDetailLocalHome is null!");
		}
	}
	
	private void updateLineDetail(ILineDetail[] lineDetails,long collateralId) throws CollateralException {
		try {
			Collection c = getCMRLineDetails();

			if (null == lineDetails) {
				if ((null == c) || (c.size() == 0)) {
					return;
				}
				else {
					deleteLineDetails(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				List<ILineDetail> lineDetailList = Arrays.asList(lineDetails);
				createLineDetails(lineDetailList, collateralId);
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); 
				ArrayList deleteList = new ArrayList(); 

				while (i.hasNext()) {
					EBLineDetailLocal local = (EBLineDetailLocal) i.next();

					long lineDetailId = local.getLineDetailID();
					boolean update = false;

					for (int j = 0; j < lineDetails.length; j++) {
						ILineDetail newOB = lineDetails[j];

						if (newOB.getLineDetailID() == lineDetailId) {
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						deleteList.add(local);
					}
				}

				for (int j = 0; j < lineDetails.length; j++) {
					i = c.iterator();
					ILineDetail newOB = lineDetails[j];
					boolean found = false;

					while (i.hasNext()) {
						EBLineDetailLocal local = (EBLineDetailLocal) i.next();
						long id = local.getLineDetailID();

						if (newOB.getLineDetailID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						createList.add(newOB);
					}
				}
				deleteLineDetails(deleteList);
				createLineDetails(createList, collateralId);
			}
		}
		catch (CollateralException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CollateralException("Caught Exception: " + e.toString());
		}
	}
	
    public abstract Collection getCMRLineDetails();
	
	public abstract void setCMRLineDetails(Collection value);

	public abstract String getGuaranteeCcyCode();

	public abstract void setGuaranteeCcyCode(String guaranteeCcyCode);

	public abstract Date getGuaranteeDate();

	public abstract void setGuaranteeDate(Date guaranteeDate);

	public abstract Date getClaimDate();

	public abstract void setClaimDate(Date claimDate);

	public abstract String getBeneficiaryName();

	public abstract void setBeneficiaryName(String beneficiaryName);

	public abstract String getIssuingBank();

	public abstract void setIssuingBank(String issuingBank);

	public abstract String getIssuingBankCountry();

	public abstract void setIssuingBankCountry(String issuingBankCountry);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getReferenceNo();

	public abstract void setReferenceNo(String referenceNo);

	public abstract String getIsBankCountryRiskApproval();

	public abstract void setIsBankCountryRiskApproval(String isBankCountryRiskApproval);

	public abstract String getMinimalFSVCcyCode();

	public abstract void setMinimalFSVCcyCode(String minimalFSVCcyCode);

	public abstract String getCurrentScheme();

	public abstract void setCurrentScheme(String currentScheme);

	public abstract Character getIsICCRulesComplied();

	public abstract void setIsICCRulesComplied(Character isICCRulesComplied);

	public abstract Character getIsURDComplied();

	public abstract void setIsURDComplied(Character isURDComplied);

	public abstract Character getIsUCP600Complied();

	public abstract void setIsUCP600Complied(Character isUCP600Complied);

	public abstract String getComments();

	public abstract Date getIssuingDate();

	public abstract void setComments(String comments);

	public abstract void setIssuingDate(Date issuingDate);

    public abstract String getReimbursementBankCategoryCode();

    public abstract void setReimbursementBankCategoryCode(String reimbursementBankCategoryCode);

    public abstract String getReimbursementBankEntryCode();

    public abstract void setReimbursementBankEntryCode(String reimbursementBankEntryCode);
    
    public abstract Date getCancellationDateLG();
    
    public abstract void setCancellationDateLG(Date cancellationDateLG);
    
    // Methods added by Dattatray Thorat for Guarantee Security on 14/07/2011 
    
    /**
	 * @return the telephoneNumber
	 */
	public abstract String getTelephoneNumber() ;

	/**
	 * @param telephoneNumber the telephoneNumber to set
	 */
	public abstract void setTelephoneNumber(String telephoneNumber) ;

	/**
	 * @return the guarantersDunsNumber
	 */
	public abstract String getGuarantersDunsNumber() ;

	/**
	 * @param guarantersDunsNumber the guarantersDunsNumber to set
	 */
	public abstract void setGuarantersDunsNumber(String guarantersDunsNumber) ;

	/**
	 * @return the guarantersPam
	 */
	public abstract String getGuarantersPam() ;

	/**
	 * @param guarantersPam the guarantersPam to set
	 */
	public abstract void setGuarantersPam(String guarantersPam);

	/**
	 * @return the guarantersName
	 */
	public abstract String getGuarantersName() ;

	/**
	 * @param guarantersName the guarantersName to set
	 */
	public abstract void setGuarantersName(String guarantersName) ;

	/**
	 * @return the guarantersNamePrefix
	 */
	public abstract String getGuarantersNamePrefix() ;

	/**
	 * @param guarantersNamePrefix the guarantersNamePrefix to set
	 */
	public abstract void setGuarantersNamePrefix(String guarantersNamePrefix) ;

	/**
	 * @return the guarantersFullName
	 */
	public abstract String getGuarantersFullName() ;

	/**
	 * @param guarantersFullName the guarantersFullName to set
	 */
	public abstract void setGuarantersFullName(String guarantersFullName) ;

	/**
	 * @return the addressLine1
	 */
	public abstract String getAddressLine1() ;

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public abstract void setAddressLine1(String addressLine1) ;

	/**
	 * @return the addressLine2
	 */
	public abstract String getAddressLine2() ;

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public abstract void setAddressLine2(String addressLine2) ;

	/**
	 * @return the addressLine3
	 */
	public abstract String getAddressLine3() ;

	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public abstract void setAddressLine3(String addressLine3) ;

	/**
	 * @return the city
	 */
	public abstract String getCity() ;

	/**
	 * @param city the city to set
	 */
	public abstract void setCity(String city) ;

	/**
	 * @return the state
	 */
	public abstract String getState() ;

	/**
	 * @param state the state to set
	 */
	public abstract void setState(String state) ;

	/**
	 * @return the region
	 */
	public abstract String getRegion();

	/**
	 * @param region the region to set
	 */
	public abstract void setRegion(String region) ;

	/**
	 * @return the country
	 */
	public abstract String getCountry() ;

	/**
	 * @param country the country to set
	 */
	public abstract void setCountry(String country) ;

	/**
	 * @return the telephoneAreaCode
	 */
	public abstract String getTelephoneAreaCode() ;

	/**
	 * @param telephoneAreaCode the telephoneAreaCode to set
	 */
	public abstract void setTelephoneAreaCode(String telephoneAreaCode) ;

	/**
	 * @return the rating
	 */
	public abstract String getRating() ;

	/**
	 * @param rating the rating to set
	 */
	public abstract void setRating(String rating) ;

	/**
	 * @return the recourse
	 */
	public abstract String getRecourse() ;

	/**
	 * @param recourse the recourse to set
	 */
	public abstract void setRecourse(String recourse) ;

	/**
	 * @return the discriptionOfAssets
	 */
	public abstract String getDiscriptionOfAssets() ;

	/**
	 * @param discriptionOfAssets the discriptionOfAssets to set
	 */
	public abstract void setDiscriptionOfAssets(String discriptionOfAssets) ;

	public abstract String getRamId();

	public abstract void setRamId(String ramId);
	
	public abstract String getAssetStatement();

	public abstract void setAssetStatement(String assetStatement);
	
	public abstract String getGuarantorType();

	public abstract void setGuarantorType(String guarantorType);
	
	public abstract String getDistrict();

	public abstract void setDistrict(String district);
	
	public abstract String getPinCode();

	public abstract void setPinCode(String pinCode);
	
	public abstract String getGuarantorNature();

	public abstract void setGuarantorNature(String guarantorNature);
	
	public abstract Date getFollowUpDate();
	
	public abstract void setFollowUpDate(Date followUpDate);

}
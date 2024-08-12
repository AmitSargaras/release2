/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/EBInsuranceCollateralBean.java,v 1.17 2005/09/29 09:38:39 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for insurance collateral type.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/09/29 09:38:39 $ Tag: $Name: $
 */
public abstract class EBInsuranceCollateralBean extends EBCollateralDetailBean implements IInsuranceCollateral {
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
	 * Get insured amount.
	 * 
	 * @return Amount
	 */
	public Amount getInsuredAmount() {
		//return new Amount(getEBInsuredAmount(), getInsuredCcyCode());
		return new Amount(getEBInsuredAmount(),new CurrencyCode(getInsuredCcyCode()));
	}

	/**
	 * Set insured amount.
	 * 
	 * @param insuredAmount of type Amount
	 */
	public void setInsuredAmount(Amount insuredAmount) {
		if (insuredAmount != null) {
			//setEBInsuredAmount(insuredAmount.getAmountAsDouble());
			setEBInsuredAmount(insuredAmount.getAmountAsBigDecimal());
		}
	}

	/**
	 * Get if the bank's interest is duly noted.
	 * 
	 * @return boolean
	 */
	public boolean getIsBankInterestDulyNoted() {
		String isNoted = getEBIsBankInterestDulyNoted();
		if ((isNoted != null) && isNoted.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}

		// hshii -- added for default it to true if it is null
		if (isNoted == null) {
			return true;
		}

		return false;
	}

	/**
	 * Set if the bank's interest is duly noted.
	 * 
	 * @param isBankInterestDulyNoted
	 */
	public void setIsBankInterestDulyNoted(boolean isBankInterestDulyNoted) {
		if (isBankInterestDulyNoted) {
			setEBIsBankInterestDulyNoted(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsBankInterestDulyNoted(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get if it is acceleration clause.
	 * 
	 * @return boolean
	 */
	public boolean getIsAccelerationClause() {
		String isAcc = getEBIsAccelerationClause();
		if ((isAcc != null) && isAcc.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is acceleration clause.
	 * 
	 * @param isAccelerationClause of type boolean
	 */
	public void setIsAccelerationClause(boolean isAccelerationClause) {
		if (isAccelerationClause) {
			setEBIsAccelerationClause(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsAccelerationClause(ICMSConstant.FALSE_VALUE);
		}
	}

	public boolean getBankRiskConfirmation() {
		String isConfirmed = getIsBankRiskConfirmation();
		if ((isConfirmed != null) && isConfirmed.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is acceleration clause.
	 * 
	 * @param isAccelerationClause of type boolean
	 */
	public void setBankRiskConfirmation(boolean bankRiskConfirmation) {
		if (bankRiskConfirmation) {
			setIsBankRiskConfirmation(ICMSConstant.TRUE_VALUE);
		}
		else {
			setIsBankRiskConfirmation(ICMSConstant.FALSE_VALUE);
		}
	}

	public Amount getInsurancePremium() {
		return new Amount(getInsurancePremiumValue(), new CurrencyCode(getInsurancePremiumCurrency()));
	}

	public void setInsurancePremium(Amount insurancePremium) {
		if (insurancePremium == null) {
			setInsurancePremiumValue(null);
			setInsurancePremiumCurrency(null);
		}
		else {
			setInsurancePremiumValue(insurancePremium.getAmountAsBigDecimal());
			setInsurancePremiumCurrency(insurancePremium.getCurrencyCode());
		}
	}
	
	public abstract Date getIssuanceDate();

	public abstract void setIssuanceDate(Date issuanceDate);

	public abstract BigDecimal getInsurancePremiumValue();

	public abstract void setInsurancePremiumValue(BigDecimal insurancePremiumValue);

	public abstract String getInsurancePremiumCurrency();

	public abstract void setInsurancePremiumCurrency(String insurancePremiumCurrency);

	/**
	 * Get a list of credit default swaps item
	 * 
	 * @return ICDSItem[]
	 */
	public ICDSItem[] getCdsItems() {
		Iterator i = getCdsItemListCMR().iterator();
		ArrayList arrList = new ArrayList();

		while (i.hasNext()) {
			EBCDSItemLocal theEjb = (EBCDSItemLocal) i.next();
			ICDSItem item = theEjb.getValue();
			if ((item.getStatus() == null)
					|| ((item.getStatus() != null) && !item.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				arrList.add(theEjb.getValue());
			}
		}
		return (OBCDSItem[]) arrList.toArray(new OBCDSItem[0]);
	}

	/**
	 * set a list of Credit Default Swaps item
	 * 
	 * @param cdsItems of type ICDSItem[]
	 */
	public void setCdsItems(ICDSItem[] cdsItems) {
	}

	public abstract Collection getCdsItemListCMR();

	public abstract void setCdsItemListCMR(Collection cdsItems);

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Date getISDADate();

	public abstract void setISDADate(Date iSDADate);

	public abstract Date getTreasuryDocDate();

	public abstract void setTreasuryDocDate(Date treasuryDocDate);

	public abstract String getDescription();

	public abstract void setDescription(String description);

	public abstract String getInsurerName();

	public abstract void setInsurerName(String insurerName);

	public abstract String getInsuranceType();

	public abstract void setInsuranceType(String insuranceType);

	public abstract BigDecimal getEBInsuredAmount();

	public abstract void setEBInsuredAmount(BigDecimal eBInsuredAmount);

	public abstract String getInsuredCcyCode();

	public abstract void setInsuredCcyCode(String insuredCcyCode);

	public abstract Date getInsEffectiveDate();

	public abstract void setInsEffectiveDate(Date insEffectiveDate);

	public abstract Date getInsExpiryDate();

	public abstract void setInsExpiryDate(Date insExpiryDate);

	public abstract String getPolicyNo();

	public abstract void setPolicyNo(String policyNo);

	public abstract String getExternalLegalCounsel();

	public abstract void setExternalLegalCounsel(String externalLegalCounsel);

	public abstract String getEBIsAccelerationClause();

	public abstract void setEBIsAccelerationClause(String eBIsAccelerationClause);

	public abstract String getLocalCCyInCoreMarket();

	public abstract void setLocalCCyInCoreMarket(String localCCyInCoreMarket);

	public abstract String getCoreMarket();

	public abstract void setCoreMarket(String coreMarket);

	public abstract String getEBIsBankInterestDulyNoted();

	public abstract void setEBIsBankInterestDulyNoted(String eBIsBankInterestDulyNoted);

	public abstract String getIsBankRiskConfirmation();

	public abstract void setIsBankRiskConfirmation(String isBankRiskConfirmation);

	public abstract String getArrInsurer();

	public abstract void setArrInsurer(String arrInsurer);

	/**
	 * Set the insurance collateral.
	 * 
	 * @param collateral is of type ICollateral
	 */
	public void setValue(ICollateral collateral) {
		AccessorUtil.copyValue(collateral, this, super.EXCLUDE_METHOD);
		setReferences(collateral, false);
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param collateral of type ICollateral
	 * @throws CreateException on error creating references to collateral
	 */
	public void ejbPostCreate(ICollateral collateral) throws CreateException {
		setReferences(collateral, true);
	}

	/**
	 * Get Credit Default Swaps Item local home
	 * 
	 * @return EBCDSItemLocalHome
	 */
	protected EBCDSItemLocalHome getEBCDSItemLocalHome() {
		EBCDSItemLocalHome ejbHome = (EBCDSItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INSURANCE_CDS_LOCAL_JNDI, EBCDSItemLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCDSItemLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Set the references to this credit default swaps item.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICollateral collateral, boolean isAdd) {
		IInsuranceCollateral insurance = (IInsuranceCollateral) collateral;
		try {
			setCDSItemRef(insurance.getCdsItems(), isAdd);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * set a list of credit default swaps (CDS) as an asset.
	 * 
	 * @param chequeList of type ICDSItem[]
	 * @throws CreateException on error creating reference to CDS
	 */
	private void setCDSItemRef(ICDSItem[] cdsList, boolean isAdd) throws CreateException {
		if ((cdsList == null) || (cdsList.length == 0)) {
			removeAllCDS();
			return;
		}

		EBCDSItemLocalHome ejbHome = getEBCDSItemLocalHome();

		Collection c = getCdsItemListCMR();

		if (isAdd) {
			for (int i = 0; i < cdsList.length; i++) {
				c.add(ejbHome.create(cdsList[i]));
			}
			return;
		}

		if (c.size() == 0) {
			for (int i = 0; i < cdsList.length; i++) {
				c.add(ejbHome.create(cdsList[i]));
			}
			return;
		}

		removeCDS(c, cdsList);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		for (int i = 0; i < cdsList.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBCDSItemLocal theEjb = (EBCDSItemLocal) iterator.next();
				ICDSItem value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (cdsList[i].getRefID() == value.getRefID()) {
					theEjb.setValue(cdsList[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(cdsList[i]);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ICDSItem) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the credit default swaps items
	 */
	private void removeAllCDS() {
		Collection c = getCdsItemListCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBCDSItemLocal theEjb = (EBCDSItemLocal) iterator.next();
			deleteCDS(theEjb);
		}
	}

	/**
	 * Helper method to delete CDS in cdsCol that are not contained in
	 * cdsItemList.
	 * 
	 * @param chequeCol a list of old CDS
	 * @param chequeList a list of newly updated CDS
	 */
	private void removeCDS(Collection cdsCol, ICDSItem[] cdsItemList) {
		Iterator iterator = cdsCol.iterator();

		while (iterator.hasNext()) {
			EBCDSItemLocal theEjb = (EBCDSItemLocal) iterator.next();
			ICDSItem item = theEjb.getValue();
			if ((item.getStatus() != null) && item.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < cdsItemList.length; i++) {
				if (cdsItemList[i].getRefID() == item.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteCDS(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a CDS.
	 * 
	 * @param theEjb of type EBCDSItemLocal
	 */
	private void deleteCDS(EBCDSItemLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}
	//Added by Pramod Katkar for New Filed CR on 20-08-2013
	public abstract int getPhysicalInspectionFreq();
	public abstract void setPhysicalInspectionFreq(int physicalInspectionFreq);
	public abstract String getPhysicalInspectionFreqUnit();
	public abstract void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);
	public abstract boolean getIsPhysicalInspection();
	public abstract void setIsPhysicalInspection(boolean isPhysicalInspection);
	public abstract Date getLastPhysicalInspectDate();
	public abstract void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);
	public abstract Date getNextPhysicalInspectDate();
	public abstract void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);
	//End by Pramod Katkar
}

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBCommodityCollateralBean.java,v 1.24 2005/07/18 09:53:31 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for collateral of type commodity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2005/07/18 09:53:31 $ Tag: $Name: $
 */
public abstract class EBCommodityCollateralBean extends EBCollateralDetailBean implements ICommodityCollateral {
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
	 * Gets the list of DELETED contracts associated with this commodity
	 * collateral. Note that this list contains non-active contracts as
	 * retrieved from the underlying persistence.
	 * 
	 * @return IContract[] - list of DELETED contracts
	 */
	public IContract[] getDeletedContracts() {
		return null;
	}

	/**
	 * Gets the list of DELETED hedging contracts associated with this commodity
	 * collateral. Note that this list contains non-active hedging contracts as
	 * retrieved from the underlying persistence.
	 * 
	 * @return IHedgingContractInfo[] - list of DELETED hedging contracts
	 */
	public IHedgingContractInfo[] getDeletedHedgeContractInfos() {
		return null;
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract String getDegreeOfEnvironmentalRisky();

	public abstract void setDegreeOfEnvironmentalRisky(String value);

	public abstract Date getDateOfDegreeOfEnvironmentalRiskyConfirmed();

	public abstract void setDateOfDegreeOfEnvironmentalRiskyConfirmed(Date value);

	public abstract String getRemarks();

	public abstract void setRemarks(String value);

	public abstract String getApprovedCustomerDifferentialSign();

	public abstract void setApprovedCustomerDifferentialSign(String value);

	public abstract Collection getContractsCMR();

	public abstract void setContractsCMR(Collection contracts);

	public abstract Collection getApprovedCommodityTypesCMR();

	public abstract void setApprovedCommodityTypesCMR(Collection contracts);

	public abstract BigDecimal getApprovedCustomerDifferential();

	public abstract void setApprovedCustomerDifferential(BigDecimal approvedCustomerDifferential);

	public abstract Collection getHedgingContractInfosCMR();

	public abstract void setHedgingContractInfosCMR(Collection headgingContractInfos);

	public abstract Collection getLoansCMR();

	public abstract void setLoansCMR(Collection loans);

	public abstract Collection getPreConditionCMR();

	public abstract void setPreConditionCMR(Collection preConditionCMR);

	/**
	 * Retrieve pre-Condition for the specific BCA.
	 * 
	 * @param limitProfileID of type long
	 * @return a pre-condition
	 */
	public IPreCondition retrievePreCondition(long limitProfileID) {
		return null;
	}

	/**
	 * Set pre-condition.
	 * 
	 * @param preCondition of type IPreCondition
	 */
	public void setPreCondition(IPreCondition preCondition) {

	}

	/**
	 * Get a list of commodity contracts.
	 * 
	 * @return IContract[]
	 */
	public IContract[] getContracts() {
		Iterator i = this.getContractsCMR().iterator();
		ArrayList list = new ArrayList();
		while (i.hasNext()) {
			EBContractLocal theEjb = (EBContractLocal) i.next();
			list.add(theEjb.getValue());
		}
		return (OBContract[]) list.toArray(new OBContract[0]);
	}

	/**
	 * Get a list of approved commodity types.
	 * 
	 * @return IApprovedCommodityType[]
	 */
	public IApprovedCommodityType[] getApprovedCommodityTypes() {
		Iterator i = this.getApprovedCommodityTypesCMR().iterator();
		ArrayList list = new ArrayList();
		while (i.hasNext()) {
			EBApprovedCommodityTypeLocal theEjb = (EBApprovedCommodityTypeLocal) i.next();
			list.add(theEjb.getValue());
		}
		return (OBApprovedCommodityType[]) list.toArray(new OBApprovedCommodityType[0]);
	}

	/**
	 * Get a list of hedging contracts.
	 * 
	 * @return IHedgingContractInfo[]
	 */
	public IHedgingContractInfo[] getHedgingContractInfos() {
		Iterator i = this.getHedgingContractInfosCMR().iterator();
		ArrayList list = new ArrayList();
		while (i.hasNext()) {
			EBHedgingContractInfoLocal theEjb = (EBHedgingContractInfoLocal) i.next();
			list.add(theEjb.getValue());
		}
		return (OBHedgingContractInfo[]) list.toArray(new OBHedgingContractInfo[0]);
	}

	/**
	 * Get a list of commodity loan agencies.
	 * 
	 * @return ILoanAgency[]
	 */
	public ILoanAgency[] getLoans() {
		Iterator i = this.getLoansCMR().iterator();
		ArrayList list = new ArrayList();
		while (i.hasNext()) {
			EBLoanAgencyLocal theEjb = (EBLoanAgencyLocal) i.next();
			list.add(theEjb.getValue());
		}
		return (OBLoanAgency[]) list.toArray(new OBLoanAgency[0]);
	}

	/**
	 * Get a list of pre-conditions.
	 * 
	 * @return IPreCondition[]
	 */
	private IPreCondition[] getPreConditions() {
		Iterator i = this.getPreConditionCMR().iterator();
		ArrayList list = new ArrayList();
		while (i.hasNext()) {
			EBPreConditionLocal theEjb = (EBPreConditionLocal) i.next();
			list.add(theEjb.getValue());
		}
		return (OBPreCondition[]) list.toArray(new OBPreCondition[0]);
	}

	/**
	 * Get the commodity collateral details.
	 * 
	 * @return collateral
	 */
	public ICollateral getValue(ICollateral collateral) {
		try {
			IPreCondition preCondition = ((OBCommodityCollateral) collateral).getPreCondition();
			ICollateral newCol = super.getValue(collateral);
			((OBCommodityCollateral) newCol).setPreCondition(preCondition);
			((OBCommodityCollateral) newCol).setPreConditions(getPreConditions());
			return newCol;
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set the commodity collateral type to this entity.
	 * 
	 * @param collateral is of type ICollateral
	 */
	public void setValue(ICollateral collateral) {
		OBCommodityCollateral cmdt = (OBCommodityCollateral) collateral;
		if (cmdt.isUpdatePreConditionOnly()) {
			try {
				setPreConditionRef(cmdt.getPreCondition(), false);
			}
			catch (Exception e) {
				throw new EJBException(e);
			}
		}
		else {
			AccessorUtil.copyValue(collateral, this, super.EXCLUDE_METHOD);
			setReferences(collateral, false);
		}
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
	 * Set the references to this collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICollateral collateral, boolean isAdd) {
		OBCommodityCollateral commodity = (OBCommodityCollateral) collateral;
		try {
			setContractsRef(commodity.getContracts(), isAdd);
			setApprovedCommodityTypesRef(commodity.getApprovedCommodityTypes(), isAdd);
			setHedgingContractInfosRef(commodity.getHedgingContractInfos(), isAdd);
			setLoansRef(commodity.getLoans(), isAdd);
			setPreConditionRef(commodity.getPreCondition(), isAdd);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set commodity contracts.
	 * 
	 * @param contracts of type IContract[]
	 * @throws CreateException on error creating reference to contract
	 * @throws RemoveException on error removing references of this entity
	 */
	private void setContractsRef(IContract[] contracts, boolean isAdd) throws CreateException, Exception {
		int cLength = 0;
		// remove all existing commodity contracts
		if ((contracts == null) || ((cLength = contracts.length) == 0)) {
			removeAllContracts();
			return;
		}

		EBContractLocalHome ejbHome = getEBContractLocalHome();
		Collection c = getContractsCMR();

		// add all newly added contracts
		if (isAdd || c.isEmpty()) {
			for (int i = 0; i < cLength; i++) {
				c.add(ejbHome.create(contracts[i]));
			}
			return;
		}

		removeContract(c, contracts);

		// update existing contracts as well as add new contracts
		Iterator iterator = c.iterator();
		ArrayList newContract = new ArrayList();

		for (int i = 0; i < cLength; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBContractLocal theEjb = (EBContractLocal) iterator.next();
				IContract value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (contracts[i].getCommonRef() == value.getCommonRef()) {
					// update existing contracts
					theEjb.setValue(contracts[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newContract.add(contracts[i]);
			}
			iterator = c.iterator();
		}

		iterator = newContract.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IContract) iterator.next()));
		}
	}

	/**
	 * Helper method to set approved commodity types.
	 * 
	 * @param apprCmdts a list of approved commodity types
	 * @param isAdd true if the caller is from ejbPostCreate, otherwise false
	 * @throws CreateException on error creating the new references
	 */
	private void setApprovedCommodityTypesRef(IApprovedCommodityType[] apprCmdts, boolean isAdd) throws CreateException {
		int aLength = 0;
		// remove all existing approved commodity types
		if ((apprCmdts == null) || ((aLength = apprCmdts.length) == 0)) {
			removeAllApprovedCommodityTypes();
			return;
		}

		EBApprovedCommodityTypeLocalHome ejbHome = getEBApprovedCommodityTypeLocalHome();
		Collection c = getApprovedCommodityTypesCMR();

		// add all newly added approved commodity types
		if (isAdd || c.isEmpty()) {
			for (int i = 0; i < aLength; i++) {
				c.add(ejbHome.create(apprCmdts[i]));
			}
			return;
		}

		removeApprovedCommodityType(c, apprCmdts);

		// update existing hedging contracts as well as add new contracts
		Iterator iterator = c.iterator();
		ArrayList newAppr = new ArrayList();

		for (int i = 0; i < aLength; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBApprovedCommodityTypeLocal theEjb = (EBApprovedCommodityTypeLocal) iterator.next();
				IApprovedCommodityType value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (apprCmdts[i].getCommonRef() == value.getCommonRef()) {
					// update existing contracts
					theEjb.setValue(apprCmdts[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newAppr.add(apprCmdts[i]);
			}
			iterator = c.iterator();
		}

		iterator = newAppr.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IApprovedCommodityType) iterator.next()));
		}
	}

	private void setHedgingContractInfosRef(IHedgingContractInfo[] hedgeContracts, boolean isAdd)
			throws CreateException {
		int hcLength = 0;
		// remove all existing hedging contract info
		if ((hedgeContracts == null) || ((hcLength = hedgeContracts.length) == 0)) {
			removeAllHedgingContractInfos();
			return;
		}

		EBHedgingContractInfoLocalHome ejbHome = getEBHedgingContractInfoLocalHome();
		Collection c = getHedgingContractInfosCMR();

		// add all newly added hedging contracts
		if (isAdd || c.isEmpty()) {
			for (int i = 0; i < hcLength; i++) {
				c.add(ejbHome.create(hedgeContracts[i]));
			}
			return;
		}

		removeHedgingContractInfos(c, hedgeContracts);

		// update existing hedging contracts as well as add new contracts
		Iterator iterator = c.iterator();
		ArrayList newHedge = new ArrayList();

		for (int i = 0; i < hcLength; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBHedgingContractInfoLocal theEjb = (EBHedgingContractInfoLocal) iterator.next();
				IHedgingContractInfo value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (hedgeContracts[i].getCommonRef() == value.getCommonRef()) {
					// update existing contracts
					theEjb.setValue(hedgeContracts[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newHedge.add(hedgeContracts[i]);
			}
			iterator = c.iterator();
		}

		iterator = newHedge.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IHedgingContractInfo) iterator.next()));
		}
	}

	/**
	 * Helper method to delete existing hedging contracts that are not in the
	 * list of new contracts
	 * 
	 * @param hedgeCol a list of old hedging contracts
	 * @param hedgeList a list of newly updated hedging contracts
	 */
	private void removeHedgingContractInfos(Collection hedgeCol, IHedgingContractInfo[] hedgeList) {
		Iterator iterator = hedgeCol.iterator();

		while (iterator.hasNext()) {
			EBHedgingContractInfoLocal theEjb = (EBHedgingContractInfoLocal) iterator.next();
			IHedgingContractInfo hedge = theEjb.getValue();
			if ((hedge.getStatus() != null) && hedge.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < hedgeList.length; i++) {
				if (hedgeList[i].getCommonRef() == hedge.getCommonRef()) {
					found = true;
					break;
				}
			}
			if (!found) {
				theEjb.softDelete();
			}
		}
	}

	/**
	 * Helper method to delete existing approved commodity types that are not in
	 * the list of new types
	 * 
	 * @param cmdtCol a list of old approved commodity types
	 * @param cmdtList a list of newly approved commodity types
	 */
	private void removeApprovedCommodityType(Collection cmdtCol, IApprovedCommodityType[] cmdtList) {
		Iterator iterator = cmdtCol.iterator();

		while (iterator.hasNext()) {
			EBApprovedCommodityTypeLocal theEjb = (EBApprovedCommodityTypeLocal) iterator.next();
			IApprovedCommodityType hedge = theEjb.getValue();
			if ((hedge.getStatus() != null) && hedge.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < cmdtList.length; i++) {
				if (cmdtList[i].getCommonRef() == hedge.getCommonRef()) {
					found = true;
					break;
				}
			}
			if (!found) {
				theEjb.softDelete();
			}
		}
	}

	/**
	 * Set pre-condition reference.
	 * 
	 * @param preCondition of type IPreCondition
	 * @param isAdd true is to create, false is to update
	 * @throws CreateException on error creating the precondition
	 */
	private void setPreConditionRef(IPreCondition preCondition, boolean isAdd) throws CreateException {
		if (preCondition == null) {
			return;
		}
		EBPreConditionLocalHome ejbHome = getEBPreConditionLocalHome();
		Collection c = getPreConditionCMR();

		// add all newly added pre-condition
		if (isAdd || c.isEmpty()) {
			c.add(ejbHome.create(preCondition));
			return;
		}

		Iterator i = c.iterator();
		boolean found = false;
		while (i.hasNext()) {
			EBPreConditionLocal theEjb = (EBPreConditionLocal) i.next();
			IPreCondition value = theEjb.getValue();
			if (value.getLimitProfileID() == preCondition.getLimitProfileID()) {
				theEjb.setValue(preCondition);
				found = true;
				break;
			}
		}
		if (!found) {
			c.add(ejbHome.create(preCondition));
		}
	}

	/**
	 * Set loan and agency to this commodity.
	 * 
	 * @param loans of type ILoanAgency[]
	 * @param isAdd true if the caller is from ejbCreate, otherwise false
	 * @throws CreateException on error creating the loan references
	 */
	private void setLoansRef(ILoanAgency[] loans, boolean isAdd) throws CreateException {
		int loanSize = 0;
		// remove all existing commodity loan
		if ((loans == null) || ((loanSize = loans.length) == 0)) {
			removeAllLoans();
			return;
		}

		EBLoanAgencyLocalHome ejbHome = getEBLoanAgencyLocalHome();
		Collection c = getLoansCMR();

		// add all newly added loan agency
		if (isAdd || c.isEmpty()) {
			for (int i = 0; i < loanSize; i++) {
				c.add(ejbHome.create(loans[i]));
			}
			return;
		}

		removeLoan(c, loans);

		// update existing loan as well as add new loan
		Iterator iterator = c.iterator();
		ArrayList newLoan = new ArrayList();

		for (int i = 0; i < loanSize; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBLoanAgencyLocal theEjb = (EBLoanAgencyLocal) iterator.next();
				ILoanAgency value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (loans[i].getCommonRef() == value.getCommonRef()) {
					// update existing contracts
					theEjb.setValue(loans[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newLoan.add(loans[i]);
			}
			iterator = c.iterator();
		}

		iterator = newLoan.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ILoanAgency) iterator.next()));
		}
	}

	/**
	 * Helper method to delete existing contracts that are not in the list of
	 * new contracts
	 * 
	 * @param cntrCol a list of old contracts
	 * @param cntrList a list of newly updated contracts
	 */
	private void removeContract(Collection cntrCol, IContract[] cntrList) {
		Iterator iterator = cntrCol.iterator();

		while (iterator.hasNext()) {
			EBContractLocal theEjb = (EBContractLocal) iterator.next();
			IContract cntr = theEjb.getValue();
			if ((cntr.getStatus() != null) && cntr.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < cntrList.length; i++) {
				if (cntrList[i].getCommonRef() == cntr.getCommonRef()) {
					found = true;
					break;
				}
			}
			if (!found) {
				theEjb.softDelete();
			}
		}
	}

	/**
	 * Helper method to delete existing loans that are not in the list of new
	 * loans.
	 * 
	 * @param loanCol a list of loan agencies
	 * @param loanList a list of newly updated loan agencies
	 */
	private void removeLoan(Collection loanCol, ILoanAgency[] loanList) {
		Iterator iterator = loanCol.iterator();

		while (iterator.hasNext()) {
			EBLoanAgencyLocal theEjb = (EBLoanAgencyLocal) iterator.next();
			ILoanAgency loan = theEjb.getValue();
			if ((loan.getStatus() != null) && loan.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < loanList.length; i++) {
				if (loanList[i].getCommonRef() == loan.getCommonRef()) {
					found = true;
					break;
				}
			}
			if (!found) {
				theEjb.softDelete();
			}
		}
	}

	/**
	 * Helper method to remove all approved commodity types.
	 */
	private void removeAllApprovedCommodityTypes() {
		Collection c = getApprovedCommodityTypesCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBApprovedCommodityTypeLocal theEjb = (EBApprovedCommodityTypeLocal) iterator.next();
			theEjb.softDelete();
		}
	}

	/**
	 * Helper method to remove all hedging contracts.
	 */
	private void removeAllHedgingContractInfos() {
		Collection c = getHedgingContractInfosCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBHedgingContractInfoLocal theEjb = (EBHedgingContractInfoLocal) iterator.next();
			theEjb.softDelete();
		}
	}

	/**
	 * Helper method to remove all commodity contracts.
	 */
	private void removeAllContracts() {
		Collection c = getContractsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBContractLocal theEjb = (EBContractLocal) iterator.next();
			theEjb.softDelete();
		}
	}

	/**
	 * Helper method to remove all commodiyt loan agency.
	 */
	private void removeAllLoans() {
		Collection c = getLoansCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBLoanAgencyLocal theEjb = (EBLoanAgencyLocal) iterator.next();
			theEjb.softDelete();
		}
	}

	/**
	 * Get local home of commodity contract.
	 * 
	 * @return EBContractLocalHome
	 */
	protected EBContractLocalHome getEBContractLocalHome() {
		EBContractLocalHome ejbHome = (EBContractLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CONTRACT_LOCAL_JNDI, EBContractLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBContractLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get local home of approved commodity type.
	 * 
	 * @return EBApprovedCommodityTypeLocalHome
	 */
	protected EBApprovedCommodityTypeLocalHome getEBApprovedCommodityTypeLocalHome() {
		EBApprovedCommodityTypeLocalHome ejbHome = (EBApprovedCommodityTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_APPROVED_COMMODITY_TYPE_LOCAL_JNDI, EBApprovedCommodityTypeLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBApprovedCommodityTypeLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get local home of hedging contract.
	 * 
	 * @return EBHedgingContractInfoLocalHome
	 */
	protected EBHedgingContractInfoLocalHome getEBHedgingContractInfoLocalHome() {
		EBHedgingContractInfoLocalHome ejbHome = (EBHedgingContractInfoLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_HEDGING_CONTRACT_INFO_LOCAL_JNDI, EBHedgingContractInfoLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBHedgingContractInfoLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get local home of loan agency.
	 * 
	 * @return EBLoanAgencyLocalHome
	 */
	protected EBLoanAgencyLocalHome getEBLoanAgencyLocalHome() {
		EBLoanAgencyLocalHome ejbHome = (EBLoanAgencyLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LOAN_AGENCY_LOCAL_JNDI, EBLoanAgencyLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLoanAgencyLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get local home of precondition.
	 * 
	 * @return EBPreConditionLocalHome
	 */
	protected EBPreConditionLocalHome getEBPreConditionLocalHome() {
		EBPreConditionLocalHome ejbHome = (EBPreConditionLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PRECONDITION_LOCAL_JNDI, EBPreConditionLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPreConditionLocalHome is Null!");
		}

		return ejbHome;
	}
}
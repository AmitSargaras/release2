/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/EBCashCollateralBean.java,v 1.18 2006/04/10 07:03:49 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.EBCMSLegalEntityLocal;
import com.integrosys.cms.app.customer.bus.EBSystemLocal;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;

/**
 * Entity bean implementation for cash collateral type.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.18 $
 * @since $Date: 2006/04/10 07:03:49 $ Tag: $Name: $
 */
public abstract class EBCashCollateralBean extends EBCollateralDetailBean implements ICashCollateral {
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
	 * Get if it is interest capitalisation.
	 * 
	 * @return boolean
	 */
	public boolean getIsInterestCapitalisation() {
		String intCapital = getEBIsInterestCapitalisation();
		if ((intCapital != null) && intCapital.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if it is interest capitalisation.
	 * 
	 * @param isInterestCapitalisation is of type boolean
	 */
	public void setIsInterestCapitalisation(boolean isInterestCapitalisation) {
		if (isInterestCapitalisation) {
			setEBIsInterestCapitalisation(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsInterestCapitalisation(ICMSConstant.FALSE_VALUE);
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

	/**
	 * Get cash deposit information.
	 * 
	 * @return a list of cash deposit
	 */
	public ICashDeposit[] getDepositInfo() {
		Iterator i = getDepositInfoCMR().iterator();
		ArrayList arrList = new ArrayList();

		while (i.hasNext()) {
			EBCashDepositLocal theEjb = (EBCashDepositLocal) i.next();
			ICashDeposit deposit;
			try {
				deposit = theEjb.getValue();
				if ((deposit.getStatus() == null)
						|| ((deposit.getStatus() != null) && !deposit.getStatus().equals(ICMSConstant.STATE_DELETED))) {
					arrList.add(deposit);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return (OBCashDeposit[]) arrList.toArray(new OBCashDeposit[0]);
	}

	/**
	 * Set cash deposit information.
	 * 
	 * @param depositInfo a list of cash deposit
	 */
	public void setDepositInfo(ICashDeposit[] depositInfo) {
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract String getEBIsInterestCapitalisation();

	public abstract void setEBIsInterestCapitalisation(String eBIsInterestCapitalisation);

	public abstract Double getEBMinimalFSV();

	public abstract void setEBMinimalFSV(Double eBMinimalFSV);

	public abstract Collection getDepositInfoCMR();

	public abstract void setDepositInfoCMR(Collection depositInfoCMR);
	
	//public abstract Collection getCashDepositeIDCMR();

	//public abstract void setCashDepositeIDCMR(Collection cashDepositeIDCMR);


	/**
	 * Set the cash collateral.
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
	 * Get cash deposit local home
	 * 
	 * @return EBCashDepositLocalHome
	 */
	protected EBCashDepositLocalHome getEBCashDepositLocalHome() {
		EBCashDepositLocalHome ejbHome = (EBCashDepositLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CASH_DEPOSIT_LOCAL_JNDI, EBCashDepositLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCashDepositLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Set the references to this cash collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICollateral collateral, boolean isAdd) {
		ICashCollateral cash = (ICashCollateral) collateral;
		try {
			setDepositInfoRef(cash.getDepositInfo(), isAdd);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set cash deposit information.
	 * 
	 * @param depositList of type ICashDeposit[]
	 * @throws CreateException on error creating reference to cash deposit
	 * @throws CollateralException 
	 */
	private void setDepositInfoRef(ICashDeposit[] depositList, boolean isAdd) throws CreateException, CollateralException {
		if ((depositList == null) || (depositList.length == 0)) {
			removeAllCashDeposit();
			return;
		}

		EBCashDepositLocalHome ejbHome = getEBCashDepositLocalHome();

		Collection c = getDepositInfoCMR();

		if (isAdd) {
			for (int i = 0; i < depositList.length; i++) {
				c.add(ejbHome.create(depositList[i]));
			}
			return;
		}

		if (c.size() == 0) {
			for (int i = 0; i < depositList.length; i++) {
				c.add(ejbHome.create(depositList[i]));
			}
			return;
		}

		removeCashDeposit(c, depositList);

		Iterator iterator = c.iterator();
		ArrayList newDeposit = new ArrayList();

		for (int i = 0; i < depositList.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBCashDepositLocal theEjb = (EBCashDepositLocal) iterator.next();
				
					ICashDeposit value = theEjb.getValue();
					if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
						continue;
					}

					//if (depositList[i].getRefID() == value.getRefID()) {
					if (depositList[i].getCashDepositID() == value.getCashDepositID()) {
						theEjb.setValue(depositList[i]);
						found = true;
						break;
					}
					
				
			}
			if (!found) {
				newDeposit.add(depositList[i]);
			}
			
			iterator = c.iterator();
		}

		iterator = newDeposit.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ICashDeposit) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the marketable equities.
	 */
	private void removeAllCashDeposit() {
		Collection c = getDepositInfoCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBCashDepositLocal theEjb = (EBCashDepositLocal) iterator.next();
			deleteCashDeposit(theEjb);
		}
	}

	/**
	 * Helper method to delete cash deposits in cashCol that are not contained
	 * in cashList.
	 * 
	 * @param cashCol a list of old cash deposits
	 * @param cashList a list of newly updated cash deposits
	 * @throws CollateralException 
	 */
	private void removeCashDeposit(Collection cashCol, ICashDeposit[] cashList) throws CollateralException {
		Iterator iterator = cashCol.iterator();

		while (iterator.hasNext()) {
			EBCashDepositLocal theEjb = (EBCashDepositLocal) iterator.next();
			
				ICashDeposit cash = theEjb.getValue();
				if ((cash.getStatus() != null) && cash.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				boolean found = false;
				DefaultLogger.debug(this, "-----------for FD check 1---------");
				DefaultLogger.debug(this, "-----------for FD check---------");
				for (int i = 0; i < cashList.length; i++) {
					//if (cashList[i].getRefID() == cash.getRefID()) {
					if (cashList[i].getCashDepositID() == cash.getCashDepositID()) {
						found = true;
						break;
					}
				}
				if (!found) {
					deleteCashDeposit(theEjb);
				}
			
		}
	}
	
	// public abstract EBCashDepositLocal getDepositInfoCMR();
	// public abstract void setDepositInfoCMR(EBCashDepositLocal value);
	 /**
     * Method to retrieve legal entity
     */
   /* private ICashDeposit retrieveCashDeposit() throws CustomerException {
        try {
        	EBCashDepositLocal local = getDepositInfoCMR();
            if (null != local) {
                return local.getValue();
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (e instanceof CustomerException) {
                throw (CustomerException) e;
            } else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }
    */
    
    private ICashDeposit[] retrieveCashDeposit() throws CollateralException {
		try {
			Collection c = getDepositInfoCMR();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCashDepositLocal local = (EBCashDepositLocal) i.next();
					ICashDeposit ob = local.getValue();
					aList.add(ob);
				}

				return (ICashDeposit[]) aList.toArray(new ICashDeposit[0]);
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
	 public ICashCollateral getValue() throws CollateralException {
	        try {
	            OBCashCollateral value = new OBCashCollateral();
	            AccessorUtil.copyValue(this, value);
	           /* ICashDeposit[] deposit = new ICashDeposit[10];
	            deposit[0]= retrieveCashDeposit();
	            value.setDepositInfo(deposit);*/
	            
	            value.setDepositInfo(retrieveCashDeposit());
	          //  value.setOfficialAddresses(retrieveOfficialAddresses());
	            //value.setCustomerSysXRefs(retrieveCustomerSysXRefs());

	            return value;
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            if (e instanceof CollateralException) {
	                throw (CollateralException) e;
	            } else {
	                throw new CollateralException("Caught Exception: " + e.toString());
	            }
	        }
	    }

	/**
	 * Helper method to delete cash deposit.
	 * 
	 * @param theEjb of type EBCashDepositLocal
	 */
	private void deleteCashDeposit(EBCashDepositLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

    public abstract String getMinimalFSVCcyCode();

    public abstract void setMinimalFSVCcyCode(String minimalFSVCcyCode);
    
    public abstract Date getPriCaveatGuaranteeDate();
    
    public abstract void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate);
    
    public abstract String getDescription();
    
    public abstract void setDescription(String description);
    
    public abstract String getCreditCardRefNumber();
    
    public abstract void setCreditCardRefNumber(String creditCardRefNumber);
    
    public abstract String getIssuer();
    
    public abstract void setIssuer(String issuer);
    
}
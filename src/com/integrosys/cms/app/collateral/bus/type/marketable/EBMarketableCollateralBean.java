/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/EBMarketableCollateralBean.java,v 1.6 2003/11/06 11:43:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Entity bean implementation for marketable collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/06 11:43:24 $ Tag: $Name: $
 */
public abstract class EBMarketableCollateralBean extends EBCollateralDetailBean implements IMarketableCollateral {
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
	
	public Amount getCappedPrice() {
		if (getEBCappedPrice()!=null) return new Amount(getEBCappedPrice().doubleValue(), IGlobalConstant.DEFAULT_CURRENCY);
		return null;
	}

	public void setCappedPrice(Amount cappedPrice) {
		if (cappedPrice!=null) setEBCappedPrice(new Double(cappedPrice.getAmountAsDouble()));
		else setEBCappedPrice(null);
	}
	
	public double getInterestRate() {
		if (getEBInterestRate()!=null) return getEBInterestRate().doubleValue();
		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}
	
	public void setInterestRate(double interestRate) {
		if (interestRate==ICMSConstant.DOUBLE_INVALID_VALUE) setEBInterestRate(null);
		else setEBInterestRate(new Double(interestRate));
	}
	
	/**
	 * Get a list of marketable equity.
	 * 
	 * @return IMarketableEquity[]
	 */
	public IMarketableEquity[] getEquityList() {
		Iterator i = getEquityListCMR().iterator();
		ArrayList arrList = new ArrayList();

		while (i.hasNext()) {
			EBMarketableEquityLocal theEjb = (EBMarketableEquityLocal) i.next();
			IMarketableEquity item = theEjb.getValue();
			if ((item.getStatus() == null)
					|| ((item.getStatus() != null) && !item.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				arrList.add(theEjb.getValue());
			}
		}
		return (OBMarketableEquity[]) arrList.toArray(new OBMarketableEquity[0]);
	}

	/**
	 * Set marketable equity list.
	 * 
	 * @param equityList of type IMarketableEquity[]
	 */
	public void setEquityList(IMarketableEquity[] equityList) {
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

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Double getEBMinimalFSV();

	public abstract void setEBMinimalFSV(Double eBMinimalFSV);

	public abstract Collection getEquityListCMR();

	public abstract void setEquityListCMR(Collection equityList);

	/**
	 * Set the marketable collateral type to this entity.
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
	 * Set the references to this collateral.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICollateral collateral, boolean isAdd) {
		IMarketableCollateral marketable = (IMarketableCollateral) collateral;
		try {
			setEquityListRef(marketable.getEquityList(), isAdd);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set marketable equity list.
	 * 
	 * @param equityList of type IMarketableEquity[]
	 * @throws CreateException on error creating reference to equity
	 */
	private void setEquityListRef(IMarketableEquity[] equityList, boolean isAdd) throws CreateException {
		if ((equityList == null) || (equityList.length == 0)) {
			removeAllMarketableEquities();
			return;
		}

		EBMarketableEquityLocalHome ejbHome = getEBMarketableEquityLocalHome();

		Collection c = getEquityListCMR();

		if (isAdd) {
			for (int i = 0; i < equityList.length; i++) {
				c.add(ejbHome.create(equityList[i]));
			}
			return;
		}

		if (c.size() == 0) {
			for (int i = 0; i < equityList.length; i++) {
				c.add(ejbHome.create(equityList[i]));
			}
			return;
		}

		removeMarketableEquities(c, equityList);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		for (int i = 0; i < equityList.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBMarketableEquityLocal theEjb = (EBMarketableEquityLocal) iterator.next();
				IMarketableEquity value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (equityList[i].getRefID() == value.getRefID()) {
					theEjb.setValue(equityList[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(equityList[i]);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IMarketableEquity) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the marketable equities.
	 */
	private void removeAllMarketableEquities() {
		Collection c = getEquityListCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBMarketableEquityLocal theEjb = (EBMarketableEquityLocal) iterator.next();
			deletePortfolioItem(theEjb);
		}
	}

	/**
	 * Helper method to delete items in itemCol that are not contained in
	 * equityList.
	 * 
	 * @param itemCol a list of old portolio items
	 * @param equityList a list of newly updated portfolio items
	 */
	private void removeMarketableEquities(Collection itemCol, IMarketableEquity[] equityList) {
		Iterator iterator = itemCol.iterator();

		while (iterator.hasNext()) {
			EBMarketableEquityLocal theEjb = (EBMarketableEquityLocal) iterator.next();
			IMarketableEquity item = theEjb.getValue();
			if ((item.getStatus() != null) && item.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < equityList.length; i++) {
				if (equityList[i].getRefID() == item.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deletePortfolioItem(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a portfolio item.
	 * 
	 * @param theEjb of type EBMarketableEquityLocal
	 */
	private void deletePortfolioItem(EBMarketableEquityLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Get marketable equity local home
	 * 
	 * @return EBMarketableEquityLocalHome
	 */
	protected EBMarketableEquityLocalHome getEBMarketableEquityLocalHome() {
		EBMarketableEquityLocalHome ejbHome = (EBMarketableEquityLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MARKETABLE_EQUITY_LOCAL_JNDI, EBMarketableEquityLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBMarketableEquityLocalHome is Null!");
		}

		return ejbHome;
	}

    public abstract String getMinimalFSVCcyCode();

    public abstract void setMinimalFSVCcyCode(String minimalFSVCcyCode);
    
    public abstract Double getEBCappedPrice();
    
    public abstract void setEBCappedPrice(Double cappedPrice);
    
    public abstract Double getEBInterestRate();
    
    public abstract void setEBInterestRate(Double interestRate);
    
	public abstract String getStockCounterCode();

	public abstract void setStockCounterCode(String stockCounterCode);

}
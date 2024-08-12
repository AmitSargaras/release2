/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/EBAssetPostDatedChequeBean.java,v 1.4 2003/10/23 06:21:00 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for Asset of type post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/23 06:21:00 $ Tag: $Name: $
 */
public abstract class EBAssetPostDatedChequeBean extends EBCollateralDetailBean implements IAssetPostDatedCheque {
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
	 * Get a list of post dated cheques of this asset.
	 * 
	 * @return IPostDatedCheque[]
	 */
	public IPostDatedCheque[] getPostDatedCheques() {
		Iterator i = getPostDatedChequesCMR().iterator();
		ArrayList arrList = new ArrayList();

		while (i.hasNext()) {
			EBPostDatedChequeLocal theEjb = (EBPostDatedChequeLocal) i.next();
			IPostDatedCheque cheque = theEjb.getValue();
			if ((cheque.getStatus() == null)
					|| ((cheque.getStatus() != null) && !cheque.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				arrList.add(theEjb.getValue());
			}
		}
		return (OBPostDatedCheque[]) arrList.toArray(new OBPostDatedCheque[0]);
	}

	/**
	 * set a list of post dated cheques as an asset.
	 * 
	 * @param postDatedCheques of type IPostDatedCheque[]
	 */
	public void setPostDatedCheques(IPostDatedCheque[] postDatedCheques) {
	}

	/**
	 * Get interestRate PostedCheque
	 * 
	 * @return double
	 */
	public double getInterestRate() {
		if (getEBInterestRate() != null)
			return getEBInterestRate().doubleValue();
		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * Set interestRate PostedCheque
	 * 
	 * @param interestRate is of type double
	 */
	public void setInterestRate(double interestRate) {
		if (interestRate == ICMSConstant.DOUBLE_INVALID_VALUE)
			setEBInterestRate(null);
		else
			setEBInterestRate(new Double(interestRate));
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Collection getPostDatedChequesCMR();

	public abstract void setPostDatedChequesCMR(Collection postDatedCheques);

	public abstract Double getEBInterestRate();

	public abstract void setEBInterestRate(Double interestRate);

	/**
	 * Set the pdt cheque asset collateral.
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
	 * Get post dated cheque local home
	 * 
	 * @return EBPostDatedChequeLocalHome
	 */
	protected EBPostDatedChequeLocalHome getEBPostDatedChequeLocalHome() {
		EBPostDatedChequeLocalHome ejbHome = (EBPostDatedChequeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_POST_DATED_CHEQUE_LOCAL_JNDI, EBPostDatedChequeLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPostDatedChequeLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Set the references to this post dated cheque asset.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICollateral collateral, boolean isAdd) {
		IAssetPostDatedCheque cheque = (IAssetPostDatedCheque) collateral;
		try {
			setPostDatedChequesRef(cheque.getPostDatedCheques(), isAdd);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * set a list of post dated cheques as an asset.
	 * 
	 * @param chequeList of type IPostDatedCheque[]
	 * @throws CreateException on error creating reference to pdt cheque
	 */
	private void setPostDatedChequesRef(IPostDatedCheque[] chequeList, boolean isAdd) throws CreateException {
		if ((chequeList == null) || (chequeList.length == 0)) {
			removeAllCheques();
			return;
		}

		EBPostDatedChequeLocalHome ejbHome = getEBPostDatedChequeLocalHome();

		Collection c = getPostDatedChequesCMR();

		if (isAdd) {
			for (int i = 0; i < chequeList.length; i++) {
				if(chequeList[i]!=null){
					c.add(ejbHome.create(chequeList[i]));
					
				}
				
			}
			return;
		}

		if (c.size() == 0) {
			for (int i = 0; i < chequeList.length; i++) {
				c.add(ejbHome.create(chequeList[i]));
			}
			return;
		}

		removeCheques(c, chequeList);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		for (int i = 0; i < chequeList.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBPostDatedChequeLocal theEjb = (EBPostDatedChequeLocal) iterator.next();
				IPostDatedCheque value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (chequeList[i].getRefID() == value.getRefID()) {
					theEjb.setValue(chequeList[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(chequeList[i]);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IPostDatedCheque) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the postdated cheques.
	 */
	private void removeAllCheques() {
		Collection c = getPostDatedChequesCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBPostDatedChequeLocal theEjb = (EBPostDatedChequeLocal) iterator.next();
			deleteCheque(theEjb);
		}
	}

	/**
	 * Helper method to delete cheques in chequeCol that are not contained in
	 * equityList.
	 * 
	 * @param chequeCol a list of old cheques
	 * @param chequeList a list of newly updated cheques
	 */
	private void removeCheques(Collection chequeCol, IPostDatedCheque[] chequeList) {
		Iterator iterator = chequeCol.iterator();

		while (iterator.hasNext()) {
			EBPostDatedChequeLocal theEjb = (EBPostDatedChequeLocal) iterator.next();
			IPostDatedCheque cheque = theEjb.getValue();
			if ((cheque.getStatus() != null) && cheque.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < chequeList.length; i++) {
				if (chequeList[i].getRefID() == cheque.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteCheque(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a cheque.
	 * 
	 * @param theEjb of type EBPostDatedChequeLocal
	 */
	private void deleteCheque(EBPostDatedChequeLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	public abstract Date getChequeDate();

	public abstract String getChequeRefNumber();

	public abstract Date getPriCaveatGuaranteeDate();

	public abstract String getRemarks();

	public abstract void setChequeDate(Date chequeDate);

	public abstract void setChequeRefNumber(String chequeRefNumber);

	public abstract void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate);

	public abstract void setRemarks(String remarks);
	
	

	
}
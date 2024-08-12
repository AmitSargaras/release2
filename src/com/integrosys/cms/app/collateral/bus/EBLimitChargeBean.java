/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeBean.java,v 1.22 2006/08/30 11:38:43 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

/**
 * Entity bean implementation for collateral limit charge entity.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/08/30 11:38:43 $ Tag: $Name: $
 */
public abstract class EBLimitChargeBean implements ILimitCharge, EntityBean {

	private static final long serialVersionUID = -5014391991838292869L;

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the limit charge. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getChargeDetailID", "getCollateralID", "getRefID" };

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID() {
		return getEBChargeDetailID().longValue();
	}

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID) {
		setEBChargeDetailID(new Long(chargeDetailID));
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		if (getEBCollateralID() != null) {
			return getEBCollateralID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get charge amount.
	 * 
	 * @return Amount
	 */
	public Amount getChargeAmount() {
		if (getEBChargeAmount() == null) {
			return null;
		}
		return new Amount(getEBChargeAmount().doubleValue(), getChargeCcyCode());
	}

	/**
	 * Set charge amount.
	 * 
	 * @param chargeAmount is of type Amount
	 */
	public void setChargeAmount(Amount chargeAmount) {
		if (chargeAmount != null) {
			setEBChargeAmount(new Double(chargeAmount.getAmountAsDouble()));
		}
		else {
			setEBChargeAmount(null);
		}
	}

	/**
	 * Get prior charge amount.
	 * 
	 * @return Amount
	 */
	public Amount getPriorChargeAmount() {
		if (getEBPriorChargeAmount() != null) {
			return new Amount(getEBPriorChargeAmount().doubleValue(), getPriorChargeCcyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set prior charge amount.
	 * 
	 * @param priorChargeAmount of type Amount
	 */
	public void setPriorChargeAmount(Amount priorChargeAmount) {
		if (priorChargeAmount != null) {
			setEBPriorChargeAmount(new Double(priorChargeAmount.getAmountAsDouble()));
		}
		else {
			setEBPriorChargeAmount(null);
		}
	}

	/**
	 * Get Caveat Waived Indicator
	 * 
	 * @return boolean
	 */
	public Boolean getCaveatWaivedInd() {
		String caveatWaivedInd = getEBCaveatWaivedInd();
		if (ICMSConstant.TRUE_VALUE.equals(caveatWaivedInd)) {
			return Boolean.TRUE;
		}
		else if (ICMSConstant.FALSE_VALUE.equals(caveatWaivedInd)) {
			return Boolean.FALSE;
		}

		return null;
	}

	/**
	 * Set Caveat Waived Indicator
	 * 
	 * @param caveatWaivedInd of type boolean
	 */
	public void setCaveatWaivedInd(Boolean caveatWaivedInd) {
		if (caveatWaivedInd == null) {
			setEBCaveatWaivedInd(null);
		}
		else {
			if (caveatWaivedInd.booleanValue()) {
				setEBCaveatWaivedInd(ICMSConstant.TRUE_VALUE);
			}
			else {
				setEBCaveatWaivedInd(ICMSConstant.FALSE_VALUE);
			}
		}
	}

	/**
	 * Get limit maps of this charge.
	 * 
	 * @return long
	 */
	public ICollateralLimitMap[] getLimitMaps() {
		Iterator i = getChargeMapsCMR().iterator();

		ArrayList arrList = new ArrayList();
		ArrayList limitIDList = new ArrayList();

		while (i.hasNext()) {
			EBLimitChargeMapLocal theEjb = (EBLimitChargeMapLocal) i.next();
			ILimitChargeMap map = theEjb.getValue();
			if ((map.getStatus() != null) && map.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			Long strLimitID = new Long(map.getLimitID());
			if (!limitIDList.contains(strLimitID)) {
				limitIDList.add(strLimitID);
			}

			arrList.add(map);
		}

		try {
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			HashMap limitMap = limitDAO.getLimitProductTypeByLimitIDList(limitIDList);
			for (int n = 0; n < arrList.size(); n++) {
				ILimitChargeMap obj = (ILimitChargeMap) arrList.get(n);
				String productType = (String) limitMap.get(String.valueOf(obj.getLimitID()));
				obj.setLimitType(productType);
				arrList.set(n, obj);
			}
		}
		catch (Exception e) {
			// do nothing
		}

		return (OBLimitChargeMap[]) arrList.toArray(new OBLimitChargeMap[0]);
	}

	/**
	 * Set limit maps of this charge.
	 * 
	 * @param limitMaps is of type ICollateralLimitMap[]
	 */
	public void setLimitMaps(ICollateralLimitMap[] limitMaps) {
	}

	public abstract Long getEBChargeDetailID();

	public abstract void setEBChargeDetailID(Long eBChargeDetailID);

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Double getEBChargeAmount();

	public abstract void setEBChargeAmount(Double eBChargeAmount);

	public abstract Double getEBPriorChargeAmount();

	public abstract void setEBPriorChargeAmount(Double eBPriorChargeAmount);

	public abstract Collection getChargeMapsCMR();

	public abstract void setChargeMapsCMR(Collection chargeMapsCMR);

	public abstract String getEBCaveatWaivedInd();

	public abstract void setEBCaveatWaivedInd(String eBCaveatWaivedInd);

	public abstract long getRefID();

	/**
	 * Get the collateral limit charge business object.
	 * 
	 * @return collateral limit charge
	 */
	public ILimitCharge getValue() {
		OBLimitCharge charge = new OBLimitCharge();
		AccessorUtil.copyValue(this, charge);
		return charge;
	}

	/**
	 * Set the limit charge to this entity.
	 * 
	 * @param limitCharge is of type ILimitCharge
	 */
	public void setValue(ILimitCharge limitCharge) {
		try {
			AccessorUtil.copyValue(limitCharge, this, EXCLUDE_METHOD);
			setReferences(limitCharge, false);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Delete the limit charge.
	 */
	public void delete() {
		try {
			setStatus(ICMSConstant.STATE_DELETED);
			setChargeMapsRef(null, getCollateralID(), false);
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Create the collateral limit charge record.
	 * 
	 * @param limitCharge the collateral limit charge
	 * @return Long
	 * @throws CreateException on error creating the limit charge
	 */
	public Long ejbCreate(ILimitCharge limitCharge) throws CreateException {
		try {
			String chargeDetailID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_LIMIT_CHARGE, true);
			AccessorUtil.copyValue(limitCharge, this, EXCLUDE_METHOD);
			setEBChargeDetailID(new Long(chargeDetailID));
			if (limitCharge.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getChargeDetailID());
			}
			else {
				// else maintain this charge SID
				setRefID(limitCharge.getRefID());
			}

			return null;
		}
		catch (Exception e) {
			CreateException ce = new CreateException("failed to generate sequence number for ["
					+ ICMSConstant.SEQUENCE_COL_LIMIT_CHARGE + "]; nested exception is " + e);
			ce.initCause(e);
			throw ce;
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param limitCharge is of type ILimitCharge
	 */
	public void ejbPostCreate(ILimitCharge limitCharge) {
		setReferences(limitCharge, true);
	}

	/**
	 * Get limit charge map local home.
	 * 
	 * @return EBLimitChargeMapLocalHome
	 */
	protected EBLimitChargeMapLocalHome getEBLimitChargeMapLocalHome() {
		EBLimitChargeMapLocalHome ejbHome = (EBLimitChargeMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_CHARGE_MAP_LOCAL_JNDI, EBLimitChargeMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBLimitChargeMapLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Set the references to this limit charge.
	 * 
	 * @param charge of type ILimitCharge
	 */
	private void setReferences(ILimitCharge charge, boolean isAdd) {
		try {
			setChargeMapsRef(charge.getLimitMaps(), charge.getCollateralID(), isAdd);
		}
		catch (CreateException e) {
			throw new EJBException(e);
		}

		if (!isAdd) {
			if (charge.getLimitMaps() == null || charge.getLimitMaps().length == 0) {
				setStatus(ICMSConstant.STATE_DELETED);
			}
			else if (charge.getLimitMaps() != null && charge.getLimitMaps().length > 0) {
				ILimitChargeMap[] limitChargeMaps = (ILimitChargeMap[]) charge.getLimitMaps();
				boolean foundActiveLimitChargeMap = false;
				for (int i = 0; i < limitChargeMaps.length && !foundActiveLimitChargeMap; i++) {
					ILimitChargeMap limitChargeMap = limitChargeMaps[i];
					if (!ICMSConstant.STATE_DELETED.equals(limitChargeMap.getStatus())) {
						foundActiveLimitChargeMap = true;
					}
				}
				if (!foundActiveLimitChargeMap) {
					setStatus(ICMSConstant.STATE_DELETED);
				}
			}
		}

	}

	/**
	 * Helper method to reupdate charge id to the given charge maps.
	 * 
	 * @param maps of type ICollateralLimitMap
	 * @param collateralID
	 */
	/*
	 * not in used now private void reUpdateChargeMaps (ICollateralLimitMap[]
	 * maps, long collateralID) { EBCollateralLimitMapLocalHome ejbHome =
	 * getEBCollateralLimitMapLocalHome(); CollateralLimitMapComparator comp =
	 * new CollateralLimitMapComparator();
	 * 
	 * for (int i=0; i<maps.length; i++) { try { Collection c = null;
	 * if(ICMSConstant
	 * .CUSTOMER_CATEGORY_MAIN_BORROWER.equals(maps[i].getCustomerCategory())){
	 * c = ejbHome.findByColIDAndLimitID (new Long(collateralID), new
	 * Long(maps[i].getLimitID())); }else{ c = ejbHome.findByColIDAndCBLimitID
	 * (new Long(collateralID), new Long(maps[i].getCoBorrowerLimitID())); }
	 * Iterator iterator = c.iterator(); maps[i].setChargeID
	 * (ICMSConstant.LONG_INVALID_VALUE);
	 * 
	 * boolean isLatest = false; if (maps[i].getSCIStatus() == null ||
	 * !maps[i].getSCIStatus().equals (String.valueOf
	 * (ICMSConstant.HOST_STATUS_DELETE))) { isLatest = true; }
	 * 
	 * while (iterator.hasNext()) { EBCollateralLimitMapLocal theEjb =
	 * (EBCollateralLimitMapLocal) iterator.next(); ICollateralLimitMap map =
	 * theEjb.getValue();
	 * 
	 * if (isLatest) { int result = comp.compare (map, maps[i]); if (result > 0
	 * || result == 0) { maps[i].setChargeID (map.getChargeID()); } } else { if
	 * (maps[i].getSCISysGenID() == map.getSCISysGenID()) { maps[i].setChargeID
	 * (map.getChargeID()); break; } } } } catch (FinderException e) {
	 * 
	 * } } }
	 */
	/**
	 * @see com.integrosys.cms.app.collateral.bus.EBLimitCharge#setChargeMapsRef
	 */
	public void setChargeMapsRef(ILimitChargeMap lmtChargeMap) {
		try {

			EBLimitChargeMapLocalHome ejbHome = getEBLimitChargeMapLocalHome();

			Collection c = getChargeMapsCMR();
			lmtChargeMap.setChargeDetailID(getChargeDetailID());
			c.add(ejbHome.create(lmtChargeMap));

		}
		catch (CreateException e) {
			throw new EJBException(
					"failed to create limit charge map [" + lmtChargeMap + "]; nested exception is " + e, e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.EBLimitCharge#removeChargeMapsRef
	 */
	public void removeChargeMapsRef(ILimitChargeMap lmtChargeMap) {

		Collection c = getChargeMapsCMR();

		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBLimitChargeMapLocal theEjb = (EBLimitChargeMapLocal) iterator.next();
			ILimitChargeMap value = theEjb.getValue();

			if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			if (lmtChargeMap.getChargeID() == value.getChargeID()) {
				deleteLimitChargeMap(theEjb);
				break;
			}
		}

	}

	/**
	 * Set limit charge maps of this charge.
	 * 
	 * @param maps is of type ICollateralLimitMap[]
	 * @param collateralID collateral id
	 * @throws CreateException on error creating the references
	 */
	private void setChargeMapsRef(ICollateralLimitMap[] maps, long collateralID, boolean isAdd) throws CreateException {
		if ((maps == null) || (maps.length == 0)) {
			removeAllLimitChargeMaps();
			return;
		}

		EBLimitChargeMapLocalHome ejbHome = getEBLimitChargeMapLocalHome();

		Collection c = getChargeMapsCMR();

		// reUpdateChargeMaps (maps, collateralID);
		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < maps.length; i++) {
				OBLimitChargeMap cm = new OBLimitChargeMap();
				cm.setCollateralID(collateralID);
				cm.setChargeDetailID(getChargeDetailID());
				cm.setLimitID(maps[i].getLimitID());
				cm.setChargeID(maps[i].getChargeID());
				cm.setCoBorrowerLimitID(maps[i].getCoBorrowerLimitID());
				cm.setCustomerCategory(maps[i].getCustomerCategory());
				cm.setCmsLimitProfileId(maps[i].getCmsLimitProfileId());
				c.add(ejbHome.create(cm));
			}
			return;
		}

		removeLimitChargeMap(c, maps);

		Iterator iterator = c.iterator();
		ArrayList newChargeMap = new ArrayList();

		for (int i = 0; i < maps.length; i++) {
			ICollateralLimitMap map = maps[i];

			boolean found = false;

			while (iterator.hasNext()) {
				EBLimitChargeMapLocal theEjb = (EBLimitChargeMapLocal) iterator.next();
				ILimitChargeMap value = theEjb.getValue();

				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (map.getChargeID() == value.getChargeID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				newChargeMap.add(map);
			}
			iterator = c.iterator();
		}

		iterator = newChargeMap.iterator();

		while (iterator.hasNext()) {
			ICollateralLimitMap map = (ICollateralLimitMap) iterator.next();
			OBLimitChargeMap cm = new OBLimitChargeMap();
			cm.setCollateralID(collateralID);
			cm.setChargeDetailID(getChargeDetailID());
			cm.setLimitID(map.getLimitID());
			cm.setChargeID(map.getChargeID());
			cm.setCmsLimitProfileId(map.getCmsLimitProfileId());
			c.add(ejbHome.create(cm));
		}
	}

	/**
	 * Helper method to delete all the mapping between limit and charge.
	 */
	private void removeAllLimitChargeMaps() {
		Collection c = getChargeMapsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBLimitChargeMapLocal theEjb = (EBLimitChargeMapLocal) iterator.next();
			deleteLimitChargeMap(theEjb);
		}
	}

	/**
	 * Helper method to delete charge maps in chargeMapCol that are not
	 * contained in chargeMapList.
	 * 
	 * @param chargeMapCol a list of old limit charge maps
	 * @param chargeMapList a list of newly updated limit charge maps
	 */
	private void removeLimitChargeMap(Collection chargeMapCol, ICollateralLimitMap[] chargeMapList) {
		Iterator iterator = chargeMapCol.iterator();

		while (iterator.hasNext()) {
			EBLimitChargeMapLocal theEjb = (EBLimitChargeMapLocal) iterator.next();
			ILimitChargeMap map = theEjb.getValue();
			if ((map.getStatus() != null) && map.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < chargeMapList.length; i++) {
				if (map.getChargeID() == chargeMapList[i].getChargeID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteLimitChargeMap(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete limit charge map.
	 * 
	 * @param theEjb of type EBLimitChargeMapLocal
	 */
	private void deleteLimitChargeMap(EBLimitChargeMapLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Get collateral limit map local home.
	 * 
	 * @return EBCollateralLimitMapLocalHome
	 */
	protected EBCollateralLimitMapLocalHome getEBCollateralLimitMapLocalHome() {
		EBCollateralLimitMapLocalHome ejbHome = (EBCollateralLimitMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMIT_MAP_LOCAL_JNDI, EBCollateralLimitMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralLimitMapLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to unassociate charge with limit.
	 * 
	 * @param maps of type ICollateralLimitMap[]
	 * @param colID collateralID
	 */
	/*
	 * private void unassociateLimitMap (ICollateralLimitMap[] maps, long colID)
	 * { Collection c = getLimitMapsCMR(); Iterator iterator = c.iterator();
	 * 
	 * // delete all charges if (maps == null || maps.length == 0) { while (
	 * iterator.hasNext() ) { EBCollateralLimitMapLocal theEjb =
	 * (EBCollateralLimitMapLocal) iterator.next(); c.remove (theEjb); iterator
	 * = c.iterator(); } } else { while ( iterator.hasNext() ) {
	 * EBCollateralLimitMapLocal theEjb = (EBCollateralLimitMapLocal)
	 * iterator.next(); boolean found = false;
	 * 
	 * for (int i=0; i<maps.length; i++) { ICollateralLimitMap map =
	 * theEjb.getValue(); if (map.getLimitID() == maps[i].getLimitID() &&
	 * map.getCollateralID() == colID) { found = true; break; } }
	 * 
	 * if (!found) { c.remove (theEjb); iterator = c.iterator(); } } } }
	 */

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	// Fields isLE & lEDate added by Pratheepa for CR235.
	public abstract String getIsLE();

	public abstract void setIsLE(String isLE);

	public abstract Date getLEDate();

	public abstract void setLEDate(Date lEDate);

	public abstract String getIsLEByChargeRanking();

	public abstract void setIsLEByChargeRanking(String isLEByChargeRanking);

	public abstract String getIsLEByJurisdiction();

	public abstract void setIsLEByJurisdiction(String isLEByJurisdiction);

	public abstract String getIsLEByGovernLaws();

	public abstract void setIsLEByGovernLaws(String isLEByGovernLaws);

	public abstract Date getLEDateByChargeRanking();

	public abstract void setLEDateByChargeRanking(Date lEDateByChargeRanking);

	public abstract Date getLEDateByJurisdiction();

	public abstract void setLEDateByJurisdiction(Date lEDateByJurisdiction);

	public abstract Date getLEDateByGovernLaws();

	public abstract void setLEDateByGovernLaws(Date lEDateByGovernLaws);

	public abstract String getNatureOfCharge();

	public abstract void setNatureOfCharge(String natureOfCharge);

	public abstract String getChargeCcyCode();

	public abstract void setChargeCcyCode(String chargeCcyCode);

	public abstract Date getLegalChargeDate();

	public abstract void setLegalChargeDate(Date legalChargeDate);

	public abstract String getPriorChargeChargee();

	public abstract void setPriorChargeChargee(String priorChargeChargee);

	public abstract int getSecurityRank();

	public abstract void setSecurityRank(int securityRank);

	public abstract String getPriorChargeCcyCode();

	public abstract void setPriorChargeCcyCode(String priorChargeCcyCode);

	public abstract void setRefID(long refID);

	public abstract String getChargeType();

	public abstract void setChargeType(String chargeType);

	public abstract Date getChargeConfirmationDate();

	public abstract void setChargeConfirmationDate(Date chargeConfirmationDate);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract String getPriorChargeType();

	public abstract void setPriorChargeType(String priorChargeType);

	public abstract String getCaveatReferenceNo();

	public abstract void setCaveatReferenceNo(String caveatReferenceNo);

	public abstract Date getExpiryDate();

	public abstract void setExpiryDate(Date expiryDate);

	public abstract String getPresentationNo();

	public abstract void setPresentationNo(String presentationNo);

	public abstract Date getPresentationDate();

	public abstract void setPresentationDate(Date presentationDate);

	public abstract Date getLodgedDate();

	public abstract void setLodgedDate(Date lodgedDate);

	public abstract String getSolicitorName();

	public abstract void setSolicitorName(String solicitorName);

	public abstract String getRedemption();

	public abstract void setRedemption(String redemption);

	public abstract String getFolio();

	public abstract void setFolio(String folio);

	public abstract String getJilid();

	public abstract void setJilid(String jilid);

	public abstract String getPartyCharge();

	public abstract void setPartyCharge(String partyCharge);

	public abstract String getHostCollateralId();

	public abstract void setHostCollateralId(String hostCollateralId);

}
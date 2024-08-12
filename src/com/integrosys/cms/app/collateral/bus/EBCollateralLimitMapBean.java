/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralLimitMapBean.java,v 1.24 2006/08/23 03:30:57 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.EBSubLimitLocal;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.EBSubLimitLocalHome;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit;
import com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.OBSubLimit;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for collateral limit map entity.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2006/08/23 03:30:57 $ Tag: $Name: $
 */
public abstract class EBCollateralLimitMapBean implements ICollateralLimitMap, EntityBean {

	private static final long serialVersionUID = 6070524872730397191L;

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create to the limit charge. */
	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getChargeID", "getSubLimit", "getLimitType" };

	/**
	 * A list of methods to be excluded during update of limit map. These
	 * include charge id and information from SCI.
	 */
	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getChargeID", "getSCILimitID",
			"getSCISysGenID", "getSCILimitProfileID", "getSCISubProfileID", "getSCILegalEntityID", "getSCISecurityID",
			"getSCIPledgorID" };

	/**
	 * Get charge id.
	 * 
	 * @return long
	 */
	public long getChargeID() {
		return getEBChargeID().longValue();
	}

	/**
	 * Set charge id.
	 * 
	 * @param chargeID of type long
	 */
	public void setChargeID(long chargeID) {
		setEBChargeID(new Long(chargeID));
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
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
	 * Get if the limit allows collateral pool.
	 * 
	 * @return boolean
	 */
	public boolean getIsCollateralPool() {
		if ((getEBIsCollateralPool() != null) && getEBIsCollateralPool().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Set if the limit allows collateral pool.
	 * 
	 * @param isCollateralPool of type boolean
	 */
	public void setIsCollateralPool(boolean isCollateralPool) {
		setEBIsCollateralPool(isCollateralPool ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Get if the limit allows specific transaction.
	 * 
	 * @return boolean
	 */
	public boolean getIsSpecificTrx() {
		if ((getEBIsSpecificTrx() != null) && getEBIsSpecificTrx().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Set if the limit allows specific transaction.
	 * 
	 * @param isSpecificTrx of type boolean
	 */
	public void setIsSpecificTrx(boolean isSpecificTrx) {
		setEBIsSpecificTrx(isSpecificTrx ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Get cash requirement percentage.
	 * 
	 * @return double
	 */
	public double getCashReqPct() {
		if (getEBCashReqPct() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		return getEBCashReqPct().doubleValue();
	}

	/**
	 * Set cash requirement percentage.
	 * 
	 * @param cashReqPct of type double
	 */
	public void setCashReqPct(double cashReqPct) {
		setEBCashReqPct(cashReqPct == ICMSConstant.DOUBLE_INVALID_VALUE ? null : new Double(cashReqPct));
	}

	/**
	 * Get if the applied limit amount is to be included in calculations for
	 * total applied limit amount for a collateral.
	 * 
	 * @return boolean
	 */
	public boolean getIsAppliedLimitAmountIncluded() {
		String isIncludedStr = getEBIsAppliedLimitAmountIncluded();
		return ((isIncludedStr == null) || isIncludedStr.equals(ICMSConstant.TRUE_VALUE));
	}

	/**
	 * Set if the applied limit amount is to be included in calculations for
	 * total applied limit amount for a collateral.
	 * 
	 * @param isIncluded - boolean
	 */
	public void setIsAppliedLimitAmountIncluded(boolean isIncluded) {
		setEBIsAppliedLimitAmountIncluded(isIncluded ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Get applied limit amount for asset based general charge security.
	 * 
	 * @return Amount
	 */
	public Amount getAppliedLimitAmount() {
		return ((getEBAppliedLimitAmount() == null) || (getLimitCcyCode() == null) || (getLimitCcyCode().length() == 0)) ? null
				: new Amount(getEBAppliedLimitAmount(), new CurrencyCode(getLimitCcyCode()));
	}

	/**
	 * Set applied limit amount for asset based general charge security.
	 * 
	 * @param appliedLimitAmt of type Amount
	 */
	public void setAppliedLimitAmount(Amount appliedLimitAmt) {
		if ((appliedLimitAmt != null) && (appliedLimitAmt.getAmountAsBigDecimal() != null)
				&& (appliedLimitAmt.getCurrencyCode() != null)) {
			setEBAppliedLimitAmount(appliedLimitAmt.getAmountAsBigDecimal());
			setLimitCcyCode(appliedLimitAmt.getCurrencyCode());
		}
	}

	/**
	 * Get if the released limit amount is to be included in calculations for
	 * total released limit amount for a collateral.
	 * 
	 * @return boolean
	 */
	public boolean getIsReleasedLimitAmountIncluded() {
		String isIncludedStr = getEBIsReleasedLimitAmountIncluded();
		return ((isIncludedStr == null) || isIncludedStr.equals(ICMSConstant.TRUE_VALUE));
	}

	/**
	 * Set if the released limit amount is to be included in calculations for
	 * total released limit amount for a collateral.
	 * 
	 * @param isIncluded - boolean
	 */
	public void setIsReleasedLimitAmountIncluded(boolean isIncluded) {
		setEBIsReleasedLimitAmountIncluded(isIncluded ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Get released limit amount for asset based general charge security.
	 * 
	 * @return Amount
	 */
	public Amount getReleasedLimitAmount() {
		return ((getEBReleasedLimitAmount() == null) || (getLimitCcyCode() == null) || (getLimitCcyCode().length() == 0)) ? null
				: new Amount(getEBReleasedLimitAmount(), new CurrencyCode(getLimitCcyCode()));
	}

	/**
	 * Set released limit amount for asset based general charge security.
	 * 
	 * @param releasedLimitAmt of type Amount
	 */
	public void setReleasedLimitAmount(Amount releasedLimitAmt) {
		if ((releasedLimitAmt != null) && (releasedLimitAmt.getAmountAsBigDecimal() != null)
				&& (releasedLimitAmt.getCurrencyCode() != null)) {
			setEBReleasedLimitAmount(releasedLimitAmt.getAmountAsBigDecimal());
			setLimitCcyCode(releasedLimitAmt.getCurrencyCode());
		}
	}

	/**
	 * Get limit priority ranking within a collateral.
	 * 
	 * @return int
	 */
	public int getPriorityRanking() {
		return getEBPriorityRanking() == null ? ICMSConstant.INT_INVALID_VALUE : getEBPriorityRanking().intValue();
	}

	/**
	 * Set limit priority ranking within a collateral.
	 * 
	 * @param priorityRanking of type int
	 */
	public void setPriorityRanking(int priorityRanking) {
		setEBPriorityRanking(priorityRanking == ICMSConstant.INT_INVALID_VALUE ? null : new Integer(priorityRanking));
	}

	/**
	 * Gets the CoBorrowerLimitID
	 * 
	 * @return long
	 */
	public long getCoBorrowerLimitID() {
		if (getEBCoBorrowerLimitID() != null) {
			return getEBCoBorrowerLimitID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Sets the CoBorrowerLimitID
	 * 
	 * @param co borrower LimitID
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID) {
		setEBCoBorrowerLimitID(coBorrowerLimitID == ICMSConstant.LONG_INVALID_VALUE ? null
				: new Long(coBorrowerLimitID));

	}

	/**
	 * Gets the LimitID
	 * 
	 * @return long
	 */
	public long getLimitID() {
		if (getEBLimitID() != null) {
			return getEBLimitID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Sets the LimitID
	 * 
	 * @param Limit ID
	 */
	public void setLimitID(long limitID) {
		setEBLimitID(limitID == ICMSConstant.LONG_INVALID_VALUE ? null : new Long(limitID));
	}

	public abstract long getCmsLimitProfileId();

	public Amount getDrawAmount() {
		if (getDrawAmountBigDecimal() != null) {
			Amount drawAmount = new Amount(getDrawAmountBigDecimal(), new CurrencyCode(getDrawAmountCurrencyCode()));
			return drawAmount;
		}
		return null;
	}

	public abstract double getDrawAmountPercentage();

	public Amount getPledgeAmount() {
		if (getPledgeAmountBigDecimal() != null) {
			Amount pledgeAmount = new Amount(getPledgeAmountBigDecimal(), new CurrencyCode(
					getPledgeAmountCurrencyCode()));

			return pledgeAmount;
		}
		return null;
	}

	public abstract double getPledgeAmountPercentage();

	public abstract void setCmsLimitProfileId(long cmsLimitProfileId);

	public void setDrawAmount(Amount drawAmount) {
		if (drawAmount != null) {
			setDrawAmountBigDecimal(drawAmount.getAmountAsBigDecimal());
			setDrawAmountCurrencyCode(drawAmount.getCurrencyCode());
		}
		else {
			setDrawAmountBigDecimal(null);
			setDrawAmountCurrencyCode(null);
		}
	}

	public abstract void setDrawAmountPercentage(double drawAmountPercentage);

	public void setPledgeAmount(Amount pledgeAmount) {
		if (pledgeAmount != null) {
			setPledgeAmountBigDecimal(pledgeAmount.getAmountAsBigDecimal());
			setPledgeAmountCurrencyCode(pledgeAmount.getCurrencyCode());
		}
		else {
			setPledgeAmountBigDecimal(null);
			setPledgeAmountCurrencyCode(null);
		}
	}

	public abstract void setPledgeAmountPercentage(double pledgeAmountPercentage);

	public abstract BigDecimal getPledgeAmountBigDecimal();

	public abstract BigDecimal getDrawAmountBigDecimal();

	public abstract void setPledgeAmountBigDecimal(BigDecimal pledgeAmountBigDecimal);

	public abstract void setDrawAmountBigDecimal(BigDecimal drawAmountBigDecimal);

	public abstract void setPledgeAmountCurrencyCode(String pledgeAmountCurrencyCode);

	public abstract void setDrawAmountCurrencyCode(String drawAmountCurrencyCode);

	public abstract String getPledgeAmountCurrencyCode();

	public abstract String getDrawAmountCurrencyCode();

	public abstract Long getEBChargeID();

	public abstract void setEBChargeID(Long eBChargeID);

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract String getEBIsCollateralPool();

	public abstract void setEBIsCollateralPool(String eBIsCollateralPool);

	public abstract String getEBIsSpecificTrx();

	public abstract void setEBIsSpecificTrx(String eBIsSpecificTrx);

	public abstract Double getEBCashReqPct();

	public abstract void setEBCashReqPct(Double eBCashReqPct);

	public abstract String getLimitCcyCode();

	public abstract void setLimitCcyCode(String ccyCode);

	public abstract String getEBIsAppliedLimitAmountIncluded();

	public abstract void setEBIsAppliedLimitAmountIncluded(String eBIsAppliedLimitAmtIncluded);

	public abstract BigDecimal getEBAppliedLimitAmount();

	public abstract void setEBAppliedLimitAmount(BigDecimal eBAppliedLimitAmt);

	public abstract String getEBIsReleasedLimitAmountIncluded();

	public abstract void setEBIsReleasedLimitAmountIncluded(String eBReleasedLimitAmtIncluded);

	public abstract BigDecimal getEBReleasedLimitAmount();

	public abstract void setEBReleasedLimitAmount(BigDecimal eBReleasedLimitAmt);

	public abstract Integer getEBPriorityRanking();

	public abstract void setEBPriorityRanking(Integer eBPriorityRanking);

	// CR035
	public abstract Long getEBLimitID();

	public abstract void setEBLimitID(Long eBLimitID);

	public abstract String getCoBorrowerLEID();

	public abstract void setCoBorrowerLEID(String coBorrowerLEID);

	public abstract long getCoBorrowerSubProfileID();

	public abstract void setCoBorrowerSubProfileID(long coBorrowerSubProfileID);

	public abstract Long getEBCoBorrowerLimitID();

	public abstract void setEBCoBorrowerLimitID(Long eBCoBorrowerLimitID);

	public abstract String getCustomerCategory();

	public abstract void setCustomerCategory(String customerCategory);
	

	public abstract String getLmtSecurityCoverage();

	public abstract void setLmtSecurityCoverage(String lmtSecurityCoverage);
	
	public abstract String getCpsSecurityId();

	public abstract void setCpsSecurityId(String cpsSecurityId);

	//

	/**
	 * Get the collateral limit map business object.
	 * 
	 * @return collateral limit map
	 */
	public ICollateralLimitMap getValue() {
		OBCollateralLimitMap limitMap = new OBCollateralLimitMap();
		// get the base ccy for the limit to be used to init applied and
		// released amt
		AccessorUtil.copyValue(this, limitMap, (new String[] { "subLimit" }));
		// filterDeletedSL(limitMap);
		return limitMap;
	}

	/**
	 * Set the limit map to this entity.
	 * 
	 * @param limitMap is of type ICollateralLimitMap
	 */
	public void setValue(ICollateralLimitMap limitMap) {
		// AccessorUtil.copyValue (limitMap, this, EXCLUDE_METHOD_UPDATE);
		this.setIsCollateralPool(limitMap.getIsCollateralPool());
		this.setIsSpecificTrx(limitMap.getIsSpecificTrx());
		this.setCashReqPct(limitMap.getCashReqPct());
		this.setAppliedLimitAmount(limitMap.getAppliedLimitAmount());
		this.setReleasedLimitAmount(limitMap.getReleasedLimitAmount());
		this.setIsAppliedLimitAmountIncluded(limitMap.getIsAppliedLimitAmountIncluded());
		this.setIsReleasedLimitAmountIncluded(limitMap.getIsReleasedLimitAmountIncluded());
		this.setPriorityRanking(limitMap.getPriorityRanking());
		this.setPledgeAmount(limitMap.getPledgeAmount());
		this.setPledgeAmountPercentage(limitMap.getPledgeAmountPercentage());
		this.setDrawAmount(limitMap.getDrawAmount());
		this.setDrawAmountPercentage(limitMap.getDrawAmountPercentage());
		this.setSCIStatus(limitMap.getSCIStatus());
		this.setLmtSecurityCoverage(limitMap.getLmtSecurityCoverage());
		this.setCpsSecurityId(limitMap.getCpsSecurityId());
		// this.setSubLimitRef(limitMap.getSubLimit(), false);
	}

	/**
	 * Create the collateral limit map record.
	 * 
	 * @param limitMap the collateral limit map
	 * @return Long
	 * @throws CreateException on error creating the limit map
	 */
	public Long ejbCreate(ICollateralLimitMap limitMap) throws CreateException {
		try {
			String chargeID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_LIMIT_MAP, true);
			AccessorUtil.copyValue(limitMap, this, EXCLUDE_METHOD_CREATE);
			setEBChargeID(new Long(chargeID));
			return null;
		}
		catch (Exception e) {
			throw new CreateException("failed to generate sequence [" + ICMSConstant.SEQUENCE_COL_LIMIT_MAP
					+ "]; nested exception is " + e);
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param limitMap is of type ICollateralLimitMap
	 */
	public void ejbPostCreate(ICollateralLimitMap limitMap) {
		// this.setSubLimitRef(limitMap.getSubLimit(), true);
	}

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

	public abstract void setSubLimitCMR(Collection slColl);

	public abstract Collection getSubLimitCMR();

	public void setSubLimit(ISubLimit[] subLimitArray) {
	}

	public ISubLimit[] getSubLimit() {
		Collection slColl = getSubLimitCMR();
		if (slColl == null) {
			return null;
		}
		Iterator iterator = slColl.iterator();
		ArrayList slList = new ArrayList();
		while (iterator.hasNext()) {
			ISubLimit sl = ((EBSubLimitLocal) iterator.next()).getValue();
			if (ICMSConstant.STATE_DELETED.equals(sl)) {
				continue;
			}
			slList.add(sl);
		}
		return (OBSubLimit[]) slList.toArray(new OBSubLimit[slList.size()]);
	}

	protected EBSubLimitLocalHome getSubLimitLocal() {
		EBSubLimitLocalHome home = (EBSubLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SUB_LIMIT_LOCAL_JNDI, EBSubLimitLocalHome.class.getName());
		return home;
	}

	private void setSubLimitRef(ISubLimit[] slArray, boolean isAdd) {
		try {
			Collection existingList = getSubLimitCMR();
			if (isAdd || existingList.isEmpty()) {
				existingList = addSubLimit(Arrays.asList(slArray), existingList);
				return;
			}
			else {
				if ((slArray == null) || (slArray.length == 0)) {
					removeSubLimit(existingList);
					return;
				}
				// put existing list into hashmap - excludes deleted values
				HashMap existingMap = new HashMap(existingList.size());
				Iterator existingIterator = existingList.iterator();
				while (existingIterator.hasNext()) {
					EBSubLimitLocal slLocal = (EBSubLimitLocal) existingIterator.next();
					ISubLimit sl = slLocal.getValue();
					if (!ICMSConstant.STATE_DELETED.equals(sl.getStatus())) {
						existingMap.put(new Long(sl.getCommonRef()), slLocal);
					}
				}
				ArrayList newList = new ArrayList();

				// compare supplier with existing list
				for (int i = 0; i < slArray.length; i++) {
					ISubLimit sl = slArray[i];
					Long commonRefID = new Long(sl.getCommonRef());
					EBSubLimitLocal theExistingEjb = (EBSubLimitLocal) existingMap.get(commonRefID);
					if (theExistingEjb != null) {
						theExistingEjb.setValue((OBSubLimit) sl);
						existingMap.remove(commonRefID);
					}
					else {
						newList.add(sl);
					}
				}
				existingList = addSubLimit(newList, existingList);
				EBSubLimitLocal[] deletedList = (EBSubLimitLocal[]) existingMap.values()
						.toArray(new EBSubLimitLocal[0]);
				removeSubLimit(Arrays.asList(deletedList));
			}
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	private void removeSubLimit(Collection existingList) throws VersionMismatchException {
		if ((existingList == null) || existingList.isEmpty()) {
			return;
		}
		Iterator i = existingList.iterator();
		while (i.hasNext()) {
			EBSubLimitLocal theEjb = (EBSubLimitLocal) i.next();
			removeSubLimit(theEjb);
		}
	}

	private void removeSubLimit(EBSubLimitLocal anEjb) throws VersionMismatchException {
		OBSubLimit sl = (OBSubLimit) anEjb.getValue();
		sl.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(sl);
	}

	private Collection addSubLimit(List newList, Collection existingList) throws CreateException {
		if ((newList == null) || newList.isEmpty()) {
			return existingList;
		}
		if (existingList == null) {
			existingList = new ArrayList();
		}
		EBSubLimitLocalHome theEjbHome = getSubLimitLocal();
		Iterator i = newList.iterator();
		while (i.hasNext()) {
			OBSubLimit sl = (OBSubLimit) i.next();
			existingList.add(theEjbHome.create(sl));
		}
		return existingList;
	}

	private void filterDeletedSL(OBCollateralLimitMap limitMap) {
		ISubLimit[] slArray = limitMap.getSubLimit();
		if (slArray != null) {
			List slList = new ArrayList();
			for (int index = 0; index < slArray.length; index++) {
				if (ICMSConstant.STATE_DELETED.equals(slArray[index].getStatus())) {
					continue;
				}
				else {
					slList.add(slArray[index]);
				}
			}
			limitMap.setSubLimit((ISubLimit[]) slList.toArray(new ISubLimit[slList.size()]));
		}
	}

	public abstract String getSCILimitID();

	public abstract void setSCILimitID(String sciLimitID);

	public abstract String getLimitType();

	public abstract void setLimitType(String limitType);

	public abstract long getSCISysGenID();

	public abstract void setSCISysGenID(long sciSysGenID);

	public abstract long getSCILimitProfileID();

	public abstract void setSCILimitProfileID(long sciLimitProfileID);

	public abstract long getSCISubProfileID();

	public abstract void setSCISubProfileID(long sciSubProfileID);

	public abstract String getSCILegalEntityID();

	public abstract void setSCILegalEntityID(String sciLegalEntityID);

	public abstract String getSCISecurityID();

	public abstract void setSCISecurityID(String sciSecurityID);

	public abstract long getSCIPledgorID();

	public abstract void setSCIPledgorID(long sciPledgorID);

	public abstract String getSCIStatus();

	public abstract void setSCIStatus(String sciStatus);

	public abstract String getSCICoBorrowerLimitID();

	public abstract void setSCICoBorrowerLimitID(String sCICoBorrowerLimitID);

	public abstract String getSourceID();

	public abstract void setSourceID(String sourceID);

	public abstract Character getChangeIndicator();

	public abstract void setChangeIndicator(Character changeIndicator);
}
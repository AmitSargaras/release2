/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBValuationBean.java,v 1.21 2005/09/30 02:31:40 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for collateral valuation entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2005/09/30 02:31:40 $ Tag: $Name: $
 */
public abstract class EBValuationBean implements IValuation, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the valuation. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getValuationID", "getCollateralID" };

	/**
	 * Get valuation id.
	 * 
	 * @return long
	 */
	public long getValuationID() {
		if (getEBValuationID() != null) {
			return getEBValuationID().longValue();
		}
		else {
			return ICMSConstant.LONG_MIN_VALUE;
		}
	}

	/**
	 * Set valuation id.
	 * 
	 * @param valuationID of type long
	 */
	public void setValuationID(long valuationID) {
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
			return ICMSConstant.LONG_MIN_VALUE;
		}
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
	}

	/**
	 * Get current market value (CMV).
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		if (getEBCMV() != null) {
			return new Amount(getEBCMV().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set current market value.
	 * 
	 * @param cMV is of type Amount
	 */
	public void setCMV(Amount cMV) {
		if (cMV != null) {
			setEBCMV(new Double(cMV.getAmountAsDouble()));
		}
		else {
			setEBCMV(null);
		}
	}

	/**
	 * Get Forced Sale Value (FSV).
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		if (getEBFSV() != null) {
			return new Amount(getEBFSV().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * set forced sale value.
	 * 
	 * @param fSV is of type Amount
	 */
	public void setFSV(Amount fSV) {
		if (fSV != null) {
			setEBFSV(new Double(fSV.getAmountAsDouble()));
		}
		else {
			setEBFSV(null);
		}
	}

	/**
	 * Get value before margin.
	 * 
	 * @return Amount
	 */
	public Amount getBeforeMarginValue() {
		if (getEBBeforeMarginValue() != null) {
			return new Amount(getEBBeforeMarginValue().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	/**
	 * Set value before margin.
	 * 
	 * @param beforeMarginValue of type Amount
	 */
	public void setBeforeMarginValue(Amount beforeMarginValue) {
		if (beforeMarginValue != null) {
			setEBBeforeMarginValue(new Double(beforeMarginValue.getAmountAsDouble()));
		}
		else {
			setEBBeforeMarginValue(null);
		}
	}

	/**
	 * Get non standard revaluation frequency
	 * 
	 * @return int
	 */
	public int getNonRevaluationFreq() {
		return getEBNonRevaluationFreq() == null ? ICMSConstant.INT_INVALID_VALUE : getEBNonRevaluationFreq()
				.intValue();
	}

	/**
	 * Set non standard revaluation frequency
	 * 
	 * @param nonRevaluationFreq of type int
	 */
	public void setNonRevaluationFreq(int nonRevaluationFreq) {
		setEBNonRevaluationFreq(nonRevaluationFreq == ICMSConstant.INT_INVALID_VALUE ? null : new Integer(
				nonRevaluationFreq));
	}

	public abstract Long getEBValuationID();

	public abstract void setEBValuationID(Long eBValuationID);

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Double getEBCMV();

	public abstract void setEBCMV(Double eBCMV);

	public abstract Double getEBFSV();

	public abstract void setEBFSV(Double eBFSV);

	public abstract Double getEBBeforeMarginValue();

	public abstract void setEBBeforeMarginValue(Double eBBeforeMarginValue);

	public abstract Integer getEBNonRevaluationFreq();

	public abstract void setEBNonRevaluationFreq(Integer eBNonRevaluationFreq);

	public Date getFSVEvaluationDate() {
		return null;
	}

	public void setFSVEvaluationDate(Date fSVEvaluationDate) {
	}

	public Date getRevaluationDate() {
		return null;
	}

	public void setRevaluationDate(Date revaluationDate) {
	}

	public Amount getAfterMarginValue() {
		return null;
	}

	public void setAfterMarginValue(Amount afterMarginValue) {
	}

	public Date getLastEvaluationDate() {
		return null;
	}

	public void setLastEvaluationDate(Date lastEvaluationDate) {
	}

	public Date getNextRevaluationDate() {
		return null;
	}

	public void setNextRevaluationDate(Date nextRevaluationDate) {
	}

	// start for ValuationIntoGCMS
	public abstract String getSourceId();

	public abstract void setSourceId(String sourceId);

	public abstract String getSourceType();

	public abstract void setSourceType(String sourceType);

	public abstract String getValuationType();

	public abstract void setValuationType(String valuationType);

	public abstract Date getReservePriceDate();

	public abstract void setReservePriceDate(Date reservePriceDate);

	public abstract Date getEvaluationDateFSV();

	public abstract void setEvaluationDateFSV(Date evaluationDateFSV);

	public abstract Double getEBReservePrice();

	public abstract void setEBReservePrice(Double eBReservePrice);

	public abstract Double getEBOlv();

	public abstract void setEBOlv(Double eBOlv);

	public abstract Double getRemainusefullife();

	public abstract void setRemainusefullife(Double remainusefullife);

	public abstract String getValuationbasis();

	public abstract void setValuationbasis(String valuationbasis);

	public Amount getReservePrice() {
		if (getEBReservePrice() != null) {
			return new Amount(getEBReservePrice().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	public void setReservePrice(Amount reservePrice) {
		if (reservePrice != null) {
			setEBReservePrice(new Double(reservePrice.getAmountAsDouble()));
		}
		else {
			setEBReservePrice(null);
		}
	}

	public Amount getOlv() {
		if (getEBOlv() != null) {
			return new Amount(getEBOlv().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	public void setOlv(Amount olv) {
		if (olv != null) {
			setEBOlv(new Double(olv.getAmountAsDouble()));
		}
		else {
			setEBOlv(null);
		}
	}

	// end of ValuationIntoGCMS

	/**
	 * Get the collateral valuation business object.
	 * 
	 * @return collateral valuation
	 */
	public IValuation getValue() {
		OBValuation valuation = new OBValuation();
		AccessorUtil.copyValue(this, valuation);
		return valuation;
	}

	/**
	 * Set the collateral valuation to this entity.
	 * 
	 * @param valuation is of type IValuation
	 */
	public void setValue(IValuation valuation) {
		AccessorUtil.copyValue(valuation, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param valuation of type IValuation
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IValuation valuation) throws CreateException {
		try {
			AccessorUtil.copyValue(valuation, this, EXCLUDE_METHOD);

			String valID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_VALUATION, true);
			setEBValuationID(new Long(valID));

			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param valuation of type IValuation
	 */
	public void ejbPostCreate(IValuation valuation) {
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

	public abstract String getValuerName();

	public abstract void setValuerName(String valuerName);

	public abstract Date getValuationDate();

	public abstract void setValuationDate(Date valuationDate);

	public abstract Date getUpdateDate();

	public abstract void setUpdateDate(Date updateDate);

	public abstract String getCurrencyCode();

	public abstract void setCurrencyCode(String currencyCode);

	public abstract int getRevaluationFreq();

	public abstract void setRevaluationFreq(int revaluationFreq);

	public abstract String getRevaluationFreqUnit();

	public abstract void setRevaluationFreqUnit(String revaluationFreqUnit);

	public abstract String getNonRevaluationFreqUnit();

	public abstract void setNonRevaluationFreqUnit(String nonRevaluationFreqUnit);

	public abstract String getComments();

	public abstract void setComments(String comments);

	public abstract Long getLosValuationId();

	public abstract void setLosValuationId(Long losValuationId);
}
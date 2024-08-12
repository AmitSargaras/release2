/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBStockBean.java,v 1.12 2005/06/07 03:24:55 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * For EBStockBean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2005/06/07 03:24:55 $ Tag: $Name: $
 */
public abstract class EBStockBean extends EBGeneralChargeSubTypeBean implements EntityBean, IStock {

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create stock. */
	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getAssetGCStockID" };

	/** A list of methods to be excluded during update stock. */
	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getAssetGCStockID" };

	public abstract Long getEBAssetGCStockID();

	public abstract void setEBAssetGCStockID(Long assetGCStockID);

	public abstract BigDecimal getEBRecoverableAmount();

	public abstract void setEBRecoverableAmount(BigDecimal recoverableAmt);

	public abstract String getEBRecoverableAmountCcyCode();

	public abstract void setEBRecoverableAmountCcyCode(String recoverableAmtCcyCode);

	public abstract Double getEBFinishGoodsAmt();

	public abstract void setEBFinishGoodsAmt(Double finishedGoodsAmt);

	public abstract Double getEBFinishGoodsMargin();

	public abstract void setEBFinishGoodsMargin(Double margin);

	public abstract Double getEBGoodsTransitAmt();

	public abstract void setEBGoodsTransitAmt(Double goodsTransitAmt);

	public abstract Double getEBGoodsTransitMargin();

	public abstract void setEBGoodsTransitMargin(Double margin);

	public abstract Double getEBOtherMerchandiseAmt();

	public abstract void setEBOtherMerchandiseAmt(Double otherMerchandiseAmt);

	public abstract Double getEBOtherMerchandiseMargin();

	public abstract void setEBOtherMerchandiseMargin(Double margin);

	public abstract Double getEBRawMaterialAmt();

	public abstract void setEBRawMaterialAmt(Double rawMaterialAmt);

	public abstract Double getEBRawMaterialMargin();

	public abstract void setEBRawMaterialMargin(Double margin);

	public abstract Double getEBStoresSparesAmt();

	public abstract void setEBStoresSparesAmt(Double storesSparesAmt);

	public abstract Double getEBStoresSparesMargin();

	public abstract void setEBStoresSparesMargin(Double margin);

	public abstract Double getEBWorkProgressAmt();

	public abstract void setEBWorkProgressAmt(Double workProgressAmt);

	public abstract Double getEBWorkProgressMargin();

	public abstract void setEBWorkProgressMargin(Double margin);

	public abstract Double getEBCreditorAmt();

	public abstract void setEBCreditorAmt(Double creditorAmt);

	public abstract String getEBPhysicalInspectionDone();

	public abstract void setEBPhysicalInspectionDone(String physicalInspectionDone);

	public abstract Integer getEBPhysicalInspectionFreq();

	public abstract void setEBPhysicalInspectionFreq(Integer ebPhysicalInspectionFreq);

	public String getID() {
		return null;
	}

	public long getAssetGCStockID() {
		return getEBAssetGCStockID().longValue();
	}

	public void setAssetGCStockID(long assetGChargeStockID) {
		setEBAssetGCStockID(new Long(assetGChargeStockID));
	}

	public Amount getRecoverableAmount() {
		return ((getEBRecoverableAmount() == null) || (getEBRecoverableAmountCcyCode() == null)) ? null : new Amount(
				getEBRecoverableAmount(), new CurrencyCode(getEBRecoverableAmountCcyCode()));
	}

	public void setRecoverableAmount(Amount recoverableAmt) {
		if ((recoverableAmt != null) && (recoverableAmt.getAmountAsBigDecimal() != null)
				&& (recoverableAmt.getCurrencyCode() != null)) {
			boolean isForexError = GeneralChargeUtil.isForexErrorAmount(recoverableAmt);
			setEBRecoverableAmount((isForexError) ? null : recoverableAmt.getAmountAsBigDecimal());
			setEBRecoverableAmountCcyCode((isForexError) ? null : recoverableAmt.getCurrencyCode());
		}
	}

	public Amount getFinishGoodsAmt() {
		return ((getEBFinishGoodsAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBFinishGoodsAmt().doubleValue(), getValuationCurrency());
	}

	public void setFinishGoodsAmt(Amount finishGoodsAmt) {
		setEBFinishGoodsAmt((finishGoodsAmt == null) ? null : new Double(finishGoodsAmt.getAmountAsDouble()));
	}

	public double getFinishGoodsMargin() {
		return (getEBFinishGoodsMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBFinishGoodsMargin()
				.doubleValue();
	}

	public void setFinishGoodsMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBFinishGoodsMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getGoodsTransitAmt() {
		return ((getEBGoodsTransitAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBGoodsTransitAmt().doubleValue(), getValuationCurrency());
	}

	public void setGoodsTransitAmt(Amount goodsTransitAmt) {
		setEBGoodsTransitAmt((goodsTransitAmt == null) ? null : new Double(goodsTransitAmt.getAmountAsDouble()));
	}

	public double getGoodsTransitMargin() {
		return (getEBGoodsTransitMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBGoodsTransitMargin()
				.doubleValue();
	}

	public void setGoodsTransitMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBGoodsTransitMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getOtherMerchandiseAmt() {
		return ((getEBOtherMerchandiseAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBOtherMerchandiseAmt().doubleValue(), getValuationCurrency());
	}

	public void setOtherMerchandiseAmt(Amount otherMerchandiseAmt) {
		setEBOtherMerchandiseAmt((otherMerchandiseAmt == null) ? null : new Double(otherMerchandiseAmt
				.getAmountAsDouble()));
	}

	public double getOtherMerchandiseMargin() {
		return (getEBOtherMerchandiseMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE
				: getEBOtherMerchandiseMargin().doubleValue();
	}

	public void setOtherMerchandiseMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBOtherMerchandiseMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getRawMaterialAmt() {
		return ((getEBRawMaterialAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBRawMaterialAmt().doubleValue(), getValuationCurrency());
	}

	public void setRawMaterialAmt(Amount rawMaterialAmt) {
		setEBRawMaterialAmt((rawMaterialAmt == null) ? null : new Double(rawMaterialAmt.getAmountAsDouble()));
	}

	public double getRawMaterialMargin() {
		return (getEBRawMaterialMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBRawMaterialMargin()
				.doubleValue();
	}

	public void setRawMaterialMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBRawMaterialMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getStoresSparesAmt() {
		return ((getEBStoresSparesAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBStoresSparesAmt().doubleValue(), getValuationCurrency());
	}

	public void setStoresSparesAmt(Amount storesSparesAmt) {
		setEBStoresSparesAmt((storesSparesAmt == null) ? null : new Double(storesSparesAmt.getAmountAsDouble()));
	}

	public double getStoresSparesMargin() {
		return (getEBStoresSparesMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBStoresSparesMargin()
				.doubleValue();
	}

	public void setStoresSparesMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBStoresSparesMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getWorkProgressAmt() {
		return ((getEBWorkProgressAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBWorkProgressAmt().doubleValue(), getValuationCurrency());
	}

	public void setWorkProgressAmt(Amount workProgressAmt) {
		setEBWorkProgressAmt((workProgressAmt == null) ? null : new Double(workProgressAmt.getAmountAsDouble()));
	}

	public double getWorkProgressMargin() {
		return (getEBWorkProgressMargin() == null) ? ICMSConstant.DOUBLE_INVALID_VALUE : getEBWorkProgressMargin()
				.doubleValue();
	}

	public void setWorkProgressMargin(double margin) {
		// to cater for both -1 and -0.01 invalid values
		setEBWorkProgressMargin((margin < 0) ? null : new Double(margin));
	}

	public Amount getCreditorAmt() {
		return ((getEBCreditorAmt() == null) || (getValuationCurrency() == null)) ? null : new Amount(
				getEBCreditorAmt().doubleValue(), getValuationCurrency());
	}

	public void setCreditorAmt(Amount creditorAmt) {
		setEBCreditorAmt((creditorAmt == null) ? null : new Double(creditorAmt.getAmountAsDouble()));
	}

	public boolean getPhysicalInspectionDone() {
		String isIns = getEBPhysicalInspectionDone();
		return ((isIns != null) && isIns.equals(ICMSConstant.TRUE_VALUE));
	}

	public void setPhysicalInspectionDone(boolean physicalInspectionDone) {
		setEBPhysicalInspectionDone((physicalInspectionDone) ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	public String[] getStockType() {
		return null;
	}

	/**
	 * Get Physical Inspection Frequency
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq() {
		return (getEBPhysicalInspectionFreq() == null) ? ICMSConstant.INT_INVALID_VALUE : getEBPhysicalInspectionFreq()
				.intValue();
	}

	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		setEBPhysicalInspectionFreq((physicalInspectionFreq == ICMSConstant.INT_INVALID_VALUE) ? null : new Integer(
				physicalInspectionFreq));
	}

	/**
	 * Sets the stock object.
	 * @param stock - IStock
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IStock stock) throws ConcurrentUpdateException {
		AccessorUtil.copyValue(stock, this, EXCLUDE_METHOD_UPDATE);
	}

	/**
	 * Return a stock object
	 * @return IStock - the object containing the stock object
	 */
	public IStock getValue() {
		OBStock value = new OBStock();
		AccessorUtil.copyValue(this, value, null);
		return value;
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param stock of type ICollateral
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IStock stock) throws CreateException {
		try {
			String assetGCStockID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_ASSET_GC_STOCK, true);
			AccessorUtil.copyValue(stock, this, EXCLUDE_METHOD_CREATE);
			setEBAssetGCStockID(new Long(assetGCStockID));
			setStatus(ICMSConstant.STATE_ACTIVE);
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param stock of type IStock
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IStock stock) throws CreateException {
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

    public abstract Date getValuationDate();

    public abstract Date getRevaluationDate();

    public abstract String getRevalFreqUnit();

    public abstract void setValuationDate(Date valuationDate);

    public abstract void setRevaluationDate(Date revaluationDate);

    public abstract void setRevalFreqUnit(String revalFreqUnit);

    public abstract String getStockID();

    public abstract void setStockID(String stockID);

    public abstract Date getLastPhysicalInspectDate();

    public abstract void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

    public abstract Date getNextPhysicalInspectDate();

    public abstract void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

    public abstract String getPhysicalInspectionFreqUnit();

    public abstract void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

    public abstract String getAddress();

    public abstract void setAddress(String address);

    public abstract String getStatus();

    public abstract String getValuerName();

    public abstract void setValuerName(String valuerName);

    public abstract String getValuationCurrency();

    public abstract void setValuationCurrency(String valuationCurrency);
}

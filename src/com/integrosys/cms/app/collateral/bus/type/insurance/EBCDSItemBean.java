/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/EBCDSItemBean.java,v 1.1 2005/09/29 09:39:37 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for credit default swaps entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:39:37 $ Tag: $Name: $
 */
public abstract class EBCDSItemBean implements ICDSItem, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the valuation. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getCdsItemID", "getRefID" };

	/**
	 * Get CDS Item id.
	 * 
	 * @return long
	 */
	public long getCdsItemID() {
		if (getEBCdsItemID() != null) {
			return getEBCdsItemID().longValue();
		}
		else {
			return ICMSConstant.LONG_MIN_VALUE;
		}
	}

	/**
	 * Set Credit Default Swaps Item id.
	 * 
	 * @param cdsItemID of type long
	 */
	public void setCdsItemID(long cdsItemID) {
	}

	/**
	 * Get tenor
	 * 
	 * @return int
	 */
	public int getTenor() {
		if (getEBTenor() != null) {
			return getEBTenor().intValue();
		}
		else {
			return ICMSConstant.INT_INVALID_VALUE;
		}
	}

	/**
	 * Set tenor
	 * 
	 * @param tenor of type int
	 */
	public void setTenor(int tenor) {
		if (tenor != ICMSConstant.INT_INVALID_VALUE) {
			setEBTenor(new Integer(tenor));
		}
		else {
			setEBTenor(null);
		}
	}

	/**
	 * Get Notional Hedge Amount.
	 * 
	 * @return Amount
	 */
	public Amount getNotionalHedgeAmt() {
		if (getEBNotionalHedgeAmt() != null) {
			return new Amount(getEBNotionalHedgeAmt().doubleValue(), getEBValuationCurrency());
		}
		else {
			return null;
		}
	}

	/**
	 * Set Notional Hedge Amount
	 * 
	 * @param notionalHedgeAmt is of type Amount
	 */
	public void setNotionalHedgeAmt(Amount notionalHedgeAmt) {
		if (notionalHedgeAmt != null) {
			setEBNotionalHedgeAmt(new Double(notionalHedgeAmt.getAmountAsDouble()));
		}
		else {
			setEBNotionalHedgeAmt(null);
		}
	}

	/**
	 * Get Dealt Price.
	 * 
	 * @return Amount
	 */
	public Amount getDealtPrice() {
		if (getEBDealtPrice() != null) {
			return new Amount(getEBDealtPrice().doubleValue(), getEBValuationCurrency());
		}
		else {
			return null;
		}
	}

	/**
	 * set Dealt Price
	 * 
	 * @param dealtPrice is of type Amount
	 */
	public void setDealtPrice(Amount dealtPrice) {
		if (dealtPrice != null) {
			setEBDealtPrice(new Double(dealtPrice.getAmountAsDouble()));
		}
		else {
			setEBDealtPrice(null);
		}
	}

	/**
	 * Get Par Value.
	 * 
	 * @return Amount
	 */
	public Amount getParValue() {
		if (getEBParValue() != null) {
			return new Amount(getEBParValue().doubleValue(), getEBValuationCurrency());
		}
		else {
			return null;
		}
	}

	/**
	 * Set Par Value
	 * 
	 * @param parValue of type Amount
	 */
	public void setParValue(Amount parValue) {
		if (parValue != null) {
			setEBParValue(new Double(parValue.getAmountAsDouble()));
		}
		else {
			setEBParValue(null);
		}
	}

	/**
	 * Get Decline Market Value.
	 * 
	 * @return Amount
	 */
	public Amount getDeclineMarketValue() {
		if (getEBDeclineMarketValue() != null) {
			return new Amount(getEBDeclineMarketValue().doubleValue(), getEBValuationCurrency());
		}
		else {
			return null;
		}
	}

	/**
	 * Set Decline Market Value
	 * 
	 * @param declineMarketValue of type Amount
	 */
	public void setDeclineMarketValue(Amount declineMarketValue) {
		if (declineMarketValue != null) {
			setEBDeclineMarketValue(new Double(declineMarketValue.getAmountAsDouble()));
		}
		else {
			setEBDeclineMarketValue(null);
		}
	}

	/**
	 * Get Residual Maturity Field
	 * 
	 * @return Amount
	 */
	public Amount getResidualMaturityField() {
		if (getEBResidualMaturityField() != null) {
			return new Amount(getEBResidualMaturityField().doubleValue(), getEBValuationCurrency());
		}
		else {
			return null;
		}
	}

	/**
	 * Set Residual Maturity Field
	 * 
	 * @param residualMaturityField of type Amount
	 */
	public void setResidualMaturityField(Amount residualMaturityField) {
		if (residualMaturityField != null) {
			setEBResidualMaturityField(new Double(residualMaturityField.getAmountAsDouble()));
		}
		else {
			setEBResidualMaturityField(null);
		}
	}

	/**
	 * Get if the compliance certificate.
	 * 
	 * @return boolean
	 */
	public boolean getComplianceCert() {
		String isComplianceCert = getEBComplianceCert();
		if ((isComplianceCert != null) && isComplianceCert.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Set if the compliance certificate.
	 * 
	 * @param complianceCert of type boolean
	 */
	public void setComplianceCert(boolean complianceCert) {
		if (complianceCert) {
			setEBComplianceCert(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBComplianceCert(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Get Nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		if (getEBNominalValue() != null) {
			return new Amount(getEBNominalValue().doubleValue(), getEBValuationCurrency());
		}
		else {
			return null;
		}
	}

	/**
	 * Set Nominal Value
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		if (nominalValue != null) {
			setEBNominalValue(new Double(nominalValue.getAmountAsDouble()));
		}
		else {
			setEBNominalValue(null);
		}
	}

	/**
	 * Get margin
	 * 
	 * @return double
	 */
	public double getMargin() {
		if (getEBMargin() != null) {
			return getEBMargin().doubleValue();
		}
		else {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
	}

	/**
	 * Set margin
	 * 
	 * @param margin of type double
	 */
	public void setMargin(double margin) {
		if (margin != ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBMargin(new Double(margin));
		}
		else {
			setEBMargin(null);
		}
	}

	/**
	 * Get valuation details of CDS
	 * 
	 * @return IValuation
	 */
	public IValuation getValuation() {
		IValuation val = new OBValuation();
		val.setValuationDate(getEBValuationDate());
		val.setCurrencyCode(getEBValuationCurrency());
		if (getEBCMV() != null) {
			val.setCMV(new Amount(getEBCMV().doubleValue(), getEBValuationCurrency()));
		}
		else {
			val.setCMV(null);
		}

		if (getEBFSV() != null) {
			val.setFSV(new Amount(getEBFSV().doubleValue(), getEBValuationCurrency()));
		}
		else {
			val.setFSV(null);
		}

		if (getEBNonRevalFreq() != null) {
			val.setNonRevaluationFreq(getEBNonRevalFreq().intValue());
		}
		else {
			val.setNonRevaluationFreq(ICMSConstant.INT_INVALID_VALUE);
		}

		val.setNonRevaluationFreqUnit(getEBNonRevalFreqUnit());
		return val;
	}

	/**
	 * Set valuation details of CDS
	 * 
	 * @param valuation of type IValuation
	 */
	public void setValuation(IValuation valuation) {
		if (valuation != null) {
			setEBValuationDate(valuation.getValuationDate());
			setEBValuationCurrency(valuation.getCurrencyCode());
			if (valuation.getCMV() != null) {
				setEBCMV(new Double(valuation.getCMV().getAmountAsDouble()));
			}
			else {
				setEBCMV(null);
			}

			if (valuation.getFSV() != null) {
				setEBFSV(new Double(valuation.getFSV().getAmountAsDouble()));
			}
			else {
				setEBFSV(null);
			}

			if (valuation.getNonRevaluationFreq() != ICMSConstant.INT_INVALID_VALUE) {
				setEBNonRevalFreq(new Integer(valuation.getNonRevaluationFreq()));
			}
			else {
				setEBNonRevalFreq(null);
			}

			setEBNonRevalFreqUnit(valuation.getNonRevaluationFreqUnit());
		}
	}

	/**
	 * Get Valuation Currency
	 * 
	 * @return String
	 */
	public String getValuationCurrency() {
		return null;
	}

	public abstract Long getEBCdsItemID();

	public abstract void setEBCdsItemID(Long eBCdsItemID);

	public abstract Integer getEBTenor();

	public abstract void setEBTenor(Integer eBTenor);

	public abstract Double getEBNotionalHedgeAmt();

	public abstract void setEBNotionalHedgeAmt(Double eBNotionalHedgeAmt);

	public abstract Double getEBDealtPrice();

	public abstract void setEBDealtPrice(Double eBDealtPrice);

	public abstract Double getEBParValue();

	public abstract void setEBParValue(Double eBParValue);

	public abstract Double getEBDeclineMarketValue();

	public abstract void setEBDeclineMarketValue(Double eBDeclineMarketValue);

	public abstract Double getEBResidualMaturityField();

	public abstract void setEBResidualMaturityField(Double eBResidualMaturityFieldDE);

	public abstract String getEBComplianceCert();

	public abstract void setEBComplianceCert(String setEBComplianceCert);

	public abstract Double getEBNominalValue();

	public abstract void setEBNominalValue(Double eBNominalValue);

	public abstract Double getEBMargin();

	public abstract void setEBMargin(Double eBMargin);

	public abstract Date getEBValuationDate();

	public abstract void setEBValuationDate(Date eBValuationDate);

	public abstract Double getEBCMV();

	public abstract void setEBCMV(Double eBCMV);

	public abstract Double getEBFSV();

	public abstract void setEBFSV(Double eBFSV);

	public abstract Integer getEBNonRevalFreq();

	public abstract void setEBNonRevalFreq(Integer eBNonRevalFreq);

	public abstract String getEBNonRevalFreqUnit();

	public abstract void setEBNonRevalFreqUnit(String eBNonRevalFreqUnit);

	public abstract String getEBValuationCurrency();

	public abstract void setEBValuationCurrency(String eBValuationCurrency);

	public abstract void setStatus(String status);

	/**
	 * Get the Credit Default Swap business object.
	 * 
	 * @return ICDSItem
	 */
	public ICDSItem getValue() {
		OBCDSItem cdsItem = new OBCDSItem();
		AccessorUtil.copyValue(this, cdsItem);
		return cdsItem;
	}

	/**
	 * Set the credit default swaps to this entity.
	 * 
	 * @param cdsItem is of type ICDSItem
	 */
	public void setValue(ICDSItem cdsItem) {
		AccessorUtil.copyValue(cdsItem, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param valuation of type ICDSItem
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICDSItem cdsItem) throws CreateException {
		try {
			String cdsItemID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CDS, true);
			AccessorUtil.copyValue(cdsItem, this, EXCLUDE_METHOD);
			this.setEBCdsItemID(new Long(cdsItemID));
			if (cdsItem.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getCdsItemID());
			}
			else {
				// else maintain this reference id.
				setRefID(cdsItem.getRefID());
			}
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
	 * @param valuation of type ICDSItem
	 */
	public void ejbPostCreate(ICDSItem cdsItem) {
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

    public abstract String getStatus();

    public abstract long getRefID();

    public abstract void setRefID(long refID);

    public abstract String getBankEntity();

    public abstract void setBankEntity(String bankEntity);

    public abstract String getHedgeType();

    public abstract void setHedgeType(String hedgeType);

    public abstract String getHedgeRef();

    public abstract void setHedgeRef(String hedgeRef);

    public abstract String getCdsRef();

    public abstract void setCdsRef(String cdsRef);

    public abstract String getTradeID();

    public abstract void setTradeID(String tradeID);

    public abstract Date getTradeDate();

    public abstract void setTradeDate(Date tradeDate);

    public abstract Date getDealDate();

    public abstract void setDealDate(Date dealDate);

    public abstract Date getStartDate();

    public abstract void setStartDate(Date startDate);

    public abstract Date getCdsMaturityDate();

    public abstract void setCdsMaturityDate(Date cdsMaturityDate);

    public abstract String getTenorUnit();

    public abstract void setTenorUnit(String tenorUnit);

    public abstract String getTradeCurrency();

    public abstract void setTradeCurrency(String tradeCurrency);

    public abstract String getReferenceEntity();

    public abstract void setReferenceEntity(String referenceEntity);

    public abstract String getCdsBookingLoc();

    public abstract void setCdsBookingLoc(String cdsBookingLoc);

    public abstract String getLoanBondBkLoc();

    public abstract void setLoanBondBkLoc(String loanBondBkLoc);

    public abstract String getReferenceAsset();

    public abstract void setReferenceAsset(String referenceAsset);

    public abstract String getIssuer();

    public abstract void setIssuer(String issuer);

    public abstract String getIssuerID();

    public abstract void setIssuerID(String issuerID);

    public abstract String getDetailsIssuer();

    public abstract void setDetailsIssuer(String detailsIssuer);

    public abstract String getSettlement();

    public abstract void setSettlement(String settlement);

    public abstract Date getEventDeterminationDate();

    public abstract void setEventDeterminationDate(Date eventDeterminationDate);
}
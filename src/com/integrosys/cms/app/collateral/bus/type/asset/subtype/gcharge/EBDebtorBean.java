/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBDebtorBean.java,v 1.11 2005/08/12 03:32:36 wltan Exp $
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
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/*
 * This is EBDebtorBean.java class
 * @author $Author: wltan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/08/12 03:32:36 $
 * Tag: $Name:  $
 */

public abstract class EBDebtorBean extends EBValuationDetailsBean implements IDebtor, EntityBean {

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** Default vaule for debtor currency code */

	private String debtorCcyCode = "SGD";

	/**
	 * A list of methods to be excluded during update to the security parameter.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getAssetGCDebtorID" };

	public abstract Long getEBAssetGCDebtorID();

	public abstract void setEBAssetGCDebtorID(Long pk);

	public abstract Integer getEBApplicablePeriod();

	public abstract void setEBApplicablePeriod(Integer ebApplicablePeriod);

	public abstract BigDecimal getEBGrossValue();

	public abstract void setEBGrossValue(BigDecimal grossValue);

	public abstract BigDecimal getEBNetValue();

	public abstract void setEBNetValue(BigDecimal netValue);

	public abstract String getEBNetValueCcyCode();

	public abstract void setEBNetValueCcyCode(String netValueCcyCode);

	public abstract BigDecimal getEBMonth1DebtAmount();

	public abstract void setEBMonth1DebtAmount(BigDecimal month1DebtAmt);

	public abstract BigDecimal getEBMonth2DebtAmount();

	public abstract void setEBMonth2DebtAmount(BigDecimal month2DebtAmt);

	public abstract BigDecimal getEBMonth3DebtAmount();

	public abstract void setEBMonth3DebtAmount(BigDecimal month3DebtAmt);

	public abstract BigDecimal getEBMonth4DebtAmount();

	public abstract void setEBMonth4DebtAmount(BigDecimal month4DebtAmt);

	public abstract BigDecimal getEBMonth5DebtAmount();

	public abstract void setEBMonth5DebtAmount(BigDecimal month5DebtAmt);

	public abstract BigDecimal getEBMonth6DebtAmount();

	public abstract void setEBMonth6DebtAmount(BigDecimal month6DebtAmt);

	public abstract BigDecimal getEBMonth7DebtAmount();

	public abstract void setEBMonth7DebtAmount(BigDecimal month7DebtAmt);

	public abstract BigDecimal getEBMonth8DebtAmount();

	public abstract void setEBMonth8DebtAmount(BigDecimal month8DebtAmt);

	public abstract BigDecimal getEBMonth9DebtAmount();

	public abstract void setEBMonth9DebtAmount(BigDecimal month9DebtAmt);

	public abstract BigDecimal getEBMonth10DebtAmount();

	public abstract void setEBMonth10DebtAmount(BigDecimal month10DebtAmt);

	public abstract BigDecimal getEBMonth11DebtAmount();

	public abstract void setEBMonth11DebtAmount(BigDecimal month11DebtAmt);

	public abstract BigDecimal getEBMonth12DebtAmount();

	public abstract void setEBMonth12DebtAmount(BigDecimal month12DebtAmt);

	public abstract BigDecimal getEBMonthMoreThan12DebtAmount();

	public abstract void setEBMonthMoreThan12DebtAmount(BigDecimal monthMoreThan12DebtAmt);

	public abstract BigDecimal getEBTotalApplicableAmount();

	public abstract void setEBTotalApplicableAmount(BigDecimal totalApplicableAmt);

	public long getAssetGCDebtorID() {
		return getEBAssetGCDebtorID().longValue();
	}

	public void setAssetGCDebtorID(long theID) {
		setEBAssetGCDebtorID(new Long(theID));
	}

	/**
	 * To get applicablePeriod
	 * @return int applicablePeriod
	 */
	public int getApplicablePeriod() {
		if (getEBApplicablePeriod() != null) {
			return getEBApplicablePeriod().intValue();
		}
		return ICMSConstant.INT_INVALID_VALUE;
	}

	/**
	 * To set applicablePeriod
	 * @param applicablePeriod applicablePeriod of type int
	 */
	public void setApplicablePeriod(int applicablePeriod) {
		setEBApplicablePeriod((applicablePeriod == ICMSConstant.INT_INVALID_VALUE) ? null : new Integer(
				applicablePeriod));
	}

	public Amount getGrossValue() {
		return ((getEBGrossValue() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBGrossValue(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setGrossValue(Amount grossValue) {
		setEBGrossValue((grossValue == null) ? null : grossValue.getAmountAsBigDecimal());
	}

	public Amount getNetValue() {
		return ((getEBNetValue() == null) || (getEBNetValueCcyCode() == null)) ? null : new Amount(getEBNetValue(),
				new CurrencyCode(getEBNetValueCcyCode()));
	}

	public void setNetValue(Amount netValue) {
		if ((netValue != null) && (netValue.getAmountAsBigDecimal() != null) && (netValue.getCurrencyCode() != null)) {
			boolean isForexError = GeneralChargeUtil.isForexErrorAmount(netValue);
			setEBNetValue((isForexError) ? null : netValue.getAmountAsBigDecimal());
			setEBNetValueCcyCode((isForexError) ? null : netValue.getCurrencyCode());
		}
	}

	public Amount getMonth1DebtAmount() {
		return ((getEBMonth1DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth1DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth1DebtAmount(Amount month1DebtAmt) {
		setEBMonth1DebtAmount((month1DebtAmt == null) ? null : month1DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth2DebtAmount() {
		return ((getEBMonth2DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth2DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth2DebtAmount(Amount month2DebtAmt) {
		setEBMonth2DebtAmount((month2DebtAmt == null) ? null : month2DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth3DebtAmount() {
		return ((getEBMonth3DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth3DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth3DebtAmount(Amount month3DebtAmt) {
		setEBMonth3DebtAmount((month3DebtAmt == null) ? null : month3DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth4DebtAmount() {
		return ((getEBMonth4DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth4DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth4DebtAmount(Amount month4DebtAmt) {
		setEBMonth4DebtAmount((month4DebtAmt == null) ? null : month4DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth5DebtAmount() {
		return ((getEBMonth5DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth5DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth5DebtAmount(Amount month5DebtAmt) {
		setEBMonth5DebtAmount((month5DebtAmt == null) ? null : month5DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth6DebtAmount() {
		return ((getEBMonth6DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth6DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth6DebtAmount(Amount month6DebtAmt) {
		setEBMonth6DebtAmount((month6DebtAmt == null) ? null : month6DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth7DebtAmount() {
		return ((getEBMonth7DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth7DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth7DebtAmount(Amount month7DebtAmt) {
		setEBMonth7DebtAmount((month7DebtAmt == null) ? null : month7DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth8DebtAmount() {
		return ((getEBMonth8DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth8DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth8DebtAmount(Amount month8DebtAmt) {
		setEBMonth8DebtAmount((month8DebtAmt == null) ? null : month8DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth9DebtAmount() {
		return ((getEBMonth9DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth9DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth9DebtAmount(Amount month9DebtAmt) {
		setEBMonth9DebtAmount((month9DebtAmt == null) ? null : month9DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth10DebtAmount() {
		return ((getEBMonth10DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth10DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth10DebtAmount(Amount month10DebtAmt) {
		setEBMonth10DebtAmount((month10DebtAmt == null) ? null : month10DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth11DebtAmount() {
		return ((getEBMonth11DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth11DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth11DebtAmount(Amount month11DebtAmt) {
		setEBMonth11DebtAmount((month11DebtAmt == null) ? null : month11DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonth12DebtAmount() {
		return ((getEBMonth12DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonth12DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonth12DebtAmount(Amount month12DebtAmt) {
		setEBMonth12DebtAmount((month12DebtAmt == null) ? null : month12DebtAmt.getAmountAsBigDecimal());
	}

	public Amount getMonthMoreThan12DebtAmount() {
		return ((getEBMonthMoreThan12DebtAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBMonthMoreThan12DebtAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setMonthMoreThan12DebtAmount(Amount monthMoreThan12DebtAmt) {
		setEBMonthMoreThan12DebtAmount((monthMoreThan12DebtAmt == null) ? null : monthMoreThan12DebtAmt
				.getAmountAsBigDecimal());
	}

	public Amount getTotalApplicableAmt() {
		return ((getEBTotalApplicableAmount() == null) || (getDebtAmountCurrency() == null)) ? null : new Amount(
				getEBTotalApplicableAmount(), new CurrencyCode(getDebtAmountCurrency()));
	}

	public void setTotalApplicableAmt(Amount totalApplicableAmt) {
		setEBTotalApplicableAmount((totalApplicableAmt == null) ? null : totalApplicableAmt.getAmountAsBigDecimal());
	}

	/**
	 * To get IDebtor
	 * @return IDebtor
	 */
	public IDebtor getValue() {
		OBDebtor value = new OBDebtor();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * To set IDebtor value
	 * @param value value of type IDebtor
	 */
	public void setValue(IDebtor value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param debtor of type IDebtor
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IDebtor debtor) throws CreateException {
		try {
			String debtorID = (new SequenceManager()).getSeqNum(getSequenceName(), true);
			AccessorUtil.copyValue(debtor, this, EXCLUDE_METHOD);
			setEBAssetGCDebtorID(new Long(debtorID));
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
	 * @param debtor of type IValuation
	 */
	public void ejbPostCreate(IDebtor debtor) {
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

	private String getSequenceName() {
		return ICMSConstant.SEQUENCE_ASSET_GC_DEBTOR;
	}

    public abstract Date getValuationDate();

    public abstract Date getRevaluationDate();

    public abstract String getRevalFreqUnit();

    public abstract void setValuationDate(Date valuationDate);

    public abstract void setRevaluationDate(Date revaluationDate);

    public abstract void setRevalFreqUnit(String revalFreqUnit);

    public abstract String getApplicablePeriodUnit();

    public abstract void setApplicablePeriodUnit(String applicablePeriodUnit);

    public abstract String getDebtAmountCurrency();

    public abstract void setDebtAmountCurrency(String debtAmtCurrency);
}

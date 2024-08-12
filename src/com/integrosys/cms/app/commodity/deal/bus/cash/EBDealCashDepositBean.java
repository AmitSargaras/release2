/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/cash/EBDealCashDepositBean.java,v 1.3 2004/07/22 13:43:48 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.cash;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/22 13:43:48 $ Tag: $Name: $
 */
public abstract class EBDealCashDepositBean implements IDealCashDeposit, EntityBean {

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getCashDepositID", "getCommonReferenceID" };

	public EBDealCashDepositBean() {
	}

	public void setEntityContext(EntityContext context) throws EJBException {
		this.context = context;
	}

	public void unsetEntityContext() throws EJBException {
		context = null;
	}

	public Long ejbCreate(IDealCashDeposit deposit) throws CreateException {
		try {
			String newDepositPK = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_DEAL_CASH, true);
			AccessorUtil.copyValue(deposit, this, EXCLUDE_METHOD);
			setEBCashDepositID(new Long(newDepositPK));

			if (deposit.getCommonReferenceID() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonReferenceID(getCashDepositID());
			}
			else {
				// else maintain this reference id.
				setCommonReferenceID(deposit.getCommonReferenceID());
			}
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	public void ejbPostCreate(IDealCashDeposit doc) throws CreateException {
	}

	public void ejbRemove() throws RemoveException, EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	public void ejbLoad() throws EJBException {
	}

	public void ejbStore() throws EJBException {
	}

	public long getCashDepositID() {
		return getEBCashDepositID().longValue();
	}

	public void setCashDepositID(long depositID) {
		setEBCashDepositID(new Long(depositID));
	}

	public Amount getAmount() {
		return (getEBAmount() == null) ? null : new Amount(getEBAmount(), new CurrencyCode(getAmountCcyCode()));
	}

	public void setAmount(Amount amount) {
		setEBAmount((amount == null) ? null : amount.getAmountAsBigDecimal());
		setAmountCcyCode((amount == null) ? null : amount.getCurrencyCode());
	}

	public long getCommonReferenceID() {
		return getEBCommonReferenceID().longValue();
	}

	public void setCommonReferenceID(long commonReferenceID) {
		setEBCommonReferenceID(new Long(commonReferenceID));
	}

	public abstract Long getEBCashDepositID();

	public abstract void setEBCashDepositID(Long eBCashDepositID);

	public abstract BigDecimal getEBAmount();

	public abstract void setEBAmount(BigDecimal amount);

	public abstract String getAmountCcyCode();

	public abstract void setAmountCcyCode(String amountCcyCode);

	public abstract Long getEBCommonReferenceID();

	public abstract void setEBCommonReferenceID(Long commonReferenceID);

	public abstract void setStatus(String status);

	/**
	 * Get the cash deposit business object.
	 * 
	 * @return commodity price
	 */
	public IDealCashDeposit getValue() {
		OBDealCashDeposit deposit = new OBDealCashDeposit();
		AccessorUtil.copyValue(this, deposit);
		return deposit;
	}

	/**
	 * Set the cash dpeosit to this entity.
	 * 
	 * @param deposit is of type ICashDeposit
	 */
	public void setValue(IDealCashDeposit deposit) {
		AccessorUtil.copyValue(deposit, this, EXCLUDE_METHOD);
	}

	/**
	 * Get cash deposit local home.
	 * 
	 * @return EBCashDepositLocalHome
	 */
	protected EBDealCashDepositLocalHome getLocalHome() {
		EBDealCashDepositLocalHome ejbHome = (EBDealCashDepositLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEAL_CASH_DEPOSIT_LOCAL_JNDI, EBDealCashDepositLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCashDepositLocalHome is Null!");
		}

		return ejbHome;
	}
}

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanScheduleBean.java,v 1.4 2004/08/19 02:52:53 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for loan agency's loan schedule.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/19 02:52:53 $ Tag: $Name: $
 */
public abstract class EBLoanScheduleBean implements javax.ejb.EntityBean, ILoanSchedule {

	private javax.ejb.EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getLoanScheduleID", "getCommonRef" };

	public EBLoanScheduleBean() {
	}

	/**
	 * Get loan schedule id.
	 * 
	 * @return long
	 */
	public long getLoanScheduleID() {
		return getEBLoanScheduleID().longValue();
	}

	/**
	 * Set loan schedule id.
	 * 
	 * @param loanScheduleID of type long
	 */
	public void setLoanScheduleID(long loanScheduleID) {
		setEBLoanScheduleID(new Long(loanScheduleID));
	}

	/**
	 * Get principal amount due.
	 * 
	 * @return Amount
	 */
	public Amount getPrincipalDueAmount() {
		return (getEBPrincipalDueAmount() == null) ? null : new Amount(getEBPrincipalDueAmount(),
				new CurrencyCode(null));
	}

	/**
	 * Set principal amount due.
	 * 
	 * @param principalDueAmount of type Amount
	 */
	public void setPrincipalDueAmount(Amount principalDueAmount) {
		setEBPrincipalDueAmount(principalDueAmount == null ? null : principalDueAmount.getAmountAsBigDecimal());
	}

	/**
	 * Get interest amount due.
	 * 
	 * @return Amount
	 */
	public Amount getInterestDueAmount() {
		return (getEBInterestDueAmount() == null) ? null : new Amount(getEBInterestDueAmount(), new CurrencyCode(null));
	}

	/**
	 * Set interest amount due.
	 * 
	 * @param interestDueAmount of type Amount
	 */
	public void setInterestDueAmount(Amount interestDueAmount) {
		setEBInterestDueAmount(interestDueAmount == null ? null : interestDueAmount.getAmountAsBigDecimal());
	}

	public abstract Long getEBLoanScheduleID();

	public abstract void setEBLoanScheduleID(Long pk);

	public abstract BigDecimal getEBPrincipalDueAmount();

	public abstract void setEBPrincipalDueAmount(BigDecimal value);

	public abstract BigDecimal getEBInterestDueAmount();

	public abstract void setEBInterestDueAmount(BigDecimal value);

	public abstract void setStatus(String status);

	/**
	 * Get loan schedule business object.
	 * 
	 * @return ILoanSchedule
	 */
	public ILoanSchedule getValue() {
		OBLoanSchedule contents = new OBLoanSchedule();
		AccessorUtil.copyValue(this, contents);
		return contents;
	}

	/**
	 * Persist newly updated loan schedule.
	 * 
	 * @param loanSchedule of type ILoanSchedule
	 */
	public void setValue(ILoanSchedule loanSchedule) {
		AccessorUtil.copyValue(loanSchedule, this, EXCLUDE_METHOD);
	}

	/**
	 * Create a record.
	 * 
	 * @param loanSchedule ILoanSchedule to be created.
	 * @return Long
	 * @throws javax.ejb.CreateException on error creating the record.
	 */
	public Long ejbCreate(ILoanSchedule loanSchedule) throws CreateException {
		try {
			String newLoanScheduleID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_LOAN_SCHEDULE,
					true);
			AccessorUtil.copyValue(loanSchedule, this, EXCLUDE_METHOD);
			setEBLoanScheduleID(new Long(newLoanScheduleID));

			if (loanSchedule.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef(getLoanScheduleID());
			}
			else {
				// else maintain this reference id.
				setCommonRef(loanSchedule.getCommonRef());
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
	 * @param loanSchedule of type ILoanSchedule
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ILoanSchedule loanSchedule) throws CreateException {
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
}
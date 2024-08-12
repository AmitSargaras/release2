/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBSubLimitBean.java,v 1.6 2004/08/19 05:11:56 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for loan agency's sub-limit.
 * 
 * @author dayanand $
 * @version $
 * @since $Date: 2004/08/19 05:11:56 $ Tag: $Name: $
 */
public abstract class EBSubLimitBean implements javax.ejb.EntityBean, ISubLimit {

	private javax.ejb.EntityContext context;

	/**
	 * A list of methods to be excluded during create/update sub limit for a
	 * loan.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getSubLimitID", "getCommonRef" };

	public EBSubLimitBean() {
	}

	/**
	 * Get sub-limit id.
	 * 
	 * @return long
	 */
	public long getSubLimitID() {
		return getEBSubLimitID().longValue();
	}

	/**
	 * Set sub-limit id.
	 * 
	 * @param subLimitID of type long
	 */
	public void setSubLimitID(long subLimitID) {
		setEBSubLimitID(new Long(subLimitID));
	}

	/**
	 * Get sub-limit amount.
	 * 
	 * @return Amount
	 */
	public Amount getAmount() {
		return (getEBAmount() == null) ? null : new Amount(getEBAmount(), new CurrencyCode(getCurrency()));
	}

	/**
	 * Set sub-limit amount.
	 * 
	 * @param amount of type Amount
	 */
	public void setAmount(Amount amount) {
		setEBAmount(amount == null ? null : amount.getAmountAsBigDecimal());
		setCurrency(amount == null ? null : amount.getCurrencyCode());
	}

	public abstract Long getEBSubLimitID();

	public abstract void setEBSubLimitID(Long pk);

	public abstract String getCurrency();

	public abstract void setCurrency(String val);

	public abstract BigDecimal getEBAmount();

	public abstract void setEBAmount(BigDecimal value);

	public abstract void setStatus(String status);

	/**
	 * Get subLimit business object.
	 * 
	 * @return ISubLimit
	 */
	public ISubLimit getValue() {
		OBSubLimit contents = new OBSubLimit();
		AccessorUtil.copyValue(this, contents);
		return contents;
	}

	/**
	 * Persist newly updated subLimit.
	 * 
	 * @param subLimit of type ISubLimit
	 */
	public void setValue(ISubLimit subLimit) {
		AccessorUtil.copyValue(subLimit, this, EXCLUDE_METHOD);
	}

	/**
	 * Create a record.
	 * 
	 * @param subLimit sublimit to be created.
	 * @return Long
	 * @throws javax.ejb.CreateException on error creating the record.
	 */
	public Long ejbCreate(ISubLimit subLimit) throws CreateException {
		try {
			String subLimitID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_SUBLIMIT, true);
			AccessorUtil.copyValue(subLimit, this, EXCLUDE_METHOD);
			setEBSubLimitID(new Long(subLimitID));

			if (subLimit.getCommonRef() == ICMSConstant.LONG_MIN_VALUE) {
				this.setCommonRef(getSubLimitID());
			}
			else {
				// else maintain this reference id.
				setCommonRef(subLimit.getCommonRef());
			}
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * Matching method of ejbCreate. The container invokes the matching
	 * ejbPostCreate method on an instance after it invokes the ejbCreate method
	 * with the same arguments. It executes in the same transaction context as
	 * that of the matching ejbCreate method.
	 * @param subLimit is of type ISubLimit
	 * @throws javax.ejb.CreateException on error creating references for this
	 *         loan sub-limit.
	 */
	public void ejbPostCreate(ISubLimit subLimit) throws CreateException {
	}

	/**
	 * EJB callback method to set the context of the bean.
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
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

}
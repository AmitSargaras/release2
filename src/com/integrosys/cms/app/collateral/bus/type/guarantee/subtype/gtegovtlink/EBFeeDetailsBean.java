package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBFeeDetailsBean implements IFeeDetails, EntityBean {
	/**
	 * The container assigned reference to the entity.
	 */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during update to the equity.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getFeeDetailsID", "getRefID" };

	protected String currencyCode;

	public abstract Long getEBFeeDetailsID();

	public abstract void setEBFeeDetailsID(Long eBFeeDetailsID);

	public long getFeeDetailsID() {
		return getEBFeeDetailsID().longValue();
	}

	public void setFeeDetailsID(long FeeDetailsID) {
		setEBFeeDetailsID(new Long(FeeDetailsID));
	}

	public abstract Date getEffectiveDate();

	public abstract void setEffectiveDate(Date effectiveDate);

	public abstract Date getExpirationDate();

	public abstract void setExpirationDate(Date expirationDate);

	public abstract int getTenor();

	public abstract void setTenor(int tenor);

	public abstract long getRefID();

	public abstract void setRefID(long refID);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract double getEBAmountFee();

	public abstract void setEBAmountFee(double eBAmountFee);
	
	public abstract Date getFeePaymentDateCGC();

	public abstract void setFeePaymentDateCGC(Date feePaymentDateCGC);	

	public Amount getAmountFee() {
		return new Amount(getEBAmountFee(), currencyCode);
	}

	public void setAmountFee(Amount amountFee) {
		if (amountFee != null) {
			setEBAmountFee(amountFee.getAmountAsDouble());
		}
	}

	public abstract double getEBAmountCGC();

	public abstract void setEBAmountCGC(double eBAmountCGC);

	public Amount getAmountCGC() {
		return new Amount(getEBAmountCGC(), currencyCode);
	}

	public void setAmountCGC(Amount amountCGC) {
		if (amountCGC != null) {
			setEBAmountCGC(amountCGC.getAmountAsDouble());
		}
	}

	/**
	 * Get feeDetails
	 * 
	 * @return IfeeDetails
	 */
	public IFeeDetails getValue() {
		OBFeeDetails ob = new OBFeeDetails();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}

	public void setValue(IFeeDetails feeDetails) {
		AccessorUtil.copyValue(feeDetails, this, EXCLUDE_METHOD);
	}

	public Long ejbCreate(IFeeDetails feeDetails) throws CreateException {
		try {
			String feeDetailsID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_FEE_DETAILS, true);
			AccessorUtil.copyValue(feeDetails, this, EXCLUDE_METHOD);
			this.setEBFeeDetailsID(new Long(feeDetailsID));
			if (feeDetails.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getFeeDetailsID());
			}
			else {
				setRefID(feeDetails.getRefID());
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
	 * @param feeDetails of type feeDetails
	 */
	public void ejbPostCreate(IFeeDetails feeDetails) {
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

    public abstract String getTenorFreq();

    public abstract void setTenorFreq(String tenorFreq);
}
/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanLimitBean.java,v 1.9 2006/08/23 03:34:37 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for loan agency's limit.
 * 
 * @author $Author: nkumar $
 * @version $Revision: 1.9 $
 * @since $Date: 2006/08/23 03:34:37 $ Tag: $Name: $
 */
public abstract class EBLoanLimitBean implements ILoanLimit, EntityBean {

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getLoanLimitID", "getCommonReferenceID" };

	public EBLoanLimitBean() {
	}

	/**
	 * Gets the loan limit id.
	 * 
	 * @return long
	 */
	public long getLoanLimitID() {
		return getEBLoanLimitID().longValue();
	}

	/**
	 * Sets the loan limit id.
	 * 
	 * @param loanLimitID
	 */
	public void setLoanLimitID(long loanLimitID) {
		setEBLoanLimitID(new Long(loanLimitID));
	}

	/**
	 * Gets the limit id associated with this loan limit.
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
	 * Sets the limit id associated with this loan limit.
	 * 
	 * @param limitID
	 */
	public void setLimitID(long limitID) {
		setEBLimitID(limitID == ICMSConstant.LONG_INVALID_VALUE ? null : new Long(limitID));
	}

	/**
	 * Get common reference for actual and staging limit.
	 * 
	 * @return long
	 */
	public long getCommonReferenceID() {
		return getEBCommonReferenceID().longValue();
	}

	/**
	 * Set common reference for actual and staging limit.
	 * 
	 * @param commonReferenceID of type long
	 */
	public void setCommonReferenceID(long commonReferenceID) {
		setEBCommonReferenceID(new Long(commonReferenceID));
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
	 * @param CoBorrowerLimitID
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID) {
		setEBCoBorrowerLimitID(coBorrowerLimitID == ICMSConstant.LONG_INVALID_VALUE ? null
				: new Long(coBorrowerLimitID));
	}

	public abstract Long getEBLoanLimitID();

	public abstract void setEBLoanLimitID(Long eBLoanLimitID);

	public abstract Long getEBLimitID();

	public abstract void setEBLimitID(Long eBLimitID);

	public abstract Long getEBCommonReferenceID();

	public abstract void setEBCommonReferenceID(Long commonReferenceID);

	public abstract Long getEBCoBorrowerLimitID();

	public abstract void setEBCoBorrowerLimitID(Long eBCoBorrowerLimitID);

	public abstract String getCustomerCategory();

	public abstract void setCustomerCategory(String customerCategory);

	public abstract void setStatus(String status);

	/**
	 * Get the loan limit business object.
	 * 
	 * @return loan limit
	 */
	public ILoanLimit getValue() {
		OBLoanLimit limit = new OBLoanLimit();
		AccessorUtil.copyValue(this, limit);
		return limit;
	}

	/**
	 * Persist newly updated loan agency limit.
	 * 
	 * @param limit of type ILoanLimit
	 */
	public void setValue(ILoanLimit limit) {
		AccessorUtil.copyValue(limit, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param limit of type ILoanLimit
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ILoanLimit limit) throws CreateException {
		try {
			String newLoanLimitPK = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_LOANLIMIT_AGENCY,
					true);
			AccessorUtil.copyValue(limit, this, EXCLUDE_METHOD);
			setEBLoanLimitID(new Long(newLoanLimitPK));

			if (limit.getCommonReferenceID() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonReferenceID(getLoanLimitID());
			}
			else {
				// else maintain this reference id.
				setCommonReferenceID(limit.getCommonReferenceID());
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
	 * @param loanLimit of type ILoanLimit
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ILoanLimit loanLimit) throws CreateException {
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

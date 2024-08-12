/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBBorrowerBean.java,v 1.5 2004/08/17 11:57:46 lyng Exp $
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
 * Entity bean implementation for loan agency's borrower.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public abstract class EBBorrowerBean implements EntityBean, IBorrower {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during create/update financing document.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getBorrowerID", "getCommonRef" };

	/**
	 * Get the borrower business object.
	 * 
	 * @return IBorrower
	 */
	public IBorrower getValue() {
		OBBorrower borrower = new OBBorrower();
		AccessorUtil.copyValue(this, borrower);
		return borrower;
	}

	/**
	 * Persist the newly update borrower information.
	 * 
	 * @param borrower of type IBorrower
	 */
	public void setValue(IBorrower borrower) {
		AccessorUtil.copyValue(borrower, this, EXCLUDE_METHOD);
	}

	/**
	 * Get borrower id.
	 * 
	 * @return long
	 */
	public long getBorrowerID() {
		return getEBBorrowerID().longValue();
	}

	/**
	 * Set borrower id.
	 * 
	 * @param borrowerID of type long
	 */
	public void setBorrowerID(long borrowerID) {
		setEBBorrowerID(new Long(borrowerID));
	}

	abstract public Long getEBBorrowerID();

	abstract public void setEBBorrowerID(Long eBBorrowerID);

	public abstract void setStatus(String status);

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param borrower of type IBorrower
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IBorrower borrower) throws CreateException {
		try {
			String borrowerID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_BORROWER, true);
			AccessorUtil.copyValue(borrower, this, EXCLUDE_METHOD);
			setEBBorrowerID(new Long(borrowerID));

			if (borrower.getCommonRef() == ICMSConstant.LONG_MIN_VALUE) {
				this.setCommonRef(getBorrowerID());
			}
			else {
				// else maintain this reference id.
				setCommonRef(borrower.getCommonRef());
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
	 * @param borrower of type IBorrower
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IBorrower borrower) throws CreateException {
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
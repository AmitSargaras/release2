/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralDetailBean.java,v 1.9 2003/07/10 08:08:42 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Abstract class for all collateral types entity bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2003/07/10 08:08:42 $ Tag: $Name: $
 */
public abstract class EBCollateralDetailBean extends OBCollateral implements EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** Currency code used for all amount objects. */
	protected String currencyCode;

	/** A list of methods to be excluded during update to the collateral. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getCollateralID" };

	/**
	 * Get the property collateral details.
	 * 
	 * @return collateral
	 */
	public ICollateral getValue(ICollateral collateral) {
		try {
			OBCollateral commonCol = new OBCollateral(collateral);
			ICollateral newCol = (ICollateral) collateral.getClass().newInstance();
			currencyCode = collateral.getCurrencyCode();
			AccessorUtil.copyValue(this, newCol);
			AccessorUtil.copyValue(commonCol, newCol);
			
			return newCol;
		}
		catch (Exception e) {
			throw new EJBException("fail to retrieve collateral detail for collateral id ["
					+ collateral.getCollateralID() + "] host id [" + collateral.getSCISecurityID() + "]", e);
		}
	}

	/**
	 * Set the property collateral type to this entity.
	 * 
	 * @param collateral is of type ICollateral
	 */
	public void setValue(ICollateral collateral) {
		AccessorUtil.copyValue(collateral, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param collateral of type ICollateral
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICollateral collateral) throws CreateException {
		AccessorUtil.copyValue(collateral, this);
		return null;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param collateral of type ICollateral
	 * @throws CreateException on error creating references to collateral
	 */
	public void ejbPostCreate(ICollateral collateral) throws CreateException {
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
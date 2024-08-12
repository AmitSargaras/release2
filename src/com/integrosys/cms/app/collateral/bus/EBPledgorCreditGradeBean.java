/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBPledgorCreditGradeBean.java,v 1.1 2003/09/03 09:26:00 elango Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;

import java.util.Date;

/**
 * Entity bean implementation for pledgor credit grade entity.
 * 
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 09:26:00 $ Tag: $Name: $
 */
public abstract class EBPledgorCreditGradeBean implements IPledgorCreditGrade, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during update to the pledgor credit
	 * grade id.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getCreditGradeID" };

	/**
	 * Get pledgor credit grade id.
	 * 
	 * @return long
	 */
	public long getCreditGradeID() {
		return getEBCreditGradeID().longValue();
	}

	/**
	 * Set pledgor credit grade id.
	 * 
	 * @param creditGradeID of type long
	 */
	public void setCreditGradeID(long creditGradeID) {
		setEBCreditGradeID(new Long(creditGradeID));
	}

	public abstract Long getEBCreditGradeID();

	public abstract void setEBCreditGradeID(Long eBCreditGradeID);

	/**
	 * Get the pledgor credit grade of this entity.
	 * 
	 * @return pledgor credit grade
	 */
	public IPledgorCreditGrade getValue() {
		OBPledgorCreditGrade creditGrade = new OBPledgorCreditGrade();
		AccessorUtil.copyValue(this, creditGrade);
		return creditGrade;
	}

	/**
	 * Set the pledgor credit grade.
	 * 
	 * @param creditGrade of type IPledgorCreditGrade
	 */
	public void setValue(IPledgorCreditGrade creditGrade) {
		AccessorUtil.copyValue(creditGrade, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param creditGrade of type IPledgorCreditGrade
	 * @return pledgor credit grade primary key
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IPledgorCreditGrade creditGrade) throws CreateException {
		try {
			AccessorUtil.copyValue(creditGrade, this);
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
	 * @param creditGrade of type IPledgorCreditGrade
	 */
	public void ejbPostCreate(IPledgorCreditGrade creditGrade) {
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

    public abstract long getPledgorID();

    public abstract void setPledgorID(long pledgorID);

    public abstract long getCreditGradeIDRef();

    public abstract void setCreditGradeIDRef(long creditGradeIDRef);

    public abstract long getPledgorIDRef();

    public abstract void setPledgorIDRef(long pledgorIDRef);

    public abstract String getCreditGradeType();

    public abstract void setCreditGradeType(String creditGradeType);

    public abstract String getCreditGradeCode();

    public abstract void setCreditGradeCode(String creditGradeCode);

    public abstract Date getCreditGradeStartDate();

    public abstract void setCreditGradeStartDate(Date creditGradeStartDate);

    public abstract String getCreditGradeDesc();

    public abstract void setCreditGradeDesc(String creditGradeDesc);
}
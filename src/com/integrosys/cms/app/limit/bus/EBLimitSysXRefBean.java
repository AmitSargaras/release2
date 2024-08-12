/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitSysXRefBean.java,v 1.4 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.EBCustomerSysXRef;
import com.integrosys.cms.app.customer.bus.EBCustomerSysXRefHome;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;

/**
 * This entity bean represents the persistence for Co Borrower Limit.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBLimitSysXRefBean implements EntityBean, ILimitSysXRef {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT_X_REF;

	private static final String[] EXCLUDE_METHOD = new String[] { "getXRefID", "getSID", "getLimitFk" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBLimitSysXRefBean() {
	}

	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get Limit System XRef ID
	 * 
	 * @return long
	 */
	public long getXRefID() {
		if (null != getXRefPK()) {
			return getXRefPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the Customer System XRef Not implemented. See
	 * <code>retrieveCustomerSysXRef()</code>
	 * 
	 * @return ICustomerSysXRef
	 */
	public ICustomerSysXRef getCustomerSysXRef() {
		return null;
	}

	// Setters
	/**
	 * Set Limit system xref ID
	 * 
	 * @param value is of type long
	 */
	public void setXRefID(long value) {
		setXRefPK(new Long(value));
	}

	public void setLimitFk(long limitId) {
		if (limitId != ICMSConstant.LONG_INVALID_VALUE) {
			setLimitID(new Long(limitId));
		}
	}

	public long getLimitFk() {
		Long val = getLimitID();
		if (val != null) {
			return val.longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set the Customer System XRef Not implemented. See
	 * <code>updateCustomerSysXRef()</code>
	 * 
	 * @param value is of type ICustomerSysXRef
	 */
	public void setCustomerSysXRef(ICustomerSysXRef value) {
		// do nothing
	}

	// ********************** Abstract Methods **********************

	// Getters
	/**
	 * Get limit system xref PK
	 * 
	 * @return Long
	 */
	public abstract Long getXRefPK();

	/**
	 * Get the limit ID
	 * 
	 * @return long
	 */
	public abstract Long getLimitID();

	/**
	 * Get the customer xref ID
	 * 
	 * @return long
	 */
	public abstract long getCustomerSysXRefID();

	// Setters
	/**
	 * Set limit system xref PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setXRefPK(Long value);

	/**
	 * Set the limit ID
	 * 
	 * @param value is of type long
	 */
	public abstract void setLimitID(Long value);

	/**
	 * Set the customer xref ID
	 * 
	 * @param value is of type long
	 */
	public abstract void setCustomerSysXRefID(long value);

	public abstract long getSID();

	public abstract String getStatus();

	public abstract void setStatus(String value);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a co borrower limit
	 * 
	 * @param value is the ILimitSysXRef object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ILimitSysXRef value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimitSysXRef is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating Limit Sys XRef with ID: " + pk);

			setXRefID(pk);

			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == value.getSID()) {
				setSID(pk);
			}
			else {
				setSID(value.getSID());
			}

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			updateCustomerSysXRef(value.getCustomerSysXRef());

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Create a Co Borrower Limit
	 * 
	 * @param value is the ILimitSysXRef object
	 */
	public void ejbPostCreate(ILimitSysXRef value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ILimitSysXRef
	 * @throws LimitException on error
	 */
	public ILimitSysXRef getValue() throws LimitException {
		OBLimitSysXRef value = new OBLimitSysXRef();
		AccessorUtil.copyValue(this, value);

		value.setCustomerSysXRef(retrieveCustomerSysXRef());

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type ILimitSysXRef
	 * @throws LimitException on error
	 */
	public void setValue(ILimitSysXRef value) throws LimitException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			updateCustomerSysXRef(value.getCustomerSysXRef());
		}
		else {
			throw new LimitException("ILimitSysXRef is null!");
		}
	}

	// ********************* Private Methods *****************
	/**
	 * Update customer system xref map
	 */
	private void updateCustomerSysXRef(ICustomerSysXRef custXRef) throws LimitException {
		if (null != custXRef) {
			long xrefID = custXRef.getXRefID();
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE != xrefID) {
				EBCustomerSysXRefHome home = getEBHomeCustomerSysXRef();
				try {
					home.findByPrimaryKey(new Long(xrefID));
					setCustomerSysXRefID(xrefID);
				}
				catch (FinderException fe) {
					try {
						EBCustomerSysXRef rem = home.create(0, custXRef);
						setCustomerSysXRefID(Long.parseLong(rem.getPrimaryKey().toString()));
					}
					catch (Exception ce) {
						ce.printStackTrace();
						throw new LimitException(ce);
					}
				}
				catch (Exception ex) {
					throw new LimitException(ex);
				}
			}
			else {
				throw new LimitException("Customer XRef ID is not initialised: " + xrefID);
			}
		}
		else {
			throw new LimitException("Customer XRef is null!");
		}
	}

	/**
	 * Retrieve customer system xref map
	 */
	public ICustomerSysXRef retrieveCustomerSysXRef() throws LimitException {
		long xrefID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

		try {
			xrefID = getCustomerSysXRefID();
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE != xrefID) {
				EBCustomerSysXRefHome home = getEBHomeCustomerSysXRef();
				EBCustomerSysXRef rem = home.findByPrimaryKey(new Long(xrefID));
				return rem.getValue();
			}
			else {
				return null;
			}
		}
		catch (FinderException e) {
			throw new LimitException("Unable to find Customer System XRef with ID: " + xrefID);
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to get EB Home for EBCustomerSysXRef
	 * 
	 * @return EBCustomerSysXRefHome
	 * @throws LimitException on errors
	 */
	protected EBCustomerSysXRefHome getEBHomeCustomerSysXRef() throws LimitException {
		EBCustomerSysXRefHome home = (EBCustomerSysXRefHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CUSTOMER_SYS_REF_JNDI, EBCustomerSysXRefHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCustomerSysXRefHome is null!");
		}
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() throws RemoveException, EJBException {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

    public abstract String getXRefRef();

    public abstract String getXRefLegalRef();

    public abstract String getXRefCustomerRef();

    public abstract String getXRefBCARef();

    public abstract String getXRefLimitRef();

    public abstract String getXRefCustomerXRef();

    public abstract void setSID(long value);

    public abstract void setXRefRef(String value);

    public abstract void setXRefLegalRef(String value);

    public abstract void setXRefCustomerRef(String value);

    public abstract void setXRefBCARef(String value);

    public abstract void setXRefLimitRef(String value);

    public abstract void setXRefCustomerXRef(String value);
}
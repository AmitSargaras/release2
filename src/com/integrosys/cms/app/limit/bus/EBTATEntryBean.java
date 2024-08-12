/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBTATEntryBean.java,v 1.4 2003/08/25 07:37:18 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * This entity bean represents the persistence for Co Borrower Limit.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/25 07:37:18 $ Tag: $Name: $
 */
public abstract class EBTATEntryBean implements EntityBean, ITATEntry {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_TAT_ENTRY;

	private static final String[] EXCLUDE_METHOD = new String[] { "getTATEntryID", "getLimitProfileID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBTATEntryBean() {
	}

	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get TAT Entry ID
	 * 
	 * @return long
	 */
	public long getTATEntryID() {
		if (null != getTATEntryPK()) {
			return getTATEntryPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	// Setters
	/**
	 * Set TAT Entry ID
	 * 
	 * @param value is of type long
	 */
	public void setTATEntryID(long value) {
		setTATEntryPK(new Long(value));
	}

	// ********************** Abstract Methods **********************

	// Getters
	/**
	 * Get TAT Entry PK
	 * 
	 * @return Long
	 */
	public abstract Long getTATEntryPK();

	/**
	 * Get the limit Profile ID
	 * 
	 * @return long
	 */
	public abstract Long getLimitProfileID();

	// Setters
	/**
	 * Set TAT Entry PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setTATEntryPK(Long value);

	/**
	 * Set the limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public abstract void setLimitProfileID(Long value);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a TAT Entry
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITATEntry object
	 * @return Long the primary key
	 */
	public Long ejbCreate(long limitProfileID, ITATEntry value) throws CreateException {
		if (null == value) {
			throw new CreateException("ITATEntry is null!");
		}
		else if (ICMSConstant.LONG_INVALID_VALUE == limitProfileID) {
			throw new CreateException("LimitProfileID is uninitialised!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating TAT Entry with ID: " + pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setTATEntryID(pk);
			setLimitProfileID(new Long(limitProfileID));

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
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITATEntry object
	 */
	public void ejbPostCreate(long limitProfileID, ITATEntry value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ITATEntry
	 */
	public ITATEntry getValue() {
		OBTATEntry value = new OBTATEntry();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type ITATEntry
	 * @throws LimitException on error
	 */
	public void setValue(ITATEntry value) throws LimitException {
		if (null != value) {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		}
		else {
			throw new LimitException("ITATEntry is null!");
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

    public abstract Date getTATStamp();

    public abstract String getTATServiceCode();

    public abstract Date getReferenceDate();

    public abstract String getRemarks();

    public abstract void setTATStamp(Date value);

    public abstract void setTATServiceCode(String value);

    public abstract void setReferenceDate(Date value);

    public abstract void setRemarks(String remarks);
}
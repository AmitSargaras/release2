/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCreditGradeBean.java,v 1.7 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * This entity bean represents the persistence for Credit Grade Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBCreditGradeBean implements EntityBean, ICreditGrade {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CREDIT_GRADE;

	private static final String[] EXCLUDE_METHOD = new String[] { "getCGID", "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCreditGradeBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get Legal ID
	 * 
	 * @return long
	 */
	public long getLEID() {
		if (null != getLEFK()) {
			return getLEFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get CG ID
	 * 
	 * @return long
	 */
	public long getCGID() {
		if (null != getCGPK()) {
			return getCGPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set Legal ID
	 * 
	 * @param value is of type long
	 */
	public void setLEID(long value) {
		setLEFK(new Long(value));
	}

	/**
	 * Set the CG ID
	 * 
	 * @param value is of type long
	 */
	public void setCGID(long value) {
		setCGPK(new Long(value));
	}

	// ************** Abstract methods ************
	/**
	 * Get Legal FK
	 * 
	 * @return Long
	 */
	public abstract Long getLEFK();

	/**
	 * Get the CG PK
	 * 
	 * @return Long
	 */
	public abstract Long getCGPK();

	/**
	 * Set Legal FK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLEFK(Long value);

	/**
	 * Set the CG PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCGPK(Long value);

	// *****************************************************
	/**
	 * Create a CreditGrade Information
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the ICreditGrade object
	 * @return Long the CreditGrade ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(long legalID, ICreditGrade value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICreditGrade is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getCGID() ==
			 * com.integrosys.cms.app.common
			 * .constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getCGID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			// setLEID(legalID); //to be set by cmr
			setCGID(pk);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param legalID the customer ID of type long
	 * @param value is the ICreditGrade object
	 */
	public void ejbPostCreate(long legalID, ICreditGrade value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return ICreditGrade
	 */
	public ICreditGrade getValue() {
		OBCreditGrade value = new OBCreditGrade();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a CreditGrade information
	 * 
	 * @param value is of type ICreditGrade
	 */
	public void setValue(ICreditGrade value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
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
	public void ejbRemove() {
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

    public abstract String getCGReference();

    public abstract String getCGType();

    public abstract String getCGCode();

    public abstract Date getEffectiveDate();

    public abstract String getReasonForChange();

    public abstract String getApproverBookingLoc();

    public abstract String getApproverEmployeeCode();

    public abstract String getApproverEmployeeType();

    public abstract String getUpdateStatusInd();

    public abstract Date getLastUpdateDate();

    public abstract void setCGReference(String value);

    public abstract void setCGType(String value);

    public abstract void setCGCode(String value);

    public abstract void setEffectiveDate(Date value);

    public abstract void setReasonForChange(String value);

    public abstract void setApproverBookingLoc(String value);

    public abstract void setApproverEmployeeCode(String value);

    public abstract void setApproverEmployeeType(String value);

    public abstract void setUpdateStatusInd(String value);

    public abstract void setLastUpdateDate(Date value);
}
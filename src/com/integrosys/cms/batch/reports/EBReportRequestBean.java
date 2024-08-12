package com.integrosys.cms.batch.reports;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: Contains the meta information of report requests
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public abstract class EBReportRequestBean implements EntityBean, IReportRequest {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_REPORT_REQUEST;

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getReportRequestID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	public EBReportRequestBean() {
	}

	public long getReportRequestID() {
		return getEBReportRequestID().longValue();
	}

	public void setReportRequestID(long reqID) {
		setEBReportRequestID(new Long(reqID));
	}

	public long getUserID() {
		return getEBUserID().longValue();
	}

	public void setUserID(long userID) {
		setEBUserID(new Long(userID));
	}

	// abstract methods

	public abstract Long getEBReportRequestID();

	public abstract void setEBReportRequestID(Long reqID);

	public abstract Long getEBUserID();

	public abstract void setEBUserID(Long userID);

	public abstract void setReportID(long reportID);

	public abstract void setReportName(String reportName);

	public abstract void setParameters(String params);

	public abstract void setRequestTime(Date reqTime);

	public abstract void setReportGenTime(Date reportGenTime);

	public abstract void setStatus(String status);

	/**
	 * Get the business object.
	 * 
	 * @return report request object
	 */
	public IReportRequest getValue() {
		OBReportRequest ob = new OBReportRequest();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}

	public void setValue(IReportRequest repReq) {
		AccessorUtil.copyValue(repReq, this, EXCLUDE_METHOD_UPDATE);
	}

	public Long ejbCreate(IReportRequest repReq) throws CreateException {
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			setValue(repReq);
			setReportRequestID(pk);

			return new Long(pk);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	public void ejbPostCreate(IReportRequest repReq) throws CreateException {
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

    public abstract long getReportID();

    public abstract String getReportName();

    public abstract String getParameters();

    public abstract Date getRequestTime();

    public abstract Date getReportGenTime();

    public abstract String getStatus();
}

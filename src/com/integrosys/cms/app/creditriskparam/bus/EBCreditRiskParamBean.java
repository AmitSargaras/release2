/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/creditrixkparam/bus/EBCreditRiskParamBean.java,v 1.1 2007/02/12 08:34:09 shphoon Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * EBCreditRiskParamBean Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBCreditRiskParamBean implements EntityBean, ICreditRiskParam {

	public abstract Long getCmpParameterId();

	public abstract void setCmpParameterId(Long anParameterId);

	public abstract Double getCmpMoa();

	public abstract void setCmpMoa(Double anCmpMoa);

	public abstract Double getCmpMaxCap();

	public abstract void setCmpMaxCap(Double anMaxCap);

	public abstract Long getCmpFeedID();

	public abstract void setCmpFeedID(Long anFeedID);

	public abstract Long getCmpParamRef();

	public abstract void setCmpParamRef(Long anParamRef);

	public abstract String getParamType();

	public abstract void setParamType(String paramType);

	public abstract String getIsLiquid();

	public abstract void setIsLiquid(String isLiquid);

	public abstract String getIsIntSuspend();

	public abstract void setIsIntSuspend(String isIntSuspend);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract void setIsFi(String isFi);

	public abstract String getIsFi();

	public abstract void setIsAcceptable(String isAcceptable);

	public abstract String getIsAcceptable();

	/**
	 * 
	 * @return version time long
	 */
	public abstract long getVersionTime();

	/**
	 * @return long - the CreditRiskParam Id
	 * 
	 */
	public long getParameterId() {
		if (getCmpParameterId() != null) {
			return getCmpParameterId().longValue();
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * @param creditRiskParamID - long
	 * 
	 */
	public void setParameterId(long creditRiskParamID) {
		setCmpParameterId(new Long(creditRiskParamID));
	}

	/**
	 * @return double - the margin of advance
	 * 
	 */
	public double getMarginOfAdvance() {
		if (getCmpMoa() != null) {
			return getCmpMoa().doubleValue();
		}

		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * @param anMoa - double
	 * 
	 */
	public void setMarginOfAdvance(double anMoa) {
		setCmpMoa(new Double(anMoa));
	}

	/**
	 * @return double - the maximum cap
	 * 
	 */
	public double getMaximumCap() {
		if (getCmpMaxCap() != null) {
			return getCmpMaxCap().doubleValue();
		}

		return ICMSConstant.DOUBLE_INVALID_VALUE;
	}

	/**
	 * @param anMaxCap - double
	 * 
	 */
	public void setMaximumCap(double anMaxCap) {
		setCmpMaxCap(new Double(anMaxCap));
	}

	/**
	 * @return long - the Feed Id
	 * 
	 */
	public long getFeedId() {
		if (getCmpFeedID() != null) {
			return getCmpFeedID().longValue();
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * @param feedId - long
	 * 
	 */
	public void setFeedId(long feedId) {
		setCmpFeedID(new Long(feedId));
	}

	/**
	 * @return long - the Parameter Reference
	 * 
	 */
	public long getParameterRef() {
		if (getCmpParamRef() != null) {
			return getCmpParamRef().longValue();
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * @param paramRef - long
	 * 
	 */
	public void setParameterRef(long paramRef) {
		setCmpParamRef(new Long(paramRef));
	}

	public String getParameterType() {
		return getParamType();
	}

	public void setParameterType(String paramType) {
		setParamType(paramType);
	}

	// public String getLiquid() {
	// return getIsLiquid();
	// }
	//
	// public void setLiquid(String isLiquid) {
	// setIsLiquid(isLiquid);
	// }

	// public String getSuspended() {
	// return getIsIntSuspend();
	// }
	//
	// public void setSuspended(String isIntSuspend) {
	// setIsIntSuspend(isIntSuspend);
	// }

	// public String getShareStatus() {
	// return getStatus();
	// }
	//
	// public void setShareStatus(String status) {
	// setStatus(status);
	// }

	/**
	 * Create a credit risk param
	 * @param aCreditRiskParam ICreditRiskParam
	 * @return Long - the document item ID (primary key)
	 * @throws javax.ejb.CreateException on error
	 * 
	 */
	public Long ejbCreate(ICreditRiskParam aCreditRiskParam) throws CreateException {
		if (aCreditRiskParam == null) {
			throw new CreateException("CreditRiskParam is null!");
		}

		try {
			long pk = ICMSConstant.LONG_INVALID_VALUE;
			DefaultLogger.debug(this, "Sequence Name: " + getSequenceName());

			// Code commented out below.

			// if (aCreditRiskParamEntry.getCreditRiskParamEntryID()() ==
			// com.integrosys
			// .cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			// pk = Long.parseLong((new SequenceManager()).getSeqNum(
			// getSequenceName(), true));
			// } else {
			// pk = aCreditRiskParamEntry.getCreditRiskParamEntryID();
			// }

			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));

			// if (aCreditRiskParam.getCreditRiskParamEntryRef() ==
			// ICMSConstant.LONG_INVALID_VALUE) {
			// setCreditRiskParamEntryRef(pk);
			// } else {
			// setCreditRiskParamEntryRef(aCreditRiskParamEntry.
			// getCreditRiskParamEntryRef());
			// }

			//DefaultLogger.debug(this, "Item to be inserted: " + aCreditRiskParam);
			AccessorUtil.copyValue(aCreditRiskParam, this, EXCLUDE_METHOD);
			DefaultLogger.debug(this, "PK: " + pk);
			setParameterId(pk);
			setVersionTime(VersionGenerator.getVersionNumber());

			return null;

		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param aCreditRiskParam ICreditRiskParam
	 */
	public void ejbPostCreate(ICreditRiskParam aCreditRiskParam) {
	}

	/**
	 * Return the Interface representation of this object
	 * @return ICreditRiskParam
	 * 
	 */
	public ICreditRiskParam getValue() {
		// DefaultLogger.debug ( this , "This class type is : " + this.getClass
		// ().getName () );

		OBCreditRiskParam value = new OBCreditRiskParam();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * 
	 * @param aCreditRiskParam ICreditRiskParam
	 * @throws ConcurrentUpdateException
	 */
	public void setValue(ICreditRiskParam aCreditRiskParam) throws ConcurrentUpdateException {
		if (getVersionTime() != aCreditRiskParam.getVersionTime()) {

			// throw new ConcurrentUpdateException("Mismatch timestamp");
		}

		//DefaultLogger.debug(this, "before AccessorUtil.copyValue(...)");

		AccessorUtil.copyValue(aCreditRiskParam, this, EXCLUDE_METHOD);

		//DefaultLogger.debug(this, "after AccessorUtil.copyValue(...)");

		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Get the name of the sequence to be used for the item id
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CMS_CREDIT_RISK_PARAM_SEQ;
	}

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

	private static final String[] EXCLUDE_METHOD = new String[] { "getParameterId" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

    public abstract String getParamBoardType();

    public abstract void setParamBoardType(String paramBoardType);

    public abstract String getParamShareStatus();

    public abstract void setParamShareStatus(String paramShareStatus);

    public abstract void setVersionTime(long l);
}

/*
 * Created on Mar 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.sharesecurity.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class EBShareSecurityBean implements IShareSecurity, EntityBean {
	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getShareSecurityId", "getCmsCollateralId" };

	public abstract Long getEBShareSecurityId();

	public abstract void setEBShareSecurityId(Long shareSecId);

	public abstract Long getEBCollateralId();

	public abstract void setEBCollateralId(Long colId);

	public EBShareSecurityBean() {
	}

	public Long ejbCreate(IShareSecurity shareSec) throws CreateException {
		if (null == shareSec) {
			throw new CreateException("ILimit is null!");
		}
		try {
			String seqId = new SequenceManager().getSeqNum(ICMSConstant.SEQUENCE_SHARE_SECURITY, true);
			setEBShareSecurityId(new Long(seqId));
			AccessorUtil.copyValue(shareSec, this, EXCLUDE_METHOD);
			return null;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreateException(ex.toString());
		}
	}

	public void ejbPostCreate(IShareSecurity shareSec) throws CreateException {

	}

	public IShareSecurity getValue() throws Exception {
		IShareSecurity res = new OBShareSecurity();
		AccessorUtil.copyValue(this, res);
		return res;
	}

	public void setValue(IShareSecurity sec) throws Exception {
		AccessorUtil.copyValue(sec, this, EXCLUDE_METHOD);
	}

	/**
	 * @return Returns the cmsCollateralId.
	 */
	public long getCmsCollateralId() {
		Long colId = getEBCollateralId();
		if (colId != null) {
			return colId.longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @param cmsCollateralId The cmsCollateralId to set.
	 */
	public void setCmsCollateralId(long cmsCollateralId) {
		if (cmsCollateralId != ICMSConstant.LONG_INVALID_VALUE) {
			setEBCollateralId(new Long(cmsCollateralId));
		}
	}

	/**
	 * @return Returns the shareSecurityId.
	 */
	public long getShareSecurityId() {
		Long shareSecId = getEBShareSecurityId();
		if (shareSecId != null) {
			return shareSecId.longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @param shareSecurityId The shareSecurityId to set.
	 */
	public void setShareSecurityId(long shareSecurityId) {
		if (shareSecurityId != ICMSConstant.LONG_INVALID_VALUE) {
			setEBShareSecurityId(new Long(shareSecurityId));
		}
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
	public void ejbRemove() throws RemoveException {
	}

	public void setEntityContext(EntityContext ctx) {
		context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		context = null;
	}

    public abstract Date getLastUpdatedTime();

    public abstract void setLastUpdatedTime(Date lastUpdatedTime);

    public abstract String getSourceId();

    public abstract void setSourceId(String sourceId);

    public abstract String getSourceSecurityId();

    public abstract void setSourceSecurityId(String sourceSecurityId);

    public abstract String getStatus();

    public abstract void setStatus(String status);

    public abstract String getSecuritySubTypeId();

    public abstract void setSecuritySubTypeId(String securitySubTypeId);
}

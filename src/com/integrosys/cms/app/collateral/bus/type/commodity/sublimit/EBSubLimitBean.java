/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/sublimit/EBSubLimitBean.java,v 1.3 2005/10/18 09:56:37 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity.sublimit;

import java.text.DecimalFormat;

import javax.ejb.CreateException;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14
 * @Tag : com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.
 *      EBSubLimitBean.java
 */
public abstract class EBSubLimitBean implements javax.ejb.EntityBean, ISubLimit {

	private javax.ejb.EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getSubLimitID", "setSubLimitID", "getSubLimitPK",
			"getSubLimitPK" };

	public EBSubLimitBean() {
	}

	public ISubLimit getValue() {
		OBSubLimit value = new OBSubLimit();
		AccessorUtil.copyValue(this, value);
		value.setInnerLimit(this.isInnerLimit());
		return value;
	}

	public void setValue(ISubLimit value) throws VersionMismatchException {
		// checkVersionMismatch(value);
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		setInnerLimit(value.isInnerLimit());
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	public Long ejbCreate(ISubLimit value) throws CreateException {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		long pk = generateSubLimitPK();
		this.setSubLimitPK(new Long(pk));
		this.setDActiveAmount(Double.valueOf(value.getActiveAmount()));
		this.setDSubLimitAmount(Double.valueOf(value.getSubLimitAmount()));
		this.setInnerLimit(value.isInnerLimit());
		setVersionTime(VersionGenerator.getVersionNumber());
		long commonRef = value.getCommonRef();
		setCommonRef((commonRef == ICMSConstant.LONG_INVALID_VALUE) ? pk : commonRef);
		setStatus(ICMSConstant.STATE_ACTIVE);
		return null;
	}

	public void ejbPostCreate(ISubLimit subLimit) throws CreateException {
	}

	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	public void unsetEntityContext() {
		this.context = null;
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void ejbLoad() {
	}

	public void ejbStore() {
	}

	public void ejbRemove() throws RemoveException {
	}

	// CMP Fields Equivalence.
	public String getActiveAmount() {
		return formatAmt(getDActiveAmount());
	}

	public void setActiveAmount(String activeAmount) {
		setDActiveAmount(Double.valueOf(activeAmount));
	}

	public String getSubLimitAmount() {
		return formatAmt(getDSubLimitAmount());
	}

	public void setSubLimitAmount(String subLimitAmount) {
		setDSubLimitAmount(Double.valueOf(subLimitAmount));
	}

	public long getSubLimitID() {
		return getSubLimitPK().longValue();
	}

	public void setSubLimitID(long subLimitID) {
		setSubLimitPK(new Long(subLimitID));
	}

	public boolean isInnerLimit() {
		return "Y".equals(getInnerFlag());
	}

	public void setInnerLimit(boolean innerFlag) {
		if (innerFlag) {
			setInnerFlag("Y");
		}
		else {
			setInnerFlag("N");
		}
	}

	// public long getChargeID() {
	// return getDChargeID().longValue();
	// }
	//
	// public void setChargeID(long chargeID) {
	// setDChargeID(new Long(chargeID));
	// }

	// CMP Fields :
	public abstract Long getSubLimitPK();

	public abstract void setSubLimitPK(Long subLimitID);

	public abstract Double getDActiveAmount();

	public abstract void setDActiveAmount(Double activeAmount);

	public abstract Double getDSubLimitAmount();

	public abstract void setDSubLimitAmount(Double subLimitAmount);

	public abstract String getSubLimitCCY();

	public abstract void setSubLimitCCY(String subLimitCCY);

	public abstract String getSubLimitType();

	public abstract void setSubLimitType(String subLimitType);

	public abstract long getGroupID();

	public abstract void setGroupID(long groupID);

	public abstract long getCommonRef();

	public abstract void setCommonRef(long commonRef);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract String getInnerFlag();

	public abstract void setInnerFlag(String innerFlag);

	// public abstract Long getDChargeID();
	//
	// public abstract void setDChargeID(Long chargeID);

	protected long generateSubLimitPK() throws CreateException {
		DefaultLogger.debug(this, "generateSubLimitPK");
		String sequenceName = ICMSConstant.SEQUENCE_CMD_SUBLIMIT_SEQ;
		try {
			String seq = new SequenceManager().getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			throw new CreateException("Exception in generating Sequence '" + sequenceName + "' \n The exception is : "
					+ e);
		}
	}

	// private void checkVersionMismatch(ISubLimit value)
	// throws VersionMismatchException {
	// if (getVersionTime() != value.getVersionTime())
	// throw new VersionMismatchException("Mismatch timestamp! "
	// + value.getVersionTime());
	// }

	private String formatAmt(Double amt) {
		String formatedStr = "";
		try {
			DecimalFormat df = new DecimalFormat("###############");
			formatedStr = df.format(amt);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return formatedStr;
	}
}
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBInstrumentBean implements IInstrument, EntityBean {

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getInstrumentID" };

	public long getInstrumentID() {
		return getEBInstrumentID().longValue();
	}

	public void setInstrumentID(long instrumentID) {
		setEBInstrumentID(new Long(instrumentID));
	}

	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	public abstract Long getEBInstrumentID();

	public abstract void setEBInstrumentID(Long eBInstrumentID);

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public IInstrument getValue() {
		IInstrument aInstrument = new OBInstrument();
		AccessorUtil.copyValue(this, aInstrument);
		return aInstrument;
	}

	public void setValue(IInstrument aInstrument) {
		AccessorUtil.copyValue(aInstrument, this, EXCLUDE_METHOD);
	}

	public Long ejbCreate(IInstrument aInstrument) throws CreateException {
		try {
			AccessorUtil.copyValue(aInstrument, this, EXCLUDE_METHOD);
			Long pk = new Long(getInstrumentIDSEQ());
			// DefaultLogger.debug(this, "PK : " + pk);
			// DefaultLogger.debug(this, "Collateral : "
			// + aInstrument.getCollateralID());
			// DefaultLogger.debug(this, "Code : "
			// + aInstrument.getInstrumentCode());
			setEBInstrumentID(pk);
			return null;
		}
		catch (Exception e) {
			throw new CreateException(e.getMessage());
		}
	}

	protected String getInstrumentIDSEQ() throws Exception {
		return (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_SECURITY_INSTRUMENT, true);
	}

	public void ejbPostCreate(IInstrument aInstrument) {
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

	public void ejbRemove() {
	}

    public abstract String getInstrumentCode();

    public abstract void setInstrumentCode(String instrumentCode);
}

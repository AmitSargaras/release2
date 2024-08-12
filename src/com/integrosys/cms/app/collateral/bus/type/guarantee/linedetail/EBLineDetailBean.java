package com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBLineDetailBean implements EntityBean, ILineDetail {
	
	private static final String SEQUENCE_NAME = "LINE_DETAIL_SEQ";
	
	private static final String[] EXCLUDE_METHOD = new String[] { "getLineDetailID", "getCollateralID", "getRefId" };

	
	protected EntityContext context = null;
	
	public EBLineDetailBean() { }
	
	public long getLineDetailID() {
		if(null != getEBLineDetailID()) {
			return getEBLineDetailID().longValue();
		}
		
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setLineDetailID(long lineDetailID) {
		setEBLineDetailID(lineDetailID);
	}
	
	public long getCollateralID() {
		if (null != getCollateralIdFK()) {
			return getCollateralIdFK().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setCollateralID(long value) {
		setCollateralIdFK(new Long(value));
	}
	
	public abstract Long getCollateralIdFK();
	
	public abstract void setCollateralIdFK(Long value);

	public ILineDetail getValue() {
		OBLineDetail ob = new OBLineDetail();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}
	
	public void setValue(ILineDetail lineDetail) {
		AccessorUtil.copyValue(lineDetail, this, EXCLUDE_METHOD);
	}
	
	public Long ejbCreate(ILineDetail lineDetail)  throws CreateException {
		if (null == lineDetail) {
			throw new CreateException("ILineDetail is null!");
		}
		try {
			long lineDetailID = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));			
			AccessorUtil.copyValue(lineDetail, this, EXCLUDE_METHOD);
			setEBLineDetailID(Long.valueOf(lineDetailID));	
			if (lineDetail.getRefId() == ICMSConstant.LONG_MIN_VALUE) {
				setRefId(lineDetailID);
			}
			else {
				setRefId(lineDetail.getRefId());
			}
			return Long.valueOf(lineDetailID);	
		}
		catch (Exception e) {
			e.printStackTrace();
			context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}
	
	public void ejbPostCreate(ILineDetail lineDetail) {	}
	
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}
	
	public void unsetEntityContext() {
		this.context = null;
	}
	
	public abstract Long getEBLineDetailID();
	public abstract void setEBLineDetailID(Long eBLineDetailID);
	
	public abstract String getFacilityName();
	public abstract void setFacilityName(String facilityName);
	
	public abstract String getFacilityID();
	public abstract void setFacilityID(String facilityID);

	public abstract String getLineNo();
	public abstract void setLineNo(String lineNo);

	public abstract String getSerialNo();
	public abstract void setSerialNo(String serialNo);

	public abstract Long getLcnNo();
	public abstract void setLcnNo(Long lcnNo);

	public abstract BigDecimal getLineLevelSecurityOMV();
	public abstract void setLineLevelSecurityOMV(BigDecimal lineLevelSecurityOMV);
	
	public abstract long getRefId();
	public abstract void setRefId(long refId);

	public void ejbActivate() { }

	public void ejbLoad() {	}

	public void ejbPassivate() { }

	public void ejbRemove() { }

	public void ejbStore() { }

}

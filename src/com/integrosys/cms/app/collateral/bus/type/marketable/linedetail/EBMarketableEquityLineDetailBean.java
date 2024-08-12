package com.integrosys.cms.app.collateral.bus.type.marketable.linedetail;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public abstract class EBMarketableEquityLineDetailBean implements EntityBean, IMarketableEquityLineDetail {
	
	private static final String SEQUENCE_NAME = "LINE_DETAIL_SEQ";
	
	private static final String[] EXCLUDE_METHOD = new String[] { "getLineDetailId", "getMarketableEquityId", "getRefID"};

	
	protected EntityContext context = null;
	
	public EBMarketableEquityLineDetailBean() { }
	
	public long getLineDetailId() {
		if(null != getEBMarketableEquityLineDetailId()) {
			return getEBMarketableEquityLineDetailId().longValue();
		}
		
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public void setLineDetailId(long lineDetailID) {
		setEBMarketableEquityLineDetailId(lineDetailID);
	}
	
	/*public long getCollateralId() {
		if (null != getCollateralIdFK()) {
			return getCollateralIdFK().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setCollateralID(long value) {
		setCollateralIdFK(new Long(value));
	}*/
	
	//Equity
	public long getMarketableEquityId() {
		if (null != getMarketableEquityIdFK()) {
			return getMarketableEquityIdFK().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}
	
	public void setMarketableEquityId(long value) {
		setMarketableEquityIdFK(new Long(value));
	}
	
	public abstract Long getMarketableEquityIdFK();
	
	public abstract void setMarketableEquityIdFK(Long value);

	public IMarketableEquityLineDetail getValue() {
		OBMarketableEquityLineDetail ob = new OBMarketableEquityLineDetail();
		AccessorUtil.copyValue(this, ob);
		return ob;
	}
	
	public void setValue(IMarketableEquityLineDetail lineDetail) {
		AccessorUtil.copyValue(lineDetail, this, EXCLUDE_METHOD);
	}
	
	public Long ejbCreate(IMarketableEquityLineDetail lineDetail)  throws CreateException {
		if (null == lineDetail) {
			throw new CreateException("IMarketableEquityLineDetail is null!");
		}
		try {
			long lineDetailID = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));			
			AccessorUtil.copyValue(lineDetail, this, EXCLUDE_METHOD);
			setEBMarketableEquityLineDetailId(Long.valueOf(lineDetailID));	
			if (lineDetail.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(lineDetailID);
			}
			else {
				setRefID(lineDetail.getRefID());
			}
			return Long.valueOf(lineDetailID);	
		}
		catch (Exception e) {
			e.printStackTrace();
			context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}
	
	public void ejbPostCreate(IMarketableEquityLineDetail lineDetail) {	}
	
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}
	
	public void unsetEntityContext() {
		this.context = null;
	}
	
	public abstract Long getEBMarketableEquityLineDetailId();

	public abstract void setEBMarketableEquityLineDetailId(Long eBLineDetailID);
	
	/*public abstract long getCollateralId();
	public abstract void setCollateralId(long value);*/
	
	public abstract String getFacilityName();
	public abstract void setFacilityName(String facilityName);
	
	public abstract String getFacilityId();
	public abstract void setFacilityId(String facilityID);

	public abstract String getLineNumber();
	public abstract void setLineNumber(String lineNo);

	public abstract String getSerialNumber();
	public abstract void setSerialNumber(String serialNo);
	
	public abstract String getFasNumber();
	public abstract void setFasNumber(String fasNumber);
	public abstract Double getLtv();
	public abstract void setLtv(Double ltv) ;
	
	public abstract String getRemarks() ;
	public abstract void setRemarks(String remarks);
	
	public abstract long getRefID();
	public abstract void setRefID(long refId);
	
	public abstract BigDecimal getLineValue();
	public abstract void setLineValue(BigDecimal lineValue); 

	
	public abstract String getLineEquityUniqueID();

	public abstract void setLineEquityUniqueID(String lineEquityUniqueID);
	
	public void ejbActivate() { }

	public void ejbLoad() {	}

	public void ejbPassivate() { }

	public void ejbRemove() { }

	public void ejbStore() { }

}

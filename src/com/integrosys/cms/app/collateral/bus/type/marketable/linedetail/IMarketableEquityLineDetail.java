package com.integrosys.cms.app.collateral.bus.type.marketable.linedetail;

import java.io.Serializable;
import java.math.BigDecimal;

public interface IMarketableEquityLineDetail extends Serializable{

	public long getLineDetailId();
	public void setLineDetailId(long lineDetailId);
	public long getRefID();
	public void setRefID(long refID);
	public String getLineNumber();
	public void setLineNumber(String lineNumber);
	public String getSerialNumber() ;
	public void setSerialNumber(String serialNumber) ;
	public String getFasNumber();
	public void setFasNumber(String fasNumber);
	public Double getLtv();
	public void setLtv(Double ltv) ;
	
	public String getRemarks() ;
	public void setRemarks(String remarks);
	public String getFacilityName();
	public void setFacilityName(String facilityName);
	public String getFacilityId();
	public void setFacilityId(String facilityId);
	
	/*public long getCollateralId();
	public void setCollateralId(long collateralId);*/
	
	public long getMarketableEquityId();
	public void setMarketableEquityId(long marketableEquityId);
	
	public BigDecimal getLineValue();
	public void setLineValue(BigDecimal lineValue); 
	
	public String getLineEquityUniqueID();

	public void setLineEquityUniqueID(String lineEquityUniqueID);
}

package com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail;

import java.io.Serializable;
import java.math.BigDecimal;

public interface ILineDetail extends Serializable{

	public long getLineDetailID();
	
	public void setLineDetailID(long lineDetailID);
	
	public long getCollateralID();
	
	public void setCollateralID(long collateralID);
	
	public String getFacilityName();
	
	public void setFacilityName(String facilityName);
	
	public String getFacilityID();
	
	public void setFacilityID(String facilityID);
	
	public String getLineNo();
	
	public void setLineNo(String lineNo);
	
	public String getSerialNo();
	
	public void setSerialNo(String serialNo);
		
	public Long getLcnNo();
	
	public void setLcnNo(Long lcnNo);
	
	public BigDecimal getLineLevelSecurityOMV();
	
	public void setLineLevelSecurityOMV(BigDecimal lineLevelSecurityOMV);
	
	public long getRefId();

	public void setRefId(long refId);
	
}

package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

	public interface INpaDataLog extends Serializable {
	
		public long getId();
		public void setId(long id);
		
		public Date getReportingDate();
		public void setReportingDate(Date reportingDate);
		
		public String getSystem();
		public void setSystem(String system);
		
		public String getPartyID();
		public void setPartyID(String partyID);
		
		public String getCollateralType();
		public void setCollateralType(String collateralType);
		
		public Date getValuationDate();
		public void setValuationDate(Date valuationDate);
		
		public BigDecimal getValuationAmount();
		public void setValuationAmount(BigDecimal valuationAmount);
		
		public Double getOriginalValue();
		public void setOriginalValue(Double originalValue);
		
		public String getStartDate();
		public void setStartDate(String startDate);
		
		public String getMaturityDate();
		public void setMaturityDate(String maturityDate);
		
		public String getFileName();
		public void setFileName(String fileName);
		
		public String getStatus();
		public void setStatus(String status);
	
}

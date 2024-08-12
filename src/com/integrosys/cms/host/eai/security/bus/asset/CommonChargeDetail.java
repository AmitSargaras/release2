package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

public class CommonChargeDetail implements Serializable {

	private String physicalInspection;

	private Long physicalInspectionFrequencyUnit;

	private String physicalInspectionFrequencyUOM;

	private Date lastPhysicalInspectionDate;

	private String environmentallyRiskyStatus;

	private Date environmentallyRiskyDate;

	private String environmentallyRiskyRemark;
	
	private String physicalInspectionStatus; 

	public String getPhysicalInspection() {
		return physicalInspection;
	}

	public void setPhysicalInspection(String physicalInspection) {
		this.physicalInspection = physicalInspection;
	}

	public Long getPhysicalInspectionFrequencyUnit() {
		return physicalInspectionFrequencyUnit;
	}

	public void setPhysicalInspectionFrequencyUnit(Long physicalInspectionFrequencyUnit) {
		this.physicalInspectionFrequencyUnit = physicalInspectionFrequencyUnit;
	}

	public String getPhysicalInspectionFrequencyUOM() {
		return physicalInspectionFrequencyUOM;
	}

	public void setPhysicalInspectionFrequencyUOM(String physicalInspectionFrequencyUOM) {
		this.physicalInspectionFrequencyUOM = physicalInspectionFrequencyUOM;
	}

	public Date getJDOLastPhysicalInspectionDate() {
		return lastPhysicalInspectionDate;
	}

	public void setJDOLastPhysicalInspectionDate(Date lastPhysicalInspectionDate) {
		this.lastPhysicalInspectionDate = lastPhysicalInspectionDate;
	}
	
	public String getLastPhysicalInspectionDate() {
		return  MessageDate.getInstance().getString(this.lastPhysicalInspectionDate);
	}

	public void setLastPhysicalInspectionDate(String lastPhysicalInspectionDate) {
		this.lastPhysicalInspectionDate = MessageDate.getInstance().getDate(lastPhysicalInspectionDate);
	}

	public String getEnvironmentallyRiskyStatus() {
		return environmentallyRiskyStatus;
	}

	public void setEnvironmentallyRiskyStatus(String environmentallyRiskyStatus) {
		this.environmentallyRiskyStatus = environmentallyRiskyStatus;
	}

	public Date getJDOEnvironmentallyRiskyDate() {
		return environmentallyRiskyDate;
	}

	public void setJDOEnvironmentallyRiskyDate(Date environmentallyRiskyDate) {
		this.environmentallyRiskyDate = environmentallyRiskyDate;
	}
	
	public String getEnvironmentallyRiskyDate() {
		return  MessageDate.getInstance().getString(this.environmentallyRiskyDate);
	}

	public void setEnvironmentallyRiskyDate(String environmentallyRiskyDate) {
		this.environmentallyRiskyDate = MessageDate.getInstance().getDate(environmentallyRiskyDate);
	}

	public String getEnvironmentallyRiskyRemark() {
		return environmentallyRiskyRemark;
	}

	public void setEnvironmentallyRiskyRemark(String environmentallyRiskyRemark) {
		this.environmentallyRiskyRemark = environmentallyRiskyRemark;
	}

	public String getPhysicalInspectionStatus() {
		return physicalInspectionStatus;
	}

	public void setPhysicalInspectionStatus(String physicalInspectionStatus) {
		this.physicalInspectionStatus = physicalInspectionStatus;
	}
	
	public StandardCode getJDOPhysicalInspectionStatus() {
		StandardCode std = new StandardCode();
		std.setStandardCodeNumber("PHYSICAL_INSPECTION_STATUS");
		std.setStandardCodeValue(physicalInspectionStatus);
		return std;
	}

	public void setJDOPhysicalInspectionStatus(StandardCode std) {
		
		if(std != null)
			this.physicalInspectionStatus = std.getStandardCodeValue() ;
	} 

}

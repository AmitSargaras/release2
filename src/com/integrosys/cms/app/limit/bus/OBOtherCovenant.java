package com.integrosys.cms.app.limit.bus;

import java.util.List;

import com.integrosys.base.techinfra.util.AccessorUtil;

public class OBOtherCovenant implements IOtherCovenant{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4635147509150L;

	private String stagingRefid;
	
	private String displayId;
	
	private String status;
	
	private String covenantType;
	
	
	private String covenantCondition;

	private String faciltyName;
	
	private String finalfaciltyName;
	private String compiled;
	
	private String covenantCategory;
	
	private String advised;
	
	private String monitoringResponsibilityList1;
	
	private  String monitoringResponsibilityList2;
	
	private String covenantDescription;
	
	private String remarks;
	
	private String MonitoringResponsibiltyValue;
	
	private String FacilityNameValue;
	
	private String targetDate;
	
	private long otherCovenantId;
	private String previousStagingId;
	private  String CustRef;
	public String IsUpdate;
	
	public String getStagingRefid() {
		return stagingRefid;
	}
	public void setStagingRefid(String stagingRefid) {
		 this.stagingRefid = stagingRefid;
	}
	public String getIsUpdate() {
		return IsUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		IsUpdate = isUpdate;
	}
	public String getPreviousStagingId() {
		return previousStagingId;
	}
	public void setPreviousStagingId(String previousStagingId) 
	{
		this.previousStagingId=previousStagingId;
	}
	
	public String getCovenantCategory() {
		return covenantCategory;
	}
	public void setCovenantCategory(String covenantCategory) {
		this.covenantCategory = covenantCategory;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDisplayId() {
		return displayId;
	}
	public void setDisplayId(String displayId) {
		this.displayId = displayId;
	}
	public String getMonitoringResponsibilityList1() {
		return monitoringResponsibilityList1;
	}
	public void setMonitoringResponsibilityList1(
			String monitoringResponsibilityList1) {
		this.monitoringResponsibilityList1 = monitoringResponsibilityList1;
	}
	public String getMonitoringResponsibilityList2() {
		return monitoringResponsibilityList2;
	}
	public void setMonitoringResponsibilityList2(
			String finalmonitoringresp) {
		this.monitoringResponsibilityList2 = finalmonitoringresp;
	}

	public String getCovenantCondition() {
		return covenantCondition;
	}
	public void setCovenantCondition(String covenantCondition) {
		this.covenantCondition = covenantCondition;
	}
	public String getFaciltyName() {
		return faciltyName;
	}
	public void setFaciltyName(String faciltyName) {
		this.faciltyName = faciltyName;
	}
	public String getCompiled() {
		return compiled;
	}
	public void setCompiled(String compiled) {
		this.compiled = compiled;
	}
	public String getAdvised() {
		return advised;
	}
	public void setAdvised(String advised) {
		this.advised = advised;
	}
	public String getCovenantDescription() {
		return covenantDescription;
	}
	public void setCovenantDescription(String covenantDescription) {
		this.covenantDescription = covenantDescription;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public OBOtherCovenant(IOtherCovenant value)
	{
		this();
		AccessorUtil.copyValue(value, this);
	}
	public OBOtherCovenant() {
	}
	
	 public String getCustRef() {
			return CustRef;
		}

		
		public void setCustRef(String CustRef) {
			this.CustRef = CustRef;
			
		}

	public String getCovenantType() {
		return covenantType;
	}

	public void setCovenantType(String covenantType) {
		this.covenantType = covenantType;
	}
	@Override
	public long getOtherCovenantId() {
		return otherCovenantId;
	}
	@Override
	public void setOtherCovenantId(long otherCovenantId) {
		this.otherCovenantId=otherCovenantId;
	}
	@Override
	public String getTargetDate() {
		// TODO Auto-generated method stub
		return targetDate;
	}
	@Override
	public void setTargetDate(String targetDate) {
		this.targetDate= targetDate;		
	}
	@Override
	public String getMonitoringResponsibiltyValue() {
		return MonitoringResponsibiltyValue;
	}
	@Override
	public void setMonitoringResponsibiltyValue(
			String monitoringResponsibiltyValue) {
		MonitoringResponsibiltyValue = monitoringResponsibiltyValue;
	}
	@Override
	public String getFacilityNameValue() {
		return FacilityNameValue;
	}
	@Override
	public void setFacilityNameValue(String facilityNameValue) {
		FacilityNameValue = facilityNameValue;
	}
	public String getFinalfaciltyName() {
		return finalfaciltyName;
	}
	public void setFinalfaciltyName(String finalfaciltyName) {
		this.finalfaciltyName = finalfaciltyName;
	}

	
	
	
}

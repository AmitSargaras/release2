package com.integrosys.cms.app.ws.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class OtherCovenantDetailsListRequestDTO {

	// New CR CAM Online Begin
	@XmlElement(name = "covenantType",required=true)
	private String covenantType;
	@XmlElement(name = "covenantCondition",required=true)
	private String covenantCondition;
	@XmlElement(name = "compiled",required=true)
	private String compiled;
	@XmlElement(name = "advised",required=true)
	private String advised;
	@XmlElement(name = "targetDate",required=true)
	private String targetDate;	
	@XmlElement(name = "CovenantCategory",required=true)
	private String CovenantCategory;
	@XmlElement(name = "monitoringResponsibiltyComboBoxList",required=true)
	private List<CAMMonitoringResponsibilityRequestDTO> monitoringResponsibiltyComboBoxList;
	@XmlElement(name = "facilityComboBoxList",required=true)
	private List<CAMFacilityNameRequestDTO> facilityNameComboBoxList;
	@XmlElement(name = "covenantDescription",required=true)
	private String covenantDescription;
	@XmlElement(name = "remarks",required=true)
	private String remarks;
	
	public String getCovenantType() {
		return covenantType;
	}
	public void setCovenantType(String covenantType) {
		this.covenantType = covenantType;
	}
	public String getCovenantCondition() {
		return covenantCondition;
	}
	public void setCovenantCondition(String covenantCondition) {
		this.covenantCondition = covenantCondition;
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
	public String getTargetDate() {
		return targetDate;
	}
	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}
	public String getCovenantCategory() {
		return CovenantCategory;
	}
	public void setCovenantCategory(String covenantCategory) {
		CovenantCategory = covenantCategory;
	}
	public List<CAMMonitoringResponsibilityRequestDTO> getMonitoringResponsibiltyComboBoxList() {
		return monitoringResponsibiltyComboBoxList;
	}
	public void setMonitoringResponsibiltyComboBoxList(
			List<CAMMonitoringResponsibilityRequestDTO> monitoringResponsibiltyComboBoxList) {
		this.monitoringResponsibiltyComboBoxList = monitoringResponsibiltyComboBoxList;
	}
	public List<CAMFacilityNameRequestDTO> getFacilityNameComboBoxList() {
		return facilityNameComboBoxList;
	}
	public void setFacilityNameComboBoxList(List<CAMFacilityNameRequestDTO> facilityNameComboBoxList) {
		this.facilityNameComboBoxList = facilityNameComboBoxList;
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
	
	
}

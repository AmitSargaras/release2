package com.integrosys.cms.app.ws.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.struts.action.ActionErrors;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Bhushan$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "FacilityInfo")
public class AdhocFacilityRequestDTO extends RequestDTO {
	
	@XmlElement(name = "camId",required=true)
	private String camId;
	
	@XmlElement(name = "climsFacilityId",required=true)
	private String climsFacilityId;
	
	@XmlElement(name = "camType",required=true)
	private String camType;
	
	@XmlElement(name = "adhocFacility",required=true)
	private String adhocFacility;
	
	@XmlElement(name = "adhocLastAvailDate",required=true)
	private String adhocLastAvailDate;
	
	@XmlElement(name = "multiDrawDownAllow",required=true)
	private String multiDrawDownAllow;
	
	@XmlElement(name = "adhocTenor",required=true)
	private String adhocTenor;
	
	@XmlElement(name = "adhocFacilityExpDate",required=true)
	private String adhocFacilityExpDate;
	
	@XmlElement(name = "generalPurposeLoan",required=true)
	private String generalPurposeLoan;
	
	
	@XmlTransient
	private ActionErrors errors;
	
	@XmlTransient
	private String event;

	public String getCamId() {
		return camId;
	}

	public void setCamId(String camId) {
		this.camId = camId;
	}

	public String getClimsFacilityId() {
		return climsFacilityId;
	}

	public void setClimsFacilityId(String climsFacilityId) {
		this.climsFacilityId = climsFacilityId;
	}

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

	public String getAdhocFacility() {
		return adhocFacility;
	}

	public void setAdhocFacility(String adhocFacility) {
		this.adhocFacility = adhocFacility;
	}

	public String getAdhocLastAvailDate() {
		return adhocLastAvailDate;
	}

	public void setAdhocLastAvailDate(String adhocLastAvailDate) {
		this.adhocLastAvailDate = adhocLastAvailDate;
	}

	public String getMultiDrawDownAllow() {
		return multiDrawDownAllow;
	}

	public void setMultiDrawDownAllow(String multiDrawDownAllow) {
		this.multiDrawDownAllow = multiDrawDownAllow;
	}

	public String getAdhocTenor() {
		return adhocTenor;
	}

	public void setAdhocTenor(String adhocTenor) {
		this.adhocTenor = adhocTenor;
	}

	public String getAdhocFacilityExpDate() {
		return adhocFacilityExpDate;
	}

	public void setAdhocFacilityExpDate(String adhocFacilityExpDate) {
		this.adhocFacilityExpDate = adhocFacilityExpDate;
	}

	public String getGeneralPurposeLoan() {
		return generalPurposeLoan;
	}

	public void setGeneralPurposeLoan(String generalPurposeLoan) {
		this.generalPurposeLoan = generalPurposeLoan;
	}

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	

		

}
/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.host.eai.security.bus.asset;

import java.io.Serializable;
import java.util.Date;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents Air Craft Detail of Asset Based.
 * 
 * @author $Author: shphoon $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class AirCraftDetail implements Serializable {
	// AirCraft Detail
	private String insuranceBrokerUnderTake;

	private String processAgent;

	private String processAgentCountry;

	private String specialistLegalOpinion;

	private StandardCode exportCreditAgency;

	private String guarantors;

	private StandardCode insurers;

	private String aircraftSerialNo;

	private String manufacturerName;

	private String manufactureWarranties;

	private Long manufactureYear;

	private String assignors;

	private String insureAssignment;

	private Double amountAssignmentIsure;

	private Date effectiveDateAssignment;

	private Date expiryDateAssignment;

	private String reinsureAssignment;

	private Date effectiveDateReinsure;

	private Date expiryDateReinsure;

	private StandardCode aircraftType;

	private StandardCode model;

	/**
	 * Default constructor.
	 */
	public AirCraftDetail() {
		super();
	}

	public String getAircraftSerialNo() {
		return aircraftSerialNo;
	}

	public StandardCode getAircraftType() {
		return aircraftType;
	}

	public Double getAmountAssignmentIsure() {
		return amountAssignmentIsure;
	}

	public String getAssignors() {
		return assignors;
	}

	public String getEffectiveDateAssignment() {
		return MessageDate.getInstance().getString(effectiveDateAssignment);
	}

	public String getEffectiveDateReinsure() {
//		System.out.println("Geting " + MessageDate.getInstance().getString(this.effectiveDateReinsure));
		return MessageDate.getInstance().getString(this.effectiveDateReinsure);
	}

	public String getExpiryDateAssignment() {
		return MessageDate.getInstance().getString(expiryDateAssignment);
	}

	public String getExpiryDateReinsure() {
		return MessageDate.getInstance().getString(expiryDateReinsure);
	}

	public StandardCode getExportCreditAgency() {
		return exportCreditAgency;
	}

	public String getGuarantors() {
		return guarantors;
	}

	public String getInsuranceBrokerUnderTake() {
		return insuranceBrokerUnderTake;
	}

	public String getInsureAssignment() {
		return insureAssignment;
	}

	public StandardCode getInsurers() {
		return insurers;
	}

	public Date getJDOEffectiveDateAssignment() {
		return effectiveDateAssignment;
	}

	public Date getJDOEffectiveDateReinsure() {
		return effectiveDateReinsure;
	}

	public Date getJDOExpiryDateAssignment() {
		return expiryDateAssignment;
	}

	public Date getJDOExpiryDateReinsure() {
		return expiryDateReinsure;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public String getManufactureWarranties() {
		return manufactureWarranties;
	}

	public Long getManufactureYear() {
		return manufactureYear;
	}

	public StandardCode getModel() {
		return model;
	}

	public String getProcessAgent() {
		return processAgent;
	}

	public String getProcessAgentCountry() {
		return processAgentCountry;
	}

	public String getReinsureAssignment() {
		return reinsureAssignment;
	}

	public String getSpecialistLegalOpinion() {
		return specialistLegalOpinion;
	}

	public void setAircraftSerialNo(String aircraftSerialNo) {
		this.aircraftSerialNo = aircraftSerialNo;
	}

	public void setAircraftType(StandardCode aircraftType) {
		this.aircraftType = aircraftType;
	}

	public void setAmountAssignmentIsure(Double amountAssignmentIsure) {
		this.amountAssignmentIsure = amountAssignmentIsure;
	}

	public void setAssignors(String assignors) {
		this.assignors = assignors;
	}

	public void setEffectiveDateAssignment(String effectiveDateAssignment) {
		this.effectiveDateAssignment = MessageDate.getInstance().getDate(effectiveDateAssignment);
	}

	public void setEffectiveDateReinsure(String effectiveDateReinsure) {
		this.effectiveDateReinsure = MessageDate.getInstance().getDate(effectiveDateReinsure);
	}

	public void setExpiryDateAssignment(String expiryDateAssignment) {
		this.expiryDateAssignment = MessageDate.getInstance().getDate(expiryDateAssignment);
	}

	public void setExpiryDateReinsure(String expiryDateReinsure) {
		this.expiryDateReinsure = MessageDate.getInstance().getDate(expiryDateReinsure);
	}

	public void setExportCreditAgency(StandardCode exportCreditAgency) {
		this.exportCreditAgency = exportCreditAgency;
	}

	public void setGuarantors(String guarantors) {
		this.guarantors = guarantors;
	}

	public void setInsuranceBrokerUnderTake(String insuranceBrokerUnderTake) {
		this.insuranceBrokerUnderTake = insuranceBrokerUnderTake;
	}

	public void setInsureAssignment(String insureAssignment) {
		this.insureAssignment = insureAssignment;
	}

	public void setInsurers(StandardCode insurers) {
		this.insurers = insurers;
	}

	public void setJDOEffectiveDateAssignment(Date effectiveDateAssignment) {
		this.effectiveDateAssignment = effectiveDateAssignment;
	}

	public void setJDOEffectiveDateReinsure(Date effectiveDateReinsure) {
		this.effectiveDateReinsure = effectiveDateReinsure;
	}

	public void setJDOExpiryDateAssignment(Date expiryDateAssignment) {
		this.expiryDateAssignment = expiryDateAssignment;
	}

	public void setJDOExpiryDateReinsure(Date expiryDateReinsure) {
		this.expiryDateReinsure = expiryDateReinsure;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setManufactureWarranties(String manufactureWarranties) {
		this.manufactureWarranties = manufactureWarranties;
	}

	public void setManufactureYear(Long manufactureYear) {
		this.manufactureYear = manufactureYear;
	}

	public void setModel(StandardCode model) {
		this.model = model;
	}

	public void setProcessAgent(String processAgent) {
		this.processAgent = processAgent;
	}

	public void setProcessAgentCountry(String processAgentCountry) {
		this.processAgentCountry = processAgentCountry;
	}

	public void setReinsureAssignment(String reinsureAssignment) {
		this.reinsureAssignment = reinsureAssignment;
	}

	public void setSpecialistLegalOpinion(String specialistLegalOpinion) {
		this.specialistLegalOpinion = specialistLegalOpinion;
	}

}

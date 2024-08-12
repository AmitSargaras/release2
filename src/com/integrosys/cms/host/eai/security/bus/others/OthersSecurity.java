/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/OthersSecurity.java,v 1.1 2004/07/05 03:05:23 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.others;

import java.util.Date;

import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents approved security of type Others.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/05 03:05:23 $ Tag: $Name: $
 */
public class OthersSecurity extends ApprovedSecurity {
	private String assetDescription;

	private String physicalInspection;

	private Integer physicalInspectionFrequencyUnit;

	private String physicalInspectionFrequencyUOM;

	private Date lastPhysicalInspectionDate;

	private Date nextPhysicalInspectionDate;

	private String environmentallyRiskyStatus;

	private Double numberOfUnits;

	private Date environmentallyRiskyConfirmedDate;

	private String environmentallyRiskyRemarks;

	public OthersSecurity() {
		super();
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getPhysicalInspection() {
		return physicalInspection;
	}

	public void setPhysicalInspection(String physicalInspection) {
		this.physicalInspection = physicalInspection;
	}

	public Integer getPhysicalInspectionFrequencyUnit() {
		return physicalInspectionFrequencyUnit;
	}

	public void setPhysicalInspectionFrequencyUnit(Integer physicalInspectionFrequencyUnit) {
		this.physicalInspectionFrequencyUnit = physicalInspectionFrequencyUnit;
	}

	public String getPhysicalInspectionFrequencyUOM() {
		return physicalInspectionFrequencyUOM;
	}

	public void setPhysicalInspectionFrequencyUOM(String physicalInspectionFrequencyUOM) {
		this.physicalInspectionFrequencyUOM = physicalInspectionFrequencyUOM;
	}

	public String getLastPhysicalInspectionDate() {
		return MessageDate.getInstance().getString(lastPhysicalInspectionDate);
	}

	public void setLastPhysicalInspectionDate(String lastPhysicalInspectionDate) {
		this.lastPhysicalInspectionDate = MessageDate.getInstance().getDate(lastPhysicalInspectionDate);
	}

	public Date getJDOLastPhysicalInspectionDate() {
		return lastPhysicalInspectionDate;
	}

	public void setJDOLastPhysicalInspectionDate(Date lastPhysicalInspectionDate) {
		this.lastPhysicalInspectionDate = lastPhysicalInspectionDate;
	}

	public String getNextPhysicalInspectionDate() {
		return MessageDate.getInstance().getString(nextPhysicalInspectionDate);
	}

	public void setNextPhysicalInspectionDate(String nextPhysicalInspectionDate) {
		this.nextPhysicalInspectionDate = MessageDate.getInstance().getDate(nextPhysicalInspectionDate);
	}

	public Date getJDONextPhysicalInspectionDate() {
		return nextPhysicalInspectionDate;
	}

	public void setJDONextPhysicalInspectionDate(Date nextPhysicalInspectionDate) {
		this.nextPhysicalInspectionDate = nextPhysicalInspectionDate;
	}

	public String getEnvironmentallyRiskyStatus() {
		return environmentallyRiskyStatus;
	}

	public void setEnvironmentallyRiskyStatus(String environmentallyRiskyStatus) {
		this.environmentallyRiskyStatus = environmentallyRiskyStatus;
	}

	public String getEnvironmentallyRiskyConfirmedDate() {
		return MessageDate.getInstance().getString(environmentallyRiskyConfirmedDate);
	}

	public void setEnvironmentallyRiskyConfirmedDate(String environmentallyRiskyConfirmedDate) {
		this.environmentallyRiskyConfirmedDate = MessageDate.getInstance().getDate(environmentallyRiskyConfirmedDate);
	}

	public Date getJDOEnvironmentallyRiskyConfirmedDate() {
		return environmentallyRiskyConfirmedDate;
	}

	public void setJDOEnvironmentallyRiskyConfirmedDate(Date environmentallyRiskyConfirmedDate) {
		this.environmentallyRiskyConfirmedDate = environmentallyRiskyConfirmedDate;
	}

	public String getEnvironmentallyRiskyRemarks() {
		return environmentallyRiskyRemarks;
	}

	public void setEnvironmentallyRiskyRemarks(String environmentallyRiskyRemarks) {
		this.environmentallyRiskyRemarks = environmentallyRiskyRemarks;
	}

	public Double getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Double numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

}

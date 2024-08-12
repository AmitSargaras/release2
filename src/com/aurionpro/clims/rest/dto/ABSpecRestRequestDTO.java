/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBCollateral.java,v 1.35 2006/09/15 08:30:10 hshii Exp $
 */
package com.aurionpro.clims.rest.dto;

public class ABSpecRestRequestDTO {
	
	private String isPhysicalInspection;
	
	
	private String physicalInspectionFreq;
	private String ramId;
	private String isSSC;
	

	private String maturityDate; //AssetAircraft
	private String lastPhysicalInspectDate;
	private String goodStatus;
	private String nextPhysicalInspectDate;
	private String startDate;
	private String scrapValue;	
	private String envRiskyStatus;
	private String remarks;
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getScrapValue() {
		return scrapValue;
	}

	public void setScrapValue(String scrapValue) {
		this.scrapValue = scrapValue;
	}

	private String envRiskyDate;
	public String getRamId() {
		return ramId;
	}

	public String getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}

	public void setPhysicalInspectionFreq(String physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}

	public String getGoodStatus() {
		return goodStatus;
	}

	public void setGoodStatus(String goodStatus) {
		this.goodStatus = goodStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEnvRiskyStatus() {
		return envRiskyStatus;
	}

	public void setEnvRiskyStatus(String envRiskyStatus) {
		this.envRiskyStatus = envRiskyStatus;
	}

	public String getEnvRiskyDate() {
		return envRiskyDate;
	}

	public void setEnvRiskyDate(String envRiskyDate) {
		this.envRiskyDate = envRiskyDate;
	}

	public String getIsPhysicalInspection() {
		return isPhysicalInspection;
	}

	public void setIsPhysicalInspection(String isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}

	public String getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}

	public void setLastPhysicalInspectDate(String lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}

	public String getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}

	public void setNextPhysicalInspectDate(String nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public void setRamId(String ramId) {
		this.ramId = ramId;
	}

	public String isPhysicalInspection() {
		return isPhysicalInspection;
	}

	public void setPhysicalInspection(String isPhysicalInspection) {
		this.isPhysicalInspection = isPhysicalInspection;
	}
	
	public String getIsSSC() {
		return isSSC;
	}

	public void setIsSSC(String isSSC) {
		this.isSSC = isSSC;
	}
	
	
}
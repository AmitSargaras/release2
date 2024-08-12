/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.whatifana;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * The ActionForm for What If Conditions Report
 * @author Siew Kheat
 * @author Chong Jun Yong
 */
public class WhatIfCondReportForm extends CommonForm {

	private static final long serialVersionUID = -8481531915659655393L;

	private String reportName = "";

	private String reportType = "";

	private String sample = "";

	private String indexType = "";

	private String directionIndexType = "";

	private String percentageIndexType = "";

	private String make = "";

	private String directionMake = "";

	private String percentageMake = "";

	private String goldGrade = "";

	private String directionGoldGrade = "";

	private String percentageGoldGrade = "";

	private String state = "";

	private String directionState = "";

	private String percentageState = "";

	private String[] checks = new String[0];

	private String district = "";

	private String mukim = "";

	private String stockCode = "";

	private String model = "";

	private String yearOfManufacture = "";

	private String reportFormatProperty = "";

	private String reportFormatStockIndex = "";

	private String reportFormatVehicle = "";

	private String reportFormatGold = "";

	public static final String MAPPER = "com.integrosys.cms.ui.whatifana.WhatIfCondReportMapper";

	public String[] getChecks() {
		return checks;
	}

	public String getStockCode() {
		return stockCode;
	}

	/**
	 * @return the directionGoldGrade
	 */
	public String getDirectionGoldGrade() {
		return directionGoldGrade;
	}

	/**
	 * @return the directionIndexType
	 */
	public String getDirectionIndexType() {
		return directionIndexType;
	}

	/**
	 * @return the directionMake
	 */
	public String getDirectionMake() {
		return directionMake;
	}

	/**
	 * @return the directionState
	 */
	public String getDirectionState() {
		return directionState;
	}

	public String getDistrict() {
		return district;
	}

	/**
	 * @return the goldGrade
	 */
	public String getGoldGrade() {
		return goldGrade;
	}

	/**
	 * @return the indexType
	 */
	public String getIndexType() {
		return indexType;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax [(key, MapperClassname)]
	 * 
	 * @return 2-dimensional String Array
	 */
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER } };
	}

	public String getModel() {
		return model;
	}

	public String getMukim() {
		return mukim;
	}

	/**
	 * @return the percentageGoldGrade
	 */
	public String getPercentageGoldGrade() {
		return percentageGoldGrade;
	}

	/**
	 * @return the percentageIndexType
	 */
	public String getPercentageIndexType() {
		return percentageIndexType;
	}

	/**
	 * @return the percentageMake
	 */
	public String getPercentageMake() {
		return percentageMake;
	}

	/**
	 * @return the percentageState
	 */
	public String getPercentageState() {
		return percentageState;
	}

	public String getReportFormatGold() {
		return reportFormatGold;
	}

	public String getReportFormatProperty() {
		return reportFormatProperty;
	}

	public String getReportFormatStockIndex() {
		return reportFormatStockIndex;
	}

	public String getReportFormatVehicle() {
		return reportFormatVehicle;
	}

	public String getReportName() {
		return reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public String getSample() {
		return sample;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	public String getYearOfManufacture() {
		return yearOfManufacture;
	}

	public void setChecks(String[] checks) {
		this.checks = checks;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	/**
	 * @param directionGoldGrade the directionGoldGrade to set
	 */
	public void setDirectionGoldGrade(String directionGoldGrade) {
		this.directionGoldGrade = directionGoldGrade;
	}

	/**
	 * @param directionIndexType the directionIndexType to set
	 */
	public void setDirectionIndexType(String directionIndexType) {
		this.directionIndexType = directionIndexType;
	}

	/**
	 * @param directionMake the directionMake to set
	 */
	public void setDirectionMake(String directionMake) {
		this.directionMake = directionMake;
	}

	/**
	 * @param directionState the directionState to set
	 */
	public void setDirectionState(String directionState) {
		this.directionState = directionState;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @param goldGrade the goldGrade to set
	 */
	public void setGoldGrade(String goldGrade) {
		this.goldGrade = goldGrade;
	}

	/**
	 * @param indexType the indexType to set
	 */
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setMukim(String mukim) {
		this.mukim = mukim;
	}

	/**
	 * @param percentageGoldGrade the percentageGoldGrade to set
	 */
	public void setPercentageGoldGrade(String percentageGoldGrade) {
		this.percentageGoldGrade = percentageGoldGrade;
	}

	/**
	 * @param percentageIndexType the percentageIndexType to set
	 */
	public void setPercentageIndexType(String percentageIndexType) {
		this.percentageIndexType = percentageIndexType;
	}

	/**
	 * @param percentageMake the percentageMake to set
	 */
	public void setPercentageMake(String percentageMake) {
		this.percentageMake = percentageMake;
	}

	/**
	 * @param percentageState the percentageState to set
	 */
	public void setPercentageState(String percentageState) {
		this.percentageState = percentageState;
	}

	public void setReportFormatGold(String reportFormatGold) {
		this.reportFormatGold = reportFormatGold;
	}

	public void setReportFormatProperty(String reportFormatProperty) {
		this.reportFormatProperty = reportFormatProperty;
	}

	public void setReportFormatStockIndex(String reportFormatStockIndex) {
		this.reportFormatStockIndex = reportFormatStockIndex;
	}

	public void setReportFormatVehicle(String reportFormatVehicle) {
		this.reportFormatVehicle = reportFormatVehicle;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public void setYearOfManufacture(String yearOfManufacture) {
		this.yearOfManufacture = yearOfManufacture;
	}

	public String writeCheckedReportType(String reportType) {
		if (reportType.equals(this.reportType)) {
			return "checked";
		}
		else {
			return "";
		}

	}

}

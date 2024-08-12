/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/ConcReportNewForm.java,v 1.2 2003/08/26 05:19:14 btchng Exp $
 */

package com.integrosys.cms.ui.report;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * The ActionForm for Concentration Report -> New.
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/26 05:19:14 $ Tag: $Name: $
 */
public class ConcReportNewForm extends CommonForm {

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String writeCheckedReportType(String reportType) {
		if (reportType.equals(this.reportType)) {
			return "checked";
		}
		else {
			return "";
		}

	}

	public String getCurrencySecTypeListing() {
		return currencySecTypeListing;
	}

	public void setCurrencySecTypeListing(String currencySecTypeListing) {
		this.currencySecTypeListing = currencySecTypeListing;
	}

	public String getPropertyCountryCode() {
		return propertyCountryCode;
	}

	public void setPropertyCountryCode(String propertyCountryCode) {
		this.propertyCountryCode = propertyCountryCode;
	}

	public String getPropertyListing() {
		return propertyListing;
	}

	public void setPropertyListing(String propertyListing) {
		this.propertyListing = propertyListing;
	}

	public String getSecSecTypeListing() {
		return secSecTypeListing;
	}

	public void setSecSecTypeListing(String secSecTypeListing) {
		this.secSecTypeListing = secSecTypeListing;
	}

	public String getSecSecTypeRegion() {
		return secSecTypeRegion;
	}

	public void setSecSecTypeRegion(String secSecTypeRegion) {
		this.secSecTypeRegion = secSecTypeRegion;
	}

	public String getSecStockExDenSecSubtype() {
		return secStockExDenSecSubtype;
	}

	public void setSecStockExDenSecSubtype(String secStockExDenSecSubtype) {
		this.secStockExDenSecSubtype = secStockExDenSecSubtype;
	}

	public String getSecStockExDenSecType() {
		return secStockExDenSecType;
	}

	public void setSecStockExDenSecType(String secStockExDenSecType) {
		this.secStockExDenSecType = secStockExDenSecType;
	}

	public String getSecStockExNumSecSubtype() {
		return secStockExNumSecSubtype;
	}

	public void setSecStockExNumSecSubtype(String secStockExNumSecSubtype) {
		this.secStockExNumSecSubtype = secStockExNumSecSubtype;
	}

	public String getStockExCode() {
		return stockExCode;
	}

	public void setStockExCode(String stockExCode) {
		this.stockExCode = stockExCode;
	}

	public String getStockSharesDenSecType() {
		return stockSharesDenSecType;
	}

	public void setStockSharesDenSecType(String stockSharesDenSecType) {
		this.stockSharesDenSecType = stockSharesDenSecType;
	}

	public String getStockSharesNumSecSubtype() {
		return stockSharesNumSecSubtype;
	}

	public void setStockSharesNumSecSubtype(String stockSharesNumSecSubtype) {
		this.stockSharesNumSecSubtype = stockSharesNumSecSubtype;
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

	public void setChecks(String[] checks) {
		this.checks = checks;
	}

	public String[] getChecks() {
		return checks;
	}

	private String reportName = "";

	private String reportType = "";

	private String sample = "";

	private String stockExCode = "";

	private String stockSharesNumSecSubtype = "";

	private String stockSharesDenSecType = "";

	private String secStockExNumSecSubtype = "";

	private String secStockExDenSecType = "";

	private String secStockExDenSecSubtype = "";

	private String secSecTypeRegion = "";

	private String secSecTypeListing = "";

	private String propertyListing = "";

	private String propertyCountryCode = "";

	private String currencySecTypeListing = "";

	private String[] checks = new String[0];

	public static final String MAPPER = "com.integrosys.cms.ui.report.ConcReportNewMapper";

}

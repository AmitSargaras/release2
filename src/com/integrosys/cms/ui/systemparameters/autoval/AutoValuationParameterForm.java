/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.systemparameters.autoval;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by Auto Valuation Parameters Description: Have set and get method to store
 * the screen value and get the value from other command class
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class AutoValuationParameterForm extends TrxContextForm implements Serializable {

	// private long parameterId;
	private String securityType = "Property";

	private String[] selectedSecuritySubType = new String[0];

	private String[] unselectedSecuritySubType = new String[0];

	private String country;

	private String[] unselectedState = new String[0];

	private String[] selectedState = new String[0];

	private String[] unselectedDistrict = new String[0];

	private String[] selectedDistrict = new String[0];

	private String[] unselectedMukim = new String[0];

	private String[] selectedMukim = new String[0];

	private String postCode;

	private String fromLandArea;

	private String fromLandAreaMeasure;

	private String toLandArea;

	private String toLandAreaMeasure;

	private String fromBuiltUpArea;

	private String fromBuiltUpAreaMeasure;

	private String toBuiltUpArea;

	private String toBuiltUpAreaMeasure;

	private String minCurrentOMV;

	private String omvIndicator;

	private String omvValue;

	private String valuationDescription;

	private String remarks;

	// public long getParameterId() {
	// return parameterId;
	// }
	//
	// public void setParameterId(long parameterId) {
	// this.parameterId = parameterId;
	// }

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String[] getSelectedSecuritySubType() {
		return selectedSecuritySubType;
	}

	public void setSelectedSecuritySubType(String[] selectedSecuritySubType) {
		this.selectedSecuritySubType = selectedSecuritySubType;
	}

	public String[] getUnselectedSecuritySubType() {
		return unselectedSecuritySubType;
	}

	public void setUnselectedSecuritySubType(String[] unselectedSecuritySubType) {
		this.unselectedSecuritySubType = unselectedSecuritySubType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String[] getUnselectedState() {
		return unselectedState;
	}

	public void setUnselectedState(String[] unselectedState) {
		this.unselectedState = unselectedState;
	}

	public String[] getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(String[] selectedState) {
		this.selectedState = selectedState;
	}

	public String[] getUnselectedDistrict() {
		return unselectedDistrict;
	}

	public void setUnselectedDistrict(String[] unselectedDistrict) {
		this.unselectedDistrict = unselectedDistrict;
	}

	public String[] getSelectedDistrict() {
		return selectedDistrict;
	}

	public void setSelectedDistrict(String[] selectedDistrict) {
		this.selectedDistrict = selectedDistrict;
	}

	public String[] getUnselectedMukim() {
		return unselectedMukim;
	}

	public void setUnselectedMukim(String[] unselectedMukim) {
		this.unselectedMukim = unselectedMukim;
	}

	public String[] getSelectedMukim() {
		return selectedMukim;
	}

	public void setSelectedMukim(String[] selectedMukim) {
		this.selectedMukim = selectedMukim;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getFromLandArea() {
		return fromLandArea;
	}

	public void setFromLandArea(String fromLandArea) {
		this.fromLandArea = fromLandArea;
	}

	public String getFromLandAreaMeasure() {
		return fromLandAreaMeasure;
	}

	public void setFromLandAreaMeasure(String fromLandAreaMeasure) {
		this.fromLandAreaMeasure = fromLandAreaMeasure;
	}

	public String getToLandArea() {
		return toLandArea;
	}

	public void setToLandArea(String toLandArea) {
		this.toLandArea = toLandArea;
	}

	public String getToLandAreaMeasure() {
		return toLandAreaMeasure;
	}

	public void setToLandAreaMeasure(String toLandAreaMeasure) {
		this.toLandAreaMeasure = toLandAreaMeasure;
	}

	public String getFromBuiltUpArea() {
		return fromBuiltUpArea;
	}

	public void setFromBuiltUpArea(String fromBuiltUpArea) {
		this.fromBuiltUpArea = fromBuiltUpArea;
	}

	public String getFromBuiltUpAreaMeasure() {
		return fromBuiltUpAreaMeasure;
	}

	public void setFromBuiltUpAreaMeasure(String fromBuiltUpAreaMeasure) {
		this.fromBuiltUpAreaMeasure = fromBuiltUpAreaMeasure;
	}

	public String getToBuiltUpArea() {
		return toBuiltUpArea;
	}

	public void setToBuiltUpArea(String toBuiltUpArea) {
		this.toBuiltUpArea = toBuiltUpArea;
	}

	public String getToBuiltUpAreaMeasure() {
		return toBuiltUpAreaMeasure;
	}

	public void setToBuiltUpAreaMeasure(String toBuiltUpAreaMeasure) {
		this.toBuiltUpAreaMeasure = toBuiltUpAreaMeasure;
	}

	public String getMinCurrentOMV() {
		return minCurrentOMV;
	}

	public void setMinCurrentOMV(String minCurrentOMV) {
		this.minCurrentOMV = minCurrentOMV;
	}

	public String getOmvIndicator() {
		return omvIndicator;
	}

	public void setOmvIndicator(String omvIndicator) {
		this.omvIndicator = omvIndicator;
	}

	public String getOmvValue() {
		return omvValue;
	}

	public void setOmvValue(String omvValue) {
		this.omvValue = omvValue;
	}

	public String getValuationDescription() {
		return valuationDescription;
	}

	public void setValuationDescription(String valuationDescription) {
		this.valuationDescription = valuationDescription;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void reset() {

	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it.
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {
				{ "autoValuationParameter",
						"com.integrosys.cms.ui.systemparameters.autoval.AutoValuationParameterMapper" },
				// {"stgautoValuationParameter",
				// "com.integrosys.cms.ui.systemparameters.autoval.StagingAutoValuationParameterMapper"
				// },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
		return input;

	}

}

/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.systemparameters.propertyindex;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PropertyIdxItemForm extends CommonForm implements Serializable {


	private String idxYear;
    private String idxType;	
    private String idxValue;
    private String state;
    private String[] unselectedDistrict = new String[0];
    private String[] selectedDistrict = new String[0];
    private String[] unselectedMukim = new String[0];
    private String[] selectedMukim = new String[0];
    private String fromEvent;
    private String[] unselectedPropertyType = new String[0];
    private String[] selectedPropertyType = new String[0];
    private String valDesc;
	
	public String getFromEvent() {
		return fromEvent;
	}



	public void setFromEvent(String fromEvent) {
		this.fromEvent = fromEvent;
	}



	public String[] getSelectedDistrict() {
		return selectedDistrict;
	}



	public void setSelectedDistrict(String[] selectedDistrict) {
		this.selectedDistrict = selectedDistrict;
	}



	public String[] getSelectedMukim() {
		return selectedMukim;
	}



	public void setSelectedMukim(String[] selectedMukim) {
		this.selectedMukim = selectedMukim;
	}



	public String[] getUnselectedDistrict() {
		return unselectedDistrict;
	}



	public void setUnselectedDistrict(String[] unselectedDistrict) {
		this.unselectedDistrict = unselectedDistrict;
	}



	public String[] getUnselectedMukim() {
		return unselectedMukim;
	}



	public void setUnselectedMukim(String[] unselectedMukim) {
		this.unselectedMukim = unselectedMukim;
	}


	public String getIdxType() {
		return idxType;
	}



	public void setIdxType(String idxType) {
		this.idxType = idxType;
	}



	public String getIdxValue() {
		return idxValue;
	}



	public void setIdxValue(String idxValue) {
		this.idxValue = idxValue;
	}



	public String getIdxYear() {
		return idxYear;
	}



	public void setIdxYear(String idxYear) {
		this.idxYear = idxYear;
	}



	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = 
		{
			{"PropertyIdxItemForm", "com.integrosys.cms.ui.systemparameters.propertyindex.PropertyIdxItemMapper"},
		};
		return input;
	}

	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String[] getSelectedPropertyType() {
		return selectedPropertyType;
	}



	public void setSelectedPropertyType(String[] selectedPropertyType) {
		this.selectedPropertyType = selectedPropertyType;
	}



	public String[] getUnselectedPropertyType() {
		return unselectedPropertyType;
	}



	public void setUnselectedPropertyType(String[] unselectedPropertyType) {
		this.unselectedPropertyType = unselectedPropertyType;
	}



	public String getValDesc() {
		return valDesc;
	}



	public void setValDesc(String valDesc) {
		this.valDesc = valDesc;
	}
}

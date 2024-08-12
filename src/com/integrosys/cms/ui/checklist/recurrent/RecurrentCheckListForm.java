/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.checklist.recurrent;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;

/**
 * 
 * @author $Author: pratheepa $<br>
 * 
 * @version $Revision: 1.7 $
 * 
 * @since $Date: 2006/02/21 02:24:46 $
 * 
 *        Tag: $Name: $
 */

public class RecurrentCheckListForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = -2108983295844526739L;

	private String limitProfileId = "";

	private String subProfileId = "";

	private String covenantSelect = "";
	
	private String covenantItemDesc = "";

	private String covenantItemRemarks = "";

	private String recurrentItemDesc = "";

	private String recurrentItemRemarks = "";
	
	private String frequency = "";

	private String frequencyUnit = "";

	private String dueDate = "";

	private String index = "";

	private String docEndDate = "";

	private String gracePeriod = "";

	private String gracePeriodUnit = "";

	private String chaserReminder = "false";

	private String oneOff = "false";

	private String lastDocEntryDate = "";

	private String riskTrigger = "false";

	private String fee = "false";
	
	private String isParameterizedDesc = "false";

	private String hasMoreSubItems = "false";
	
	private String noCovenant = "";
	
	private String noRecurrent = "";

	public String getNoCovenant() {
		return noCovenant;
	}

	public void setNoCovenant(String noCovenant) {
		this.noCovenant = noCovenant;
	}

	public String getNoRecurrent() {
		return noRecurrent;
	}

	public void setNoRecurrent(String noRecurrent) {
		this.noRecurrent = noRecurrent;
	}

	public String getDocEndDate() {
		return docEndDate;
	}

	public void setDocEndDate(String docEndDate) {
		this.docEndDate = docEndDate;
	}

	public String getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getGracePeriodUnit() {
		return gracePeriodUnit;
	}

	public void setGracePeriodUnit(String gracePeriodUnit) {
		this.gracePeriodUnit = gracePeriodUnit;
	}

	public String getChaserReminder() {
		return chaserReminder;
	}

	public void setChaserReminder(String chaserReminder) {
		this.chaserReminder = chaserReminder;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getCovenantItemDesc() {
		return covenantItemDesc;
	}

	public void setCovenantItemDesc(String covenantItemDesc) {
		this.covenantItemDesc = covenantItemDesc;
	}

	public String getCovenantItemRemarks() {
		return covenantItemRemarks;
	}

	public void setCovenantItemRemarks(String covenantItemRemarks) {
		this.covenantItemRemarks = covenantItemRemarks;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyUnit() {
		return frequencyUnit;
	}

	public void setFrequencyUnit(String frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
	}

	public String getRecurrentItemDesc() {
		return recurrentItemDesc;
	}

	public void setRecurrentItemDesc(String recurrentItemDesc) {
		this.recurrentItemDesc = recurrentItemDesc;
	}

	public String getRecurrentItemRemarks() {
		return recurrentItemRemarks;
	}

	public void setRecurrentItemRemarks(String recurrentItemRemarks) {
		this.recurrentItemRemarks = recurrentItemRemarks;
	}

	public String getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

	public String getSubProfileId() {
		return subProfileId;
	}

	public void setSubProfileId(String subProfileId) {
		this.subProfileId = subProfileId;
	}

	public String getOneOff() {
		return oneOff;
	}

	public void setOneOff(String oneOff) {
		this.oneOff = oneOff;
	}

	public String getLastDocEntryDate() {
		return lastDocEntryDate;
	}

	public void setLastDocEntryDate(String lastDocEntryDate) {
		this.lastDocEntryDate = lastDocEntryDate;
	}

	public String getHasMoreSubItems() {
		return this.hasMoreSubItems;
	}

	public void setHasMoreSubItems(String hasMoreSubItems) {
		this.hasMoreSubItems = hasMoreSubItems;
	}

	public String getRiskTrigger() {
		return this.riskTrigger;
	}

	public void setRiskTrigger(String riskTrigger) {
		this.riskTrigger = riskTrigger;
	}

	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * 
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it.
	 * 
	 * it has a syntax (keyMapperclassname)
	 * 
	 * 
	 * 
	 * @return two-dimesnional String Array
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "covenantItem", "com.integrosys.cms.ui.checklist.recurrent.CovenantMapper" },

		{ "recurrentItem", "com.integrosys.cms.ui.checklist.recurrent.RecurrentMapper" },

		{ "limitProfile", "com.integrosys.cms.ui.checklist.recurrent.LimitProfileMapper" },

		};

		return input;

	}

	public String getCovenantSelect() {
		return covenantSelect;
	}

	public void setCovenantSelect(String covenantSelect) {
		this.covenantSelect = covenantSelect;
	}

	public String getIsParameterizedDesc() {
		return isParameterizedDesc;
	}

	public void setIsParameterizedDesc(String isParameterizedDesc) {
		this.isParameterizedDesc = isParameterizedDesc;
	}

}

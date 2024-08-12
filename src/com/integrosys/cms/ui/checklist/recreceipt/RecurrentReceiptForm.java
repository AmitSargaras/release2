/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.checklist.recreceipt;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;

/**
 * 
 * @author $Author: pratheepa $<br>
 * 
 * @version $Revision: 1.7 $
 * 
 * @since $Date: 2006/02/27 02:34:05 $
 * 
 *        Tag: $Name: $
 */

public class RecurrentReceiptForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = 2432984688975910618L;

	// Common form fields
	private String limitProfileId = "";

	private String subProfileId = "";

	private String index = "";

	// Covenant form fields
	private String covenantItemDesc = "";

	private String covenantItemRemarks = "";

	private String dateChecked = "";

	private boolean isVerified;

	// Recurrent form fields
	private String recurrentItemDesc = "";

	private String recurrentItemRemarks = "";

	private String frequency = "";

	private String frequencyUnit = "";

	private String dueDate = "";

	private String dateReceived = "";

	private String recurrentStatus = "";

	private String docEndDate = "";

	private String gracePeriod = "";

	private String gracePeriodUnit = "";

	private String chaserReminder;

	private String deferredDate = "";

	private String daysOverdue = "";

	private String subItemIndex = "";

	private boolean isPrintReminderSet;

	private String oneOff = "";

	private String dateWaived = "";

	private String recurrentSubItemRemarks = "";

	private String riskTrigger = "";

	private String fee = "";

	private String actionParty = "";

	private String isParameterizedDesc = "";

	private String noCovenant = "";

	private String noRecurrent = "";

	public String getIsParameterizedDesc() {
		return isParameterizedDesc;
	}

	public void setIsParameterizedDesc(String isParameterizedDesc) {
		this.isParameterizedDesc = isParameterizedDesc;
	}

	public String getActionParty() {
		return actionParty;
	}

	public void setActionParty(String actionParty) {
		this.actionParty = actionParty;
	}

	public boolean getIsPrintReminderSet() {
		return isPrintReminderSet;
	}

	public void setIsPrintReminderSet(boolean printReminderSet) {
		isPrintReminderSet = printReminderSet;
	}

	public String getSubItemIndex() {
		return subItemIndex;
	}

	public void setSubItemIndex(String subItemIndex) {
		this.subItemIndex = subItemIndex;
	}

	public String getDaysOverdue() {
		return daysOverdue;
	}

	public void setDaysOverdue(String daysOverdue) {
		this.daysOverdue = daysOverdue;
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

	public String getDeferredDate() {
		return deferredDate;
	}

	public void setDeferredDate(String deferredDate) {
		this.deferredDate = deferredDate;
	}

	public String getRecurrentStatus() {
		return recurrentStatus;
	}

	public void setRecurrentStatus(String recurrentStatus) {
		this.recurrentStatus = recurrentStatus;
	}

	public String getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(String dateReceived) {
		this.dateReceived = dateReceived;
	}

	public boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(boolean verified) {
		isVerified = verified;
	}

	public String getDateChecked() {
		return dateChecked;
	}

	public void setDateChecked(String dateChecked) {
		this.dateChecked = dateChecked;
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

	public String getDateWaived() {
		return dateWaived;
	}

	public void setDateWaived(String dateWaived) {
		this.dateWaived = dateWaived;
	}

	public String getRecurrentSubItemRemarks() {
		return recurrentSubItemRemarks;
	}

	public void setRecurrentSubItemRemarks(String recurrentSubItemRemarks) {
		this.recurrentSubItemRemarks = recurrentSubItemRemarks;
	}

	public String getRiskTrigger() {
		return this.riskTrigger;
	}

	public void setRiskTrigger(String riskTrigger) {
		this.riskTrigger = riskTrigger;
	}

	// Added by Pratheepa for CR234
	public String getFee() {
		return this.fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */

	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "covenantItem", "com.integrosys.cms.ui.checklist.recreceipt.CovenantMapper" },
				{ "covenantSubItem", "com.integrosys.cms.ui.checklist.recreceipt.CovenantMapper" },
				{ "recurrentItem", "com.integrosys.cms.ui.checklist.recreceipt.RecurrentMapper" },
				{ "recurrentSubItem", "com.integrosys.cms.ui.checklist.recreceipt.RecurrentMapper" },
				{ "limitProfile", "com.integrosys.cms.ui.checklist.recreceipt.LimitProfileMapper" }, };
		return input;
	}

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

}

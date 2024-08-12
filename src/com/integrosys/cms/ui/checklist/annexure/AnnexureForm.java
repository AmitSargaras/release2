/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.checklist.annexure;

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

public class AnnexureForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = 2432984688975910618L;

	// Common form fields
	private String limitProfileId = "";

	private String subProfileId = "";

	private String index = "";

	// Annexure form fields
	private String annexureName = "";

	private String dueDate = "";

	private String dateReceived = "";

	private String recurrentStatus = "";

	private String deferredDate = "";

	private boolean isPrintReminderSet;
	
	public String getAnnexureName() {
		return annexureName;
	}

	public void setAnnexureName(String annexureName) {
		this.annexureName = annexureName;
	}

	public boolean getIsPrintReminderSet() {
		return isPrintReminderSet;
	}

	public void setIsPrintReminderSet(boolean printReminderSet) {
		isPrintReminderSet = printReminderSet;
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */

	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "annexureItem", "com.integrosys.cms.ui.checklist.annexure.AnnexureMapper" },
				{ "limitProfile", "com.integrosys.cms.ui.checklist.annexure.LimitProfileMapper" }, };
		return input;
	}
}

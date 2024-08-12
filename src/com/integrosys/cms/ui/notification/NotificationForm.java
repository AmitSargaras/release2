/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/notification/NotificationForm.java,v 1.3 2005/10/18 01:41:03 lini Exp $
 */
package com.integrosys.cms.ui.notification;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * @author $Author: lini $<br>
 * @version $Revision $
 * @since $Date $ Tag: $Name $
 */

public class NotificationForm extends CommonForm implements Serializable {

	private String notificationID;

	private String creationString;

	private String effectiveString;

	private String expiryString;

	private String url;

	private String originatorID;

	private String notificationState;

	private String notificationMessage;

	private String businessObject;

	private String pastNotificationID;

	// Recurrence info
	private String notificationRecurrenceID;

	private String recurringFrequencyUnitID;

	private String recurringFrequencyUnit;

	private String recurringFrequency;

	// Recipient info
	private String recipientType;

	private String userID;

	private String teamID;

	// CR34
	private String[] notificationIDs;

	// CR-120
	private String searchLeID;

	private String searchLegalName;

	private String searchAANumber;

	public NotificationForm() {
	}

	public NotificationForm(String notificationID, String creationString, String effectiveString, String expiryString,
			String url, String originatorID, String notificationState, String notificationMessage,
			String businessObject, String pastNotificationID, String notificationRecurrenceID,
			String recurringFrequencyUnitID, String recurringFrequencyUnit, String recurringFrequency,
			String recipientType, String userID, String teamID) {
		this.notificationID = notificationID;
		this.creationString = creationString;
		this.effectiveString = effectiveString;
		this.expiryString = expiryString;
		this.url = url;
		this.originatorID = originatorID;
		this.notificationState = notificationState;
		this.notificationMessage = notificationMessage;
		this.businessObject = businessObject;
		this.pastNotificationID = pastNotificationID;
		this.notificationRecurrenceID = notificationRecurrenceID;
		this.recurringFrequencyUnitID = recurringFrequencyUnitID;
		this.recurringFrequencyUnit = recurringFrequencyUnit;
		this.recurringFrequency = recurringFrequency;
		this.recipientType = recipientType;
		this.userID = userID;
		this.teamID = teamID;
	}

	public String getBusinessObject() {
		return businessObject;
	}

	public String getCreationString() {
		return creationString;
	}

	public String getEffectiveString() {
		return effectiveString;
	}

	public String getExpiryString() {
		return expiryString;
	}

	public String getNotificationID() {
		return notificationID;
	}

	/**
	 * @return Returns the notificationIDs.
	 */
	public String[] getNotificationIDs() {
		return notificationIDs;
	}

	public String getNotificationMessage() {
		return notificationMessage;
	}

	public String getNotificationRecurrenceID() {
		return notificationRecurrenceID;
	}

	public String getNotificationState() {
		return notificationState;
	}

	public String getOriginatorID() {
		return originatorID;
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * required by the form and class used to map the same. it has a syntax
	 * (key,classname,Mapperclassname)
	 * 
	 * @return One-dimesnional String Array
	 */
	public String[][] getOutputDescriptor() {
		/*
		 * String[][] output = {{"pricingContinuousfrm",
		 * "com.integrosys.los.app.product.bus.OBProductType",
		 * "com.integrosys.los.ui.product.NotificationMapper"}}; return output;
		 */
		return null;
	}

	public String getPastNotificationID() {
		return pastNotificationID;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public String getRecurringFrequency() {
		return recurringFrequency;
	}

	public String getRecurringFrequencyUnit() {
		return recurringFrequencyUnit;
	}

	public String getRecurringFrequencyUnitID() {
		return recurringFrequencyUnitID;
	}

	public String getSearchAANumber() {
		return searchAANumber;
	}

	public String getSearchLegalName() {
		return searchLegalName;
	}

	public String getSearchLeID() {
		return searchLeID;
	}

	public String getTeamID() {
		return teamID;
	}

	public String getUrl() {
		return url;
	}

	public String getUserID() {
		return userID;
	}

	public void setBusinessObject(String businessObject) {
		this.businessObject = businessObject;
	}

	public void setCreationString(String creationString) {
		this.creationString = creationString;
	}

	public void setEffectiveString(String effectiveString) {
		this.effectiveString = effectiveString;
	}

	public void setExpiryString(String expiryString) {
		this.expiryString = expiryString;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}

	/**
	 * @param notificationIDs The notificationIDs to set.
	 */
	public void setNotificationIDs(String[] notificationIDs) {
		this.notificationIDs = notificationIDs;
	}

	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}

	public void setNotificationRecurrenceID(String notificationRecurrenceID) {
		this.notificationRecurrenceID = notificationRecurrenceID;
	}

	public void setNotificationState(String notificationState) {
		this.notificationState = notificationState;
	}

	public void setOriginatorID(String originatorID) {
		this.originatorID = originatorID;
	}

	public void setPastNotificationID(String pastNotificationID) {
		this.pastNotificationID = pastNotificationID;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}

	public void setRecurringFrequency(String recurringFrequency) {
		this.recurringFrequency = recurringFrequency;
	}

	public void setRecurringFrequencyUnit(String recurringFrequencyUnit) {
		this.recurringFrequencyUnit = recurringFrequencyUnit;
	}

	public void setRecurringFrequencyUnitID(String recurringFrequencyUnitID) {
		this.recurringFrequencyUnitID = recurringFrequencyUnitID;
	}

	public void setSearchAANumber(String searchAANumber) {
		this.searchAANumber = searchAANumber;
	}

	public void setSearchLegalName(String searchLegalName) {
		this.searchLegalName = searchLegalName;
	}

	public void setSearchLeID(String searchLeID) {
		this.searchLeID = searchLeID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (key,classname,Mapperclassname)
	 * 
	 * @return One-dimesnional String Array
	 */
	public String[][] getInputDescriptor() {
		String[][] input = { { "notification", "com.integrosys.cms.app.notification.bus.OBCMSNotification",
				"com.integrosys.cms.ui.notification.CMSNotificationMapper" } };
		return input;
	}

	public String[][] getMapper() {
		String[][] input = { { "notification", "com.integrosys.cms.ui.notification.CMSNotificationMapper" }, };
		return input;
	}

}

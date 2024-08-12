/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/OBEventInfo.java,v 1.8 2006/03/06 12:17:50 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import java.util.Date;
import java.util.Locale;

/**
 * This is the common Event Notification class Contains the common headers shown
 * in event notifications
 */
public class OBEventInfo implements java.io.Serializable {

	private String eventID;

	private String receipient;

	private String leName;

	private String leID;

	private String subject;

	private Date notificationDate;

	private String segment; // for routing purpose

	private String originatingCountry; // for routing and display purpose

	private String[] secondaryCountryList;

	private String originatingOrganisation; // for routing purpose

	private String securityId;

	private String details;

	/**
	 * For storing locale information when needed.
	 */
	private Locale locale;

	/**
	 * The date for which this event will expire.
	 */
	private Date notificationExpiryDate;

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public Date getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(Date notificateDate) {
		this.notificationDate = notificateDate;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getOriginatingCountry() {
		return originatingCountry;
	}

	public void setOriginatingCountry(String country) {
		this.originatingCountry = country;
	}

	public String getOriginatingOrganisation() {
		return originatingOrganisation;
	}

	public void setOriginatingOrganisation(String organisation) {
		this.originatingOrganisation = organisation;
	}

	public String getReceipient() {
		return receipient;
	}

	public void setReceipient(String receipient) {
		this.receipient = receipient;
	}

	public String getLeName() {
		return leName;
	}

	public void setLeName(String leName) {
		this.leName = leName;
	}

	public String getLeID() {
		return leID;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getNotificationExpiryDate() {
		return notificationExpiryDate;
	}

	public void setNotificationExpiryDate(Date notificationExpiryDate) {
		this.notificationExpiryDate = notificationExpiryDate;
	}

	public String[] getSecondaryCountryList() {
		return secondaryCountryList;
	}

	public void setSecondaryCountryList(String[] secondaryCountryList) {
		this.secondaryCountryList = secondaryCountryList;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getSecurityId() {
		return securityId;
	}

	public String getSecurityID() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setSecurityId(long securityId) {
		this.securityId = String.valueOf(securityId);
	}

	public void setSecurityID(String securityID) {
		this.securityId = securityID;
	}

	public void setSecurityID(long securityID) {
		this.securityId = String.valueOf(securityID);
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}

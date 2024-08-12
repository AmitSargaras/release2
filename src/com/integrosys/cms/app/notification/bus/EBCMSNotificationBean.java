/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/notification/bus/EBCMSNotificationBean.java,v 1.3 2006/09/29 08:51:35 hmbao Exp $
 */

package com.integrosys.cms.app.notification.bus;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.component.notification.bus.EBNotificationBean;
import com.integrosys.component.notification.bus.OBPersistEntity;
import com.integrosys.component.notification.bus.VersionMismatchException;

/**
 * @author $Author: hmbao $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/09/29 08:51:35 $ Tag: $Name: $
 */

public abstract class EBCMSNotificationBean extends EBNotificationBean implements javax.ejb.EntityBean,
		ICMSNotification {

	private static final long serialVersionUID = 7008172240033054302L;

	abstract public String getLeName();

	abstract public void setLeName(String leID);

	/**
	 * Get the customer's le ID.
	 * 
	 * @returns long
	 */
	public abstract String getLeID();

	/**
	 * Set the customer's le ID.
	 * 
	 * @param leID - long
	 */
	public abstract void setLeID(String leID);

	public String getNotificationMessage() {
		String msgPart = getMessagePart1();
		StringBuffer buf = new StringBuffer();
		appendMessage(buf, msgPart);
		msgPart = getMessagePart2();
		appendMessage(buf, msgPart);
		msgPart = getMessagePart3();
		appendMessage(buf, msgPart);
		msgPart = getMessagePart4();
		appendMessage(buf, msgPart);
		msgPart = getMessagePart5();
		appendMessage(buf, msgPart);
		return buf.toString();
	}

	private void appendMessage(StringBuffer buf, String msgPart) {
		if ((buf != null) && (msgPart != null) && (msgPart.length() > 0)) {
			buf.append(msgPart);
		}
	}

	public void setNotificationMessage(String message) {
		// if (message == null || message.length() == 0) return;
		// int size = message.length();
		// setMessageSize(size);
		// if (size <= MAX_MSG_SIZE) {
		//
		// double num_parts = ((double)size)/MAX_MSG_PART_SIZE;
		//
		// DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> msg size : " + size);
		// DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> num parts : " + num_parts);
		//
		// if (num_parts < 1) {
		// setMessagePart1(message.substring(0));
		// } else {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 1 : 0 to " + MAX_MSG_PART_SIZE);
		// setMessagePart1(message.substring(0, MAX_MSG_PART_SIZE));
		// if (num_parts > 1 && num_parts < 2) {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 2 : 4000 to " + size);
		// setMessagePart2(message.substring(MAX_MSG_PART_SIZE));
		// } else {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 2 : 4000 to 8000");
		// setMessagePart2(message.substring(MAX_MSG_PART_SIZE,
		// MAX_MSG_PART_SIZE*2));
		// if (num_parts > 2 && num_parts < 3) {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 3 : 8000 to " + size);
		// setMessagePart3(message.substring(MAX_MSG_PART_SIZE*2));
		// } else {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 3 : 8000 to 12000");
		// setMessagePart3(message.substring(MAX_MSG_PART_SIZE*2,
		// MAX_MSG_PART_SIZE*3));
		// if (num_parts > 3 && num_parts < 4) {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 4 : 12000 to " + size);
		// setMessagePart4(message.substring(MAX_MSG_PART_SIZE*3));
		// } else {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 4 : 12000 to 16000");
		// setMessagePart4(message.substring(MAX_MSG_PART_SIZE*3,
		// MAX_MSG_PART_SIZE*4));
		// if (num_parts > 4) {
		// //DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// ">>>> part 5 : 16000 to " + size);
		// setMessagePart5(message.substring(MAX_MSG_PART_SIZE*4));
		// }
		// }
		// }
		// }
		// }
		//
		// } else {
		// DefaultLogger.debug("EBCMSNotificationBean.setNotificationMessage",
		// "Notification exceeds max size for message ie.20000 chars.
		// Notification length : " + size);
		// }
	}

	// abstract public String getMessageObjectData();
	// abstract public void setMessageObjectData(String data);

	abstract public String getMessagePart1();

	abstract public void setMessagePart1(String part1);

	abstract public String getMessagePart2();

	abstract public void setMessagePart2(String part2);

	abstract public String getMessagePart3();

	abstract public void setMessagePart3(String part3);

	abstract public String getMessagePart4();

	abstract public void setMessagePart4(String part4);

	abstract public String getMessagePart5();

	abstract public void setMessagePart5(String part5);

	abstract public int getMessageSize();

	abstract public void setMessageSize(int size);

	public Object getMessageObject() {
		return null;
		// DefaultLogger.debug("EBCMSNotificationBean.getMessageObject",
		// ">>>> getting message object");
		// return (Object)getMessageObjectData();
	}

	public void setMessageObject(Object ob) {
		// DefaultLogger.debug("EBCMSNotificationBean.setMessageObject",
		// ">>>> setting message object : " + ob);
		// setMessageObjectData((ob == null) ? null : (String)ob);
	}

	public ICMSNotification getCMSNotification() {
		OBCMSNotification contents = new OBCMSNotification();
		AccessorUtil.copyValue(this, contents);
		contents.setVersionTime(this.getVersionTime());
		contents.setStatus(this.getStatus());
		return contents;
	}

	public void setCMSNotification(ICMSNotification contents) throws VersionMismatchException {
		checkVersionMismatch((OBPersistEntity) contents);
		AccessorUtil.copyValue(contents, this, new String[] { "getNotificationID", "setNotificationID",
				"getVersionTime", "setVersionTime" });
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Create a record.
	 * 
	 * @param value notification to be created.
	 * @return Long
	 * @throws CreateException on error creating the record.
	 */
	public Long ejbCreate(ICMSNotification value) throws CreateException {
		AccessorUtil.copyValue(value, this, new String[] { "getNotificationRecipients", "setNotificationRecipients",
				"getNotificationRecurrences", "setNotificationRecurrences" });

		this.setVersionTime(VersionGenerator.getVersionNumber());
		this.setNotificationID(java.lang.Long.parseLong(getSeqNum()));

		return null;
	}

	/**
	 * Matching method of ejbCreate. The container invokes the matching
	 * ejbPostCreate method on an instance after it invokes the ejbCreate method
	 * with the same arguments. It executes in the same transaction context as
	 * that of the matching ejbCreate method.
	 * @param value is of type INotification
	 * @throws CreateException on error creating references for this borrower.
	 */
	public void ejbPostCreate(ICMSNotification value) throws CreateException {
	}

	public void setChilds(ICMSNotification contents) {
		try {
			setReferences(contents);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	private String getSeqNum() {
		SequenceManager seqmgr = new SequenceManager();
		try {
			String seq = seqmgr.getSeqNum("NOTIFICATION_SEQ", true);
			return seq;
		}
		catch (Exception e) {
			return null;
		}
	}

	public abstract Long getNotificationPK();

	public abstract void setNotificationPK(Long aLong);

	public abstract Date getCreationDate();

	public abstract void setCreationDate(Date date);

	public abstract Date getEffectiveDate();

	public abstract void setEffectiveDate(Date date);

	public abstract Date getExpiryDate();

	public abstract void setExpiryDate(Date date);

	public abstract String getUrl();

	public abstract void setUrl(String s);

	public abstract long getOriginatorID();

	public abstract void setOriginatorID(long l);

	public abstract Integer getNotificationStateInt();

	public abstract void setNotificationStateInt(Integer integer);

	public abstract long getPastNotificationID();

	public abstract void setPastNotificationID(long l);

	public abstract Collection getNotificationRecurrencesCMR();

	public abstract void setNotificationRecurrencesCMR(Collection collection);

	public abstract Collection getNotificationRecipientsCMR();

	public abstract void setNotificationRecipientsCMR(Collection collection);

	public abstract Long getNotificationTypePK();

	public abstract void setNotificationTypePK(Long aLong);

	public abstract String getNotificationTypeName();

	public abstract void setNotificationTypeName(String s);

	public abstract int getSeverity();

	public abstract void setSeverity(int i);

	public abstract String getNotificatonChannelsStr();

	public abstract void setNotificatonChannelsStr(String s);

	public abstract String getNotificationTitle();

	public abstract void setNotificationTitle(String s);

	public abstract String getNotificationTypeCode();

	public abstract void setNotificationTypeCode(String s);

	public abstract boolean getRecurring();

	public abstract void setRecurring(boolean b);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long l);

	public abstract String getStatus();

	public abstract void setStatus(String s);

	public abstract String getDetails();

	public abstract void setDetails(String details);
}

package com.integrosys.cms.host.stp.log;

import java.io.Serializable;
import java.util.Date;

/**
 * @author $Author: marvin $<br>
 * @author Chin Kok Cheong
 * @version $Id$
 */
public interface ILogMessage extends Serializable {
	public String getErrorStackTrace();

	public Date getPublishDate();

	public Date getReceivedDate();

	public String getReceivedMessage();

	public String getSCIMsgId();

	public Date getSubscriberAckDate();

	public String getSubscriberResponseCode();

	public String getSubscriberResponseDesc();

	public String getSubscriberResponseMessage();

	public char getSubscriberResponseType();

	public void setErrorStackTrace(String message);

	public void setPublishDate(Date publishDate);

	public void setReceivedDate(Date receivedDate);

	public void setReceivedMessage(String message);

	public void setSCIMsgId(String SCIMsgId);

	public void setSubscriberResponseCode(String subscriberResponseCode);

	public void setSubscriberResponseDesc(String str);

	public void setSubscriberResponseMessage(String message);

	public void setSubscriberResponseType(char subscriberResponseType);
}
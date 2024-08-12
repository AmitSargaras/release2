package com.integrosys.cms.batch.customer;

import java.util.Date;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class OBCustomerUpdate {

	private long customerUpdateId;

	private String cifId;

	private String sourceId;

	private char processedIndicator;

	private Date timeReceived;

	private Date timeProcessed;

	public long getCustomerUpdateId() {
		return customerUpdateId;
	}

	public void setCustomerUpdateId(long customerUpdateId) {
		this.customerUpdateId = customerUpdateId;
	}

	public String getCifId() {
		return cifId;
	}

	public void setCifId(String cifId) {
		this.cifId = cifId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public char getProcessedIndicator() {
		return processedIndicator;
	}

	public void setProcessedIndicator(char processedIndicator) {
		this.processedIndicator = processedIndicator;
	}

	public Date getTimeReceived() {
		return timeReceived;
	}

	public void setTimeReceived(Date timeReceived) {
		this.timeReceived = timeReceived;
	}

	public Date getTimeProcessed() {
		return timeProcessed;
	}

	public void setTimeProcessed(Date timeProcessed) {
		this.timeProcessed = timeProcessed;
	}

}

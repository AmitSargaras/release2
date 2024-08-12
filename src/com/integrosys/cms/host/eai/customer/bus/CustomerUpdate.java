package com.integrosys.cms.host.eai.customer.bus;

import java.util.Date;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerUpdate implements java.io.Serializable {

	private long cmsId;

	private String cifId;

	private String sourceId;

	private char processedIndicator;

	private Date timeReceived;

	private Date timeProcessed;

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
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

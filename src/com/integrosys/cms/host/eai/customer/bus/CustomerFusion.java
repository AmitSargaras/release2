package com.integrosys.cms.host.eai.customer.bus;

import java.util.Date;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CustomerFusion implements java.io.Serializable {

	private long cmsId;

	private String oldCifId;

	private String newCifId;

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

	public String getOldCifId() {
		return oldCifId;
	}

	public void setOldCifId(String oldCifId) {
		this.oldCifId = oldCifId;
	}

	public String getNewCifId() {
		return newCifId;
	}

	public void setNewCifId(String newCifId) {
		this.newCifId = newCifId;
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

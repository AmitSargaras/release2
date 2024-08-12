/*
 * OBCustomerFusion.java
 *
 * Created on May 22, 2007, 11:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.batch.customer;

import java.util.Date;

/**
 * 
 * @author OEM
 */
public class OBCustomerFusion {

	private long cifFusionId;

	private String oldLeId;

	private String newLeId;

	private String sourceId;

	private char processedIndicator;

	private Date timeReceived;

	private Date timeProcessed;

	public long getCifFusionId() {
		return cifFusionId;
	}

	public void setCifFusionId(long cifFusionId) {
		this.cifFusionId = cifFusionId;
	}

	public String getOldLeId() {
		return oldLeId;
	}

	public void setOldLeId(String oldLeId) {
		this.oldLeId = oldLeId;
	}

	public String getNewLeId() {
		return newLeId;
	}

	public void setNewLeId(String newLeId) {
		this.newLeId = newLeId;
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
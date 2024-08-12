package com.integrosys.cms.batch.common;

import java.io.Serializable;

/**
 * Batch Feed error for each feed, normally the error when parsing from flat
 * file to object. Keep the error for further process such as logging of all
 * errors of a flat file to a physical file.
 * 
 * @author Chong Jun Yong
 * 
 */
public class BatchFeedError implements Serializable {
	private int lineNumber;

	private String lineString;

	private String detailMessage;

	public BatchFeedError(int lineNumber, String lineString, String detailMessage) {
		this.lineNumber = lineNumber;
		this.lineString = lineString;
		this.detailMessage = detailMessage;
	}

	public String getDetailMessage() {
		return detailMessage;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getLineString() {
		return lineString;
	}

	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setLineString(String lineString) {
		this.lineString = lineString;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("\"").append(lineNumber).append("\",");
		buf.append("\"").append(lineString).append("\",");
		buf.append("\"").append(detailMessage).append("\"");

		return buf.toString();
	}
}

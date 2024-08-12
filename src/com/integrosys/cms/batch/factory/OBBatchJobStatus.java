package com.integrosys.cms.batch.factory;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity represent a batch job. To be used to keep track the batch job status.
 * 
 * @author Chong Jun Yong
 * 
 */
public class OBBatchJobStatus implements Serializable {
	private Long batchId;

	private String jobName;

	private String className;

	private String parameters;

	private Date startDate;

	private Date endDate;

	private String status;

	private String message;

	public Long getBatchId() {
		return batchId;
	}

	public String getClassName() {
		return className;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getJobName() {
		return jobName;
	}

	public String getMessage() {
		return message;
	}

	public String getParameters() {
		return parameters;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("jobName [").append(jobName).append("] ");
		buf.append("parameters [").append(parameters).append("] ");
		buf.append("startDate [").append(startDate).append("] ");
		buf.append("endDate [").append(endDate).append("] ");
		buf.append("status [").append(status).append("]");

		return buf.toString();
	}
}

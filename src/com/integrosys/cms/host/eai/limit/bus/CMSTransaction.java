/*
 * Copyright Integro Technologies Pte Ltd
 *
 */
package com.integrosys.cms.host.eai.limit.bus;

/**
 * This class represents a workflow value object in CMS system.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2004/02/25
 */
public class CMSTransaction implements java.io.Serializable {

	private static final long serialVersionUID = 7038712006568704909L;

	private long transactionID;

	private long referenceID;

	private long stageReferenceID;

	private String transactionType;

	private String status;

	/**
	 * Default constructor.
	 */
	public CMSTransaction() {
		super();
	}

	/**
	 * Get transaction id.
	 * 
	 * @return long
	 */
	public long getTransactionID() {
		return transactionID;
	}

	/**
	 * Set transaction id.
	 * 
	 * @param transactionID of type long
	 */
	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	/**
	 * Get actual reference id.
	 * 
	 * @return long
	 */
	public long getReferenceID() {
		return referenceID;
	}

	/**
	 * Set actual reference id.
	 * 
	 * @param referenceID of type long
	 */
	public void setReferenceID(long referenceID) {
		this.referenceID = referenceID;
	}

	/**
	 * Get staging reference id.
	 * 
	 * @return long
	 */
	public long getStageReferenceID() {
		return stageReferenceID;
	}

	/**
	 * Set staging reference id.
	 * 
	 * @param stageReferenceID of type long
	 */
	public void setStageReferenceID(long stageReferenceID) {
		this.stageReferenceID = stageReferenceID;
	}

	/**
	 * Get transaction type.
	 * 
	 * @return String
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Set transaction type.
	 * 
	 * @param transactionType of type String
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Get status
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}

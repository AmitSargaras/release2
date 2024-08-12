package com.integrosys.cms.app.email.notification.bus;

import java.util.Date;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

public class OBEmailNotification implements IEmailNotification {
	//table fields
	private long notifcationId;
	private String noticationTypeCode;
	private String recipentEmailId;
	private String msgSubject;
	private String msgBody;
	private String isSent;

	
	//for audit
	private long versionTime;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private String errorLog;
	private String fromServer;
	
	//for message genration
	private ICMSCustomer customer;
	private ILimitProfile limitProfile;
	
	public long getNotifcationId() {
		return notifcationId;
	}
	public void setNotifcationId(long notifcationId) {
		this.notifcationId = notifcationId;
	}
	public String getNoticationTypeCode() {
		return noticationTypeCode;
	}
	public void setNoticationTypeCode(String noticationTypeCode) {
		this.noticationTypeCode = noticationTypeCode;
	}
	public String getRecipentEmailId() {
		return recipentEmailId;
	}
	public void setRecipentEmailId(String recipentEmailId) {
		this.recipentEmailId = recipentEmailId;
	}
	public String getMsgSubject() {
		return msgSubject;
	}
	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public String getIsSent() {
		return isSent;
	}
	public void setIsSent(String isSent) {
		this.isSent = isSent;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	public ICMSCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(ICMSCustomer customer) {
		this.customer = customer;
	}
	public ILimitProfile getLimitProfile() {
		return limitProfile;
	}
	public void setLimitProfile(ILimitProfile limitProfile) {
		this.limitProfile = limitProfile;
	}
	/**
	 * @return the fromServer
	 */
	public String getFromServer() {
		return fromServer;
	}
	/**
	 * @param fromServer the fromServer to set
	 */
	public void setFromServer(String fromServer) {
		this.fromServer = fromServer;
	}
	
}

package com.integrosys.cms.app.email.notification.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

public interface IEmailNotification extends Serializable, IValueObject  {
	
	public long getNotifcationId() ;
	public void setNotifcationId(long notifcationId);
	
	public String getNoticationTypeCode() ;
	public void setNoticationTypeCode(String noticationTypeCode) ;
	
	public String getRecipentEmailId();
	public void setRecipentEmailId(String recipentEmailId) ;
	
	public String getMsgSubject() ;
	public void setMsgSubject(String msgSubject) ;
	
	public String getMsgBody();
	public void setMsgBody(String msgBody) ;
	
	public String getIsSent() ;
	public void setIsSent(String isSent) ;

    public long getVersionTime();
    public void setVersionTime(long versionTime);

    public Date getCreationDate();
	public void setCreationDate(Date creationDate);
	
	public String getCreateBy();
	public void setCreateBy(String createBy);
	
	public Date getLastUpdateDate();
	public void setLastUpdateDate(Date lastUpdateDate);
	
	public String getLastUpdateBy();
	public void setLastUpdateBy(String lastUpdateBy);
	
	public ICMSCustomer getCustomer();
	public void setCustomer(ICMSCustomer customer);
	
	public ILimitProfile getLimitProfile();
	public void setLimitProfile(ILimitProfile limitProfile) ;
	
	public String getErrorLog() ;
	public void setErrorLog(String errorLog) ;
	
	public String getFromServer();
	public void setFromServer(String fromServer) ;
}

package com.integrosys.cms.app.email.notification.bus;

import java.io.Serializable;

public interface ICaseCreationNotificationDetail extends Serializable{
	
	public String getCaseCreationDate();
	public void setCaseCreationDate(String caseCreationDate) ;
	
	public String getCaseCreationId() ;
	public void setCaseCreationId(String caseCreationId);
	
	public String getDocNos() ;
	public void setDocNos(String docNos);
	
	public String getPendingDays();
	public void setPendingDays(String pendingDays);

	public String getRemarks() ;
	public void setRemarks(String remarks) ;
	
	public String getSegment();
	public void setSegment(String segment) ;
	
	public String getBranch() ;
	public void setBranch(String branch);
	
	public String getPartyId();
	public void setPartyId(String partyId);
	
	public String getPartyName();
	public void setPartyName(String partyName);
}

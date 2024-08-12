/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequest.java,v 1.3 2003/09/22 02:23:23 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * This interface defines the list of attributes that is required for request
 * generation
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/22 02:23:23 $ Tag: $Name: $
 */
public interface IRequest extends IValueObject, Serializable {
	public long getRequestID();

	public long getLimitProfileID();

	public long getCustomerID();

	public IRequestHeader getRequestHeader();

	public IRequestSubject getRequestSubject();

	public IRequestDescription getRequestDescription();

	public IRequestItem[] getRequestItemList();

	public String getProposedByName();

	public String getProposedByDesignation();

	public String getProposedBySignNo();

	public Date getProposedByDate();

	public String getSupportedByName();

	public String getSupportedByDesignation();

	public String getSupportedByCoinNo();

	public Date getSupportedByDate();

	public String getApprovedBySCOName();

	public String getApprovedBySCODesignation();

	public String getApprovedBySCOCoinNo();

	public Date getApprovedBySCODate();

	public String getApprovedByRCOName();

	public String getApprovedByRCODesignation();

	public String getApprovedByRCOCoinNo();

	public Date getApprovedByRCODate();

	public String getApprovedByCCOName();

	public String getApprovedByCCODesignation();

	public String getApprovedByCCOCoinNo();

	public Date getApprovedByCCODate();

	public String getName();

	public String getCreditCommittee();

	public String getMinsOfMeeting();

	public Date getMeetingDate();

	public String getReason();

	public void setRequestID(long aRequestID);

	public void setLimitProfileID(long aLimitProfileID);

	public void setCustomerID(long aCustomerID);

	public void setRequestHeader(IRequestHeader anIRequestHeader);

	public void setRequestSubject(IRequestSubject anIRequestSubject);

	public void setRequestDescription(IRequestDescription anIRequestDescription);

	public void setRequestItemList(IRequestItem[] aRequestItemList);

	public void setProposedByName(String aProposedByName);

	public void setProposedByDesignation(String aProposedByDesignation);

	public void setProposedBySignNo(String aProposedBySignNo);

	public void setProposedByDate(Date aProposedByDate);

	public void setSupportedByName(String aSupportedByName);

	public void setSupportedByDesignation(String aSupporteddByDesignation);

	public void setSupportedByCoinNo(String aSupportedByCoinNo);

	public void setSupportedByDate(Date aSupportedByDate);

	public void setApprovedBySCOName(String anApprovedBySCOName);

	public void setApprovedBySCODesignation(String anApprovedBySCODesignation);

	public void setApprovedBySCOCoinNo(String anApprovedBySCOCoinNo);

	public void setApprovedBySCODate(Date anApprovedBySCODate);

	public void setApprovedByRCOName(String anApprovedByRCOName);

	public void setApprovedByRCODesignation(String anApprovedByRCODesignation);

	public void setApprovedByRCOCoinNo(String anApprovedByRCOCoinNo);

	public void setApprovedByRCODate(Date anApprovedByRCODate);

	public void setApprovedByCCOName(String anApprovedByCCOName);

	public void setApprovedByCCODesignation(String anApprovedByCCODesignation);

	public void setApprovedByCCOCoinNo(String anApprovedByCCOCoinNo);

	public void setApprovedByCCODate(Date anApprovedByCCODate);

	public void setName(String aName);

	public void setCreditCommittee(String aCreditCommittee);

	public void setMinsOfMeeting(String aMinsOfMeeting);

	public void setMeetingDate(Date aMeetingDate);

	public void setReason(String aReason);
}

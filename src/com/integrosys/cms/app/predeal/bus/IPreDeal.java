/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * IPreDeal
 *
 * Created on 6:02:13 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 22, 2007 Time: 6:02:13 PM
 */
public interface IPreDeal extends Serializable, IValueObject {
	public Long getEarMarkId();

	public void setEarMarkId(Long id);

	//
	// public long getEarMarkGroupId ( ) ;
	//
	// public void setEarMarkGroupId ( long id ) ;

	public long getFeedId();

	public void setFeedId(long feedId);

	public String getCustomerName();

	public void setCustomerName(String name);

	public String getSourceSystem();

	public void setSourceSystem(String source);

	public String getSecurityId();

	public void setSecurityId(String securityId);

	public String getAaNumber();

	public void setAaNumber(String aANumber);

	public String getBranchName();

	public void setBranchName(String branch);

	public String getBranchCode();

	public void setBranchCode(String code);

	public String getCifNo();

	public void setCifNo(String cifNo);

	public String getAccountNo();

	public void setAccountNo(String accountNo);

	public long getEarMarkUnits();

	public void setEarMarkUnits(long earMarkUnit);

	public String getEarMarkStatus();

	public void setEarMarkStatus(String earMarkStatus);

	public boolean getHoldingInd();

	public void setHoldingInd(boolean holdingInd);

	public String getReleaseStatus();

	public void setReleaseStatus(String releaseStatus);

	public boolean getInfoCorrectInd();

	public void setInfoCorrectInd(boolean infoCorrectInd);

	public String getInfoIncorrectDetails();

	public void setInfoIncorrectDetails(String details);

	public boolean getWaiveApproveInd();

	public void setWaiveApproveInd(boolean waiveApproveInd);

	public boolean getStatus();

	public void setStatus(boolean status);

	public Date getDateMaxCapBreach();

	public void setDateMaxCapBreach(Date dateMaxCapBreach);

	public void setPurposeOfEarmarking(String purposeOfEarmarking);

	public String getPurposeOfEarmarking();
}

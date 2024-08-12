/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.feed.bus.mutualfunds;

import java.util.Date;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IMutualFundsFeedEntry extends java.io.Serializable, IValueObject {

	long getMutualFundsFeedEntryRef();

	public double getCurrentNAV() ;

	public void setCurrentNAV(double currentNAV) ;

	Date getLastUpdatedDate();

	long getMutualFundsFeedEntryID();

	long getVersionTime();

	void setLastUpdatedDate(Date lastUpdatedDate);

	void setMutualFundsFeedEntryID(long param);

	void setMutualFundsFeedEntryRef(long param);

	void setVersionTime(long versionTime);

	String getSchemeCode();
	
	void setSchemeCode(String schemeCode);
	
	String getSchemeName();
	
	void setSchemeName(String schemeName);
	
	String getSchemeType();
	
	void setSchemeType(String schemeType);
	
	Date getExpiryDate();

	void setExpiryDate(Date expiryDate);
	
	Date getStartDate();

	void setStartDate(Date startDate);

	public FormFile getFileUpload();
	
	public void setFileUpload(FormFile fileUpload);
}


/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedEntry.java,v 1.6 2004/06/04 04:55:19 hltan Exp $
 */
package com.integrosys.cms.app.generalparam.bus;

import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IGeneralParamEntry extends java.io.Serializable, IValueObject {
	String EXISTING_USER_DORMANCY_DAYS="EXISTING_USER_DORMANCY_DAYS";
	String PARAM_CODE_BASE_INTREST_RATE="BASE_INT_RATE";
	String PARAM_CODE_LSS_UNIT_HEAD="LSS_UNIT_HEAD";
	String PARAM_SESSION_TIMEOUT="SESSION_TIMEOUT";
	String DORMANCY_DAYS="NEW_USER_DORMANCY_DAYS";  //by Shiv
	String BASE_INT_RATE="BASE_INT_RATE";  //by Shiv
	String BPLR_INT_RATE="BPLR_INT_RATE";  //by Shiv
	/**
	 * @return the paramID
	 */
	public long getParamID();

	/**
	 * @param paramID the paramID to set
	 */
	public void setParamID(long paramID);
	
	long getVersionTime();

	void setVersionTime(long versionTime);

	/**
	 * @return the paramCode
	 */
	public String getParamCode() ;

	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode) ;
	
	/**
	 * @return the paramName
	 */
	public String getParamName() ;

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) ;

	/**
	 * @return the uiView
	 */
	public String getUiView() ;

	/**
	 * @param uiView the uiView to set
	 */
	public void setUiView(String uiView) ;

	/**
	 * @return the paramValue
	 */
	public String getParamValue() ;

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) ;

	/**
	 * @return the generalParamEntryRef
	 */
	public long getGeneralParamEntryRef() ;

	/**
	 * @param generalParamEntryRef the generalParamEntryRef to set
	 */
	public void setGeneralParamEntryRef(long generalParamEntryRef); 
	
	
	public Date getLastUpdatedDate() ;


	public void setLastUpdatedDate(Date lastUpdateDate);

    public String getHandUpTime() ;
	
	public void setHandUpTime(String handUpTime) ;
}

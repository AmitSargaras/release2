/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/IBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IMutualFundsFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IBondFeedGroup busines entity
	 * 
	 * @return IMutualFundsFeedGroup
	 */
	public IMutualFundsFeedGroup getMutualFundsFeedGroup();

	/**
	 * Get the staging IMutualFundsFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IMutualFundsFeedGroup getStagingMutualFundsFeedGroup();

	/**
	 * Set the IMutualFundsFeedGroup busines entity
	 * 
	 * @param value is of type IMutualFundsFeedGroup
	 */
	public void setMutualFundsFeedGroup(IMutualFundsFeedGroup value);

	/**
	 * Set the staging IMutualFundsFeedGroup business entity
	 * 
	 * @param value is of type IMutualFundsFeedGroup
	 */
	public void setStagingMutualFundsFeedGroup(IMutualFundsFeedGroup value);
	
	//Add By Govind S For File Upload Update on 30/Aug/2011
	/**
	 * @return the fileMapperID
	 */
	public IFileMapperId getFileMapperID();
	/**
	 * @param fileMapperID the fileMapperID to set
	 */
	public void setFileMapperID(IFileMapperId fileMapperID);

	/**
	 * @return the stagingFileMapperID
	 */
	public IFileMapperId getStagingFileMapperID();

	/**
	 * @param stagingFileMapperID the stagingFileMapperID to set
	 */
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID);
	
	//End: Add by  Govind S For File Upload Update on  30/Aug/2011
}
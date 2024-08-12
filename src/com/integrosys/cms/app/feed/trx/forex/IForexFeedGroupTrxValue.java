/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/IForexFeedGroupTrxValue.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.forex;

import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $ Tag: $Name: $
 */
public interface IForexFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IForexFeedGroup busines entity
	 * 
	 * @return IForexFeedGroup
	 */
	public IForexFeedGroup getForexFeedGroup();

	/**
	 * Get the staging IForexFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IForexFeedGroup getStagingForexFeedGroup();

	/**
	 * Set the IForexFeedGroup busines entity
	 * 
	 * @param value is of type IForexFeedGroup
	 */
	public void setForexFeedGroup(IForexFeedGroup value);

	/**
	 * Set the staging IForexFeedGroup business entity
	 * 
	 * @param value is of type IForexFeedGroup
	 */
	public void setStagingForexFeedGroup(IForexFeedGroup value);
	
	
	//Add By Govind S For File Upload Update on 16-jun-2011
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
	
	//End: Add by  Govind S For File Upload Update on 16-jun-2011
	
}
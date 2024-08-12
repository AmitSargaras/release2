/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/IBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.bond;

import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 */
public interface IBondFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IBondFeedGroup busines entity
	 * 
	 * @return IBondFeedGroup
	 */
	public IBondFeedGroup getBondFeedGroup();

	/**
	 * Get the staging IBondFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IBondFeedGroup getStagingBondFeedGroup();

	/**
	 * Set the IBondFeedGroup busines entity
	 * 
	 * @param value is of type IBondFeedGroup
	 */
	public void setBondFeedGroup(IBondFeedGroup value);

	/**
	 * Set the staging IBondFeedGroup business entity
	 * 
	 * @param value is of type IBondFeedGroup
	 */
	public void setStagingBondFeedGroup(IBondFeedGroup value);
	
	//Add By Govind S For File Upload Update on 24-aug-2011
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
	
	//End: Add by  Govind S For File Upload Update on  24-aug-2011
}
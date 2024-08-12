/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stock/IStockFeedGroupTrxValue.java,v 1.1 2003/08/07 08:36:38 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.stock;

import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/07 08:36:38 $ Tag: $Name: $
 */
public interface IStockFeedGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the IStockFeedGroup busines entity
	 * 
	 * @return IStockFeedGroup
	 */
	public IStockFeedGroup getStockFeedGroup();

	/**
	 * Get the staging IStockFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IStockFeedGroup getStagingStockFeedGroup();

	/**
	 * Set the IStockFeedGroup busines entity
	 * 
	 * @param value is of type IStockFeedGroup
	 */
	public void setStockFeedGroup(IStockFeedGroup value);

	/**
	 * Set the staging IStockFeedGroup business entity
	 * 
	 * @param value is of type IStockFeedGroup
	 */
	public void setStagingStockFeedGroup(IStockFeedGroup value);
	
	//Add By Govind S For File Upload Update on 25-Aug-2011
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
	
	//End: Add by  Govind S For File Upload Update on  25-Aug-2011
}
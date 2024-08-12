/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stock/OBStockFeedGroupTrxValue.java,v 1.1 2003/08/07 08:36:38 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.stock;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBStockFeedGroupTrxValue extends OBCMSTrxValue implements IStockFeedGroupTrxValue {

	/**
	 * Get the IStockFeedGroup busines entity
	 * 
	 * @return IStockFeedGroup
	 */
	public IStockFeedGroup getStockFeedGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBStockFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBStockFeedGroupTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_STOCK_FEED_GROUP);
	}

	/**
	 * Get the staging IStockFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IStockFeedGroup getStagingStockFeedGroup() {
		return staging;
	}

	/**
	 * Set the IStockFeedGroup busines entity
	 * 
	 * @param value is of type IStockFeedGroup
	 */
	public void setStockFeedGroup(IStockFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IStockFeedGroup business entity
	 * 
	 * @param value is of type IStockFeedGroup
	 */
	public void setStagingStockFeedGroup(IStockFeedGroup value) {
		staging = value;
	}

	private IStockFeedGroup actual;

	private IStockFeedGroup staging;
	
	//Add By Govind S For File Upload Update on 25-Aug-2011
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    //End: Add By Govind S For File Upload Update on 25-Aug-2011
	/**
	 * @return the fileMapperID
	 */
	public IFileMapperId getFileMapperID() {
		return FileMapperID;
	}

	/**
	 * @param fileMapperID the fileMapperID to set
	 */
	public void setFileMapperID(IFileMapperId fileMapperID) {
		FileMapperID = fileMapperID;
	}

	/**
	 * @return the stagingFileMapperID
	 */
	public IFileMapperId getStagingFileMapperID() {
		return stagingFileMapperID;
	}

	/**
	 * @param stagingFileMapperID the stagingFileMapperID to set
	 */
	public void setStagingFileMapperID(IFileMapperId stagingFileMapperID) {
		this.stagingFileMapperID = stagingFileMapperID;
	}
}

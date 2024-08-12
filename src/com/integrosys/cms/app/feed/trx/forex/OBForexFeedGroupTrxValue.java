/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/OBForexFeedGroupTrxValue.java,v 1.5 2003/08/06 05:42:09 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.forex;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBForexFeedGroupTrxValue extends OBCMSTrxValue implements IForexFeedGroupTrxValue {

	/**
	 * Get the IForexFeedGroup busines entity
	 * 
	 * @return IForexFeedGroup
	 */
	public IForexFeedGroup getForexFeedGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBForexFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBForexFeedGroupTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_FOREX_FEED_GROUP);
	}

	/**
	 * Get the staging IForexFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IForexFeedGroup getStagingForexFeedGroup() {
		return staging;
	}

	/**
	 * Set the IForexFeedGroup busines entity
	 * 
	 * @param value is of type IForexFeedGroup
	 */
	public void setForexFeedGroup(IForexFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IForexFeedGroup business entity
	 * 
	 * @param value is of type IForexFeedGroup
	 */
	public void setStagingForexFeedGroup(IForexFeedGroup value) {
		staging = value;
	}

	private IForexFeedGroup actual;

	private IForexFeedGroup staging;
	
	
	//Add By Govind S For File Upload Update on 16-jun-2011
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    //End: Add By Govind S For File Upload Update on 16-jun-2011
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

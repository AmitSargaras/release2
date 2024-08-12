/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/OBBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.bond;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBBondFeedGroupTrxValue extends OBCMSTrxValue implements IBondFeedGroupTrxValue {

	/**
	 * Get the IBondFeedGroup busines entity
	 * 
	 * @return IBondFeedGroup
	 */
	public IBondFeedGroup getBondFeedGroup() {
		return actual;
	}
	
	//Add By Govind S For File Upload Update on 24-aug-2011
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    //End: Add By Govind S For File Upload Update on 24-aug-2011
	
	
	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBBondFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBBondFeedGroupTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_BOND_FEED_GROUP);
	}

	/**
	 * Get the staging IBondFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IBondFeedGroup getStagingBondFeedGroup() {
		return staging;
	}

	/**
	 * Set the IBondFeedGroup busines entity
	 * 
	 * @param value is of type IBondFeedGroup
	 */
	public void setBondFeedGroup(IBondFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IBondFeedGroup business entity
	 * 
	 * @param value is of type IBondFeedGroup
	 */
	public void setStagingBondFeedGroup(IBondFeedGroup value) {
		staging = value;
	}

	private IBondFeedGroup actual;

	private IBondFeedGroup staging;


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

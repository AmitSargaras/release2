/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/OBBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */

package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
/**
* @author $Author: Dattatray Thorat $
* @version $Revision: 1.4 $
* @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
*/
public class OBMutualFundsFeedGroupTrxValue extends OBCMSTrxValue implements IMutualFundsFeedGroupTrxValue {

	/**
	 * Get the IMutualFundsFeedGroup busines entity
	 * 
	 * @return IMutualFundsFeedGroup
	 */
	public IMutualFundsFeedGroup getMutualFundsFeedGroup() {
		return actual;
	}
	//Add By Govind S For File Upload Update on 30-Aug-2011
    IFileMapperId FileMapperID;
    IFileMapperId stagingFileMapperID;
    //End: Add By Govind S For File Upload Update on 30-Aug-2011
	
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

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBMutualFundsFeedGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBMutualFundsFeedGroupTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_BOND_FEED_GROUP);
	}

	/**
	 * Get the staging IMutualFundsFeedGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IMutualFundsFeedGroup getStagingMutualFundsFeedGroup() {
		return staging;
	}

	/**
	 * Set the IMutualFundsFeedGroup busines entity
	 * 
	 * @param value is of type IMutualFundsFeedGroup
	 */
	public void setMutualFundsFeedGroup(IMutualFundsFeedGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IMutualFundsFeedGroup business entity
	 * 
	 * @param value is of type IMutualFundsFeedGroup
	 */
	public void setStagingMutualFundsFeedGroup(IMutualFundsFeedGroup value) {
		staging = value;
	}

	private IMutualFundsFeedGroup actual;

	private IMutualFundsFeedGroup staging;
}

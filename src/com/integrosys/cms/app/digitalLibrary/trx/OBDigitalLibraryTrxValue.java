/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/OBBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */

package com.integrosys.cms.app.digitalLibrary.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBDigitalLibraryTrxValue extends OBCMSTrxValue implements IDigitalLibraryTrxValue {

	/**
	 * Get the IDigitalLibraryGroup busines entity
	 * 
	 * @return IDigitalLibraryGroup
	 */
	public IDigitalLibraryGroup getDigitalLibraryGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBDigitalLibraryTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBDigitalLibraryTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_BOND_FEED_GROUP);
	}

	/**
	 * Get the staging IDigitalLibraryGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IDigitalLibraryGroup getStagingDigitalLibraryGroup() {
		return staging;
	}

	/**
	 * Set the IDigitalLibraryGroup busines entity
	 * 
	 * @param value is of type IDigitalLibraryGroup
	 */
	public void setDigitalLibraryGroup(IDigitalLibraryGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IDigitalLibraryGroup business entity
	 * 
	 * @param value is of type IDigitalLibraryGroup
	 */
	public void setStagingDigitalLibraryGroup(IDigitalLibraryGroup value) {
		staging = value;
	}

	private IDigitalLibraryGroup actual;

	private IDigitalLibraryGroup staging;
}

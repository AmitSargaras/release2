/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/OBBondFeedGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */

package com.integrosys.cms.app.approvalmatrix.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBApprovalMatrixTrxValue extends OBCMSTrxValue implements IApprovalMatrixTrxValue {

	/**
	 * Get the IApprovalMatrixGroup busines entity
	 * 
	 * @return IApprovalMatrixGroup
	 */
	public IApprovalMatrixGroup getApprovalMatrixGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBApprovalMatrixTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBApprovalMatrixTrxValue() {
		// Follow "limit".
		// super.setTransactionType(ICMSConstant.INSTANCE_BOND_FEED_GROUP);
	}

	/**
	 * Get the staging IApprovalMatrixGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IApprovalMatrixGroup getStagingApprovalMatrixGroup() {
		return staging;
	}

	/**
	 * Set the IApprovalMatrixGroup busines entity
	 * 
	 * @param value is of type IApprovalMatrixGroup
	 */
	public void setApprovalMatrixGroup(IApprovalMatrixGroup value) {
		actual = value;
	}

	/**
	 * Set the staging IApprovalMatrixGroup business entity
	 * 
	 * @param value is of type IApprovalMatrixGroup
	 */
	public void setStagingApprovalMatrixGroup(IApprovalMatrixGroup value) {
		staging = value;
	}

	private IApprovalMatrixGroup actual;

	private IApprovalMatrixGroup staging;
}

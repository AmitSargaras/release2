/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/IApprovalMatrixGroupTrxValue.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.approvalmatrix.trx;

import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CheckList trx value.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 */
public interface IApprovalMatrixTrxValue extends ICMSTrxValue {

	/**
	 * Get the IApprovalMatrixGroup busines entity
	 * 
	 * @return IApprovalMatrixGroup
	 */
	public IApprovalMatrixGroup getApprovalMatrixGroup();

	/**
	 * Get the staging IApprovalMatrixGroup business entity
	 * 
	 * @return ICheckList
	 */
	public IApprovalMatrixGroup getStagingApprovalMatrixGroup();

	/**
	 * Set the IApprovalMatrixGroup busines entity
	 * 
	 * @param value is of type IApprovalMatrixGroup
	 */
	public void setApprovalMatrixGroup(IApprovalMatrixGroup value);

	/**
	 * Set the staging IApprovalMatrixGroup business entity
	 * 
	 * @param value is of type IApprovalMatrixGroup
	 */
	public void setStagingApprovalMatrixGroup(IApprovalMatrixGroup value);
}
/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/ApproveRelodgeCustodianDocTrxOperation.java,v 1.4 2003/09/22 03:18:51 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the approval of custodian doc relodgement
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/22 03:18:51 $ Tag: $Name: $
 */
public class ApproveRelodgeCustodianDocTrxOperation extends ApproveUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public ApproveRelodgeCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_APPROVE_RELODGE_CUSTODIAN_DOC;
	}
}

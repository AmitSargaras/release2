/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the approval of custodian doc relodgement
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/22 02:21:11 $ Tag: $Name: $
 */
public class CnclRejectReLodgeCustodianDocTrxOperation extends RejectUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public CnclRejectReLodgeCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CNCL_REJECT_RELODGE_CUSTODIAN_DOC;
	}
}

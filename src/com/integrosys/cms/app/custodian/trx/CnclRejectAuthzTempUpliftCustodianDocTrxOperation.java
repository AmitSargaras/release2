/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the approval of custodian doc lodgement
 * 
 * @author $Author: ravi $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/10 10:20:36 $ Tag: $Name: $
 */
public class CnclRejectAuthzTempUpliftCustodianDocTrxOperation extends RejectUpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public CnclRejectAuthzTempUpliftCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CNCL_REJECT_TEMP_UPLIFT_AUTHZ_CUSTODIAN_DOC;
	}
}

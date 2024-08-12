/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/TempUpliftAuthzCustodianDocTrxOperation.java,v 1.1 2003/06/23 06:39:40 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the temp uplifting of an authorized
 * custodian doc
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 06:39:40 $ Tag: $Name: $
 */
public class TempUpliftAuthzCustodianDocTrxOperation extends UpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public TempUpliftAuthzCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation This will overwrite the
	 * method from the super class
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_TEMP_UPLIFT_AUTHZ_CUSTODIAN_DOC;
	}
}

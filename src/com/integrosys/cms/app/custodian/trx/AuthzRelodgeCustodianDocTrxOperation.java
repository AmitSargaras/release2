/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/AuthzRelodgeCustodianDocTrxOperation.java,v 1.1 2004/05/10 03:14:16 btan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the authorization of temp uplifting of
 * custodian doc
 * 
 * @author $Author: btan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/05/10 03:14:16 $ Tag: $Name: $
 */
public class AuthzRelodgeCustodianDocTrxOperation extends UpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public AuthzRelodgeCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation This will overwrite the
	 * method from the super class
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_AUTHZ_RELODGE_CUSTODIAN_DOC;
	}
}

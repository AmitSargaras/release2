/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/PermUpliftCustodianDocTrxOperation.java,v 1.1 2003/06/23 06:39:40 hltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//app
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation is responsible for the perm uplifting of custodian doc
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/23 06:39:40 $ Tag: $Name: $
 */
public class PermUpliftCustodianDocTrxOperation extends UpdateCustodianDocTrxOperation {
	/**
	 * Default Constructor
	 */
	public PermUpliftCustodianDocTrxOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation This will overwrite the
	 * method from the super class
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_PERM_UPLIFT_CUSTODIAN_DOC;
	}
}

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/profile/ReadProfileGroupOperation.java,v 1.6 2006/03/23 09:24:54 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.profile;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.commodity.main.bus.profile.ProfileSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a profile trx based on a ID
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/03/23 09:24:54 $ Tag: $Name: $
 */
public class ReadProfileGroupOperation extends AbstractProfileTrxOperation implements ITrxReadOperation {

	/**
	 * Default Constructor
	 */
	public ReadProfileGroupOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_PROFILE_GROUP;
	}

	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			IProfileTrxValue profileTrxValue = (IProfileTrxValue) val;
			ProfileSearchCriteria searchCriteria = profileTrxValue.getSearchCriteria();
			if (searchCriteria == null) {
				searchCriteria = new ProfileSearchCriteria();
			}
			return getProfileTrxValue(profileTrxValue.getTransactionType(), searchCriteria);

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}
}
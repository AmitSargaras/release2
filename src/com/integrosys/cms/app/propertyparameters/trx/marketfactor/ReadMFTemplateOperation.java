/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.PropertyParametersHelper;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Operation to read MF Template.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadMFTemplateOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadMFTemplateOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_MF_TEMPLATE;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);

			IMFTemplate stageMFTemplate = null;
			IMFTemplate actualMFTemplate = null;

			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(cmsTrxValue.getReferenceID(),
					ICMSConstant.INSTANCE_MF_TEMPLATE);

			IMFTemplateTrxValue trxVal = new OBMFTemplateTrxValue(cmsTrxValue);

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				// get staging MF Template
				SBPropertyParameters mgr = PropertyParametersHelper.getInstance().getStagingPropertyParameters();
				stageMFTemplate = mgr.getMFTemplate(Long.parseLong(stagingRef));
				trxVal.setStagingMFTemplate(stageMFTemplate);
			}
			if (actualRef != null) {
				// get actual MF Template
				SBPropertyParameters mgr = PropertyParametersHelper.getInstance().getActualPropertyParameters();
				actualMFTemplate = mgr.getMFTemplate(Long.parseLong(actualRef));
				trxVal.setMFTemplate(actualMFTemplate);
			}

			return trxVal;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

}

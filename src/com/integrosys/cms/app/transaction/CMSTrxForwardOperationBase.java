/* Copyright Integro Technologies Pte Ltd
 * com.integrosys.cms.app.transaction.AbstractExtCMSTrxOperation.java Created on Jun 28, 2004 5:17:00 PM
 *
 */

package com.integrosys.cms.app.transaction;

import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @since Jun 28, 2004
 * @author heju
 * @version 1.0.0
 */
public abstract class CMSTrxForwardOperationBase extends CMSTrxOperation {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#preProcess(com
	 * .integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		ICMSTrxValue orgTrxValue = new OBCMSTrxValue();
		AccessorUtil.copyValue(value, orgTrxValue);
		orgTrxValue.setTransactionType(orgTrxValue.getInstanceName());
		ITrxContext trxContext = orgTrxValue.getTrxContext();
		DefaultLogger.debug(this, "-----PreProcessing...country in trxContext=" + trxContext.getTrxCountryOrigin());
		DefaultLogger.debug(this, "-----PreProcessing...country in trxValue=" + orgTrxValue.getOriginatingCountry());
		trxContext.setTrxCountryOrigin(orgTrxValue.getOriginatingCountry());
		trxContext.setTrxOrganisationOrigin(orgTrxValue.getOriginatingOrganisation());
		ICMSTrxValue newTrxValue = (ICMSTrxValue) super.preProcess(value);
		// It found trxContext.getTrxCountryOrigin,
		// trxContext.getTrxOrganisationOrigin() is null;
		// The EBCMSTrxValueBean set by trxContext, still need force trxContext
		// from trxValue
		// newTrxValue.setOriginatingCountry(
		// orgTrxValue.getOriginatingCountry());
		// newTrxValue.setOriginatingOrganisation(
		// orgTrxValue.getOriginatingOrganisation());
		return newTrxValue;
	}

	/*
	 * For <<forward>> operation, its transaction's ID is taken from previous
	 * transaction ( initiated 1stly by Maker ). Here it will update the
	 * transaction with new status and next route.
	 * 
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxOperation#performProcess
	 * (com.integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			// Downcast to ICMSTrxValue under CMS contex
			ICMSTrxValue orgTrxValue = (ICMSTrxValue) value;
			DefaultLogger.debug(this, "-----Permorming...country=" + orgTrxValue.getOriginatingCountry());
			
			orgTrxValue.setTransactionDate(new Date());
			ICMSTrxValue newValue = super.updateTransaction((ICMSTrxValue) value);

			// newValue.setUserID(value.getUserID());

			// assign back
			orgTrxValue.setCurrentTrxHistoryID(newValue.getCurrentTrxHistoryID());
			orgTrxValue.setStatus(newValue.getStatus());
			orgTrxValue.setVersionTime(newValue.getVersionTime());
			orgTrxValue.setTransactionDate(newValue.getTransactionDate());
			// Return
			OBCMSTrxResult result = new OBCMSTrxResult();
			result.setTrxValue(orgTrxValue);
			return result;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}

	}

	/**
	 * operation name is assignable.
	 */
	private String operationName = "";

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
}

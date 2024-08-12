/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/AbstractWaiverRequestTrxOperation.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.generatereq.bus.GenerateRequestException;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;
import com.integrosys.cms.app.generatereq.bus.SBGenerateRequestBusManager;
import com.integrosys.cms.app.generatereq.bus.SBGenerateRequestBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of waiver
 * request trx operations
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public abstract class AbstractWaiverRequestTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * This method set the primary key from the original to the copied waiver
	 * request objects. It is required for the case of updating staging from
	 * actual and vice versa as there is a need to perform a deep clone of the
	 * object and set the required attribute in the object to the original one
	 * so that a proper update can be done.
	 * @param anOrginal - IWaiverRequest
	 * @param aCopy - IWaiverRequest
	 * @return IWaiverRequest - the copied object with required attributes from
	 *         the original waiver request
	 * @throws TrxOperationException on errors
	 */
	protected IWaiverRequest mergeWaiverRequest(IWaiverRequest anOriginal, IWaiverRequest aCopy)
			throws TrxOperationException {
		aCopy.setRequestID(anOriginal.getRequestID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging waiver request
	 * @param anIWaiverRequestTrxValue - IWaiverRequestTrxValue
	 * @return IWaiverRequestTrxValue - the trx object containing the created
	 *         staging waiver request
	 * @throws TrxOperationException if errors
	 */
	protected IWaiverRequestTrxValue createStagingWaiverRequest(IWaiverRequestTrxValue anIWaiverRequestTrxValue)
			throws TrxOperationException {
		try {
			IWaiverRequest waiverRequest = getSBStagingGenerateRequestBusManager().createRequest(
					anIWaiverRequestTrxValue.getStagingWaiverRequest());
			anIWaiverRequestTrxValue.setStagingWaiverRequest(waiverRequest);
			anIWaiverRequestTrxValue.setStagingReferenceID(String.valueOf(waiverRequest.getRequestID()));
			return anIWaiverRequestTrxValue;
		}
		catch (GenerateRequestException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a waiver request transaction
	 * @param anITrxValue - ITrxValue
	 * @return IWaiverRequestTrxValue - the waiver request specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IWaiverRequestTrxValue updateWaiverRequestTransaction(IWaiverRequestTrxValue anIWaiverRequestTrxValue)
			throws TrxOperationException {
		try {
			anIWaiverRequestTrxValue = prepareTrxValue(anIWaiverRequestTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anIWaiverRequestTrxValue);
			OBWaiverRequestTrxValue newValue = new OBWaiverRequestTrxValue(tempValue);
			newValue.setWaiverRequest(anIWaiverRequestTrxValue.getWaiverRequest());
			newValue.setStagingWaiverRequest(anIWaiverRequestTrxValue.getStagingWaiverRequest());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a waiver request
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IWaiverRequestValue - the waiver request specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IWaiverRequestTrxValue getWaiverRequestTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IWaiverRequestTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBWaiverRequestTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging generate request session bean
	 * @return SBGenerateRequestBusManager - the remote handler for the staging
	 *         generate request session bean
	 */
	protected SBGenerateRequestBusManager getSBStagingGenerateRequestBusManager() {
		SBGenerateRequestBusManager remote = (SBGenerateRequestBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_GENERATE_REQUEST_BUS_JNDI, SBGenerateRequestBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the certificate session bean
	 * @return SBGenerateRequestBusManager - the remote handler for the
	 *         certificate session bean
	 */
	protected SBGenerateRequestBusManager getSBGenerateRequestBusManager() {
		SBGenerateRequestBusManager remote = (SBGenerateRequestBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_GENERATE_REQUEST_BUS_JNDI, SBGenerateRequestBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected IWaiverRequestTrxValue prepareTrxValue(IWaiverRequestTrxValue anIWaiverRequestTrxValue) {
		if (anIWaiverRequestTrxValue != null) {
			IWaiverRequest actual = anIWaiverRequestTrxValue.getWaiverRequest();
			IWaiverRequest staging = anIWaiverRequestTrxValue.getStagingWaiverRequest();
			if ((actual != null)
					&& (actual.getRequestID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anIWaiverRequestTrxValue.setReferenceID(String.valueOf(actual.getRequestID()));
			}
			else {
				anIWaiverRequestTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getRequestID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anIWaiverRequestTrxValue.setStagingReferenceID(String.valueOf(staging.getRequestID()));
			}
			else {
				anIWaiverRequestTrxValue.setStagingReferenceID(null);
			}
			return anIWaiverRequestTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IWaiverRequestTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IWaiverRequestTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
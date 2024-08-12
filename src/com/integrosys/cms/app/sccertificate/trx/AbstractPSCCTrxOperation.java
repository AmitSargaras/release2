/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/AbstractPSCCTrxOperation.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.SBSCCertificateBusManager;
import com.integrosys.cms.app.sccertificate.bus.SBSCCertificateBusManagerHome;
import com.integrosys.cms.app.sccertificate.bus.SCCertificateException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class AbstractPSCCTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	 * This method set the primary key from the original to the copied sc
	 * certificate objects. It is required for the case of updating staging from
	 * actual and vice versa as there is a need to perform a deep clone of the
	 * object and set the required attribute in the object to the original one
	 * so that a proper update can be done.
	 * @param anOrginal of ISCCertificate type
	 * @param aCopy of ISCCertificate type
	 * @return ISCCertificate - the copied object with required attributes from
	 *         the original sc certificate
	 * @throws TrxOperationException on errors
	 */
	protected IPartialSCCertificate mergePartialSCCertificate(IPartialSCCertificate anOriginal,
			IPartialSCCertificate aCopy) throws TrxOperationException {
		aCopy.setSCCertID(anOriginal.getSCCertID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anIPartialSCCertificateTrxValue of IPartialSCCertificateTrxValue
	 *        type
	 * @return IPartialSCCertificateTrxValue - the trx object containing the
	 *         created staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IPartialSCCertificateTrxValue createStagingPartialSCCertificate(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws TrxOperationException {
		try {
			IPartialSCCertificate pscCertificate = getSBStagingCertificateBusManager().createPartialSCCertificate(
					anIPartialSCCertificateTrxValue.getStagingPartialSCCertificate());
			anIPartialSCCertificateTrxValue.setStagingPartialSCCertificate(pscCertificate);
			anIPartialSCCertificateTrxValue.setStagingReferenceID(String.valueOf(pscCertificate.getSCCertID()));
			return anIPartialSCCertificateTrxValue;
		}
		catch (SCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a partial sc certificate transaction
	 * @param anITrxValue of ITrxValue type
	 * @return IPartialSCCertificateTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IPartialSCCertificateTrxValue updatePartialSCCertificateTransaction(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) throws TrxOperationException {
		try {
			anIPartialSCCertificateTrxValue = prepareTrxValue(anIPartialSCCertificateTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anIPartialSCCertificateTrxValue);
			OBPartialSCCertificateTrxValue newValue = new OBPartialSCCertificateTrxValue(tempValue);
			newValue.setPartialSCCertificate(anIPartialSCCertificateTrxValue.getPartialSCCertificate());
			newValue.setStagingPartialSCCertificate(anIPartialSCCertificateTrxValue.getStagingPartialSCCertificate());
			return newValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return IPartialSCCertificateTrxValue - the document item specific trx
	 *         value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IPartialSCCertificateTrxValue getPartialSCCertificateTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IPartialSCCertificateTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBSCCertificateTrxValue: " + ex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging sc certificate session bean
	 * @return SBCertificiateBusManager - the remote handler for the staging sc
	 *         certificate session bean
	 */
	protected SBSCCertificateBusManager getSBStagingCertificateBusManager() {
		SBSCCertificateBusManager remote = (SBSCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_SCCERTIFICATE_BUS_JNDI, SBSCCertificateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the certificate session bean
	 * @return SBSCCertificateBusManager - the remote handler for the
	 *         certificate session bean
	 */
	protected SBSCCertificateBusManager getSBSCCertificateBusManager() {
		SBSCCertificateBusManager remote = (SBSCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_SCCERTIFICATE_BUS_JNDI, SBSCCertificateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected IPartialSCCertificateTrxValue prepareTrxValue(
			IPartialSCCertificateTrxValue anIPartialSCCertificateTrxValue) {
		if (anIPartialSCCertificateTrxValue != null) {
			IPartialSCCertificate actual = anIPartialSCCertificateTrxValue.getPartialSCCertificate();
			IPartialSCCertificate staging = anIPartialSCCertificateTrxValue.getStagingPartialSCCertificate();
			if ((actual != null)
					&& (actual.getSCCertID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anIPartialSCCertificateTrxValue.setReferenceID(String.valueOf(actual.getSCCertID()));
			}
			else {
				anIPartialSCCertificateTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getSCCertID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anIPartialSCCertificateTrxValue.setStagingReferenceID(String.valueOf(staging.getSCCertID()));
			}
			else {
				anIPartialSCCertificateTrxValue.setStagingReferenceID(null);
			}
			return anIPartialSCCertificateTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of IPartialSCCertificateTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IPartialSCCertificateTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
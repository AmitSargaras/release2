/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/AbstractSCCTrxOperation.java,v 1.3 2004/01/16 10:48:20 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
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
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/01/16 10:48:20 $ Tag: $Name: $
 */
public abstract class AbstractSCCTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	protected ISCCertificate mergeSCCertificate(ISCCertificate anOriginal, ISCCertificate aCopy)
			throws TrxOperationException {
		aCopy.setSCCertID(anOriginal.getSCCertID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anISCCertificateTrxValue of ISCCertificateTrxValue type
	 * @return ISCCertificateTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ISCCertificateTrxValue createStagingSCCertificate(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws TrxOperationException {
		try {
			ISCCertificate scCertificate = getSBStagingCertificateBusManager().createSCCertificate(
					anISCCertificateTrxValue.getStagingSCCertificate());
			anISCCertificateTrxValue.setStagingSCCertificate(scCertificate);
			anISCCertificateTrxValue.setStagingReferenceID(String.valueOf(scCertificate.getSCCertID()));
			return anISCCertificateTrxValue;
		}
		catch (SCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a sc certificate transaction
	 * @param anITrxValue of ITrxValue type
	 * @return ISCCertificateTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ISCCertificateTrxValue updateSCCertificateTransaction(ISCCertificateTrxValue anISCCertificateTrxValue)
			throws TrxOperationException {
		try {
			anISCCertificateTrxValue = prepareTrxValue(anISCCertificateTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anISCCertificateTrxValue);
			OBSCCertificateTrxValue newValue = new OBSCCertificateTrxValue(tempValue);
			newValue.setSCCertificate(anISCCertificateTrxValue.getSCCertificate());
			newValue.setStagingSCCertificate(anISCCertificateTrxValue.getStagingSCCertificate());
			return newValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	protected void systemLockCheckList(ISCCertificate anISCCertificate) throws TrxOperationException {
		long limitProfileID = anISCCertificate.getLimitProfileID();
		try {
			if (limitProfileID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				proxy.lockCheckListItemByLimitProfile(limitProfileID);
			}
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("Caught CheckListException in systemLockCheckList", ex);
		}
	}

	protected void systemUnlockCheckList(ISCCertificate anISCCertificate) throws TrxOperationException {
		long limitProfileID = anISCCertificate.getLimitProfileID();
		try {
			if (limitProfileID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				proxy.unlockCheckListItemByLimitProfile(limitProfileID);
			}
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("Caught CheckListException in systemUnlockCheckList", ex);
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return ISCCertificateTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ISCCertificateTrxValue getSCCertificateTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ISCCertificateTrxValue) anITrxValue;
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
	protected ISCCertificateTrxValue prepareTrxValue(ISCCertificateTrxValue anISCCertificateTrxValue) {
		if (anISCCertificateTrxValue != null) {
			ISCCertificate actual = anISCCertificateTrxValue.getSCCertificate();
			ISCCertificate staging = anISCCertificateTrxValue.getStagingSCCertificate();
			if ((actual != null)
					&& (actual.getSCCertID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anISCCertificateTrxValue.setReferenceID(String.valueOf(actual.getSCCertID()));
			}
			else {
				anISCCertificateTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getSCCertID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anISCCertificateTrxValue.setStagingReferenceID(String.valueOf(staging.getSCCertID()));
			}
			else {
				anISCCertificateTrxValue.setStagingReferenceID(null);
			}
			return anISCCertificateTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of ISCCertificateTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ISCCertificateTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
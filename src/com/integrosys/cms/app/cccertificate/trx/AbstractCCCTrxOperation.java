/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/AbstractCCCTrxOperation.java,v 1.3 2004/01/16 10:47:59 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.cccertificate.bus.CCCertificateException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.SBCCCertificateBusManager;
import com.integrosys.cms.app.cccertificate.bus.SBCCCertificateBusManagerHome;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
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
 * @since $Date: 2004/01/16 10:47:59 $ Tag: $Name: $
 */
public abstract class AbstractCCCTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	 * This method set the primary key from the original to the copied cc
	 * certificate objects. It is required for the case of updating staging from
	 * actual and vice versa as there is a need to perform a deep clone of the
	 * object and set the required attribute in the object to the original one
	 * so that a proper update can be done.
	 * @param anOrginal - ICCCertificate
	 * @param aCopy - ICCCertificate
	 * @return ICCCertificate - the copied object with required attributes from
	 *         the original cc certificate
	 * @throws TrxOperationException on errors
	 */
	protected ICCCertificate mergeCCCertificate(ICCCertificate anOriginal, ICCCertificate aCopy)
			throws TrxOperationException {
		aCopy.setCCCertID(anOriginal.getCCCertID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anICCCertificateTrxValue - ICCCertificateTrxValue
	 * @return ICCCertificateTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICCCertificateTrxValue createStagingCCCertificate(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws TrxOperationException {
		try {
			ICCCertificate ccCertificate = getSBStagingCertificateBusManager().createCCCertificate(
					anICCCertificateTrxValue.getStagingCCCertificate());
			anICCCertificateTrxValue.setStagingCCCertificate(ccCertificate);
			anICCCertificateTrxValue.setStagingReferenceID(String.valueOf(ccCertificate.getCCCertID()));
			return anICCCertificateTrxValue;
		}
		catch (CCCertificateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a cc certificate transaction
	 * @param anITrxValue - ITrxValue
	 * @return ICCCertificateTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICCCertificateTrxValue updateCCCertificateTransaction(ICCCertificateTrxValue anICCCertificateTrxValue)
			throws TrxOperationException {
		try {
			anICCCertificateTrxValue = prepareTrxValue(anICCCertificateTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anICCCertificateTrxValue);
			OBCCCertificateTrxValue newValue = new OBCCCertificateTrxValue(tempValue);
			newValue.setCCCertificate(anICCCertificateTrxValue.getCCCertificate());
			newValue.setStagingCCCertificate(anICCCertificateTrxValue.getStagingCCCertificate());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	protected void systemLockCheckList(ICCCertificate anICCCertificate) throws TrxOperationException {
		long checkListID = anICCCertificate.getCheckListID();
		try {
			if (checkListID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				proxy.lockCheckListItemByCheckList(checkListID);
			}
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("Caught CheckListException in systemLockCheckList", ex);
		}
	}

	protected void systemUnlockCheckList(ICCCertificate anICCCertificate) throws TrxOperationException {
		long checkListID = anICCCertificate.getCheckListID();
		try {
			if (checkListID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				proxy.unlockCheckListItemByCheckList(checkListID);
			}
		}
		catch (CheckListException ex) {
			throw new TrxOperationException("Caught CheckListException in systemUnlockCheckList", ex);
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return ICCCertificateTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICCCertificateTrxValue getCCCertificateTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ICCCertificateTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCCCertificateTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging cc certificate session bean
	 * @return SBCertificiateBusManager - the remote handler for the staging cc
	 *         certificate session bean
	 */
	protected SBCCCertificateBusManager getSBStagingCertificateBusManager() {
		SBCCCertificateBusManager remote = (SBCCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CCCERTIFICATE_BUS_JNDI, SBCCCertificateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the certificate session bean
	 * @return SBCCCertificateBusManager - the remote handler for the
	 *         certificate session bean
	 */
	protected SBCCCertificateBusManager getSBCCCertificateBusManager() {
		SBCCCertificateBusManager remote = (SBCCCertificateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CCCERTIFICATE_BUS_JNDI, SBCCCertificateBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected ICCCertificateTrxValue prepareTrxValue(ICCCertificateTrxValue anICCCertificateTrxValue) {
		if (anICCCertificateTrxValue != null) {
			ICCCertificate actual = anICCCertificateTrxValue.getCCCertificate();
			ICCCertificate staging = anICCCertificateTrxValue.getStagingCCCertificate();
			if ((actual != null)
					&& (actual.getCCCertID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCCertificateTrxValue.setReferenceID(String.valueOf(actual.getCCCertID()));
			}
			else {
				anICCCertificateTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getCCCertID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCCertificateTrxValue.setStagingReferenceID(String.valueOf(staging.getCCCertID()));
			}
			else {
				anICCCertificateTrxValue.setStagingReferenceID(null);
			}
			return anICCCertificateTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICCCertificateTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICCCertificateTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
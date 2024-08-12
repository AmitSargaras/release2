/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/AbstractCCDocumentLocationTrxOperation.java,v 1.3 2004/04/06 09:22:38 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.bus.SBDocumentLocationBusManager;
import com.integrosys.cms.app.documentlocation.bus.SBDocumentLocationBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of CC
 * document location trx operations
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/06 09:22:38 $ Tag: $Name: $
 */
public abstract class AbstractCCDocumentLocationTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	 * This method set the primary key from the original to the copied CC
	 * document location objects. It is required for the case of updating
	 * staging from actual and vice versa as there is a need to perform a deep
	 * clone of the object and set the required attribute in the object to the
	 * original one so that a proper update can be done.
	 * @param anOrginal of ICCDocumentLocation type
	 * @param aCopy of ICCDocumentLocation type
	 * @return ICCDocumentLocation - the copied object with required attributes
	 *         from the original CC document location
	 * @throws TrxOperationException on errors
	 */
	protected ICCDocumentLocation mergeCCDocumentLocation(ICCDocumentLocation anOriginal, ICCDocumentLocation aCopy)
			throws TrxOperationException {
		aCopy.setDocLocationID(anOriginal.getDocLocationID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * Create the staging document item doc
	 * @param anICCDocumentLocationTrxValue of ICCDocumentLocationTrxValue type
	 * @return ICCDocumentLocationTrxValue - the trx object containing the
	 *         created staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICCDocumentLocationTrxValue createStagingCCDocumentLocation(
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws TrxOperationException {
		try {
			ICCDocumentLocation colDocumentLocation = getSBStagingDocumentLocationBusManager()
					.createCCDocumentLocation(anICCDocumentLocationTrxValue.getStagingCCDocumentLocation());
			anICCDocumentLocationTrxValue.setStagingCCDocumentLocation(colDocumentLocation);
			anICCDocumentLocationTrxValue.setStagingReferenceID(String.valueOf(colDocumentLocation.getDocLocationID()));
			return anICCDocumentLocationTrxValue;
		}
		catch (DocumentLocationException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	/**
	 * Update a CC document location transaction
	 * @param anITrxValue of ITrxValue type
	 * @return ICCDocumentLocationTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICCDocumentLocationTrxValue updateCCDocumentLocationTransaction(
			ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) throws TrxOperationException {
		try {
			anICCDocumentLocationTrxValue = prepareTrxValue(anICCDocumentLocationTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anICCDocumentLocationTrxValue);
			OBCCDocumentLocationTrxValue newValue = new OBCCDocumentLocationTrxValue(tempValue);
			newValue.setCCDocumentLocation(anICCDocumentLocationTrxValue.getCCDocumentLocation());
			newValue.setStagingCCDocumentLocation(anICCDocumentLocationTrxValue.getStagingCCDocumentLocation());
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
	 * @return ICCDocumentLocationTrxValue - the document item specific trx
	 *         value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICCDocumentLocationTrxValue getCCDocumentLocationTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (ICCDocumentLocationTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type OBCCDocumentLocationTrxValue: "
					+ ex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging document location session bean
	 * @return SBDocumentLocationBusManager - the remote handler for the staging
	 *         document location session bean
	 */
	protected SBDocumentLocationBusManager getSBStagingDocumentLocationBusManager() {
		SBDocumentLocationBusManager remote = (SBDocumentLocationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_DOC_LOCATION_BUS_JNDI, SBDocumentLocationBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * To get the remote handler for the document location session bean
	 * @return SBDocumentLocationBusManager - the remote handler for the
	 *         document location session bean
	 */
	protected SBDocumentLocationBusManager getSBDocumentLocationBusManager() {
		SBDocumentLocationBusManager remote = (SBDocumentLocationBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_DOC_LOCATION_BUS_JNDI, SBDocumentLocationBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Prepares a trx object
	 */
	protected ICCDocumentLocationTrxValue prepareTrxValue(ICCDocumentLocationTrxValue anICCDocumentLocationTrxValue) {
		if (anICCDocumentLocationTrxValue != null) {
			ICCDocumentLocation actual = anICCDocumentLocationTrxValue.getCCDocumentLocation();
			ICCDocumentLocation staging = anICCDocumentLocationTrxValue.getStagingCCDocumentLocation();
			if ((actual != null)
					&& (actual.getDocLocationID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCDocumentLocationTrxValue.setReferenceID(String.valueOf(actual.getDocLocationID()));
			}
			else {
				anICCDocumentLocationTrxValue.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.getDocLocationID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				anICCDocumentLocationTrxValue.setStagingReferenceID(String.valueOf(staging.getDocLocationID()));
			}
			else {
				anICCDocumentLocationTrxValue.setStagingReferenceID(null);
			}
			return anICCDocumentLocationTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value of ICCDocumentLocationTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICCDocumentLocationTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/AbstractSLTTrxOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.OBSubLimitType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-20
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      AbstractSLTTrxOperation.java
 */
public abstract class AbstractSLTTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * 
	 * @param anITrxValue
	 * @return ISubLimitTypeTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException
	 * @throws TrxOperationException - if there is a ClassCastException
	 */
	protected ISubLimitTypeTrxValue getSLTTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ISubLimitTypeTrxValue) anITrxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("The ITrxValue is not of type OBProfileTrxValue: " + e.toString());
		}
	}

	/**
	 * Create the staging document item doc
	 * 
	 * @param ISubLimitTypeTrxValue - ISubLimitTypeTrxValue
	 * @return ISubLimitTypeTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         errors
	 */
	protected ISubLimitTypeTrxValue createStagingSLT(ISubLimitTypeTrxValue anISLTTrxValue) throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "createStagingSLT - Begin.");
			ISubLimitType[] sltArray = anISLTTrxValue.getStagingSubLimitTypes();
			DefaultLogger.debug(this, " - Length of sltArray : " + sltArray.length);
			// Step-1 : Create groupID and fillIn
			long generatedGroupID = generateSeq(CommodityConstant.SEQUENCE_SLT_GROUP_SEQ);
			DefaultLogger.debug(this, " - GroupID : " + generatedGroupID);

			// Step-2 : set groupID into all elements
			setGroupID(sltArray, generatedGroupID);

			// Step-3 : prepare staging value and Transactio value value
			anISLTTrxValue.setStagingSubLimitTypes(sltArray);

			// Step-4 create the staging document
			ICommodityMainInfo[] commodityInfoArray = getStagingBusManager().createInfo(
					anISLTTrxValue.getStagingSubLimitTypes());

			ISubLimitType[] valueCreated = null;
			try {
				valueCreated = (ISubLimitType[]) commodityInfoArray;
			}
			catch (ClassCastException e) {
				e.printStackTrace();
				throw e;
			}
			anISLTTrxValue.setStagingSubLimitTypes(valueCreated);
			anISLTTrxValue.setStagingReferenceID(String.valueOf(valueCreated[0].getGroupID()));
			return anISLTTrxValue;
		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		finally {
			DefaultLogger.debug(this, "createStagingSLT - End.");
		}
	}

	protected ISubLimitTypeTrxValue updateActualSLT(ISubLimitTypeTrxValue anSLTTrxValue) throws TrxOperationException {
		try {
			ISubLimitType[] staging = anSLTTrxValue.getStagingSubLimitTypes();
			ISubLimitType[] actual = anSLTTrxValue.getSubLimitTypes();
			ISubLimitType[] updateActual = (ISubLimitType[]) CommonUtil.deepClone(staging);
			DefaultLogger.debug(this, "No of Actutal : " + (actual == null ? 0 : actual.length));
			DefaultLogger.debug(this, "No of Staging : " + (staging == null ? 0 : staging.length));
			if (actual != null) {
				updateActual = mergeSLT(actual, updateActual);
			}
			ISubLimitType[] actualSLT = (ISubLimitType[]) getBusManager().updateInfo(updateActual);
			DefaultLogger.debug(this, "After updated,No of Actual  : " + (actualSLT == null ? 0 : actualSLT.length));
			anSLTTrxValue.setSubLimitTypes(actualSLT);
			return anSLTTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception in updateActualProfile(): " + e.toString());
		}
	}

	protected ISubLimitTypeTrxValue createTransaction(ISubLimitTypeTrxValue anISLTTrxValue)
			throws TrxOperationException {
		DefaultLogger.debug(this, "createTransaction - Begin.");
		try {
			anISLTTrxValue = prepareTrxValue(anISLTTrxValue);
			// create transaction.
			ICMSTrxValue tempValue = super.createTransaction(anISLTTrxValue);
			OBSubLimitTypeTrxValue obSLTTrxValue = new OBSubLimitTypeTrxValue(tempValue);
			obSLTTrxValue.setSubLimitTypes(anISLTTrxValue.getSubLimitTypes());
			obSLTTrxValue.setStagingSubLimitTypes(anISLTTrxValue.getStagingSubLimitTypes());
			return obSLTTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
		finally {
			DefaultLogger.debug(this, "createTransaction - End.");
		}
	}

	protected ISubLimitTypeTrxValue updateTransaction(ISubLimitTypeTrxValue anISLTTrxValue)
			throws TrxOperationException {
		DefaultLogger.debug(this, "updateTransaction - Begin.");
		try {
			anISLTTrxValue = prepareTrxValue(anISLTTrxValue);
			// update transaction.
			ICMSTrxValue tempValue = super.updateTransaction(anISLTTrxValue);
			OBSubLimitTypeTrxValue obSLTTrxValue = new OBSubLimitTypeTrxValue(tempValue);
			obSLTTrxValue.setSubLimitTypes(anISLTTrxValue.getSubLimitTypes());
			obSLTTrxValue.setStagingSubLimitTypes(anISLTTrxValue.getStagingSubLimitTypes());
			return obSLTTrxValue;
		}
		catch (TransactionException e) {
			throw new TrxOperationException(e);
		}
		finally {
			DefaultLogger.debug(this, "updateTransaction - End.");
		}
	}

	protected ITrxResult prepareResult(ISubLimitTypeTrxValue anISLTTrxValue) {
		DefaultLogger.debug(this, "prepareResult - Begin.");
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(anISLTTrxValue);
		DefaultLogger.debug(this, "prepareResult - End.");
		return result;
	}

	protected ICommodityMainInfoManager getBusManager() {
		return CommodityMainInfoManagerFactory.getManager();
	}

	protected ITrxResult defaultProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISubLimitTypeTrxValue trxValue = createStagingSLT(getSLTTrxValue(anITrxValue));
		if (trxValue.getTransactionID() == null) {
			trxValue = createTransaction(trxValue);
		}
		else {
			trxValue = updateTransaction(trxValue);
		}
		return prepareResult(trxValue);
	}

	protected ITrxResult closeProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ISubLimitTypeTrxValue trxValue = getSLTTrxValue(anITrxValue);
		trxValue = updateTransaction(trxValue);
		return prepareResult(trxValue);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Helper Methods ...
	// /////////////////////////////////////////////////////////////////////////

	private long generateSeq(String sequenceName) throws CommodityException {
		SequenceManager seqmgr = new SequenceManager();
		try {
			String seq = seqmgr.getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException(this.getClass().getName() + " : Exception in generating Sequence '"
					+ sequenceName + "' \n The exception is : " + e);
		}
	}

	private void setGroupID(ISubLimitType[] sltArray, long groupID) {
		if (sltArray != null) {
			for (int i = 0; i < sltArray.length; i++) {
				((OBSubLimitType) sltArray[i]).setGroupID(groupID);
			}
		}
	}

	private ICommodityMainInfoManager getStagingBusManager() {
		return CommodityMainInfoManagerFactory.getStagingManager();
	}

	private ISubLimitTypeTrxValue prepareTrxValue(ISubLimitTypeTrxValue aTrxValue) {
		if (aTrxValue == null) {
			return aTrxValue;
		}
		ISubLimitType[] actualValues = aTrxValue.getSubLimitTypes();
		ISubLimitType[] stagingValues = aTrxValue.getStagingSubLimitTypes();

		// updates the transaction value with the correct reference id and
		// staging reference id
		// which in this case will be the group id which identifies a set of
		// profiles
		String referenceID = null;
		if ((actualValues != null) && (actualValues.length > 0)) {
			referenceID = String.valueOf(actualValues[0].getGroupID());
		}
		aTrxValue.setReferenceID(referenceID);
		String stagingReferenceID = null;
		if ((stagingValues != null) && (stagingValues.length > 0)) {
			stagingReferenceID = String.valueOf(stagingValues[0].getGroupID());
		}
		aTrxValue.setStagingReferenceID(stagingReferenceID);
		return aTrxValue;
	}

	private ISubLimitType[] mergeSLT(ISubLimitType[] actualArray, ISubLimitType[] stagingArray)
			throws TrxOperationException {
		HashMap actualMap = new HashMap();
		for (int i = 0; i < actualArray.length; i++) {
			actualMap.put(new Long(actualArray[i].getCommonRef()), actualArray[i]);
		}
		// get actual groupid if available
		long groupID = getGroupID(actualArray);
		if (groupID == ICMSConstant.LONG_INVALID_VALUE) {
			groupID = getGroupID(stagingArray);
		}
		for (int i = 0; i < stagingArray.length; i++) {
			OBSubLimitType staging = (OBSubLimitType) stagingArray[i];
			ISubLimitType actual = (ISubLimitType) actualMap.get(new Long(staging.getCommonRef()));
			if (actual == null) {
				staging.setSubLimitTypeID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else {
				staging.setSubLimitTypeID(actual.getSubLimitTypeID());
				staging.setVersionTime(actual.getVersionTime());
			}
			staging.setGroupID(groupID);
		}
		return stagingArray;
	}

	private long getGroupID(ISubLimitType[] slt) {
		if (slt != null) {
			for (int i = 0; i < slt.length; i++) {
				if (slt[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return slt[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
}

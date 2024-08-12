/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/AbstractTitleDocumentTrxOperation.java,v 1.6 2004/11/03 09:08:06 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
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
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.OBTitleDocument;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author Dayanand $Author: wltan $
 * @version $Revision: 1.6 $
 * @since $Date: 2004/11/03 09:08:06 $ Tag: $Name: $
 */
public abstract class AbstractTitleDocumentTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws com.integrosys.base.businfra.transaction.TransactionException on
	 *         error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Helper method to compare the staging and actual list to identify those
	 * that are actual and those that needs to tbe created. This is accompilshed
	 * by comparing the common reference id of the staging and actual objects.
	 * 
	 * @param actualList - ITitleDocument[]
	 * @param stagingList - ITitleDocument[]
	 * @return IUnitofMeasure - the copied object with required attributes from
	 *         the original UnitofMeasure
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	private ITitleDocument[] mergeTitleDocument(ITitleDocument[] actualList, ITitleDocument[] stagingList)
			throws TrxOperationException {

		DefaultLogger.debug(this, "============ merge == START");
		HashMap actualMap = new HashMap();
		for (int i = 0; i < actualList.length; i++) {
			actualMap.put(new Long(actualList[i].getCommonRef()), actualList[i]);
		}

		// get actual groupid if available
		long groupID = getGroupID(actualList);

		for (int i = 0; i < stagingList.length; i++) {
			ITitleDocument staging = stagingList[i];
			ITitleDocument actual = (ITitleDocument) actualMap.get(new Long(staging.getCommonRef()));
			if (actual == null) {
				((OBTitleDocument) staging).setTitleDocumentID(ICMSConstant.LONG_INVALID_VALUE);
			}
			else {
				((OBTitleDocument) staging).setTitleDocumentID(actual.getTitleDocumentID());
				staging.setVersionTime(actual.getVersionTime());
			}
			((OBTitleDocument) staging).setGroupID(groupID);
		}
		DefaultLogger.debug(this, "============ merge == EDN");
		return stagingList;
	}

	/**
	 * Helper method to get group id given a list of title document type.
	 * 
	 * @param types - ITitleDocument[]
	 * @return group id
	 */
	private long getGroupID(ITitleDocument[] types) {
		if (types != null) {
			for (int i = 0; i < types.length; i++) {
				if (types[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return types[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Create the staging document item doc
	 * @param anITitleDocumentTrxValue - ITitleDocumentTrxValue
	 * @return ITitleDocumentTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         errors
	 */
	protected ITitleDocumentTrxValue createStagingTitleDocument(ITitleDocumentTrxValue anITitleDocumentTrxValue)
			throws TrxOperationException {
		try {
			DefaultLogger.debug(this, "$$$ debug:STG_1");
			ITitleDocument[] value = anITitleDocumentTrxValue.getStagingTitleDocument();

			// Step-1 : Create groupID and fillIn
			long generatedGroupID = generateSeq(CommodityConstant.SEQUENCE_TITLE_DOCUMENT_GROUP_SEQ);
			DefaultLogger.debug(this, "$$$ debug:STG_1.5 generated GroupID = " + generatedGroupID);
			// Step-2 : set groupID into all elements
			setGroupID(value, generatedGroupID);

			// Step-3 : prepare staging value and Transactio value value
			DefaultLogger.debug(this, "$$$ debug:STG_2 ");
			anITitleDocumentTrxValue.setStagingTitleDocument(value);
			DefaultLogger.debug(this, "$$$ debug:STG_3 value length=" + value.length + " , 1st element(value[0])="
					+ value[0]);
			DefaultLogger.debug(this, "$$$ debug:STG_3.5 value[0].getGroupID()='" + value[0].getGroupID() + "'");

			// Step-4 create the staging document
			ICommodityMainInfo[] commodityInfoArray = getStagingBusManager().createInfo(
					anITitleDocumentTrxValue.getStagingTitleDocument());
			DefaultLogger.debug(this, "$$$ debug:STG_3.6 - staging creation done. ");
			ITitleDocument[] valueCreated = null;
			try {
				valueCreated = (ITitleDocument[]) commodityInfoArray;
			}
			catch (ClassCastException e) {
				e.printStackTrace();
				throw e;
			}
			DefaultLogger.debug(this, "$$$ debug:STG_3.7 - staging info typecasted.");

			// Set the groupID(of any one of the element. say 1st element) as
			// staging reference
			anITitleDocumentTrxValue.setStagingTitleDocument(valueCreated);
			anITitleDocumentTrxValue.setStagingReferenceID(String.valueOf(valueCreated[0].getGroupID()));
			DefaultLogger.debug(this, "$$$ debug:STG_4");
			return anITitleDocumentTrxValue;
		}
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Create a document item transaction
	 * @param anITitleDocumentTrxValue - ITitleDocumentTrxValue
	 * @return ITitleDocumentTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected ITitleDocumentTrxValue createTitleDocumentTransaction(ITitleDocumentTrxValue anITitleDocumentTrxValue)
			throws TrxOperationException {
		try {
			anITitleDocumentTrxValue = prepareTrxValue(anITitleDocumentTrxValue);
			ICMSTrxValue trxValue = createTransaction(anITitleDocumentTrxValue);
			OBTitleDocumentTrxValue commodityMainInfoTrxValue = new OBTitleDocumentTrxValue(trxValue);
			commodityMainInfoTrxValue.setStagingTitleDocument(anITitleDocumentTrxValue.getStagingTitleDocument());
			commodityMainInfoTrxValue.setTitleDocument(anITitleDocumentTrxValue.getTitleDocument());
			return commodityMainInfoTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Update the actual TitleDocument from the staging TitleDocument
	 * @param anITitleDocumentTrxValue - ITitleDocumentTrxValue
	 * @return ITitleDocumentTrxValue - the TitleDocument trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected ITitleDocumentTrxValue updateActualTitleDocument(ITitleDocumentTrxValue anITitleDocumentTrxValue)
			throws TrxOperationException {
		try {
			ITitleDocument[] staging = anITitleDocumentTrxValue.getStagingTitleDocument();
			ITitleDocument[] actual = anITitleDocumentTrxValue.getTitleDocument();
			ITitleDocument[] updActual = (ITitleDocument[]) CommonUtil.deepClone(staging);
			DefaultLogger.debug(this, "$$$Debug:::UPD1 staging=" + staging);
			DefaultLogger.debug(this, "$$$Debug:::UPD2 actual =" + actual);
			DefaultLogger.debug(this, "$$$Debug:::UPD3 updActual=" + updActual);
			updActual = mergeTitleDocument(actual, updActual);
			DefaultLogger.debug(this, "$$$Debug:::UPD4 updActual=" + updActual);
			ITitleDocument[] actualTitleDocument = (ITitleDocument[]) getBusManager().updateInfo(updActual);
			DefaultLogger.debug(this, "$$$Debug:::UPD5 updActual=" + actualTitleDocument);
			anITitleDocumentTrxValue.setTitleDocument(actualTitleDocument);
			return anITitleDocumentTrxValue;
		}/*
		 * catch(ConcurrentUpdateException ex) { ex.printStackTrace(); throw new
		 * TrxOperationException(ex); }
		 */
		catch (CommodityException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualTitleDocument(): " + ex.toString());
		}
		catch (java.io.IOException ex) {
			ex.printStackTrace();
			throw new TrxOperationException("Exception in updateActualTitleDocument(): " + ex.toString());
		}
	}

	/**
	 * Update a TitleDocument transaction
	 * @param anITitleDocumentTrxValue - ITrxValue
	 * @return ITitleDocumentTrxValue - the document item specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected ITitleDocumentTrxValue updateTitleDocumentTransaction(ITitleDocumentTrxValue anITitleDocumentTrxValue)
			throws TrxOperationException {
		try {
			anITitleDocumentTrxValue = prepareTrxValue(anITitleDocumentTrxValue);
			ICMSTrxValue tempValue = super.updateTransaction(anITitleDocumentTrxValue);
			OBTitleDocumentTrxValue newValue = new OBTitleDocumentTrxValue(tempValue);
			newValue.setTitleDocument(anITitleDocumentTrxValue.getTitleDocument());
			newValue.setStagingTitleDocument(anITitleDocumentTrxValue.getStagingTitleDocument());
			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return ITitleDocumentTrxValue - the document item specific trx value
	 *         object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is a ClassCastException
	 */
	protected ITitleDocumentTrxValue getTitleDocumentTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ITitleDocumentTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBTitleDocumentTrxValue: " + cex.toString());
		}
	}

	/**
	 * To get the remote handler for the staging TitleDocument session bean
	 * @return SBCommodityMainInfoManager - the remote handler for the staging
	 *         TitleDocument session bean
	 */
	protected ICommodityMainInfoManager getStagingBusManager() {
		return CommodityMainInfoManagerFactory.getStagingManager();
	}

	/**
	 * To get the remote handler for the TitleDocument session bean
	 * @return SBCommodityMainInfoManager - the remote handler for the
	 *         TitleDocument session bean
	 */
	protected ICommodityMainInfoManager getBusManager() {
		return CommodityMainInfoManagerFactory.getManager();
	}

	/**
	 * Prepares a trx object
	 */
	protected ITitleDocumentTrxValue prepareTrxValue(ITitleDocumentTrxValue anITitleDocumentTrxValue) {
		if (anITitleDocumentTrxValue != null) {
			ITitleDocument[] actual = anITitleDocumentTrxValue.getTitleDocument();
			ITitleDocument[] staging = anITitleDocumentTrxValue.getStagingTitleDocument();
			if (staging != null) {
				DefaultLogger.debug(this, " $$upd:1 staging length = " + staging.length + " \n 1st value is : "
						+ staging[0]);
			}
			else {
				DefaultLogger.debug(this, " $$upd:2 staging is NULL");
			}
			if (actual != null) {
				anITitleDocumentTrxValue.setReferenceID(String.valueOf(actual[0].getGroupID()));
			}
			else {
				anITitleDocumentTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anITitleDocumentTrxValue.setStagingReferenceID(String.valueOf(staging[0].getGroupID()));
			}
			else {
				anITitleDocumentTrxValue.setStagingReferenceID(null);
			}
			return anITitleDocumentTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ITitleDocumentTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ITitleDocumentTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected long generateSeq(String sequenceName) throws CommodityException {

		SequenceManager seqmgr = new SequenceManager();
		String seq = null;
		try {
			seq = seqmgr.getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException(this.getClass().getName() + " : Exception in generating Sequence '"
					+ sequenceName + "' \n The exception is : " + e);
		}

	}

	private void setGroupID(ITitleDocument[] array, long groupID) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				((OBTitleDocument) array[i]).setGroupID(groupID);

			}
		}
	}

}
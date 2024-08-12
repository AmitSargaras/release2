/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/titledocument/ReadTitleDocumentIDOperation.java,v 1.5 2004/08/17 06:52:30 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.titledocument;

//ofa
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.TitleDocumentSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a titledocument trx based on a ID
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/17 06:52:30 $ Tag: $Name: $
 */
public class ReadTitleDocumentIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadTitleDocumentIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException if
	 *         any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);

			// get actual commodity title doc type
			TitleDocumentSearchCriteria searchCriteria = new TitleDocumentSearchCriteria();
			ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
			SearchResult result = mgr.searchCommodityMainInfos(searchCriteria);
			ITitleDocument[] actualTitleDocument = (ITitleDocument[]) result.getResultList().toArray(
					new ITitleDocument[0]);

			long actualRefID = getGroupID(actualTitleDocument);
			ITitleDocument[] stagingTitleDocument = null;
			ICommodityMainInfoManager stageMgr = CommodityMainInfoManagerFactory.getStagingManager();
			if (actualRefID != ICMSConstant.LONG_INVALID_VALUE) {
				cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualRefID),
						val.getTransactionType());
				stagingTitleDocument = (ITitleDocument[]) stageMgr.getCommodityMainInfosByGroupID(cmsTrxValue
						.getStagingReferenceID(), ICommodityMainInfo.INFO_TYPE_TITLEDOC);
			}
			else {
				// find transaction for staging title doc type with no actual
				// title doc type
				cmsTrxValue = new TitleDocumentTrxDAO().getTitleDocumentTrxValue(true);
			}
			OBTitleDocumentTrxValue newValue = new OBTitleDocumentTrxValue(cmsTrxValue);
			newValue.setStagingTitleDocument(stagingTitleDocument);
			newValue.setTitleDocument(actualTitleDocument);
			return newValue;

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	/**
	 * Helper method to get groupID of title document type list.
	 * 
	 * @param types a list of title document types
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
}
/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/ReadSLTByGroupIdOperation.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-21
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      ReadSLTByGroupIdOperation.java
 */
public class ReadSLTByGroupIdOperation extends CMSTrxOperation implements ITrxReadOperation {

	/*
	 * @seecom.integrosys.base.businfra.transaction.AbstractTrxOperation#
	 * getOperationName()
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_SLT_GROUP;
	}

	/*
	 * @see
	 * com.integrosys.cms.app.transaction.ITrxReadOperation#getTransaction(com
	 * .integrosys.base.businfra.transaction.ITrxValue)
	 */
	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		DefaultLogger.debug(this, " - getTransaction() - Begin.");
		try {
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(value);
			// get actual sub limit type
			SubLimitTypeSearchCriteria searchCriteria = new SubLimitTypeSearchCriteria();
			ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
			SearchResult result = mgr.searchCommodityMainInfos(searchCriteria);
			ISubLimitType[] actualSLT = (ISubLimitType[]) result.getResultList().toArray(new ISubLimitType[0]);

			long actualRefID = getGroupID(actualSLT);
			DefaultLogger.debug(this, "GroupId : " + actualRefID);
			ISubLimitType[] stagingSLT = null;
			if (actualRefID != ICMSConstant.LONG_INVALID_VALUE) {
				DefaultLogger.debug(this, "TrxType : " + value.getTransactionType());
				cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualRefID),
						value.getTransactionType());
				ICommodityMainInfoManager stageMgr = CommodityMainInfoManagerFactory.getStagingManager();
				stagingSLT = (ISubLimitType[]) stageMgr.getCommodityMainInfosByGroupID(cmsTrxValue
						.getStagingReferenceID(), ICommodityMainInfo.INFO_TYPE_SUBLIMITTYPE);
			}
			else {
				// find transaction for staging slt with no actual slt.
				cmsTrxValue = new SubLimitTypeTrxDAO().getSubLimitTypeTrxValue(true);
			}
			OBSubLimitTypeTrxValue newValue = new OBSubLimitTypeTrxValue(cmsTrxValue);
			newValue.setSubLimitTypes(actualSLT);
			newValue.setStagingSubLimitTypes(stagingSLT);
			return newValue;
		}
		catch (Exception ex) {
			throw new TrxOperationException(ex);
		}
		finally {
			DefaultLogger.debug(this, " - getTransaction() - End.");
		}
	}

	private long getGroupID(ISubLimitType[] types) {
		if (types != null) {
			for (int i = 0; i < types.length; i++) {
				if (types[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return types[i].getGroupID();
				}
			}
		}
		DefaultLogger.debug(this, "SubLimitType == null.");
		return ICMSConstant.LONG_INVALID_VALUE;
	}
}

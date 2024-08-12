/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/ReadWarehouseCountryOperation.java,v 1.8 2004/08/17 06:52:48 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.commodity.main.bus.warehouse.WarehouseSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.8 $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public class ReadWarehouseCountryOperation extends CMSTrxOperation implements ITrxReadOperation {

	public ReadWarehouseCountryOperation() {
		super();
	}

	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_COUNTRY;
	}

	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);
			String countryCode = ((IWarehouseTrxValue) val).getCountryCode();

			DefaultLogger.debug(this, "Country Code: " + countryCode);

			// get actual warehouse
			WarehouseSearchCriteria searchCriteria = new WarehouseSearchCriteria();
			searchCriteria.setCountryCode(countryCode);
			ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
			SearchResult result = mgr.searchCommodityMainInfos(searchCriteria);
			IWarehouse[] actualWarehouse = (IWarehouse[]) result.getResultList().toArray(new IWarehouse[0]);

			IWarehouse[] stageWarehouse = null;
			ICommodityMainInfoManager stageMgr = CommodityMainInfoManagerFactory.getStagingManager();

			long actualRefID = getGroupID(actualWarehouse);
			if (actualRefID != ICMSConstant.LONG_INVALID_VALUE) {
				cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualRefID),
						val.getTransactionType());
				stageWarehouse = (IWarehouse[]) stageMgr.getCommodityMainInfosByGroupID(cmsTrxValue
						.getStagingReferenceID(), ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
			}
			else {
				// find transaction for staging warehouse in country
				cmsTrxValue = new WarehouseTrxDAO().getWarehouseTrxValue(countryCode, true);

				if (cmsTrxValue.getStatus().equals(ICMSConstant.STATE_DELETED)
						|| cmsTrxValue.getStatus().equals(ICMSConstant.STATE_CLOSED)) {
					cmsTrxValue = super.getCMSTrxValue(val);
					actualWarehouse = null;
					stageWarehouse = null;
				}

			}

			IWarehouseTrxValue whTrx = new OBWarehouseTrxValue(cmsTrxValue);

			whTrx.setWarehouse(actualWarehouse);
			// if (stageWarehouse == null || stageWarehouse.length == 0)
			// stageWarehouse = actualWarehouse;
			whTrx.setStagingWarehouse(stageWarehouse);

			return whTrx;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

	private long getGroupID(IWarehouse[] whs) {
		if (whs != null) {
			for (int i = 0; i < whs.length; i++) {
				if (whs[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return whs[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
}

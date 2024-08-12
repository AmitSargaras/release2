/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/ReadUOMByCatTypePdtTypeOperation.java,v 1.4 2004/07/22 03:27:37 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.UnitofMeasureSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/22 03:27:37 $ Tag: $Name: $
 */
public class ReadUOMByCatTypePdtTypeOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadUOMByCatTypePdtTypeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_CAT_PROD;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws com.integrosys.base.businfra.transaction.TransactionException on
	 *         errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);
			IUnitofMeasureTrxValue trxVal = (IUnitofMeasureTrxValue) val;

			String categoryCode = trxVal.getCategoryCode();
			String prodTypeCode = trxVal.getProductTypeCode();
			DefaultLogger.debug(this, "Category Code: " + categoryCode + ", Product Type Code: " + prodTypeCode);

			// get actual commodity uom
			UnitofMeasureSearchCriteria searchCriteria = new UnitofMeasureSearchCriteria();
			searchCriteria.setCategory(categoryCode);
			searchCriteria.setProductTypeCode(prodTypeCode);
			ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
			SearchResult result = mgr.searchCommodityMainInfos(searchCriteria);
			IUnitofMeasure[] actualUnitofMeaure = (IUnitofMeasure[]) result.getResultList().toArray(
					new IUnitofMeasure[0]);

			IUnitofMeasure[] stageUnitofMeasure = null;
			ICommodityMainInfoManager stageMgr = CommodityMainInfoManagerFactory.getStagingManager();

			long actualRefID = getGroupID(actualUnitofMeaure);

			if (actualRefID != ICMSConstant.LONG_INVALID_VALUE) {
				cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualRefID),
						val.getTransactionType());
				stageUnitofMeasure = (IUnitofMeasure[]) stageMgr.getCommodityMainInfosByGroupID(cmsTrxValue
						.getStagingReferenceID(), ICommodityMainInfo.INFO_TYPE_UOM);
			}
			else {
				// find transaction for staging uom with no actual uom for
				// profiles in
				// this particular category and product type
				cmsTrxValue = new UnitofMeasureTrxDAO().getUnitofMeasureTrxValue(categoryCode, prodTypeCode, true);
			}

			OBUnitofMeasureTrxValue uomTrx = new OBUnitofMeasureTrxValue(cmsTrxValue);
			uomTrx.setUnitofMeasure(actualUnitofMeaure);
			if ((stageUnitofMeasure == null) || (stageUnitofMeasure.length == 0)) {
				stageUnitofMeasure = actualUnitofMeaure;
			}

			uomTrx.setStagingUnitofMeasure(stageUnitofMeasure);

			return uomTrx;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Helper method to get groupID of unit of measure list.
	 * 
	 * @param uoms a list of unit of measures
	 * @return group id
	 */
	private long getGroupID(IUnitofMeasure[] uoms) {
		if (uoms != null) {
			for (int i = 0; i < uoms.length; i++) {
				if (uoms[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return uoms[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
}
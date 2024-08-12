/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/list/ReadCommodityUOMListCommand.java,v 1.4 2004/08/19 02:11:20 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.commodity.main.bus.uom.UnitofMeasureComparator;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue;
import com.integrosys.cms.app.commodity.main.trx.uom.OBUnitofMeasureTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/19 02:11:20 $ Tag: $Name: $
 */

public class ReadCommodityUOMListCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "category", "java.lang.String", REQUEST_SCOPE },
				{ "commodityType", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "commodityCategory", "java.lang.String", SERVICE_SCOPE },
				{ "commodityProductType", "java.lang.String", SERVICE_SCOPE },
				{ "productSubTypeMap", "java.util.Map", SERVICE_SCOPE },
				{ "commodityUOMTrxValue", "com.integrosys.cms.app.commodity.main.trx.uom.IUnitofMeasureTrxValue",
						SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String event = (String) map.get("event");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		IUnitofMeasureTrxValue trxValue = new OBUnitofMeasureTrxValue();
		String category = "";
		String commodityType = "";
		if (!event.equals(EVENT_PREPARE) && !event.equals(EVENT_READ)) {
			String trxID = (String) map.get("trxID");
			try {
				trxValue = proxy.getUnitofMeasureTrxValue(ctx, trxID);
				DefaultLogger.debug(this, "trxValue: " + trxValue);
				if ((trxValue.getUnitofMeasure() != null) && (trxValue.getUnitofMeasure().length > 0)) {
					category = trxValue.getUnitofMeasure()[0].getCommodityProfile().getCategory();
					commodityType = trxValue.getUnitofMeasure()[0].getCommodityProfile().getProductType();
				}
				else if ((trxValue.getStagingUnitofMeasure() != null)
						&& (trxValue.getStagingUnitofMeasure().length > 0)) {
					category = trxValue.getStagingUnitofMeasure()[0].getCommodityProfile().getCategory();
					commodityType = trxValue.getStagingUnitofMeasure()[0].getCommodityProfile().getProductType();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			category = (String) map.get("category");
			commodityType = (String) map.get("commodityType");
			try {
				trxValue = proxy.getUnitofMeasureTrxValue(ctx, category, commodityType);
				if (event.equals(EVENT_PREPARE) && !trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
					result.put("wip", "wip");
				}
				if (trxValue.getUnitofMeasure() != null) {
					IUnitofMeasure[] staging = (IUnitofMeasure[]) AccessorUtil.deepClone(trxValue.getUnitofMeasure());
					trxValue.setStagingUnitofMeasure(staging);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		result.put("commodityCategory", category);
		result.put("commodityProductType", commodityType);

		try {
			Map productSubTypeMap = proxy.getProductSubTypesByCategoryAndProduct(category, commodityType);
			result.put("productSubTypeMap", productSubTypeMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		if (trxValue.getUnitofMeasure() != null) {
			Arrays.sort(trxValue.getUnitofMeasure(), new UnitofMeasureComparator(UnitofMeasureComparator.BY_PROFILE));
		}
		if (trxValue.getStagingUnitofMeasure() != null) {
			Arrays.sort(trxValue.getStagingUnitofMeasure(), new UnitofMeasureComparator(
					UnitofMeasureComparator.BY_PROFILE));
		}
		result.put("commodityUOMTrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

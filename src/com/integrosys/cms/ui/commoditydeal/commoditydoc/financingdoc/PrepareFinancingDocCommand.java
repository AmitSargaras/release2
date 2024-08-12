/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/PrepareFinancingDocCommand.java,v 1.2 2004/06/04 05:05:48 hltan Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:05:48 $ Tag: $Name: $
 */

public class PrepareFinancingDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "uomMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", SERVICE_SCOPE },
				{ "commodityDealTrxValue", "com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue",
						SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "docTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "docTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "quantityUOMID", "java.util.Collection", REQUEST_SCOPE },
				{ "quantityUOMValue", "java.util.Collection", REQUEST_SCOPE },
				{ "uomMap", "java.util.HashMap", SERVICE_SCOPE }, });
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

		CountryList countryList = CountryList.getInstance();
		result.put("countryLabels", countryList.getCountryLabels());
		result.put("countryValues", countryList.getCountryValues());

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		SalesDocTypeList docTypeList = SalesDocTypeList.getInstance();
		result.put("docTypeID", docTypeList.getSalesDocTypeID());
		result.put("docTypeValue", docTypeList.getSalesDocTypeValue());

		HashMap uomMap = (HashMap) map.get("uomMap");
		Collection uomID = new ArrayList();
		Collection uomValue = new ArrayList();
		long profileID = ICMSConstant.LONG_INVALID_VALUE;
		String from_event = (String) map.get("from_event");
		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		if (from_event.equals(FinancingDocAction.EVENT_READ)) {
			profileID = trxValue.getCommodityDeal().getContractProfileID();
		}
		else {
			profileID = trxValue.getStagingCommodityDeal().getContractProfileID();
		}

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			uomMap = new HashMap();
			UOMWrapper[] uomList = proxy.getUnitofMeasure(profileID);
			if (uomList != null) {
				for (int i = 0; i < uomList.length; i++) {
					UOMWrapper tempUOM = uomList[i];
					uomID.add(tempUOM.getID());
					uomValue.add(tempUOM.getLabel());
					uomMap.put(tempUOM.getID(), tempUOM);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		result.put("uomMap", uomMap);

		result.put("quantityUOMID", uomID);
		result.put("quantityUOMValue", uomValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

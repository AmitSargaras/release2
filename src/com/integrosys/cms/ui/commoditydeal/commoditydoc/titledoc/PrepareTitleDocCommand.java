/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/PrepareTitleDocCommand.java,v 1.6 2004/11/18 07:24:44 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/11/18 07:24:44 $ Tag: $Name: $
 */

public class PrepareTitleDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceTitleDocObj", "com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument",
						SERVICE_SCOPE }, { "docType", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "processStageID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "processStageValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "titleDocTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "titleDocTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
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

		ProcessStageList processStageList = ProcessStageList.getInstance();
		result.put("processStageID", processStageList.getProcessStageID());
		result.put("processStageValue", processStageList.getProcessStageValue());

		ArrayList titleDocTypeID = new ArrayList();
		ArrayList titleDocTypeValue = new ArrayList();

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		String strDocType = (String) map.get("docType");
		ICommodityTitleDocument titleDocObj = (ICommodityTitleDocument) map.get("serviceTitleDocObj");
		if ((strDocType == null) && (titleDocObj != null) && (titleDocObj.getTitleDocType() != null)) {
			strDocType = titleDocObj.getTitleDocType().getType();
		}
		if ((strDocType != null) && (strDocType.length() > 0)) {
			try {
				ITitleDocument[] titleDocList = proxy.getAllTitleDocuments(strDocType);
				if (titleDocList != null) {
					Arrays.sort(titleDocList, new Comparator() {
						public int compare(Object o1, Object o2) {
							ITitleDocument se1 = (ITitleDocument) o1;
							ITitleDocument se2 = (ITitleDocument) o2;
							// if (se1.getType().compareTo(se2.getType()) == 0)
							// {
							return se1.getName().compareToIgnoreCase(se2.getName());
							// }
							// return
							// se1.getType().compareToIgnoreCase(se2.getType());
						}
					});
					for (int i = 0; i < titleDocList.length; i++) {
						titleDocTypeID.add(String.valueOf(titleDocList[i].getTitleDocumentID()));
						titleDocTypeValue.add(titleDocList[i].getName());
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}

		if ((titleDocObj != null) && (titleDocObj.getTitleDocType() != null)) {
			ITitleDocument docType = titleDocObj.getTitleDocType();
			if (!titleDocTypeID.contains(String.valueOf(docType.getTitleDocumentID()))) {
				int index = 0;
				while ((index < titleDocTypeValue.size())
						&& (docType.getName().compareToIgnoreCase((String) titleDocTypeValue.get(index)) < 0)) {
					index++;
				}
				titleDocTypeID.add(index, String.valueOf(docType.getTitleDocumentID()));
				titleDocTypeValue.add(index, docType.getName());
			}
		}

		result.put("titleDocTypeID", titleDocTypeID);
		result.put("titleDocTypeValue", titleDocTypeValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

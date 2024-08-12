/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.facmaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.CollateralSubTypeSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchResultItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterDAOFactory;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.contractfinancing.bus.IContractFacilityType;
import com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.ui.common.ContractFinancingFacTypeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.SecurityTypeList;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 04:12:38 $ Tag: $Name: $
 */
public class PrepareFacilityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	
	public PrepareFacilityMasterCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "contractFinancingTrxValue",
				"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "facTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "facTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "facSubTypeList", "java.util.List", REQUEST_SCOPE },};
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			SearchResult facilityObjects=proxy.getFacilityList();
			List values = new ArrayList();
			List labels = new ArrayList();
			TemplateSearchCriteria aCriteria = new TemplateSearchCriteria();
		//	aCriteria.setCollateralType("FAC 2");
			aCriteria.setCountry("IN");
			List list=(List) facilityObjects.getResultList();
			
			
			//CollateralSubTypeSearchResultItem[] itemList = new CollateralSubTypeSearchResultItem[list.size()];
		
			for(int i=0; i<list.size();i++){
				IFacilityNewMaster newMaster=(IFacilityNewMaster) list.get(i);
				values.add(newMaster.getNewFacilityCode());
				labels.add(newMaster.getNewFacilityName());
				//itemList[i] = new CollateralSubTypeSearchResultItem(newMaster.getNewFacilityCode(), newMaster.getNewFacilityName());
			}
			
			
			
			ICheckListTemplateProxyManager proxy2 = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			
			CollateralSubTypeSearchResultItem sr[] = proxy2.getFacilitySubType(aCriteria);
			List facSubTypeList = Arrays.asList(sr);
			
			
			
			resultMap.put("facTypeValues", values);
			resultMap.put("facTypeLabels", labels);
			resultMap.put("facSubTypeList", facSubTypeList);

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

	/**
	 * This method is to filter a list, which will remove previous selected
	 * value
	 * 
	 * @param values - list of value
	 * @param labels - list of labels
	 * @param hm - previous selected value
	 * @return HashMap with the Result
	 */
	public HashMap filterList(List values, List labels, HashMap hm) {

		for (int i = values.size() - 1; i > -1; i--) {
			if (hm.get(values.get(i)) != null) {
				values.remove(i);
				labels.remove(i);
			}
		}
		HashMap returnHm = new HashMap();
		returnHm.put("values", values);
		returnHm.put("labels", labels);
		return returnHm;
	}
}

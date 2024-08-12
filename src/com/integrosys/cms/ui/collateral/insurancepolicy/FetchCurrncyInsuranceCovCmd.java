/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/ReadInsurancePolicyCommand.java,v 1.4 2006/09/06 01:54:23 pratheepa Exp $
 */

package com.integrosys.cms.ui.collateral.insurancepolicy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
//import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
//import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
//import com.integrosys.cms.app.limit.bus.LimitException;
//import com.integrosys.cms.app.limit.proxy.ILimitProxy;
//import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.CollateralStpValidateUtils;
import com.integrosys.cms.ui.collateral.CollateralStpValidator;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/06 01:54:23 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class FetchCurrncyInsuranceCovCmd extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE },
				
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "insuranceCoverageList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
			{ "currencyList", "java.util.List", REQUEST_SCOPE },});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
	
		String from_event = (String) map.get("from_event");
	IInsuranceCoverageProxyManager insuranceCoverageProxyManager = (IInsuranceCoverageProxyManager) BeanHouse.get("insuranceCoverageProxyManager");
		
		SearchResult sr = insuranceCoverageProxyManager.getInsuranceCoverageList(null,null);
		
		ArrayList resultList = (ArrayList)sr.getResultList();
		result.put("insuranceCoverageList", getInsuranceCoverageList(resultList));
		
		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());
		result.put("currencyList", getCurrencyList());
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getCurrencyIsoCode().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
		private List getInsuranceCoverageList(ArrayList resultList) {
		List lbValList = new ArrayList();
		try {
			
			for (int i = 0; i < resultList.size(); i++) {
				IInsuranceCoverage insuranceCoverage = (IInsuranceCoverage) resultList.get(i);
				String id = Long.toString(insuranceCoverage.getId());
				String val = insuranceCoverage.getCompanyName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}

}

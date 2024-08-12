package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamSearchCriteria;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.ui.common.CountryList;

/**
 * PrepareCreditRiskParamUnitTrustCommand Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class PrepareCreditRiskParamUnitTrustCommand extends AbstractCommand {

	// public String[][] getParameterDescriptor() {
	// return (new String[][]{ });
	// }

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "listLabel", "java.util.List", REQUEST_SCOPE },
				{ "listValue", "java.util.List", REQUEST_SCOPE } });
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

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		CountryList countryList = CountryList.getInstance();

		ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();
		CreditRiskParamSearchCriteria searchCriteria = new CreditRiskParamSearchCriteria();
		searchCriteria.setGroupSubType(null);
		searchCriteria.setGroupStockType(null);

		List paramGroupList = new ArrayList();
		try {
			paramGroupList = (List) proxy.getSearchResultForCriteria(searchCriteria, CreditRiskParamType.UNIT_TRUST)
					.getResultList();
		}
		catch (CreditRiskParamGroupException ex) {
			throw new CommandProcessingException("failed to retrieve unit trust credit risk param group", ex);
		}

		Map countryLabelValMap = new HashMap();
		Iterator iterator = paramGroupList.iterator();
		while (iterator.hasNext()) {
			OBCreditRiskParamGroup obParamGroup = (OBCreditRiskParamGroup) iterator.next();
			countryLabelValMap.put(countryList.getCountryName(obParamGroup.getSubType()), String.valueOf(obParamGroup
					.getCreditRiskParamGroupID()));
		}

		String[] keyLabel = (String[]) countryLabelValMap.keySet().toArray(new String[0]);

		List sortedRiskParamCountryLabels = new ArrayList();

		// sort the label according to the country label in CountryList instance
		Collection sortedCountryLabels = countryList.getCountryLabels();
		for (Iterator itr = sortedCountryLabels.iterator(); itr.hasNext();) {
			String countryLabel = (String) itr.next();
			if (ArrayUtils.indexOf(keyLabel, countryLabel) != ArrayUtils.INDEX_NOT_FOUND) {
				sortedRiskParamCountryLabels.add(countryLabel);
			}
		}

		List groupIdList = new ArrayList();
		for (Iterator itr = sortedRiskParamCountryLabels.iterator(); itr.hasNext();) {
			groupIdList.add(countryLabelValMap.get(itr.next()));
		}

		List groupSubtypeList = new ArrayList(sortedRiskParamCountryLabels);

		resultMap.put("listLabel", groupSubtypeList);
		resultMap.put("listValue", groupIdList);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccountry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.CollateralSubTypeSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.ui.common.SecurityTypeList;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/21 03:57:22 $ Tag: $Name: $
 */
public class ListSecurityCountryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListSecurityCountryCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.REQUEST_LOCALE, "java.util.Locale", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
                { "countryCode", "java.lang.String", REQUEST_SCOPE }
        });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "colSubTypeList", "java.util.List", REQUEST_SCOPE },
				{ "secTypeDesc", "java.lang.String", REQUEST_SCOPE },
                { "secType", "java.lang.String", REQUEST_SCOPE },
                { "frame", "java.lang.String", SERVICE_SCOPE }
        });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String countryCode = (String) map.get("countryCode");
			// String countryName =
			// CountryList.getInstance().getCountryName(countryCode);
			String secType = (String) map.get("secType");
			SecurityTypeList secList = SecurityTypeList.getInstance();
			Collection secTypeValue = secList.getSecurityTypeProperty();
			if (secType == null) {
				if (secTypeValue.size() > 0) {
					secType = (String) new ArrayList(secTypeValue).get(0);
				}
				if ((secType == null) || "".equals(secType)) {
					throw new CommandProcessingException("Security Type is null or empty");
				}
			}
			DefaultLogger.debug(this, "Security type >>>>>>>>>>" + secType);

			String secTypeDesc = secList.getSecurityTypeValue(secType, (Locale) map
					.get(ICommonEventConstant.REQUEST_LOCALE));
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			TemplateSearchCriteria criteria = new TemplateSearchCriteria();
			criteria.setCollateralType(secType);
			criteria.setCountry(countryCode);
			CollateralSubTypeSearchResultItem sr[] = proxy.getCollateralSubType(criteria);
			List colSubTypeList = Arrays.asList(sr);
			resultMap.put("colSubTypeList", colSubTypeList);
			resultMap.put("secTypeDesc", secTypeDesc);
			resultMap.put("secType", secType);
            resultMap.put("frame", "true");      //frame is always true when accessed via this class
        }
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}

package com.integrosys.cms.ui.genddn;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CountryOrgCodeUtil;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-12-9
 * @Tag com.integrosys.cms.ui.genccc.RefreshGenerateCCCommand.java
 */

public class RefreshGenerateDDNCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				// { "cert","com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE }
				{ "creditOfficerLocationCountry", "java.lang.String", REQUEST_SCOPE },
				{ "seniorCreditOfficerLocationCountry", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				// { "cert", "com.integrosys.cms.app.ddn.bus.IDDN",
				// SERVICE_SCOPE },
				// { "cert", "com.integrosys.cms.app.ddn.bus.IDDN", FORM_SCOPE
				// },
				{ "countryLabels", "java.util.List", REQUEST_SCOPE },
				{ "countryValues", "java.util.List", REQUEST_SCOPE },
				{ "creditOrgCodeLabels", "java.util.List", REQUEST_SCOPE },
				{ "creditOrgCodeValues", "java.util.List", REQUEST_SCOPE },
				{ "seniorOrgCodeLabels", "java.util.List", REQUEST_SCOPE },
				{ "seniorOrgCodeValues", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			// IDDN cert = (IDDN) map.get("cert");
			// IBookingLocation creditLoc = cert.getCreditOfficerLocation();
			// IBookingLocation seniorLoc = cert.getSeniorOfficerLocation();
			String creditLocCountry = (String) map.get("creditOfficerLocationCountry");
			String seniorLocCountry = (String) map.get("seniorCreditOfficerLocationCountry");

			ArrayList creditOrgCodeLabels = new ArrayList();
			ArrayList creditOrgCodeValues = new ArrayList();
			ArrayList seniorOrgCodeLabels = new ArrayList();
			ArrayList seniorOrgCodeValues = new ArrayList();

			if (creditLocCountry != null) {
				CountryOrgCodeUtil.fillOrgCode2List(creditLocCountry, creditOrgCodeValues, creditOrgCodeLabels);
			}
			if (seniorLocCountry != null) {
				CountryOrgCodeUtil.fillOrgCode2List(seniorLocCountry, seniorOrgCodeValues, seniorOrgCodeLabels);
			}
			resultMap.put("creditOrgCodeValues", creditOrgCodeValues);
			resultMap.put("creditOrgCodeLabels", creditOrgCodeLabels);
			resultMap.put("seniorOrgCodeValues", seniorOrgCodeValues);
			resultMap.put("seniorOrgCodeLabels", seniorOrgCodeLabels);

			CountryList list = CountryList.getInstance();
			resultMap.put("countryValues", list.getCountryValues());
			resultMap.put("countryLabels", list.getCountryLabels());

			// resultMap.put("cert", cert);

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
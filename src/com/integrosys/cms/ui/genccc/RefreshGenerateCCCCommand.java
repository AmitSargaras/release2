package com.integrosys.cms.ui.genccc;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-12-9
 * @Tag com.integrosys.cms.ui.genccc.RefreshGenerateCCCommand.java
 */

public class RefreshGenerateCCCCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", FORM_SCOPE },
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
			ICCCertificate cert = (ICCCertificate) map.get("cert");
			IBookingLocation creditLoc = cert.getCreditOfficerLocation();
			IBookingLocation seniorLoc = cert.getSeniorOfficerLocation();

			ArrayList valuesList = new ArrayList();
			ArrayList labelsList = new ArrayList();
			if (creditLoc != null) {
				fillOrgCodeValueLabelList(creditLoc.getCountryCode(), valuesList, labelsList);
			}
			resultMap.put("creditOrgCodeValues", valuesList);
			resultMap.put("creditOrgCodeLabels", labelsList);

			valuesList = new ArrayList();
			labelsList = new ArrayList();
			if (seniorLoc != null) {
				fillOrgCodeValueLabelList(seniorLoc.getCountryCode(), valuesList, labelsList);
			}
			resultMap.put("seniorOrgCodeValues", valuesList);
			resultMap.put("seniorOrgCodeLabels", labelsList);

			CountryList list = CountryList.getInstance();
			resultMap.put("countryValues", list.getCountryValues());
			resultMap.put("countryLabels", list.getCountryLabels());

			resultMap.put("cert", cert);

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

	private void fillOrgCodeValueLabelList(String countryCode, ArrayList valuesList, ArrayList labelsList) {
		if (countryCode == null) {
			return;
		}
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		try {
			IBookingLocation bkg[] = proxy.getBookingLocationByCountry(countryCode);
			if (bkg != null) {
				DefaultLogger.debug(this, "Length of BookingLocation : " + bkg.length);
				if (valuesList == null) {
					valuesList = new ArrayList();
				}
				if (labelsList == null) {
					labelsList = new ArrayList();
				}
				for (int i = 0; i < bkg.length; i++) {
					valuesList.add(bkg[i].getOrganisationCode());
					labelsList.add(CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.ORG_CODE, bkg[i]
							.getOrganisationCode()));
				}
			}
		}
		catch (LimitException e) {
			e.printStackTrace();
		}
	}
}
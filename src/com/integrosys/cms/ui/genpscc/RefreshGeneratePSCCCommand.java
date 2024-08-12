package com.integrosys.cms.ui.genpscc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.ui.common.CountryOrgCodeUtil;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-12-9
 * @Tag com.integrosys.cms.ui.genccc.RefreshGenerateCCCommand.java
 */

public class RefreshGeneratePSCCCommand extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", FORM_SCOPE },
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
			IPartialSCCertificate cert = (IPartialSCCertificate) map.get("cert");
			IBookingLocation creditLoc = null;
			IBookingLocation seniorLoc = null;
			if (cert != null) {
				creditLoc = cert.getCreditOfficerLocation();
				seniorLoc = cert.getSeniorOfficerLocation();
			}
			CountryOrgCodeUtil.fillCountryOrgCode2Map(resultMap, creditLoc, seniorLoc);
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
}
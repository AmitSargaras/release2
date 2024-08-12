/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genpscc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.proxy.ISCCertificateProxyManager;
import com.integrosys.cms.app.sccertificate.proxy.SCCertificateProxyManagerFactory;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/01/28 10:29:12 $ Tag: $Name: $
 */
public class SubmitGeneratePSCCCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitGeneratePSCCCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "certTrxVal", "com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue", SERVICE_SCOPE },
				{ "cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, });
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
				{ "certTrxVal", "com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue", SERVICE_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
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
			IPartialSCCertificateTrxValue certTrxVal = (IPartialSCCertificateTrxValue) map.get("certTrxVal");
			IPartialSCCertificate cert = (IPartialSCCertificate) map.get("cert");
			// DefaultLogger.debug(this,"Partial cert"+cert);
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ISCCertificateProxyManager proxy = SCCertificateProxyManagerFactory.getSCCertificateProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
			// ctx.setTrxOrganisationOrigin(limit.getOriginatingLocation().
			// getOrganisationCode());
			if (ICMSConstant.STATE_REJECTED.equals(certTrxVal.getStatus())) {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>maker edit generate");
				certTrxVal = proxy.makerEditRejectedGeneratePartialSCC(ctx, certTrxVal, cert);
			}
			else {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>maker generate");
				certTrxVal = proxy.makerGeneratePartialSCC(ctx, certTrxVal, cert);
			}
			resultMap.put("certTrxVal", certTrxVal);
			resultMap.put("request.ITrxValue", certTrxVal);
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

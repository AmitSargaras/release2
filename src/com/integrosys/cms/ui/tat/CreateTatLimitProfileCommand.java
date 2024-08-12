/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/CreateTatLimitProfileCommand.java,v 1.19 2005/08/05 02:49:56 lyng Exp $
 */

package com.integrosys.cms.ui.tat;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: lyng $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2005/08/05 02:49:56 $ Tag: $Name: $
 */
public class CreateTatLimitProfileCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public CreateTatLimitProfileCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "bcaLocalInd", "java.lang.String", REQUEST_SCOPE },
				{ "bcaCreateDate", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "limitObList", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "fam", "java.lang.String", REQUEST_SCOPE }, { "famcode", "java.lang.String", REQUEST_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE }, { "sscStatus", "java.lang.String", REQUEST_SCOPE }

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
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

		try {
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (null == limitProfileOB) {
				throw new CommandProcessingException("ILimitProfile is null in session!");
			}
			DefaultLogger.debug(this, "inside CreateTatLimitProfileCommand");
			long limitProfileID = limitProfileOB.getLimitProfileID();
			String limitprofileid = Long.toString(limitProfileID);
			String event = (String) map.get("event");
			String sscStatus = "";
			String famcode = new String();
			result.put("event", event);
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue limitProfileTrxValue = new OBLimitProfileTrxValue();
			String fam = (String) limitproxy.getFAMName(limitProfileID).get(ICMSConstant.FAM_NAME);
			famcode = (String) limitproxy.getFAMName(limitProfileID).get(ICMSConstant.FAM_CODE);
			limitProfileTrxValue = limitproxy.getTrxLimitProfile(Long.parseLong(limitprofileid));
			ILimit limit[];
			limit = limitProfileTrxValue.getLimitProfile().getLimits();
			result.put("fam", fam);
			result.put("famcode", famcode);
			int scc_status_ind = limitproxy.getSCCStatus(limitProfileTrxValue.getLimitProfile());
			if (ICMSConstant.SCC_NOT_APPLICABLE == scc_status_ind) {
				sscStatus = "N.A.";
			}
			result.put("sscStatus", sscStatus);
			if (limitProfileTrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_CREATE)
					|| limitProfileTrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_UPDATE)
					|| limitProfileTrxValue.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				result.put("limitObList", limitProfileTrxValue.getStagingLimitProfile());
				result.put("limitprofileOb", limitProfileTrxValue.getStagingLimitProfile());
				if ((!event.equals("prepare_form")) && (!event.equals("prepare_form1")) && (!event.equals("refresh"))
						&& (!event.equals("refreshresubmit")) && (!event.equals("close"))
						&& (!event.equals("updateBflInd"))) {
					result.put("wip", "passive");
				}
				DefaultLogger.debug(this, "inside staging");

			}
			else {
				result.put("limitObList", limitProfileTrxValue.getLimitProfile());
				result.put("limitprofileOb", limitProfileTrxValue.getLimitProfile());
				result.put("wip", "active");
				DefaultLogger.debug(this, "inside else");
			}

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}

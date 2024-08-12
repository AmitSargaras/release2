/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/PrepareEditLimitsCommand.java,v 1.3 2005/09/23 05:24:06 whuang Exp $
 */
package com.integrosys.cms.ui.limit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/23 05:24:06 $ Tag: $Name: $
 */

public class PrepareEditLimitsCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limitObList", "com.integrosys.cms.app.limit.bus.OBLimitProfile", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "limitTrxProfile", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", FORM_SCOPE },
				{ "limitTrxProfile", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE } });
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
		HashMap temp = new HashMap();
		ILimitProfile limitProfile = (ILimitProfile) map.get("limitObList");
		ITrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ITeam currentTeam = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		trxContext.setLimitProfile(limitProfile);
		String wip = "N";
		boolean haveSameCountryCode = true;
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		try {
			ILimitTrxValue limitTrxProfile = limitProxy.getTrxLimitByLimitProfile(trxContext, limitProfile);
			ILimitTrxValue[] trxValues = limitTrxProfile.getLimitTrxValues();
			if ((trxValues != null) && (trxValues.length != 0)) {
				long teamID = trxValues[0].getTeamID();
				DefaultLogger.debug(this, "teamID: -------------------------------------- " + teamID);
				if ((teamID != ICMSConstant.LONG_INVALID_VALUE) && (teamID != 0)) {
					haveSameCountryCode = CompareUtil.haveSameCountryCode(currentTeam, teamID);
				}
				for (int i = 0; i < trxValues.length; i++) {
					String status = trxValues[i].getStatus();
					if (ICMSConstant.STATE_PENDING_UPDATE.equals(status) || ICMSConstant.STATE_REJECTED.equals(status)) {
						wip = "Y";
						break;
					}
				}
			}
			result.put("limitTrxProfile", limitTrxProfile);
			result.put("wip", wip);
			result.put("haveSameCountryCode", String.valueOf(haveSameCountryCode));
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
			throw new CommandProcessingException();
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		return temp;
	}
}

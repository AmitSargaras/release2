/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/ddndue/MonDDNDue.java,v 1.9 2003/12/24 03:23:56 btchng Exp $
 */

package com.integrosys.cms.app.eventmonitor.ddndue;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.eventmonitor.AbstractMonCommon;
import com.integrosys.cms.app.eventmonitor.IMonRule;
import com.integrosys.cms.app.eventmonitor.IMonitorDAO;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.common.OBDateRuleParam;
import com.integrosys.cms.app.systemparameters.Constants;
import com.integrosys.cms.app.systemparameters.proxy.SystemParametersProxy;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;
import com.integrosys.component.commondata.app.bus.IBusinessParameter;
import com.integrosys.component.commondata.app.bus.IBusinessParameterGroup;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

public class MonDDNDue extends AbstractMonCommon {
	private IMonitorDAO[] daoArray = { new DDNDueDAO() };

	private static final String EVENT_DDN_DUE = "EV_DDN_DUE";

	public IMonitorDAO[] getDAOArray() {
		return this.daoArray;
	}

	public String getEventName() {
		return EVENT_DDN_DUE;
	}

	/**
	 * This method constructs the Rule Param based on the input bussiness object
	 * passed in..
	 */
	public IRuleParam constructRuleParam(int ruleNum) {
		OBDateRuleParam param = new OBDateRuleParam();
		param.setRuleID("R_DDN_DUE");
		param.setSysDate(DateUtil.getDate());

		int paramValue = Integer.MAX_VALUE;

		try {
			SystemParametersProxy proxy = new SystemParametersProxy();
			IBusinessParameterGroupTrxValue value = proxy
					.getBusinessParameterGroupByGroupCode(Constants.SYSTEM_PARAMS_GROUP_CODE);

			if (value != null) {
				IBusinessParameterGroup group = value.getBusinessParameterGroup();

				if (group != null) {
					IBusinessParameter[] paramsArr = group.getBusinessParameters();

					if (paramsArr != null) {
						for (int i = 0; i < paramsArr.length; i++) {
							if (Constants.SYSTEM_PARAMS_NUM_DAYS_DDN_UNISSUED_UPON_BCA_RECEIPT.equals(paramsArr[i]
									.getParameterCode())) {
								paramValue = Integer.parseInt(paramsArr[i].getParameterValue());
								break;
							}
						}
					}
				}
				else {
					DefaultLogger.warn(this, "business param group for \"system params\" is null.");
				}
			}
			else {

				DefaultLogger.warn(this, "trx value is null.");

			}

			DefaultLogger.debug(this, "param value = " + paramValue);

		}
		catch (CommonDataManagerException e) {

			DefaultLogger.error(this, "cannot get business parameter group.", e);

		}
		catch (NumberFormatException e) {

			DefaultLogger.error(this, "cannot parse param value.");

		}
		// param.setNumOfDays(RuleParamUtil.getInt(param.getRuleID(),
		// "num_of_days"));
		param.setNumOfDays(paramValue);

		return param;

	}

	/**
	 * this method is to be overriden, which otherwise will be null
	 */
	public IMonRule getTriggerRule() {
		return new DDNDueRule();
	}

}

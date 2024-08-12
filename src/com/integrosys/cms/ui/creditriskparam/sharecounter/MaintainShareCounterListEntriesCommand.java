package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamSearchCriteria;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.OBShareCounter;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 11:32:57 AM
 */

public class MaintainShareCounterListEntriesCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.OFFSET, "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.CURRENT_OFFSET_NUMBER, "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.GROUP_STOCK_TYPE, "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.GROUP_SUBTYPE, "java.lang.String", REQUEST_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "java.lang.Object", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", REQUEST_SCOPE },
				{ ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_FORM, "java.lang.Object", FORM_SCOPE },
				{ ShareCounterConstants.OFFSET, "java.lang.Integer", REQUEST_SCOPE },
				{ ShareCounterConstants.LENGTH, "java.lang.Integer", REQUEST_SCOPE },
				{ ShareCounterConstants.SHARE_COUNTER_WIP, "java.lang.String", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String event = (String) hashMap.get("event");
		OBTrxContext ctx = (OBTrxContext) hashMap.get("theOBTrxContext");

		String offsetStr = (String) hashMap.get(ShareCounterConstants.OFFSET);
		Integer offset;

		try {
			offset = new Integer(offsetStr);
		}
		catch (NumberFormatException ex) {
			offset = ShareCounterConstants.INITIAL_OFFSET;
		}

		if (ShareCounterConstants.SHARE_COUNTER_MAKER_PAGINATE.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_ERORR.equals(event)
				|| ShareCounterConstants.SHARE_COUNTER_MAKER_REFRESH.equals(event)) {

			OBCreditRiskParamGroupTrxValue trxValue = (OBCreditRiskParamGroupTrxValue) hashMap
					.get(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE);
			OBShareCounter obTrx = (OBShareCounter) hashMap.get(ShareCounterConstants.SHARE_COUNTER_FORM);
			OBCreditRiskParamGroup stagingData = (OBCreditRiskParamGroup) trxValue.getStagingCreditRiskParamGroup();
			OBCreditRiskParamGroup actual = (OBCreditRiskParamGroup) trxValue.getCreditRiskParamGroup();

			try {
				String[] shareStatus = obTrx.getParamShareStatus();
				String[] isInternalSuspend = obTrx.getIsIntSuspend();
				if (shareStatus != null) {
					for (int i = 0; i < shareStatus.length; i++) {
						String status = CommonDataSingleton.getCodeCategoryLabelByValue(
								ShareCounterConstants.SHARE_STATUS, shareStatus[i]);
						OBCreditRiskParam[] params = actual.getFeedEntries();
						try {
							String feedIdString = obTrx.getFeedId()[i];
							long feedId1 = Long.parseLong(feedIdString);

							for (int j = 0; j < params.length; j++) {
								long feedId2 = params[j].getFeedId();

								if (feedId1 == feedId2) {
									if ("normal".equalsIgnoreCase(status)) {
										params[j].setIsAcceptable(ICMSConstant.TRUE_VALUE);
									}
									else {
										params[j].setIsAcceptable(ICMSConstant.FALSE_VALUE);
									}
									if (ICMSConstant.TRUE_VALUE.equals(isInternalSuspend[j])) {
										params[j].setIsAcceptable(ICMSConstant.FALSE_VALUE);
									}
								}
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				actual = MaintainShareCounterUtil.mergeStagingWithOb(actual, obTrx);
				stagingData = MaintainShareCounterUtil.mergeStagingWithOb(stagingData, obTrx);

				trxValue.setStagingCreditRiskParamGroup(stagingData);
				trxValue.setCreditRiskParamGroup(actual);

			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try {
				if (ShareCounterConstants.SHARE_COUNTER_MAKER_UPDATE_ERORR.equals(event)) {
					String lastOffest = (String) hashMap.get(ShareCounterConstants.CURRENT_OFFSET_NUMBER);
					offset = new Integer(lastOffest);
				}
			}
			catch (NumberFormatException ex) {
				offset = ShareCounterConstants.INITIAL_OFFSET;
			}

			result.put(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, trxValue);
		}
		else {
			ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();
			CreditRiskParamSearchCriteria criteria = new CreditRiskParamSearchCriteria();
			String groupStockType = (String) hashMap.get(ShareCounterConstants.GROUP_STOCK_TYPE);
			String groupSubType = (String) hashMap.get(ShareCounterConstants.GROUP_SUBTYPE);
			OBCreditRiskParamGroupTrxValue obTrx = new OBCreditRiskParamGroupTrxValue();

			try {

				criteria.setCurrentIndex(0);
				criteria.setNItems(10);
				criteria.setGroupSubType(groupSubType);
				criteria.setGroupStockType(groupStockType);

				Collection creditResult = proxy.getSearchResultForCriteria(criteria, CreditRiskParamType.SHARE_COUNTER)
						.getResultList();

				if (creditResult == null || creditResult.isEmpty()) {
					throw new CommandProcessingException("No such share counter info for type [" + groupStockType
							+ "], sub type [" + groupSubType + "]");
				}

				ICreditRiskParamGroup group = (ICreditRiskParamGroup) creditResult.iterator().next();

				obTrx.setReferenceID(Long.toString(group.getCreditRiskParamGroupID()));

				OBCreditRiskParamGroupTrxValue value = (OBCreditRiskParamGroupTrxValue) proxy.makerReadCreditRiskParam(
						ctx, obTrx, group, CreditRiskParamType.SHARE_COUNTER);
				ICreditRiskParamGroup groupParam = value.getCreditRiskParamGroup();

				groupParam.setStockType(group.getStockType());
				groupParam.setSubType(group.getSubType());

				groupParam.setSubTypeDescription(CommonDataSingleton.getCodeCategoryLabelByValue(
						ShareCounterConstants.STOCK_EXCHANGE, group.getSubType()));
				groupParam.setStockTypeDescription(CommonDataSingleton.getCodeCategoryLabelByValue(
						ShareCounterConstants.SHARE_TYPE, group.getStockType()));

				result.put(ShareCounterConstants.CREDIT_RISK_PARAM_TRX_VALUE, value);

				String toState = value.getToState();

				if (!ICMSConstant.STATE_ACTIVE.equals(toState)) {
					result.put(ShareCounterConstants.SHARE_COUNTER_WIP, ShareCounterConstants.SHARE_COUNTER_WIP);
				}
			}
			catch (CreditRiskParamGroupException e) {
				throw new CommandProcessingException("failed to read credit risk param group", e);
			}
		}

		result.put(ShareCounterConstants.OFFSET, offset);
		result.put(ShareCounterConstants.LENGTH, ShareCounterConstants.FIXED_LENGTH);
		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}

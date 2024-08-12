package com.integrosys.cms.ui.mfchecklist;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.OBMFChecklist;
import com.integrosys.cms.app.collateral.proxy.marketfactor.ICollateralMarketFactorProxy;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.collateral.trx.marketfactor.OBMFChecklistTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class ReadCommand extends AbstractCommand {

	private ICollateralMarketFactorProxy collateralMarketFactorProxy;

	public void setCollateralMarketFactorProxy(ICollateralMarketFactorProxy collateralMarketFactorProxy) {
		this.collateralMarketFactorProxy = collateralMarketFactorProxy;
	}

	public ICollateralMarketFactorProxy getCollateralMarketFactorProxy() {
		return collateralMarketFactorProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "colTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "colSubTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "colCollateralID", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateID", "java.lang.String", REQUEST_SCOPE },
				{ "isRefresh", "java.lang.String", REQUEST_SCOPE },
				{ "orig_event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "flag1", "java.lang.String", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "colTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "colSubTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "colCollateralID", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateID", "java.lang.String", REQUEST_SCOPE },
				{ "orig_event", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "templateNameList", "java.util.List", REQUEST_SCOPE },
				{ "MFCheckListForm", "java.lang.Object", FORM_SCOPE },
				{ "MFChecklistTrxObj", IMFChecklistTrxValue.class.getName(), SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "flag", "java.lang.String", REQUEST_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			String event = (String) (map.get("event"));
			String isRefresh = (String) map.get("isRefresh");

			IPrPaProxyManager parameterManager = PrPaProxyManagerFactory.getProxyManager();

			IMFChecklistTrxValue marketFactorTrxValue = null;

			String trxID = (String) (map.get("trxID"));
			String strColID = (String) map.get("colCollateralID");
			String strColTypeCode = (String) map.get("colTypeCode");
			String strColSubTypeCode = (String) map.get("colSubTypeCode");
			String selMFTemplateID = (String) map.get("MFTemplateID");

			String flag = (String) map.get("flag");
			String flag1 = (String) map.get("flag1");

			if (flag1 != null) {
				result.put("flag1", flag1);
			}
			else {
				result.put("flag1", flag);
			}

			if (MFCheckListAction.EVENT_MAKER_CLOSE_MFCHECKLIST.equals(event)
					|| MFCheckListAction.EVENT_MFCHECKLIST_TRACK.equals(event)
					|| MFCheckListAction.EVENT_PROCESS_UPDATE.equals(event)) {
				marketFactorTrxValue = getCollateralMarketFactorProxy().getMFChecklistTrxValueByTrxID(ctx, trxID);
				result.put("fromEvent", "todo");

				IMFChecklist checklist = marketFactorTrxValue.getStagingMFChecklist();
				strColID = String.valueOf(checklist.getCollateralID());

				if (checklist.getCollateralSubType() != null) {
					strColTypeCode = checklist.getCollateralSubType().getTypeCode();
					strColSubTypeCode = checklist.getCollateralSubType().getSubTypeCode();
				}
			}
			else {
				marketFactorTrxValue = getCollateralMarketFactorProxy().getMFChecklistTrxValue(ctx,
						Long.parseLong(strColID));
			}

			result.put("MFChecklistTrxObj", marketFactorTrxValue);
			if (checkWip(event, marketFactorTrxValue)) {
				result.put("wip", "wip");
			}

			IMFChecklist curTemplate = null;
			IMFChecklist origTemplate = null;
			if ((selMFTemplateID != null) && !selMFTemplateID.equals("")) {

				boolean found = false;
				if (marketFactorTrxValue == null) {
					marketFactorTrxValue = new OBMFChecklistTrxValue();
				}
				else {
					curTemplate = marketFactorTrxValue.getStagingMFChecklist();
					if (selMFTemplateID.equals(String.valueOf(curTemplate.getMFTemplateID()))) {
						found = true;
					}
					origTemplate = curTemplate;
				}

				if (!found) {
					IMFTemplate selMFTemplate = parameterManager.getMFTemplate(Long.parseLong(selMFTemplateID));
					curTemplate = new OBMFChecklist(selMFTemplate);

				}
				result.put("MFCheckListForm", curTemplate);

				marketFactorTrxValue.setStagingMFChecklist(curTemplate);
				result.put("MFChecklistTrxObj", marketFactorTrxValue);

			}
			else if (marketFactorTrxValue != null) {

				if (MFCheckListAction.EVENT_MAKER_CLOSE_MFCHECKLIST.equals(event)
						|| MFCheckListAction.EVENT_MFCHECKLIST_TRACK.equals(event)
						|| MFCheckListAction.EVENT_PROCESS_UPDATE.equals(event)) {

					curTemplate = marketFactorTrxValue.getStagingMFChecklist();
				}
				else {
					curTemplate = marketFactorTrxValue.getMFChecklist();
				}
				origTemplate = curTemplate;

				if ((curTemplate != null) && ((isRefresh == null) || !isRefresh.equals("refresh"))) {
					result.put("MFCheckListForm", curTemplate);
				}
			}

			ICommonMFTemplate[] mfTemplate = parameterManager.getMFTemplateBySecSubType(strColTypeCode,
					strColSubTypeCode);

			result.put("templateNameList", MarketFactorHelper.getMarketFactorTemplateList(mfTemplate, origTemplate));
			result.put("trxID", map.get("trxID"));
			result.put("colCollateralID", strColID);
			result.put("colTypeCode", strColTypeCode);
			result.put("colSubTypeCode", strColSubTypeCode);
			result.put("orig_event", event);
			result.put("event", event);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		temp.put(COMMAND_RESULT_MAP, result);
		temp.put(COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;

	}

	protected boolean checkWip(String event, ITrxValue value) {
		if (value != null) {
			if ("read".equals(event)) {
				String status = value.getStatus();
				if (ICMSConstant.STATE_PENDING_UPDATE.equals(status)
						|| ICMSConstant.STATE_PENDING_CREATE.equals(status)
						|| ICMSConstant.STATE_REJECTED_CREATE.equals(status)
						|| ICMSConstant.STATE_REJECTED_UPDATE.equals(status)) {
					return true;
				}
			}
		}

		return false;
	}

}

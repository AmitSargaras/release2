package com.integrosys.cms.ui.mfchecklist;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.OBMFChecklist;
import com.integrosys.cms.app.collateral.proxy.marketfactor.ICollateralMarketFactorProxy;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.collateral.trx.marketfactor.OBMFChecklistTrxValue;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.ICommonMFTemplate;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class PrepareMFChecklistCommand extends AbstractCommand {

	private ICollateralMarketFactorProxy collateralMarketFactorProxy;

	public void setCollateralMarketFactorProxy(ICollateralMarketFactorProxy collateralMarketFactorProxy) {
		this.collateralMarketFactorProxy = collateralMarketFactorProxy;
	}

	public ICollateralMarketFactorProxy getCollateralMarketFactorProxy() {
		return collateralMarketFactorProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "MFChecklistTrxObj", IMFChecklistTrxValue.class.getName(), SERVICE_SCOPE },
				{ "MFCheckListForm", "java.lang.Object", FORM_SCOPE },
				{ "colTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "colSubTypeCode", "java.lang.String", REQUEST_SCOPE },
				{ "colCollateralID", "java.lang.String", REQUEST_SCOPE },
				{ "MFTemplateID", "java.lang.String", REQUEST_SCOPE },
				{ "orig_event", "java.lang.String", REQUEST_SCOPE }, { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "flag", "java.lang.String", REQUEST_SCOPE },
				{ "flag1", "java.lang.String", SERVICE_SCOPE }, });
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
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "flag1", "java.lang.String", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

			String event = (String) (map.get("event"));
			String orig_event = (String) map.get("orig_event");
			event = orig_event;

			String flag = (String) map.get("flag");
			String flag1 = (String) map.get("flag1");

			if (flag1 != null) {
				result.put("flag1", flag1);
			}
			else {
				result.put("flag1", flag);
			}

			String trxID = (String) (map.get("trxID"));
			String strColTypeCode = (String) map.get("colTypeCode");
			String strColSubTypeCode = (String) map.get("colSubTypeCode");
			String selMFTemplateID = (String) map.get("MFTemplateID");
			IMFChecklistTrxValue marketFactorTrxValue = null;
			IMFChecklist origTemplate = null;

			if (MFCheckListAction.EVENT_PROCESS_UPDATE.equals(event)) {
				marketFactorTrxValue = getCollateralMarketFactorProxy().getMFChecklistTrxValueByTrxID(ctx, trxID);

				origTemplate = marketFactorTrxValue.getStagingMFChecklist();
			}
			else {
				marketFactorTrxValue = (IMFChecklistTrxValue) (map.get("MFChecklistTrxObj"));
			}

			if (marketFactorTrxValue == null) {
				marketFactorTrxValue = new OBMFChecklistTrxValue();
			}

			IMFChecklist curTemplate = (IMFChecklist) (map.get("MFCheckListForm"));

			IPrPaProxyManager parameterManager = PrPaProxyManagerFactory.getProxyManager();

			if ((selMFTemplateID != null) && !selMFTemplateID.equals("")) {
				boolean found = false;
				if (selMFTemplateID.equals(String.valueOf(curTemplate.getMFTemplateID()))) {
					found = true;
				}

				if (!found) {
					IMFTemplate selMFTemplate = parameterManager.getMFTemplate(Long.parseLong(selMFTemplateID));
					curTemplate = new OBMFChecklist(selMFTemplate);

				}

				result.put("MFCheckListForm", curTemplate);
			}

			ICommonMFTemplate[] mfTemplate = parameterManager.getMFTemplateBySecSubType(strColTypeCode,
					strColSubTypeCode);

			result.put("templateNameList", MarketFactorHelper.getMarketFactorTemplateList(mfTemplate, origTemplate));
			result.put("trxID", map.get("trxID"));
			result.put("colCollateralID", map.get("colCollateralID"));
			result.put("colTypeCode", strColTypeCode);
			result.put("colSubTypeCode", strColSubTypeCode);
			result.put("orig_event", orig_event);
			result.put("event", event);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		temp.put(COMMAND_RESULT_MAP, result);
		temp.put(COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;

	}

}

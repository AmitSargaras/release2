package com.integrosys.cms.ui.mfchecklist;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.proxy.marketfactor.ICollateralMarketFactorProxy;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class ProcessMFCheckListCommand extends AbstractCommand {

	private ICollateralMarketFactorProxy collateralMarketFactorProxy;

	public void setCollateralMarketFactorProxy(ICollateralMarketFactorProxy collateralMarketFactorProxy) {
		this.collateralMarketFactorProxy = collateralMarketFactorProxy;
	}

	public ICollateralMarketFactorProxy getCollateralMarketFactorProxy() {
		return collateralMarketFactorProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "MFChecklistTrxObj", "com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue",
						SERVICE_SCOPE }, { "MFCheckListForm", "java.lang.Object", FORM_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String strID = (String) map.get("trxID");

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

		IMFChecklistTrxValue MFChecklistTrxObj = null;

		try {
			MFChecklistTrxObj = getCollateralMarketFactorProxy().getMFChecklistTrxValueByTrxID(ctx, strID);
		}
		catch (CollateralException ex) {
			throw new CommandProcessingException(ex.getMessage());
		}

		if (MFChecklistTrxObj != null) {
			result.put("MFChecklistTrxObj", MFChecklistTrxObj);
			result.put("MFCheckListForm", MFChecklistTrxObj.getStagingMFChecklist());
		}

		temp.put(COMMAND_RESULT_MAP, result);
		temp.put(COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

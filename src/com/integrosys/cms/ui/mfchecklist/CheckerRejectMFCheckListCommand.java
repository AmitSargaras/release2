package com.integrosys.cms.ui.mfchecklist;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.proxy.marketfactor.ICollateralMarketFactorProxy;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerRejectMFCheckListCommand extends AbstractCommand {
	private ICollateralMarketFactorProxy collateralMarketFactorProxy;

	public void setCollateralMarketFactorProxy(ICollateralMarketFactorProxy collateralMarketFactorProxy) {
		this.collateralMarketFactorProxy = collateralMarketFactorProxy;
	}

	public ICollateralMarketFactorProxy getCollateralMarketFactorProxy() {
		return collateralMarketFactorProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "MFChecklistTrxObj", IMFChecklistTrxValue.class.getName(), SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IMFChecklistTrxValue MFChecklistTrxObj = (IMFChecklistTrxValue) map.get("MFChecklistTrxObj");

			ITrxContext oldCtx = MFChecklistTrxObj.getTrxContext();
			if (oldCtx != null) {
				ctx.setCustomer(oldCtx.getCustomer());
				ctx.setLimitProfile(oldCtx.getLimitProfile());
			}

			IMFChecklistTrxValue res = getCollateralMarketFactorProxy().checkerRejectUpdateMFChecklist(ctx,
					MFChecklistTrxObj);
			result.put("request.ITrxValue", res);
		}
		catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}

		temp.put(COMMAND_RESULT_MAP, result);
		temp.put(COMMAND_EXCEPTION_MAP, exceptionMap);

		return temp;
	}

}

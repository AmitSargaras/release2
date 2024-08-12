package com.integrosys.cms.ui.mfchecklist;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.proxy.marketfactor.ICollateralMarketFactorProxy;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class MakerMFChecklistConfirmCommand extends AbstractCommand {

	private ICollateralMarketFactorProxy collateralMarketFactorProxy;

	public void setCollateralMarketFactorProxy(ICollateralMarketFactorProxy collateralMarketFactorProxy) {
		this.collateralMarketFactorProxy = collateralMarketFactorProxy;
	}

	public ICollateralMarketFactorProxy getCollateralMarketFactorProxy() {
		return collateralMarketFactorProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },
				{ "MFChecklistTrxObj", IMFChecklistTrxValue.class.getName(), SERVICE_SCOPE },
				{ "MFCheckListForm", "com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist",
						FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },

		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();

		HashMap temp = new HashMap();
		try {

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IMFChecklistTrxValue res = null;
			IMFChecklistTrxValue MFChecklistTrxObj = (IMFChecklistTrxValue) map.get("MFChecklistTrxObj");
			IMFChecklist checklist = (IMFChecklist) (map.get("MFCheckListForm"));

			String flag = (String) map.get("flag");
			if ((flag != null) && !flag.equals("null")) {

				if (flag.equals(CategoryCodeConstant.SEARCH_BY_COLLATERAL)) {
					ctx.setCollateralID(checklist.getCollateralID());
				}
				else {
					ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
					IBookingLocation bl = cust.getOriginatingLocation();
					if (bl != null) {
						ctx.setTrxCountryOrigin(bl.getCountryCode());
					}
				}

			}
			else {
				ITrxContext oldCtx = MFChecklistTrxObj.getTrxContext();
				if (oldCtx != null) {

					ctx.setCustomer(oldCtx.getCustomer());
					ctx.setLimitProfile(oldCtx.getLimitProfile());
				}
			}

			if (MFChecklistTrxObj.getStatus().equals(ICMSConstant.STATE_ND)
					|| MFChecklistTrxObj.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) {
				res = getCollateralMarketFactorProxy().makerCreateMFChecklist(ctx, MFChecklistTrxObj, checklist);
			}
			else {
				res = getCollateralMarketFactorProxy().makerUpdateMFChecklist(ctx, MFChecklistTrxObj, checklist);
			}
			result.put("request.ITrxValue", res);

		}
		catch (Exception e) {
			throw (new CommandProcessingException(e.getMessage()));
		}
		temp.put(COMMAND_RESULT_MAP, result);

		return temp;
	}
}

package com.integrosys.cms.ui.limit.facility.main;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;

public class ApproveFacilityMainCommand extends FacilityMainCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "facilityTrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue", SERVICE_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.limit.trx.IFacilityTrxValue",
				REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		if (facilityTrxValue == null) {
			facilityTrxValue = new OBFacilityTrxValue();
		}
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		facilityTrxValue.setStagingFacilityMaster(facilityMasterObj);

		String limitProductType = getFacilityProxy().getProductGroupByProductCode(
				facilityMasterObj.getLimit().getProductDesc());
		String limitDealerProductFlag = getFacilityProxy().getDealerProductFlagByProductCode(
				facilityMasterObj.getLimit().getProductDesc());
		String limitConceptCode = getFacilityProxy().getConceptCodeByProductCode(
				facilityMasterObj.getLimit().getProductDesc());
		String facilityAccountType = getFacilityProxy().getAccountTypeByFacilityCode(
				facilityMasterObj.getLimit().getFacilityCode());

		if (facilityTrxValue.getStatus().equals(ICMSConstant.STATE_PENDING_RETRY)) {
			trxContext.setStpAllowed(true);
		} else {
			trxContext.setStpAllowed(FacilityUtil.CheckIsStpAllowed(facilityMasterObj, trxContext.getLimitProfile()
				.getApplicationType(), limitProductType, limitDealerProductFlag, trxContext.getLimitProfile()
				.getCustomerID(), limitConceptCode, facilityAccountType, trxContext.getLimitProfile()
				.getApplicationLawType()));
		}

		facilityTrxValue = getFacilityProxy().checkerApproveFacilityMaster(trxContext, facilityTrxValue);
		result.put("request.ITrxValue", facilityTrxValue);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
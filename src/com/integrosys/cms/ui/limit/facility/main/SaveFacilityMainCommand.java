package com.integrosys.cms.ui.limit.facility.main;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.FacilityMasterReplicationUtils;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;

public class SaveFacilityMainCommand extends FacilityMainCommand {
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
		try {
			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
			if (facilityTrxValue == null) {
				facilityTrxValue = new OBFacilityTrxValue();
			}

			IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
			facilityMasterObj = FacilityMasterReplicationUtils
					.replicateFacilityMasterForCreateStagingCopy(facilityMasterObj);
			facilityTrxValue.setStagingFacilityMaster(facilityMasterObj);

            String limitProductType = getFacilityProxy().getProductGroupByProductCode(
                    facilityMasterObj.getLimit().getProductDesc());
            String limitDealerProductFlag = getFacilityProxy().getDealerProductFlagByProductCode(
                    facilityMasterObj.getLimit().getProductDesc());
            String limitConceptCode = getFacilityProxy().getConceptCodeByProductCode(
					facilityMasterObj.getLimit().getProductDesc());
			String facilityAccountType = getFacilityProxy().getAccountTypeByFacilityCode(
					facilityMasterObj.getLimit().getFacilityCode());
            boolean isStpAllowed = FacilityUtil.CheckIsStpAllowed(facilityMasterObj, trxContext.getLimitProfile().getApplicationType(),
                    limitProductType, limitDealerProductFlag, trxContext.getLimitProfile().getCustomerID(),
                    limitConceptCode, facilityAccountType, trxContext.getLimitProfile().getApplicationLawType());
			trxContext.setStpAllowed(isStpAllowed);

			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitTrxValue limitTrxValue = limitProxy.getTrxLimit(facilityMasterObj.getLimit().getLimitID());

			facilityTrxValue.setTrxReferenceID(limitTrxValue.getTransactionID());

			IFacilityProxy proxy = getFacilityProxy();
			facilityTrxValue = proxy.makerSaveFacilityMaster(trxContext, facilityTrxValue);
			result.put("request.ITrxValue", facilityTrxValue);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}

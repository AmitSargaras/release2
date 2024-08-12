package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class AddLineDetailCommand extends AbstractCommand implements ILineDetailConstants{
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ILineDetail lineDetail = (ILineDetail) map.get(LINE_DETAIL_FORM);
		
		IGuaranteeCollateral stageCollateral = (IGuaranteeCollateral) itrxValue.getStagingCollateral();
		ILineDetail[] lineDetails = stageCollateral.getLineDetails();
		if(lineDetails == null) {
			lineDetails = new ILineDetail[0];
		}
		DefaultLogger.info(this, "Adding Line Detail into session of staged collateral id: " + stageCollateral.getCollateralID());
		addLineDetail(stageCollateral, lineDetail);
		
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		resultMap.put("itrxValue", itrxValue);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{"from_event", String.class.getName(), REQUEST_SCOPE},
			{"subtype", String.class.getName(), REQUEST_SCOPE},
			{"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE},
			{LINE_DETAIL_FORM, ILineDetail.class.getName(), FORM_SCOPE},
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{"from_event", String.class.getName(), REQUEST_SCOPE},
			{"subtype", String.class.getName(), REQUEST_SCOPE},
			{"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE}
		};
	}
	
	public static void addLineDetail(IGuaranteeCollateral iCol, ILineDetail lineDetail) {
		ILineDetail[] existingArray = iCol.getLineDetails();
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}

		ILineDetail[] newArray = new ILineDetail[arrayLength + 1];
		
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}
		newArray[arrayLength] = lineDetail;

		iCol.setLineDetails(newArray);
	}

}

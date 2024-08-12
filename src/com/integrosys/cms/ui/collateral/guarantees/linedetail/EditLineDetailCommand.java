package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class EditLineDetailCommand extends AbstractCommand implements ILineDetailConstants{
	
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		String selectedItem = (String) map.get("selectedItem");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ILineDetail lineDetail = (ILineDetail) map.get(LINE_DETAIL_FORM);

		if(!AbstractCommonMapper.isEmptyOrNull(selectedItem)) {
			IGuaranteeCollateral stageCollateral = (IGuaranteeCollateral) itrxValue.getStagingCollateral();
			ILineDetail[] lineDetails = stageCollateral.getLineDetails();
			int idx = Integer.valueOf(selectedItem);
			if(lineDetails == null || idx >= lineDetails.length) {
				throw new CommandProcessingException("Failed to edit line detail since total line detail item present is " 
													+ lineDetails.length + " and selected index is " + idx);
			}
			DefaultLogger.info(this, "Updating Line Detail into session of staged collateral id: " + stageCollateral.getCollateralID());
			lineDetails[idx] = lineDetail;
		}else {
			throw new CommandProcessingException("Failed to edit line detail since selectedItem is blank");
		}
		
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
			{"selectedItem", String.class.getName(), REQUEST_SCOPE} 
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{"from_event", String.class.getName(), REQUEST_SCOPE},
			{"subtype", String.class.getName(), REQUEST_SCOPE},
			{"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE}
		};
	}
}

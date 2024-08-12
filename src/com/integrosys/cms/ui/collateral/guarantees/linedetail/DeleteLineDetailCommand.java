package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

public class DeleteLineDetailCommand extends AbstractCommand implements ILineDetailConstants{
	
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		String selectedItemForRemoval = (String) map.get("selectedItemForRemoval");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		
		IGuaranteeCollateral stageCollateral = (IGuaranteeCollateral) itrxValue.getStagingCollateral();

		ILineDetail[] lineDetails = stageCollateral.getLineDetails();
		if(lineDetails != null && lineDetails.length !=0) {
			if(!AbstractCommonMapper.isEmptyOrNull(selectedItemForRemoval)) {
				String [] indexToRemove = selectedItemForRemoval.split(",");
				int newSize = lineDetails.length - indexToRemove.length;
				ILineDetail[] newArray;
				if(newSize > 0) {
					List<ILineDetail> existingItems = new ArrayList<ILineDetail>(Arrays.asList(lineDetails));
					List<ILineDetail> toRemovedITems = new ArrayList<ILineDetail>();
					for(String index : indexToRemove) {
						ILineDetail item = existingItems.get(Integer.valueOf(index));
						toRemovedITems.add(item);
					}
					existingItems.removeAll(toRemovedITems);
					newArray = existingItems.toArray(new ILineDetail[0]);
				}else {
					newArray = new ILineDetail[0];
				}
				stageCollateral.setLineDetails(newArray);
				DefaultLogger.info(this, "Deleted Line Detail from session of staged collateral id: " + stageCollateral.getCollateralID());
			}else {
				throw new CommandProcessingException("Failed to delete line detail since selectedItemForRemoval is blank");
			}
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
			{"selectedItemForRemoval", String.class.getName(), REQUEST_SCOPE},
			{"from_event", String.class.getName(), REQUEST_SCOPE},
			{"subtype", String.class.getName(), REQUEST_SCOPE},
			{"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE}
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

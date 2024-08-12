package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.OBLineDetail;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.limit.bus.IFacilityJdbc;
import com.integrosys.cms.app.limit.bus.ILimit;

public class PrepareLineDetailCommand extends AbstractCommand implements ILineDetailConstants{

	@Override
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		String event = (String) map.get("event");
		List<ILimit> facDetailList = (List<ILimit>) map.get(SESSION_FAC_DETAIL_LIST);
		List<LabelValueBean> facNameList = (List<LabelValueBean>) map.get(SESSION_FAC_NAME_LIST);
		List<LabelValueBean> facIdList = (List<LabelValueBean>) map.get(SESSION_FAC_ID_LIST);
		List<LabelValueBean> facLineNoList = (List<LabelValueBean>) map.get(SESSION_FAC_LINE_NO_LIST);
		
		IFacilityJdbc facilityJdbc = (IFacilityJdbc) BeanHouse.get("facilityJdbc");
		
		if(facDetailList == null || facDetailList.size() == 0) {
			 DefaultLogger.info(this ,"Fetching facilities linked to collateral of id: " + itrxValue.getCollateral().getCollateralID());
			 facDetailList = facilityJdbc.getFacDetailBySecurityId(itrxValue.getCollateral().getCollateralID());
			 
			 facIdList = new ArrayList<LabelValueBean>();
			 facLineNoList =  new ArrayList<LabelValueBean>();
			 facNameList = new ArrayList<LabelValueBean>();
			 DefaultLogger.info(this ,"Preparing dropdown list for Facility Name");
			 RefreshFacilityDropdownCommand.prepareFacilityDropdownList(facDetailList, DROPDOWN_FACILITY_NAME, null, facNameList);
		}
		
		if(facLineNoList == null || facLineNoList.size() == 0) {
			DefaultLogger.info(this ,"Preparing dropdown list for Line No ");
			RefreshFacilityDropdownCommand.prepareFacilityDropdownList(facDetailList, DROPDOWN_LINE_NO, null, facLineNoList);
		}
		
		ILineDetail lineDetail = null;
		
		if(LineDetailAction.EVENT_PREPARE_EDIT_LINE_DETAIL.equals(event) || LineDetailAction.EVENT_VIEW_LINE_DETAIL.equals(event)) {
			String selectedItem = (String) map.get("selectedItem");
			if(!AbstractCommonMapper.isEmptyOrNull(selectedItem)) {
				IGuaranteeCollateral  guaranteeCol = (IGuaranteeCollateral) itrxValue.getStagingCollateral(); 
				int idx = Integer.valueOf(selectedItem);
				if(guaranteeCol.getLineDetails() == null || idx >= guaranteeCol.getLineDetails().length) {
					throw new CommandProcessingException("Failed to open line detail since total line detail item present is " 
														+ guaranteeCol.getLineDetails().length + " and selected index is " + idx);
				}
				lineDetail = guaranteeCol.getLineDetails()[idx];
				
				if(!AbstractCommonMapper.isEmptyOrNull(lineDetail.getFacilityName())) {
					DefaultLogger.info(this ,"Preparing dropdown list for Facility id for facility name: " + lineDetail.getFacilityName());
					RefreshFacilityDropdownCommand.prepareFacilityDropdownList(facDetailList, DROPDOWN_FACILITY_ID, lineDetail.getFacilityName(), facIdList);
				}
					
			}else {
				throw new CommandProcessingException("Failed to open line detail since selectedItem is blank");
			}
			
			if("process".equals(from_event)) {
				IGuaranteeCollateral  guaranteeCol = (IGuaranteeCollateral) itrxValue.getCollateral();
				ILineDetail actualLineDetail = null;
				if(guaranteeCol.getLineDetails() != null) {
					for(ILineDetail actualLd : guaranteeCol.getLineDetails()) {
						if(actualLd.getRefId() == lineDetail.getRefId()) {
							actualLineDetail = actualLd;
							break;
						}
					}
				}
				resultMap.put("actualLineDetail", actualLineDetail);
				resultMap.put("stageLineDetail", lineDetail);
			}
			resultMap.put("selectedItem", selectedItem);
		}else {
			lineDetail = new OBLineDetail();
		}
		
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		resultMap.put(LINE_DETAIL_FORM, lineDetail);
		resultMap.put(SESSION_FAC_NAME_LIST, facNameList);
		resultMap.put(SESSION_FAC_DETAIL_LIST, facDetailList);
		resultMap.put(SESSION_FAC_ID_LIST, facIdList);
		resultMap.put(SESSION_FAC_LINE_NO_LIST, facLineNoList);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
	@Override
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{"serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_NAME_LIST, List.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_LINE_NO_LIST, List.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_ID_LIST, List.class.getName(), SERVICE_SCOPE},
			{"from_event", String.class.getName(), REQUEST_SCOPE},
			{"subtype", String.class.getName(), REQUEST_SCOPE},
			{"event", String.class.getName(), REQUEST_SCOPE},
			{"selectedItem", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
	@Override
	public String[][] getResultDescriptor() {
		return new String[][] {
			{SESSION_FAC_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_NAME_LIST, List.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_LINE_NO_LIST, List.class.getName(), SERVICE_SCOPE},
			{SESSION_FAC_ID_LIST, List.class.getName(), SERVICE_SCOPE},
			{LINE_DETAIL_FORM, LineDetailForm.class.getName(), FORM_SCOPE},
			{"from_event", String.class.getName(), REQUEST_SCOPE},
			{"subtype", String.class.getName(), REQUEST_SCOPE},
			{"selectedItem", String.class.getName(), REQUEST_SCOPE},
			{"actualLineDetail", ILineDetail.class.getName(), REQUEST_SCOPE},
			{"stageLineDetail", ILineDetail.class.getName(), REQUEST_SCOPE}
		};
	}
	
}

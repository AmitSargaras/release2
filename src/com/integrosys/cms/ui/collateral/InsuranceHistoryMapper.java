package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.app.common.util.MapperUtil.bigDecimalToString;
import static com.integrosys.cms.app.common.util.MapperUtil.dateToString;
import static com.integrosys.cms.app.common.util.MapperUtil.emptyIfNull;
import static com.integrosys.cms.ui.collateral.CollateralConstant.INSURANCE_HISTORY_REPORT_FILE_NAME;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.PAGINATION_CURRENT_INDEX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.util.MapperUtil;

public class InsuranceHistoryMapper extends AbstractCommonMapper {

	@Override
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		InsuranceHistoryForm form = (InsuranceHistoryForm) cForm;
		InsuranceHistorySearchCriteria obj = new InsuranceHistorySearchCriteria();
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) map.get(SERVICE_COLLATERAL_OBJ);
		String collateralId = collateralTrx.getReferenceID();
		if(StringUtils.isNotBlank(collateralId)) {
			obj.setCollateralId(Long.valueOf(collateralId));
		}
		obj.setInsuranceCompanyName(StringUtils.strip(form.getInsuranceCompanyName()));
		obj.setReceivedDateFrom(StringUtils.strip(form.getReceivedDateFrom()));
		obj.setReceivedDateTo(StringUtils.strip(form.getReceivedDateTo()));
		
		return obj;
	}

	@Override
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		InsuranceHistoryForm form = (InsuranceHistoryForm) cForm;
		InsuranceHistorySearchResult sr = (InsuranceHistorySearchResult) obj;
		
		if(!CollateralAction.EVENT_SEARCH_INSURANCE_HISTORY_ERROR.equals(form.getEvent())) {
			form.setInsuranceCompanyName(MapperUtil.emptyIfNull(sr.getInsuranceCompanyName()));
			form.setReceivedDateFrom(MapperUtil.emptyIfNull(sr.getReceivedDateFrom()));
			form.setReceivedDateTo(MapperUtil.emptyIfNull(sr.getReceivedDateTo()));
			form.setReport(MapperUtil.emptyIfNull(sr.getReport()));
		}
		
		List<IInsuranceHistoryItem> historyObjList = sr.getInsuranceHistory();
		List<InsuranceHistoryItemForm> historyFormList = new ArrayList<InsuranceHistoryItemForm>();
		
		mapInsuranceHistoryItemOBToForm(historyObjList, historyFormList);
		
		if(!historyFormList.isEmpty())
			form.setInsuranceHistoryItem(historyFormList);
		return form;
	}
	
	public static void mapInsuranceHistoryItemOBToForm(List<IInsuranceHistoryItem> historyObjList,
			List<InsuranceHistoryItemForm> historyFormList) {
		if (CollectionUtils.isEmpty(historyObjList))
			return;
		int index = 1;
		for (IInsuranceHistoryItem item : historyObjList) {
			InsuranceHistoryItemForm historyForm = new InsuranceHistoryItemForm();

			historyForm.setIndex(index);

			historyForm.setStatus(item.getStatus());
			
			String dueDate = dateToString(item.getDueDate(), null);
			historyForm.setDueDate(emptyIfNull(dueDate));

			String insuranceCompanyName = emptyIfNull(item.getInsuranceCompanyName());
			historyForm.setInsuranceCompanyName(insuranceCompanyName);

			String insurancePolicyNo = emptyIfNull(item.getInsurancePolicyNo());
			historyForm.setInsurancePolicyNo(insurancePolicyNo);

			historyForm.setInsuredAmount(bigDecimalToString(item.getInsuredAmount()));

			String expiryDate = dateToString(item.getExpiryDate(), null);
			historyForm.setExpiryDate(emptyIfNull(expiryDate));

			String receivedDate = dateToString(item.getReceivedDate(), null);
			historyForm.setReceivedDate(emptyIfNull(receivedDate));

			historyFormList.add(historyForm);
			index++;
		}
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ PAGINATION_CURRENT_INDEX, String.class.getName(), REQUEST_SCOPE },
			{ INSURANCE_HISTORY_REPORT_FILE_NAME, String.class.getName(), REQUEST_SCOPE}
		};
	}

}

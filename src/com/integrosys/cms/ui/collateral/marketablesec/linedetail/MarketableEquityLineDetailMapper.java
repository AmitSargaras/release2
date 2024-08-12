package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.IMarketableEquityLineDetail;
import com.integrosys.cms.app.collateral.bus.type.marketable.linedetail.OBMarketableEquityLineDetail;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.common.UIUtil;

public class MarketableEquityLineDetailMapper extends AbstractCommonMapper implements IMarketableEquityLineDetailConstants {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapFormToOB");
		MarketableEquityLineDetailForm aForm = (MarketableEquityLineDetailForm) cForm;
        String event = (String) inputs.get("event");
        Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);
        
        IMarketableEquityLineDetail obLineDetail = new OBMarketableEquityLineDetail();
        obLineDetail.setFacilityId(aForm.getFacilityId());
        obLineDetail.setFacilityName(aForm.getFacilityName());
        obLineDetail.setLineNumber(aForm.getLineNumber());
        
        if(StringUtils.isNotBlank(aForm.getSerialNumber())) {
        	obLineDetail.setSerialNumber(aForm.getSerialNumber());
        }
		
        if(StringUtils.isNotBlank(aForm.getFasNumber())) {
        	obLineDetail.setFasNumber(aForm.getFasNumber());
        }
        
        if(StringUtils.isNotBlank(aForm.getLtv())) {
        	obLineDetail.setLtv(Double.valueOf(aForm.getLtv()));
        }
        
        if(StringUtils.isNotBlank(aForm.getRemarks())) {
        	obLineDetail.setRemarks(aForm.getRemarks());
        }
        
        if(StringUtils.isNotBlank(aForm.getMarketableEquityId())) {
        	obLineDetail.setMarketableEquityId(Long.valueOf(aForm.getMarketableEquityId()));
        }
		
        if(StringUtils.isNotBlank(aForm.getLineDetailId())) {
        	obLineDetail.setLineDetailId(Long.valueOf(aForm.getLineDetailId()));
        }
        
        if(StringUtils.isNotBlank(aForm.getRefID())) {
        	obLineDetail.setRefID(Long.valueOf(aForm.getRefID()));
        }
        
		if(!isEmptyOrNull(aForm.getLineValue())) {
			obLineDetail.setLineValue(UIUtil.mapStringToBigDecimal(aForm.getLineValue()));
		}
        
		return obLineDetail;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		
		DefaultLogger.debug(this, "(Inside mapOBToForm called :cForm " + cForm);
		DefaultLogger.debug(this, "(Inside mapOBToForm called :Object " + obj);
		DefaultLogger.debug(this, "(Inside mapOBToForm called :inputs " + inputs);
		
		MarketableEquityLineDetailForm aForm = (MarketableEquityLineDetailForm) cForm;
		IMarketableEquityLineDetail lineDetail = (IMarketableEquityLineDetail) obj;
		
		Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);
		List<IMarketableEquityLineDetail> equityLineDetailList = (List<IMarketableEquityLineDetail>) inputs.get(SESSION_LINE_DETAIL_LIST);
		List<ILimit> facDetailList = (List<ILimit>) inputs.get(SESSION_FAC_DETAIL_LIST);
		
		String event = (String)  inputs.get("event");

		double totalLtv = 0.00;
		if(MarketableEquityLineDetailAction.EVENT_PREPARE_CREATE_LINE_DETAIL.equals(event)) {
			if(equityLineDetailList != null && equityLineDetailList.size()>0) {
				for(IMarketableEquityLineDetail dtl : equityLineDetailList) {
					if(dtl.getLtv() != null)
						totalLtv += dtl.getLtv();
				}
			}
		}
		else if(MarketableEquityLineDetailAction.EVENT_PREPARE_EDIT_LINE_DETAIL.equals(event) ||
					MarketableEquityLineDetailAction.EVENT_ERROR_EDIT_LINE_DETAIL.equals(event)) {
			int selectedItem = isEmptyOrNull((String) inputs.get("selectedItem")) ? -1 : Integer.parseInt((String) inputs.get("selectedItem"));
			if(equityLineDetailList != null) {
				for(int i = 0; i < equityLineDetailList.size(); i++) {
					IMarketableEquityLineDetail equityLineDetail = equityLineDetailList.get(i);
					if((ICMSConstant.LONG_INVALID_VALUE == lineDetail.getLineDetailId() && selectedItem != i) 
							|| !(lineDetail.getLineDetailId() == equityLineDetail.getLineDetailId())) {
						if(equityLineDetail.getLtv() != null)
							totalLtv += equityLineDetail.getLtv();
					}
				}
			}
		}
		
		aForm.setTotalLtv(String.valueOf(totalLtv));
		
		String facDetailMandatory = ICMSConstant.NO;
		if(null != facDetailList) {
			for(ILimit limit : facDetailList) {
				boolean exists = ALLOWED_SYSTEM.contains(limit.getFacilitySystem());
				if(exists) {
					facDetailMandatory = ICMSConstant.YES;
					break;
				}
			}
		}
		aForm.setFacDetailMandatory(facDetailMandatory);
		
		if(StringUtils.isNotBlank(lineDetail.getFacilityId())) {
			aForm.setFacilityId(lineDetail.getFacilityId());
		}
		if(StringUtils.isNotBlank(lineDetail.getFacilityName())) {
			aForm.setFacilityName(lineDetail.getFacilityName());
		}
		if(StringUtils.isNotBlank(lineDetail.getLineNumber())) {
			aForm.setLineNumber(lineDetail.getLineNumber());
		}
		
		if(StringUtils.isNotBlank(lineDetail.getSerialNumber())) {
			aForm.setSerialNumber(lineDetail.getSerialNumber());
		}
		
		if(StringUtils.isNotBlank(lineDetail.getFasNumber())) {
			aForm.setFasNumber(lineDetail.getFasNumber());
		}
		
		if(lineDetail.getLtv() != null) {
			aForm.setLtv(String.valueOf(lineDetail.getLtv()));
		}
		
		if(StringUtils.isNotBlank(lineDetail.getRemarks())) {
			aForm.setRemarks(lineDetail.getRemarks());
		}
		
		if(lineDetail.getMarketableEquityId()>0) {
			aForm.setMarketableEquityId(String.valueOf(lineDetail.getMarketableEquityId()));
		}
		
		if(lineDetail.getLineDetailId()>0) {
			aForm.setLineDetailId(String.valueOf(lineDetail.getLineDetailId()));
		}
		
		if(lineDetail.getRefID()>0) {
			aForm.setRefID(String.valueOf(lineDetail.getRefID()));
		}
		
		if(lineDetail.getLineValue() != null) {
			aForm.setLineValue(lineDetail.getLineValue().toPlainString());
		}
		
		return aForm;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{SESSION_FAC_DETAIL_LIST, "java.util.List", SERVICE_SCOPE},
			{IMarketableEquityLineDetailConstants.SESSION_LINE_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
			{ "event", String.class.getName(), REQUEST_SCOPE},
			{Constants.GLOBAL_LOCALE_KEY, Locale.class.getName(), GLOBAL_SCOPE},
			{ "selectedItem", String.class.getName(), REQUEST_SCOPE},
		});
	}

}

package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.ILineDetail;
import com.integrosys.cms.app.collateral.bus.type.guarantee.linedetail.OBLineDetail;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.common.UIUtil;

public class LineDetailMapper extends AbstractCommonMapper implements ILineDetailConstants{

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		Locale locale = (Locale) inputs.get(Constants.GLOBAL_LOCALE_KEY);
		
		LineDetailForm form = (LineDetailForm) cForm;
		
		ILineDetail lineDetail = new OBLineDetail();
		
		lineDetail.setFacilityName(form.getFacilityName());
		
		lineDetail.setFacilityID(form.getFacilityID());
		
		if(!isEmptyOrNull(form.getLineLevelSecurityOMV())) {
			lineDetail.setLineLevelSecurityOMV(UIUtil.mapStringToBigDecimal(form.getLineLevelSecurityOMV()));
		}
		
		lineDetail.setLineNo(form.getLineNo());
		
		lineDetail.setSerialNo(form.getSerialNo());
		
		if(!isEmptyOrNull(form.getLcnNo())) {
			lineDetail.setLcnNo(UIUtil.mapStringToLong(form.getLcnNo(), locale));
		}
		
		if(!isEmptyOrNull(form.getLineDetailID())) {
			lineDetail.setLineDetailID(Long.valueOf(form.getLineDetailID()));
		}
		
		if(!isEmptyOrNull(form.getCollateralId())) {
			lineDetail.setCollateralID(Long.valueOf(form.getCollateralId()));
		}
		
		if(!isEmptyOrNull(form.getRefId())) {
			lineDetail.setRefId(Long.valueOf(form.getRefId()));
		}
		
		return lineDetail;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		List<ILimit> facDetailList = (List<ILimit>) inputs.get(SESSION_FAC_DETAIL_LIST);
		
		LineDetailForm form = (LineDetailForm) cForm;
		ILineDetail lineDetail = (ILineDetail) obj;
		
		if(!isEmptyOrNull(lineDetail.getFacilityName())) {
			form.setFacilityName(lineDetail.getFacilityName());
		}
		
		String facDetailMandatory = ICMSConstant.NO;
		for(ILimit limit : facDetailList) {
			boolean exists = ALLOWED_SYSTEM.contains(limit.getFacilitySystem());
			if(exists) {
				facDetailMandatory = ICMSConstant.YES;
				DefaultLogger.info(this, "Facility details are mandatory since facility list linked"
						+ " to collateral are from " + ALLOWED_SYSTEM);
				break;
			}
		}
		form.setFacDetailMandatory(facDetailMandatory);
		
		if(!isEmptyOrNull(lineDetail.getFacilityID())) {
			form.setFacilityID(lineDetail.getFacilityID());
		}
		
		if(lineDetail.getLineLevelSecurityOMV() != null) {
			form.setLineLevelSecurityOMV(lineDetail.getLineLevelSecurityOMV().toPlainString());
		}
		
		if(!isEmptyOrNull(lineDetail.getLineNo())) {
			form.setLineNo(lineDetail.getLineNo());
		}
		
		if(!isEmptyOrNull(lineDetail.getSerialNo())) {
			form.setSerialNo(lineDetail.getSerialNo());
		}
		if(lineDetail.getLcnNo() != null) {
			form.setLcnNo(String.valueOf(lineDetail.getLcnNo()));
		}
		
		if(lineDetail.getLineDetailID() > 0) {
			form.setLineDetailID(String.valueOf(lineDetail.getLineDetailID()));
		}
		
		if(lineDetail.getCollateralID() > 0) {
			form.setCollateralId(String.valueOf(lineDetail.getCollateralID()));
		}
		
		form.setRefId(String.valueOf(lineDetail.getRefId()));
		
		return form;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{SESSION_FAC_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE},
			{Constants.GLOBAL_LOCALE_KEY, Locale.class.getName(), GLOBAL_SCOPE}
			};
	}

}

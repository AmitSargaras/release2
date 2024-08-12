package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.util.CommonUtil;

public class CheckListMapper extends AbstractCommonMapper {

	public CheckListMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "prev_event", "java.lang.String", REQUEST_SCOPE }});
	}

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		RecurrentDocReceiptForm aForm = (RecurrentDocReceiptForm) cForm;
		ICheckList temp = (ICheckList) map.get("checkList");
		if(aForm.getLawFirmPanelFlag().equals("Y")){
			temp.setLegalFirm(CommonUtil.isEmpty(aForm.getPanellawyerLegalFirm()) ? null : aForm.getPanellawyerLegalFirm());
		}else if(aForm.getLawFirmPanelFlag().equals("N")){
			temp.setLegalFirm(CommonUtil.isEmpty(aForm.getNonPanellawyerLegalFirm()) ? null : aForm.getNonPanellawyerLegalFirm());
		}
		temp.setLawFirmAddress(CommonUtil.isEmpty(aForm.getLawFirmAddress()) ? null : aForm.getLawFirmAddress());
		temp.setLawFirmContactNumber(CommonUtil.isEmpty(aForm.getLawFirmContactNumber()) ? null : aForm.getLawFirmContactNumber());
		temp.setLawFirmPanelFlag(CommonUtil.isEmpty(aForm.getLawFirmPanelFlag()) ? null : aForm.getLawFirmPanelFlag());
		temp.setLawyerEmail(CommonUtil.isEmpty(aForm.getLawyerEmail()) ? null : aForm.getLawyerEmail());
		temp.setLawyerInCharge(CommonUtil.isEmpty(aForm.getLawyerInCharge()) ? null : aForm.getLawyerInCharge());
		temp.setLawyerReferenceNumber(CommonUtil.isEmpty(aForm.getLawyerReferenceNumber()) ? null : aForm.getLawyerReferenceNumber());
		
		if(aForm.getValuerFirmPanelFlag().equals("Y")){
			temp.setValuerFirm(CommonUtil.isEmpty(aForm.getValuerFirm()) ? null : aForm.getValuerFirm());
		}else if(aForm.getValuerFirmPanelFlag().equals("N")){
			temp.setValuerFirm(CommonUtil.isEmpty(aForm.getNonPanelvaluerLegalFirm()) ? null : aForm.getNonPanelvaluerLegalFirm());
		}
		temp.setValuerFirmAddress(CommonUtil.isEmpty(aForm.getValuerFirmAddress()) ? null : aForm.getValuerFirmAddress());
		temp.setValuerFirmContactNumber(CommonUtil.isEmpty(aForm.getValuerFirmContactNumber()) ? null : aForm.getValuerFirmContactNumber());
		temp.setValuerFirmPanelFlag(CommonUtil.isEmpty(aForm.getValuerFirmPanelFlag()) ? null : aForm.getValuerFirmPanelFlag());
		temp.setValuerEmail(CommonUtil.isEmpty(aForm.getValuerEmail()) ? null : aForm.getValuerEmail());
		temp.setValuerInCharge(CommonUtil.isEmpty(aForm.getValuerInCharge()) ? null : aForm.getValuerInCharge());
		temp.setValuerReferenceNumber(CommonUtil.isEmpty(aForm.getValuerReferenceNumber()) ? null : aForm.getValuerReferenceNumber());
		
		if(aForm.getInsurerFirmPanelFlag().equals("Y")){
			temp.setInsurerFirm(CommonUtil.isEmpty(aForm.getInsurerFirm()) ? null : aForm.getInsurerFirm());
		}else if(aForm.getInsurerFirmPanelFlag().equals("N")){
			temp.setInsurerFirm(CommonUtil.isEmpty(aForm.getNonPanelinsurerLegalFirm()) ? null : aForm.getNonPanelinsurerLegalFirm());
		}
		
		temp.setInsurerFirmAddress(CommonUtil.isEmpty(aForm.getInsurerFirmAddress()) ? null : aForm.getInsurerFirmAddress());
		temp.setInsurerFirmContactNumber(CommonUtil.isEmpty(aForm.getInsurerFirmContactNumber()) ? null : aForm.getInsurerFirmContactNumber());
		temp.setInsurerFirmPanelFlag(CommonUtil.isEmpty(aForm.getInsurerFirmPanelFlag()) ? null : aForm.getInsurerFirmPanelFlag());
		temp.setInsurerEmail(CommonUtil.isEmpty(aForm.getInsurerEmail()) ? null : aForm.getInsurerEmail());
		temp.setInsurerInCharge(CommonUtil.isEmpty(aForm.getInsurerInCharge()) ? null : aForm.getInsurerInCharge());
		temp.setInsurerReferenceNumber(CommonUtil.isEmpty(aForm.getInsurerReferenceNumber()) ? null : aForm.getInsurerReferenceNumber());

		
		

		return temp;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "*************************** inside mapOb to form ");
		RecurrentDocReceiptForm aForm = (RecurrentDocReceiptForm) cForm;       

        try {
			if (obj != null) {
				ICheckList tempOb = (ICheckList) obj;
				if(tempOb.getLawFirmPanelFlag()!=null){
					aForm.setLawFirmPanelFlag(tempOb.getLawFirmPanelFlag());
					if(tempOb.getLawFirmPanelFlag().equals("Y")){
					aForm.setPanellawyerLegalFirm(tempOb.getLegalFirm());
					}else if(tempOb.getLawFirmPanelFlag().equals("N")){
						aForm.setNonPanellawyerLegalFirm(tempOb.getLegalFirm());
					}
				}else{
					aForm.setNonPanellawyerLegalFirm(tempOb.getLegalFirm());
				}
				
				aForm.setLawFirmAddress(tempOb.getLawFirmAddress());
				aForm.setLawFirmContactNumber(tempOb.getLawFirmContactNumber());
	//			aForm.setLawFirmPanelFlag(tempOb.getLawFirmPanelFlag());
				aForm.setLawyerEmail(tempOb.getLawyerEmail());
				aForm.setLawyerInCharge(tempOb.getLawyerInCharge());
				aForm.setLawyerReferenceNumber(tempOb.getLawyerReferenceNumber());
				
				if(tempOb.getValuerFirmPanelFlag()!=null){
					aForm.setValuerFirmPanelFlag(tempOb.getValuerFirmPanelFlag());
					if(tempOb.getValuerFirmPanelFlag().equals("Y")){
					aForm.setValuerFirm(tempOb.getValuerFirm());
					}else if(tempOb.getValuerFirmPanelFlag().equals("N")){
						aForm.setNonPanelvaluerLegalFirm(tempOb.getValuerFirm());
					}
				}else{
					aForm.setNonPanelvaluerLegalFirm(tempOb.getValuerFirm());
				}
				
				
				aForm.setValuerFirmAddress(tempOb.getInsurerFirmAddress());
				aForm.setValuerFirmContactNumber(tempOb.getInsurerFirmContactNumber());
				aForm.setValuerEmail(tempOb.getInsurerEmail());
				aForm.setValuerInCharge(tempOb.getInsurerInCharge());
				aForm.setValuerReferenceNumber(tempOb.getInsurerReferenceNumber());
				
				if(tempOb.getInsurerFirmPanelFlag()!=null){
					aForm.setInsurerFirmPanelFlag(tempOb.getInsurerFirmPanelFlag());
					if(tempOb.getInsurerFirmPanelFlag().equals("Y")){
					aForm.setInsurerFirm(tempOb.getInsurerFirm());
					}else if(tempOb.getInsurerFirmPanelFlag().equals("N")){
						aForm.setNonPanelinsurerLegalFirm(tempOb.getInsurerFirm());
					}
				}else{
					aForm.setNonPanelinsurerLegalFirm(tempOb.getInsurerFirm());
				}
				
				aForm.setInsurerFirmAddress(tempOb.getInsurerFirmAddress());
				aForm.setInsurerFirmContactNumber(tempOb.getInsurerFirmContactNumber());
				aForm.setInsurerEmail(tempOb.getInsurerEmail());
				aForm.setInsurerInCharge(tempOb.getInsurerInCharge());
				aForm.setInsurerReferenceNumber(tempOb.getInsurerReferenceNumber());
				
				
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}

package com.integrosys.cms.ui.collateral;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGC;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.collateral.MaintainInsuranceGCForm;;

public class InsuranceGCMapper extends AbstractCommonMapper {
	
	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");

	
	public Object mapFormToOB(CommonForm cForm, HashMap inputs)
			throws MapperException {
		MaintainInsuranceGCForm form = (MaintainInsuranceGCForm) cForm;
		
		IInsuranceGC obItem = null;
		try {
			
				obItem = new OBInsuranceGC();
				
				
				if(form.getId()!=null && (!form.getId().equals("")))
	            {
					obItem.setId(Long.parseLong(form.getId()));
	            }
				
				if(form.getInsuranceCode()!=null && (!form.getInsuranceCode().equals(""))){
					obItem.setInsuranceCode(form.getInsuranceCode());
				}
	           
							
				if(form.getCreationDate()!=null && (!form.getCreationDate().equals("")))
	            {
				obItem.setCreationDate(df.parse(form.getCreationDate()));
	            }else{
	            	obItem.setCreationDate(new Date());
	            }
				obItem.setVersionTime(0l);
				
				
				obItem.setIsProcessed(form.getIsProcessed());
				if(form.getParentId()!=null && (!form.getParentId().equals("")))
	            {
					obItem.setParentId(Long.parseLong(form.getParentId()));
	            }
				obItem.setDeprecated(form.getDeprecated());
				obItem.setInsurancePolicyNo(form.getInsurancePolicyNo());
				obItem.setCoverNoteNo(form.getCoverNoteNo());
				obItem.setRemark(form.getRemark());
				obItem.setInsuranceCompany(form.getInsuranceCompany());
				obItem.setInsuranceCoverge(form.getInsuranceCoverge());
				obItem.setInsuranceCurrency(form.getInsuranceCurrency());
				DateFormat df1 = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
				obItem.setLastApproveBy(form.getLastApproveBy());
				if(form.getLastApproveOnStr()!=null && (!form.getLastApproveOnStr().equals(""))){
				obItem.setLastApproveOn(df1.parse(form.getLastApproveOnStr()));
				}
				obItem.setLastUpdatedBy(form.getLastUpdatedBy());
				if(form.getLastUpdatedOnStr()!=null && (!form.getLastUpdatedOnStr().equals(""))){
				obItem.setLastUpdatedOn(df1.parse(form.getLastUpdatedOnStr()));
				}						
				
				if(form.getReceivedDate()!=null && (!form.getReceivedDate().equals("")))
	            {
				obItem.setReceivedDate(df.parse(form.getReceivedDate()));
	            }else{
	            	obItem.setReceivedDate(new Date());
	            }
				if(form.getEffectiveDate()!=null && (!form.getEffectiveDate().equals("")))
	            {
				obItem.setEffectiveDate(df.parse(form.getEffectiveDate()));
	            }
//				else{
//	            	obItem.setEffectiveDate(new Date());
//	            }
				if(form.getExpiryDate()!=null && (!form.getExpiryDate().equals("")))
	            {
				obItem.setExpiryDate(df.parse(form.getExpiryDate()));
	            }
//				else{
//	            	obItem.setExpiryDate(new Date());
//	            }
				obItem.setInsuranceRequired(form.getInsuranceRequired());
				
				//Phase 3 CR:comma separated
				obItem.setInsurancePolicyAmt(UIUtil.removeComma(form.getInsurancePolicyAmt()));
				obItem.setInsuredAmount(UIUtil.removeComma(form.getInsuredAmount()));
				obItem.setInsuranceDefaulted(form.getInsuranceDefaulted());
				obItem.setInsurancePremium(UIUtil.removeComma(form.getInsurancePremium()));
				
				
				//Uma Khot::Insurance Deferral maintainance
				if(null!=form.getInsuredAddress()){
				  obItem.setInsuredAddress(form.getInsuredAddress());
				}
				if(null!=form.getRemark2()){
				  obItem.setRemark2(form.getRemark2());
				}
				if(null!=form.getInsuredAgainst()){
					  obItem.setInsuredAgainst(form.getInsuredAgainst());
					}
				
			/*	if("maker_submit_insurance_pending".equals(form.getEvent())){
					obItem.setInsuranceStatus("PENDING_AWAITING");	
				}else if("maker_submit_insurance_deferred".equals(form.getEvent())){
					obItem.setInsuranceStatus("PENDING_DEFER");	
				}else if("maker_submit_insurance_received".equals(form.getEvent()) || "maker_edit_insreceived_list".equals(form.getEvent())){
					obItem.setInsuranceStatus("PENDING_RECEIVED");	
				}else if("maker_submit_insurance_waived".equals(form.getEvent())){
					obItem.setInsuranceStatus("PENDING_WAIVER");	
				} 
				else */
				
				if(null!=form.getInsuranceStatus()){
					obItem.setInsuranceStatus(form.getInsuranceStatus());
				}
				
				if(form.getOriginalTargetDate()!=null && (!form.getOriginalTargetDate().equals("")))
	            {
				obItem.setOriginalTargetDate(df.parse(form.getOriginalTargetDate()));
	            }
				if(form.getNextDueDate()!=null && (!form.getNextDueDate().equals("")))
	            {
				obItem.setNextDueDate(df.parse(form.getNextDueDate()));
	            }
				if(form.getDateDeferred()!=null && (!form.getDateDeferred().equals("")))
	            {
				obItem.setDateDeferred(df.parse(form.getDateDeferred()));
	            }
				if(form.getCreditApprover()!=null && (!form.getCreditApprover().equals("")))
	            {
				obItem.setCreditApprover(form.getCreditApprover());
	            }
				if(form.getWaivedDate()!=null && (!form.getWaivedDate().equals("")))
	            {
				obItem.setWaivedDate(df.parse(form.getWaivedDate()));
	            }
				
				if("Component_wise".equals(form.getInsuranceRequired())){
					
				if(form.getAppendedComponent()!=null && (!form.getAppendedComponent().equals("")))
	            {
					obItem.setSelectComponent(form.getAppendedComponent());
	            }
				}
				
				if("All".equals(form.getInsuranceRequired())){
					if(form.getAllComponent()!=null && (!form.getAllComponent().equals("")))
		            {
						obItem.setSelectComponent(form.getAllComponent());
		            }
					
				}
				//obItem.setAppendedComponent(form.getAppendedComponent());
				
				if(StringUtils.isNotBlank(form.getOldPolicyNo())) {
					obItem.setOldPolicyNo(form.getOldPolicyNo());
				}
				
			return obItem;

		}
		catch (Exception ex) {
			throw new MapperException("failed to map form to ob of InsuranceGC item", ex);
		}
	}

	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs)
			throws MapperException {
		MaintainInsuranceGCForm form = (MaintainInsuranceGCForm) cForm;
		IInsuranceGC item = (IInsuranceGC) obj;

		form.setVersionTime(Long.toString(item.getVersionTime()));		
		form.setId(Long.toString(item.getId()));		
		form.setIsProcessed(item.getIsProcessed());
		form.setParentId(Long.toString(item.getParentId()));
		form.setDeprecated(item.getDeprecated());
		form.setInsuranceCode(item.getInsuranceCode());		
		form.setInsuranceCompany(item.getInsuranceCompany());
		form.setInsuranceCoverge(item.getInsuranceCoverge());
		form.setInsuranceCurrency(item.getInsuranceCurrency());
		form.setInsuranceDefaulted(item.getInsuranceDefaulted());
		form.setInsurancePolicyAmt(item.getInsurancePolicyAmt());
		form.setInsurancePolicyNo(item.getInsurancePolicyNo());
		
		form.setInsuranceRequired(item.getInsuranceRequired());
		form.setInsuredAmount(item.getInsuredAmount());
				
		form.setLastApproveBy(item.getLastApproveBy());
		if(item.getLastApproveOn()!=null){
		form.setLastApproveOnStr(item.getLastApproveOn().toString());
		}
		form.setLastUpdatedBy(item.getLastUpdatedBy());
		if(item.getLastUpdatedOn()!=null){
		form.setLastUpdatedOnStr(item.getLastUpdatedOn().toString());
		}
		
		if(item.getInsurancePremium()!=null && !item.getInsurancePremium().equals("")){
		form.setInsurancePremium(item.getInsurancePremium());
		}else
			form.setInsurancePremium("");
		
		if(item.getCoverNoteNo()!=null && !item.getCoverNoteNo().equals("")){
			form.setCoverNoteNo(item.getCoverNoteNo());
		}else
			form.setCoverNoteNo("");
		
		if(item.getRemark()!=null && !item.getRemark().equals("")){
			form.setRemark(item.getRemark());
		}else
			form.setRemark("");
		
					
		
		if(item.getSelectComponent()!=null){
				form.setSelectComponent(item.getSelectComponent());
				}else
					form.setSelectComponent("");
		
		if(item.getCreationDate()!=null && !item.getCreationDate().equals("")){
			String date=df.format(item.getCreationDate());
			form.setCreationDate(date);
			}
				
		if(item.getReceivedDate()!=null && !item.getReceivedDate().equals("")){
			String date=df.format(item.getReceivedDate());
			form.setReceivedDate(date);
			}
		
		if(item.getEffectiveDate()!=null  && !item.getEffectiveDate().equals("")){
			String date=df.format(item.getEffectiveDate());
			form.setEffectiveDate(date);
			}
		
		if(item.getExpiryDate()!=null && !item.getExpiryDate().equals("")){
			String date=df.format(item.getExpiryDate());
			form.setExpiryDate(date);
			}
		
		//Uma Khot::Insurance Deferral maintainance
		
//		if(null == item.getReceivedDate() || item.getReceivedDate().equals("")){
//			String date=df.format(new Date());
//			form.setReceivedDate(date);
//			}
//		
		if(null!=item.getInsuredAddress()){
			form.setInsuredAddress(item.getInsuredAddress());
			}
			if(null!=item.getRemark2()){
				form.setRemark2(item.getRemark2());
			}
			if(null!=item.getInsuredAgainst()){
				form.setInsuredAgainst(item.getInsuredAgainst());
				}
			if(null!=item.getInsuranceStatus()){
				form.setInsuranceStatus(item.getInsuranceStatus());
			}
			if(item.getOriginalTargetDate()!=null  && !item.getOriginalTargetDate().equals("")){
				String date=df.format(item.getOriginalTargetDate());
				form.setOriginalTargetDate(date);
				}
			if(item.getNextDueDate()!=null  && !item.getNextDueDate().equals("")){
				String date=df.format(item.getNextDueDate());
				form.setNextDueDate(date);
				}
			if(item.getDateDeferred()!=null  && !item.getDateDeferred().equals("")){
				String date=df.format(item.getDateDeferred());
				form.setDateDeferred(date);
				}
			if(item.getWaivedDate()!=null  && !item.getWaivedDate().equals("")){
				String date=df.format(item.getWaivedDate());
				form.setWaivedDate(date);
				}
			if(item.getCreditApprover()!=null && (!item.getCreditApprover().equals("")))
            {
				form.setCreditApprover(item.getCreditApprover());
            }
			
			form.setOldPolicyNo(item.getOldPolicyNo());
			
		return form;
	}
	
	public String getInsCodeSeq() {
		IInsuranceGCDao  insuranceGCDao = (IInsuranceGCDao) BeanHouse.get("insuranceGcDao");
		String insCode=insuranceGCDao.getInsCode();
		
		return insCode;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "insuranceGCObj", "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", SERVICE_SCOPE }, });
	}

}

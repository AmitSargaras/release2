package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_TOTAL_INSURANCE_POLICY_AMT;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class EditListInsuranceCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				 {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				 { "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "insuranceCode",  "java.lang.String", REQUEST_SCOPE },
				{ "id",  "java.lang.String", REQUEST_SCOPE },
				{ "edit",  "java.lang.String", REQUEST_SCOPE },
				{ "insuranceGCObj", "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", FORM_SCOPE },
				{ "componentList", "java.util.List", SERVICE_SCOPE },
				//{"actualObj", " com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", SERVICE_SCOPE},
				{"actualObj", " com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", REQUEST_SCOPE},
				});
	}
	
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "serviceColObj", ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
				{ SESSION_TOTAL_INSURANCE_POLICY_AMT, String.class.getName() , SERVICE_SCOPE },
				
				});
	}
	
	 public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			List insuranceList=(List)map.get("insuranceList");
			OBInsuranceGC insurance = (OBInsuranceGC) map.get("insuranceGCObj");
			OBInsuranceGC actualObj = (OBInsuranceGC) map.get("actualObj");
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			String event=(String)map.get("event");
			String insuranceCode=(String)map.get("insuranceCode");
			String id=(String)map.get("id");
			List componentList=(List)map.get("componentList");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			try {
				
				IInsuranceGCJdbc  insuranceGcJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
				List insList = insuranceGcJdbc.getAllInsuranceForSec(itrxValue.getReferenceID());
				HashMap exceptionMap = new HashMap();
				if(insList!=null && insList.size()!=0)
				{	
				for(int i = 0;i<insList.size();i++)
				{OBInsuranceGC insActual = (OBInsuranceGC)insList.get(i);
				if(actualObj !=null){
				  if(!"0".equals(id) && !String.valueOf(actualObj.getId()).equals(String.valueOf(insActual.getId()))){
					  
					  if(null!=insActual.getInsurancePolicyNo() && null!=insActual.getInsuranceCompany()){
					if(insActual.getInsurancePolicyNo().equals(insurance.getInsurancePolicyNo()) && insActual.getInsuranceCompany().equals(insurance.getInsuranceCompany()))
					{	
						exceptionMap.put("duplicateInsuranceError", new ActionMessage("error.string.insurance.id"));
						resultMap.put("request.ITrxValue", itrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
				}
				  }
				}
				}
				}
				
				for (int i=0;i<insuranceList.size();i++){
						IInsuranceGC insObj=(IInsuranceGC)insuranceList.get(i);
						if(insObj.getInsuranceCode().equals(insurance.getInsuranceCode())){
							
							insuranceList.remove(insObj);
							IInsuranceGC newObj=editObject(insurance);
//							if(newObj.getInsuranceRequired().equals("All")){
							
							//Uma Khot::Insurance Deferral maintainance	
							if("All".equals(newObj.getInsuranceRequired())){
								StringBuffer add=new StringBuffer();
								for(int j=0;j<componentList.size();j++){
									LabelValueBean newBean= new LabelValueBean();
									newBean=(LabelValueBean)componentList.get(j);
									String component=newBean.getValue();
									add.append(component).append("-");
								}
								newObj.setSelectComponent(add.toString());
							}
							insuranceList.add(newObj);	
							
						}
					}
				  if("0".equals(id)){
					  int count = 0;
				for(int i = 0;i<insuranceList.size();i++)
				{
					OBInsuranceGC insActual = (OBInsuranceGC)insuranceList.get(i);
					
					if(null!=insActual.getInsurancePolicyNo() && null!=insActual.getInsuranceCompany()){
					if(insActual.getInsurancePolicyNo().equals(insurance.getInsurancePolicyNo()) && insActual.getInsuranceCompany().equals(insurance.getInsuranceCompany()))
							{
						 count++;
						     }
					}
				}
					if(count>1) 
					{
						exceptionMap.put("duplicateInsuranceError", new ActionMessage("error.string.insurance.id"));
						resultMap.put("request.ITrxValue", itrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
				
				
					resultMap.put("insuranceList", insuranceList);
					resultMap.put("event", event);
					
					ICollateral stagingCollateral = itrxValue.getStagingCollateral();
					CollateralHelper.updateSecurityCoverageDetails(stagingCollateral,insuranceList);
					itrxValue.setStagingCollateral(stagingCollateral);
					resultMap.put("serviceColObj", itrxValue);
					
					resultMap.put("calculatedDP", map.get("calculatedDP"));
					resultMap.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(stagingCollateral,insuranceList));
				/*	if(insurance.getInsuranceCode().equals(insuranceCode)){
				
					
						String code=insurance.getInsuranceCode();
						String type=insurance.getInsuranceType();	
						String deprecated=insurance.getDeprecated();
						long id=insurance.getId();
						String process=insurance.getIsProcessed();
						long parent=insurance.getParentId();
						long version=insurance.getVersionTime();
						Date creation=insurance.getCreationDate();
						
						insuranceList.remove(insurance);
						
						IInsuranceGC newIns=new OBInsuranceGC();
						
						newIns.setDeprecated(deprecated);
						newIns.setCreationDate(creation);
						newIns.setId(id);
						newIns.setInsuranceCode(code);
						newIns.setInsuranceType(type);
						newIns.setIsProcessed(process);
						newIns.setParentId(parent);
						newIns.setVersionTime(version);
						
						
						insuranceList.add(newIns);				
						resultMap.put("insuranceList", insuranceList);
						resultMap.put("event", event);
						resultMap.put("calculatedDP", map.get("calculatedDP"));
					}
					else{
						resultMap.put("insuranceList", insuranceList);
						resultMap.put("event", event);
						resultMap.put("calculatedDP", map.get("calculatedDP"));
					
					}
				}*/
				
				
				
				
				
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	    }
	 
	 public OBInsuranceGC editObject(OBInsuranceGC insurance){
		 OBInsuranceGC replaceIns = new OBInsuranceGC();
		 replaceIns.setCreationDate(insurance.getCreationDate());
			replaceIns.setInsuranceCode(insurance.getInsuranceCode());				
			replaceIns.setParentId(insurance.getParentId());
			replaceIns.setDeprecated(insurance.getDeprecated());
			
			replaceIns.setId(insurance.getId());
			replaceIns.setVersionTime(insurance.getVersionTime());
			replaceIns.setCoverNoteNo(insurance.getCoverNoteNo());
			replaceIns.setEffectiveDate(insurance.getEffectiveDate());
			replaceIns.setExpiryDate(insurance.getExpiryDate());
			replaceIns.setReceivedDate(insurance.getReceivedDate());
			replaceIns.setInsuranceCompany(insurance.getInsuranceCompany());
			replaceIns.setInsuranceCoverge(insurance.getInsuranceCoverge());
			replaceIns.setInsuranceCurrency(insurance.getInsuranceCurrency());
			replaceIns.setInsuranceDefaulted(insurance.getInsuranceDefaulted());
			replaceIns.setInsurancePolicyAmt(insurance.getInsurancePolicyAmt());
			replaceIns.setInsurancePolicyNo(insurance.getInsurancePolicyNo());
			replaceIns.setInsurancePremium(insurance.getInsurancePremium());
			replaceIns.setInsuranceRequired(insurance.getInsuranceRequired());
			replaceIns.setInsuredAmount(insurance.getInsuredAmount());
			if(insurance.getInsuranceRequired().equals("Component_wise")){
				if(insurance.getSelectComponent()!=null){
					replaceIns.setSelectComponent(insurance.getSelectComponent());
				}
			}
			/*if(insurance.getAllComponent()!=null){
				replaceIns.setAllComponent(insurance.getAllComponent());
			}*/
			
			if(insurance.getRemark()!=null){
				replaceIns.setRemark(insurance.getRemark());
			}
		/*	else
				replaceIns.setRemark("-");
			*/
			if(insurance.getIsProcessed().equals("Y")||insurance.getIsProcessed().equals("YR")){
				replaceIns.setIsProcessed("NM");
			}
			else 
				replaceIns.setIsProcessed(insurance.getIsProcessed());
			
			
			//Uma Khot::Insurance Deferral maintainance
			replaceIns.setInsuranceStatus(insurance.getInsuranceStatus());
			replaceIns.setInsuredAddress(insurance.getInsuredAddress());
			replaceIns.setRemark2(insurance.getRemark2());
			replaceIns.setInsuredAgainst(insurance.getInsuredAgainst());
			replaceIns.setOriginalTargetDate(insurance.getOriginalTargetDate());
			replaceIns.setNextDueDate(insurance.getNextDueDate());
			replaceIns.setDateDeferred(insurance.getDateDeferred());
			replaceIns.setWaivedDate(insurance.getWaivedDate());
			replaceIns.setCreditApprover(insurance.getCreditApprover());
			replaceIns.setOldPolicyNo(insurance.getOldPolicyNo());
			
			return replaceIns;
	 }
	 
}



package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;

import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class MakerViewInsuranceCommand extends AbstractCommand{

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				//{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{"insuranceCode","java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
//				{ "fundedShare", "java.lang.String", REQUEST_SCOPE },
				
				{ "dpShare", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				//{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
				{ "componentList", "java.util.List", SERVICE_SCOPE },
				});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", REQUEST_SCOPE },
				
				{ "dpShare", "java.lang.String", REQUEST_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"insuranceGCObj","com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", FORM_SCOPE },
				{"insuranceObj","com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", REQUEST_SCOPE },
				
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		List insuranceList=(List)map.get("insuranceList");
		String insuranceCode=(String)map.get("insuranceCode");
		List componentList=(List)map.get("componentList");
		String event=(String)map.get("event");
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		try{
			if(insuranceList!=null){
			Iterator it=insuranceList.iterator();
			while(it.hasNext()){
				OBInsuranceGC insurance = (OBInsuranceGC) it.next();
				if(insuranceCode.equals(insurance.getInsuranceCode())){
				//	if(insurance.getInsuranceRequired().equals("All")){
				
					//Uma Khot::Insurance Deferral maintainance
					if("All".equals(insurance.getInsuranceRequired())){
						StringBuffer add=new StringBuffer();
						for(int i=0;i<componentList.size();i++){
							LabelValueBean newBean= new LabelValueBean();
							newBean=(LabelValueBean)componentList.get(i);
							String component=newBean.getValue();
							add.append(component).append("-");
						}
						insurance.setSelectComponent(add.toString());
					}
					/*OBInsuranceGC newIns = new OBInsuranceGC();
					newIns.setCreationDate(s.getCreationDate());
					newIns.setId(s.getId());
					newIns.setIsProcessed(s.getIsProcessed());
					newIns.setParentId(s.getParentId());
					newIns.setVersionTime(s.getVersionTime());
					newIns.setInsuranceCode(s.getInsuranceCode());
					newIns.setDeprecated(s.getDeprecated());
					newIns.setCoverNoteNo(s.getCoverNoteNo());
					newIns.setEffectiveDate(s.getEffectiveDate());
					newIns.setReceivedDate(s.getReceivedDate());
					newIns.setExpiryDate(s.getExpiryDate());
					newIns.setInsuranceCompany(s.getInsuranceCompany());
					newIns.setInsuranceCoverge(s.getInsuranceCoverge());
					newIns.setInsuranceCurrency(s.getInsuranceCurrency());
					newIns.setInsuranceDefaulted(s.getInsuranceDefaulted());
					newIns.setInsurancePolicyAmt(s.getInsurancePolicyAmt());
					newIns.setInsurancePolicyNo(s.getInsurancePolicyNo());
					newIns.setInsurancePremium(s.getInsurancePremium());
					newIns.setInsuranceRequired(s.getInsuranceRequired());
					newIns.setInsuredAmount(s.getInsuredAmount());
					if(s.getSelectComponent()!=null){
						newIns.setSelectComponent(s.getSelectComponent());
					}
					if(s.getAllComponent()!=null){
						newIns.setAllComponent(s.getAllComponent());
					}
					if(s.getRemark()!=null){
						newIns.setRemark(s.getRemark());
					}*/
					result.put("insuranceGCObj", insurance);
					result.put("insuranceObj", insurance);
				}
			}
			}
			result.put("event", event);
			result.put("theOBTrxContext", map.get("theOBTrxContext"));
			result.put("trxID", map.get("trxID"));
			result.put("calculatedDP", map.get("calculatedDP"));
//			result.put("fundedShare", map.get("fundedShare"));
			
			result.put("dpShare", map.get("dpShare"));
			result.put("dueDate", map.get("dueDate"));
			
			result.put("deferCreditApproverList", getAllDeferCreditApprover());
			result.put("waiverCreditApproverList", getAllWaiveCreditApprover());
				
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		}
		catch(Exception ex){
			
		}
		return temp;
	}
	
	private List getAllDeferCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			List defer = (List)proxy.getAllDeferCreditApprover();
			
			for (int i = 0; i < defer.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)defer.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getAllWaiveCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			List waive = (List)proxy.getAllWaiveCreditApprover();
			
			for (int i = 0; i < waive.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)waive.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
}

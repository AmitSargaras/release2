package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.businfra.LabelValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import static com.integrosys.cms.ui.collateral.CollateralConstant.OLD_POLICY_NO_LIST;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.util.LabelValueBean;

public class MakerPreparCreateInsuranceCmd extends AbstractCommand {

	  public String[][] getParameterDescriptor() {
	        return (new String[][]{
	                {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE},
	                {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
	                {"flag1", "java.lang.String", SERVICE_SCOPE},
	                {"securityId", "java.lang.String", SERVICE_SCOPE},
	                { "calculatedDP", "java.lang.String", REQUEST_SCOPE },
//	                { "fundedShare", "java.lang.String", REQUEST_SCOPE },
	                {"dpCalculateManually","java.lang.String", REQUEST_SCOPE},
	                { "dpShare", "java.lang.String", REQUEST_SCOPE },
	                { "dueDate", "java.lang.String", REQUEST_SCOPE },
	                { "loanable", "java.lang.String", REQUEST_SCOPE },
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"isSSC", "java.lang.String", REQUEST_SCOPE},
	                
	                { "insuranceStatusRadio", "java.lang.String", REQUEST_SCOPE },

	              /*  { "stockdocMonth", "java.lang.String", REQUEST_SCOPE },
					{ "stockdocYear", "java.lang.String", REQUEST_SCOPE },
					{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
					*/
					{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
					
					{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
					
	                });
	    }

	    /**
	     * Defines an two dimensional array with the result list to be expected as a
	     * result from the doExecute method using a HashMap syntax for the array is
	     * (HashMapkey,classname,scope) The scope may be request,form or service
	     *
	     * @return the two dimensional String array
	     */
	    public String[][] getResultDescriptor() {
	        return (new String[][]{{"request.ITrxValue", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue",
	                REQUEST_SCOPE},
	                {"collateralID", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
	                { "calculatedDP", "java.lang.String", REQUEST_SCOPE },
//	                { "fundedShare", "java.lang.String", REQUEST_SCOPE },
	                {"dpCalculateManually","java.lang.String", REQUEST_SCOPE},
	                {"dpCalculateManually","java.lang.String", SERVICE_SCOPE},
	                { "dpShare", "java.lang.String", REQUEST_SCOPE },
	            	{ "dueDate", "java.lang.String", SERVICE_SCOPE },
	                { "dueDate", "java.lang.String", REQUEST_SCOPE },
	                { "loanable", "java.lang.String", REQUEST_SCOPE },
	                { "insuranceStatusRadio", "java.lang.String", REQUEST_SCOPE },
	                { "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
					{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
					
					{"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
				
					/* { "stockdocMonth", "java.lang.String", REQUEST_SCOPE },
					{ "stockdocYear", "java.lang.String", REQUEST_SCOPE },
					{ "stockdocMonth", "java.lang.String", SERVICE_SCOPE },
					{ "stockdocYear", "java.lang.String", SERVICE_SCOPE },
					{ "remarkByMaker", "java.lang.String", REQUEST_SCOPE },
					{ "remarkByMaker", "java.lang.String", SERVICE_SCOPE },
					*/
					{ "totalLonable", "java.lang.String", REQUEST_SCOPE },
					{ "totalLonable", "java.lang.String", SERVICE_SCOPE },
					
					{ "migrationFlag", "java.lang.String", REQUEST_SCOPE },
					{ "migrationFlag", "java.lang.String", SERVICE_SCOPE },
					
				
					{ OLD_POLICY_NO_LIST, List.class.getName(), REQUEST_SCOPE},
	               });
	    }
	
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

	        HashMap result = new HashMap();
	        HashMap temp = new HashMap();
	        String securityId= (String) map.get("securityId");
	        try{
	        	
	        
	        result.put("collateralID", securityId);
	        result.put("calculatedDP", map.get("calculatedDP"));
//	        result.put("fundedShare", map.get("fundedShare"));
	        result.put("dpCalculateManually", map.get("dpCalculateManually"));
	        result.put("dpShare", map.get("dpShare"));
	        result.put("dueDate", map.get("dueDate"));
	    /*    result.put("stockdocMonth", map.get("stockdocMonth"));
	        result.put("stockdocYear", map.get("stockdocYear"));
	        String month = (String)map.get("stockdocMonth");
	        System.out.println("After for insurance date added Doc Month=>"+month);
	        result.put("remarkByMaker", map.get("remarkByMaker"));
	       */
	        result.put("totalLonable", map.get("totalLonable"));
	        
	        result.put("migrationFlag", map.get("migrationFlag"));

	        //Uma Khot::Insurance Deferral maintainance
	        
	        result.put("deferCreditApproverList", getAllDeferCreditApprover());
			result.put("waiverCreditApproverList", getAllWaiveCreditApprover());
	        
	        result.put("insuranceStatusRadio", map.get("insuranceStatusRadio"));
	        //BigDecimal loanable=null;
	       //loanable.add((BigDecimal)map.get("loanable"));
	       //loanable.add(new BigDecimal());
	        result.put("loanable",map.get("loanable"));
	        result.put("serviceColObj", map.get("serviceColObj"));
	        
	        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
	        }
	        catch(Exception e){
	        	System.out.println("///////////////  EXCEPTION ");
	        }
	        
	        ICollateralTrxValue collateralTrx = (ICollateralTrxValue)map.get(SERVICE_COLLATERAL_OBJ);
	        Long collateralId = 0l;
	        if(collateralTrx != null && StringUtils.isNotBlank(collateralTrx.getReferenceID())) {
	        	collateralId = Long.valueOf(collateralTrx.getReferenceID());
	        }
	        IInsuranceGCJdbc insuranceGcJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
	        List<LabelValue> oldPolicyNoList = insuranceGcJdbc.getInsurancePolicyNo(collateralId);
	        result.put(OLD_POLICY_NO_LIST, oldPolicyNoList);
	        
	        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return  temp;
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

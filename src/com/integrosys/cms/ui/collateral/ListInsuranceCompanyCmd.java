package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.OBCaseBranch;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageDAO;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class ListInsuranceCompanyCmd extends AbstractCommand {
	
	public ListInsuranceCompanyCmd(){
		
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				{"event", "java.lang.String", REQUEST_SCOPE},
				
			};
	}
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				
				{ "insuranceCoverageList", "java.util.List", SERVICE_SCOPE },
				
			});
	}
	
public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		List systemBankList= null;
		ISystemBank systemBank= null;
		String event = (String) map.get("event");
		
		
		resultMap.put("insuranceCoverageList", getInsuranceCompany());
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

private List getInsuranceCompany(){
	List lbValList = new ArrayList();
	HashMap sysBranch=new HashMap();
	try{
		IInsuranceCoverageDAO insuranceCoverage=(IInsuranceCoverageDAO)BeanHouse.get("insuranceCoverageDAO");
		List insuranceCoverageList=insuranceCoverage.getInsuranceCoverageList();
		//List insuranceCoverageList=(List)insuranceCoverageResult.getResultList();
		for(int i=0;i<insuranceCoverageList.size();i++){
			OBInsuranceCoverage insObj=(OBInsuranceCoverage)insuranceCoverageList.get(i);
			
			String id=insObj.getInsuranceCoverageCode();
			String value=(String) insObj.getCompanyName();
			LabelValueBean lvBean = new LabelValueBean(value, id);
			lbValList.add(lvBean);
			
			
		}
		
	}
	catch(Exception ex){
		
	}
	return (List) CommonUtil.sortDropdown(lbValList);
	
}
	
	
}

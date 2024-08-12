package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageDAO;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class ListComponentCmd extends AbstractCommand {

	public ListComponentCmd(){
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				{"event", "java.lang.String", REQUEST_SCOPE},
				
			};
	}
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				
				{ "componentList", "java.util.List", SERVICE_SCOPE },
				
			});
	}
	
public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		List systemBankList= null;
		ISystemBank systemBank= null;
		String event = (String) map.get("event");
		
		
		resultMap.put("componentList", getcomponentList());
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

private List getcomponentList(){
	List lbValList = new ArrayList();
	HashMap sysBranch=new HashMap();
	try{
		IComponentDao component=(IComponentDao)BeanHouse.get("componentDao");
		List componentList=(List)component.getActualComponentList();
		//List insuranceCoverageList=(List)insuranceCoverageResult.getResultList();
		for(int i=0;i<componentList.size();i++){
			OBComponent insObj=(OBComponent)componentList.get(i);
			
			String id=insObj.getComponentCode();
			String value=(String) insObj.getComponentName();
			LabelValueBean lvBean = new LabelValueBean(value, id);
			lbValList.add(lvBean);
			
			
		}
		
	}
	catch(Exception ex){
		
	}
	return (List) CommonUtil.sortDropdown(lbValList);
	
}
	
	
}

package com.integrosys.cms.ui.component;

import java.util.*;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntries;
import com.integrosys.cms.app.component.bus.ComponentDaoImpl;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

public class MakerPrepareCreateComponentCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IComponentProxyManager componentProxy;
	
	
	public IComponentProxyManager getComponentProxy() {
		return componentProxy;
	}


	public void setComponentProxy(IComponentProxyManager componentProxy) {
		this.componentProxy = componentProxy;
	}
	
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  {"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
		            {"session.startIndex", "java.lang.String", SERVICE_SCOPE}
				  
					 
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"IComponentTrxValue", "com.integrosys.cms.app.component.trx.OBComponentTrxValue", SERVICE_SCOPE},
				{"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
	            { "session.ageList", "java.util.List", SERVICE_SCOPE }
				 });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		
		
	
		
		OBComponentTrxValue componentTrxValue = new OBComponentTrxValue();
		resultMap.put("session.startIndex", map.get("session.startIndex"));
		resultMap.put("session.searchcomponentName",map.get("session.searchcomponentName"));
		resultMap.put("session.ageList",getAgeList());
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
	public List getAgeList(){
		List age= new ArrayList();
		
		try {
			IComponentDao component=(IComponentDao)BeanHouse.get("componentDao");
			ArrayList ageList=(ArrayList)component.getAgeDetailList();
			String[] stringArray = new String[2];
			for(int i=0;i<ageList.size();i++){
				Object[] ob=(Object[])ageList.get(i);
				String id=String.valueOf(ob[0].toString());
				String val=String.valueOf(ob[1].toString());
				LabelValueBean lvBean = new LabelValueBean(val, id);
				age.add(lvBean);
			}
		}
			catch (Exception ex) {
				ex.printStackTrace();
	}
	return age;
	}
}

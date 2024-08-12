package com.integrosys.cms.ui.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.bus.OBComponent;

public class MakerReadComponentCmd extends AbstractCommand implements ICommonEventConstant  {
	
private IComponentProxyManager componentProxy;

	
	
	
	public IComponentProxyManager getComponentProxy() {
		return componentProxy;
	}

	public void setComponentProxy(
			IComponentProxyManager componentProxy) {
		this.componentProxy = componentProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadComponentCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				 {"componentCode", "java.lang.String", REQUEST_SCOPE},
				 {"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
		            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
		            {"startIndex", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
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
		return (new String[][] { 
				{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", SERVICE_SCOPE },
				{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"session.searchcomponentName", "java.lang.String", SERVICE_SCOPE},
	            {"session.startIndex", "java.lang.String", SERVICE_SCOPE},
				{ "hubValueList", "java.util.List", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{"IComponentTrxValue", "com.integrosys.cms.app.component.trx.IComponentTrxValue", SERVICE_SCOPE},
				//Start:-------->Abhishek Naik
				{ "session.ageList", "java.util.List", SERVICE_SCOPE },
				// End:-------->Abhishek Naik 
				//start SANTOSH
	            { "session.componentCategoryList", "java.util.List", SERVICE_SCOPE }
				//end SANTOSH
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IComponent component;
			IComponentTrxValue trxValue=null;
			String startIdx ="";
			String event = (String) map.get("event");
			String componentCode=(String) (map.get("componentCode"));
			startIdx = (String) map.get("startIndex");
			if(startIdx==null){
				startIdx = (String) map.get("session.startIndex");
			}
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			
			trxValue = (OBComponentTrxValue) getComponentProxy().getComponentTrxValue(Long.parseLong(componentCode));
			component = (OBComponent) trxValue.getComponent();
			
			
			
			if(!(trxValue.getStatus().equals("ACTIVE")))
			{
				resultMap.put("wip", "wip");
			}
			
			resultMap.put("event", event);
			resultMap.put("session.startIndex",startIdx);	
			resultMap.put("session.searchcomponentName",map.get("session.searchcomponentName"));
			resultMap.put("IComponentTrxValue", trxValue);
			resultMap.put("componentObj", component);
			//Start:-------->Abhishek Naik
			resultMap.put("session.ageList",getAgeList());
			// End:-------->Abhishek Naik 
			//santosh
			resultMap.put("session.componentCategoryList",getComponentCategoryList());
			//end santosh
			
		}catch (ComponentException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
		

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	//Start:-------->Abhishek Naik
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
	
	// End:-------->Abhishek Naik 
	//START Santosh
		public List getComponentCategoryList(){
			List componentCategory= new ArrayList();
			
			try {
				IComponentDao component=(IComponentDao)BeanHouse.get("componentDao");
				ArrayList ageList=(ArrayList)component.getComponentCategoryDetailList();
				String[] stringArray = new String[2];
				for(int i=0;i<ageList.size();i++){
					Object[] ob=(Object[])ageList.get(i);
					String id=String.valueOf(ob[0].toString());
					String val=String.valueOf(ob[1].toString());
					LabelValueBean lvBean = new LabelValueBean(val, id);
					componentCategory.add(lvBean);
				}
			}
				catch (Exception ex) {
					ex.printStackTrace();
		}
		return componentCategory;
		}
	//END SANTOSH
}

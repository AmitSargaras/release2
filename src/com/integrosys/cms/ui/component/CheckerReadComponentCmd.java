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
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;

public class CheckerReadComponentCmd extends AbstractCommand implements	ICommonEventConstant {
	
	private IComponentProxyManager componentProxy;

	public IComponentProxyManager getComponentProxy() {
		return componentProxy;
	}

	public void setComponentProxy(IComponentProxyManager componentProxy) {
		this.componentProxy = componentProxy;
	}
	
	
	
	/**
	 * Default Constructor
	 */
	public CheckerReadComponentCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				
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
				{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", FORM_SCOPE },
				{"IComponentTrxValue", "com.integrosys.cms.app.component.trx.IComponentTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,ComponentException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IComponent component;
			IComponentTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get Component Trx value
			trxValue = (OBComponentTrxValue) getComponentProxy().getComponentByTrxID(branchCode);
			
			component = (OBComponent) trxValue.getStagingComponent();
			
			resultMap.put("IComponentTrxValue", trxValue);
			resultMap.put("componentObj", component);
			resultMap.put("event", event);
			//Start:-------->Abhishek Naik
			resultMap.put("session.ageList",getAgeList());
			// End:-------->Abhishek Naik 
			//santosh
			resultMap.put("session.componentCategoryList",getComponentCategoryList());
			//end santosh
			
		} catch (ComponentException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
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

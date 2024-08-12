package com.integrosys.cms.ui.component;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class MakerCreateComponentCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IComponentProxyManager componentProxy;

	public IComponentProxyManager getComponentProxy() {
		return componentProxy;
	}

	public void setComponentProxy(IComponentProxyManager componentProxy) {
		this.componentProxy = componentProxy;
	}
	
	public MakerCreateComponentCmd(){
	}

	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"IComponentTrxValue", "com.integrosys.cms.app.component.trx.IComponentTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
					{ "componentObj", "com.integrosys.cms.app.component.bus.OBComponent", FORM_SCOPE }
					   });
		}
	 
	 public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			HashMap exceptionMap = new HashMap();
			IComponent trxValueIn = null;
			String event=(String)map.get("event");
			
			OBComponent component = (OBComponent) map.get("componentObj");
			
			String componentName=component.getComponentName();
			boolean execute = false;
			if(component.getComponentName()==null||component.getComponentName().trim().equals("")){
				execute=true;
				exceptionMap.put("componentName", new ActionMessage("error.string.mandatory"));
				
			}
			
			//Start:-------->Abhishek Naik

			if(component.getDebtors()!= null && component.getDebtors().equalsIgnoreCase("Y")){
				if(component.getAge()== null || component.getAge().trim().equals("")){
					execute=true;
					exceptionMap.put("age", new ActionMessage("error.integer.mandatory"));
					}
			}
			
			//Start Santosh
			if(component.getComponentCategory()== null || component.getComponentCategory().trim().equals("")) {
					execute=true;
					exceptionMap.put("componentCategory", new ActionMessage("error.integer.mandatory"));
			}
			//End Santosh
			
			resultMap.put("request.ITrxValue", trxValueIn);
			resultMap.put("componentObj",component);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			// End:-------->Abhishek Naik 
			
			 if(component.getComponentName()!=null||!component.getComponentName().trim().equals("")){
				/*boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(componentName);
	    		if( searchTextFlag == true){
	    			execute=true;
	    			exceptionMap.put("componentName", new ActionMessage("error.string.invalidCharacter"));
					
				}*/
	    		
	    		boolean validCode = false;
				
					 try {
							validCode=getComponentProxy().isUniqueCode(component.getComponentName());
						} 
						catch(Exception ex){
							throw (new CommandProcessingException(ex.getMessage()));
						}
				
				if(validCode){
					execute=true;
					exceptionMap.put("componentName", new ActionMessage("error.string.exist","Component Name"));
							
				}
				resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				
			}
			 if(!execute){
			
			try {
				
				
				String ins=component.getHasInsurance();
				if(ins==null){
					component.setHasInsurance("No");
				}
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

				IComponentTrxValue trxValueOut = new OBComponentTrxValue();
				trxValueOut = getComponentProxy().makerCreateComponent(ctx,component);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				
			}catch (ComponentException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			
			}
			 return returnMap;
	 }
	
}

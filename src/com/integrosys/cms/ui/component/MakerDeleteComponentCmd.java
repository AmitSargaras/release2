package com.integrosys.cms.ui.component;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class MakerDeleteComponentCmd extends AbstractCommand implements	ICommonEventConstant {
	
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
	
	
	public MakerDeleteComponentCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
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
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
					   });
		}
	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	    	HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			HashMap exceptionMap = new HashMap();
			boolean execute = false;
			try {
				OBComponent component = (OBComponent) map.get("componentObj");
				if(component.getHasInsurance()==null){
					component.setHasInsurance("No");
				}
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IComponentTrxValue trxValueIn = (OBComponentTrxValue) map.get("IComponentTrxValue");
				OBComponent actualComponent = (OBComponent) trxValueIn.getComponent();
				IComponentTrxValue trxValueOut = new OBComponentTrxValue();

				
					if (event.equals("maker_delete_component")) {
						trxValueOut = getComponentProxy().makerDeleteComponent(ctx, trxValueIn, component);
					}
					else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						if(component.getComponentName()==null||component.getComponentName().trim().equals("")){
							execute=true;
							exceptionMap.put("componentName", new ActionMessage("error.string.mandatory"));
							resultMap.put("request.ITrxValue", trxValueIn);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						}
						if(component.getDebtors()!= null && component.getDebtors().equalsIgnoreCase("Y")){
						if(component.getAge()==null||component.getAge().trim().equals("")){
							execute=true;
							exceptionMap.put("age", new ActionMessage("error.integer.mandatory"));
							resultMap.put("request.ITrxValue", trxValueIn);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						}}
						//Start Santosh
						if(component.getComponentCategory()== null || component.getComponentCategory().trim().equals("")) {
								execute=true;
								exceptionMap.put("componentCategory", new ActionMessage("error.integer.mandatory"));
								resultMap.put("request.ITrxValue", trxValueIn);
								returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						}
						//End Santosh
						
						 if(component.getComponentName()!=null||!component.getComponentName().trim().equals("")){
							/*boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(component.getComponentName().trim());
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
								if(!actualComponent.getComponentName().equals(component.getComponentName())){
								execute=true;
								exceptionMap.put("componentName", new ActionMessage("error.string.exist","Component Name"));
								}	
							}
							resultMap.put("request.ITrxValue", trxValueIn);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							
						}
						 if(!execute){
						trxValueOut = getComponentProxy().makerEditRejectedComponent(ctx, trxValueIn, component);
						 }
					} 

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
			return returnMap;
	    }

}

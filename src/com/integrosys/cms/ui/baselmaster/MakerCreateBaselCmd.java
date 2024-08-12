package com.integrosys.cms.ui.baselmaster;

import java.util.Date;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.baselmaster.bus.IBaselDao;
import com.integrosys.cms.app.baselmaster.bus.OBBaselMaster;
import com.integrosys.cms.app.baselmaster.proxy.BaselProxyManagerImpl;
import com.integrosys.cms.app.baselmaster.proxy.IBaselProxyManager;
import com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue;
import com.integrosys.cms.app.baselmaster.trx.OBBaselMasterTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCreateBaselCmd extends AbstractCommand implements	ICommonEventConstant {
	
	private IBaselProxyManager baselProxy;

	public IBaselProxyManager getBaselProxy() {
		return baselProxy;
	}

	public void setBaselProxy(IBaselProxyManager baselProxy) {
		this.baselProxy = baselProxy;
	}

	public MakerCreateBaselCmd(){
		
	}
	
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        		{"IBaselMasterTrxValue", "com.integrosys.cms.app.baselmaster.trx.IBaselMasterTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{ "baselObj", "com.integrosys.cms.app.baselmaster.bus.OBBaselMaster", FORM_SCOPE }
	               
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] { 
					{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
					   });
		}
	 
	 public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			HashMap exceptionMap = new HashMap();
			boolean execute = false;
			IComponent trxValueIn = null;
			Date toDate=new Date();
			String event=(String)map.get("event");
			OBBaselMaster baselObj = (OBBaselMaster) map.get("baselObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IBaselDao baselDAO=(IBaselDao)BeanHouse.get("baselMasterDao");	
			baselObj.setCreatedOn(toDate);
			baselObj.setCreatedBy(ctx.getUser().getLoginID());
			
			if(baselObj.getSystem()!=null||!baselObj.getSystem().trim().equals("")){
				/*boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(componentName);
	    		if( searchTextFlag == true){
	    			execute=true;
	    			exceptionMap.put("componentName", new ActionMessage("error.string.invalidCharacter"));
					
				}*/
	    		
	    		boolean validCode = false;
				
					 try {
						 validCode=baselDAO.isUniqueCode(baselObj.getSystem());
						} 
						catch(Exception ex){
							throw (new CommandProcessingException(ex.getMessage()));
						}
				
				if(validCode){
					execute=true;
					exceptionMap.put("systemError", new ActionMessage("error.string.basel.system"));
							
				}
				resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				
			}
			
			if(!execute && baselObj.getSystem()!=null){
			try {			

				IBaselMasterTrxValue trxValueOut = new OBBaselMasterTrxValue();
				
				
				trxValueOut = getBaselProxy().makerCreateBasel(ctx,baselObj);
					
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

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.collateralNewMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
@author $Author: Abhijit R$
* Command for Save CollateralNewMaster
 */
public class MakerSaveCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICollateralNewMasterProxyManager collateralNewMasterProxy;

	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerSaveCollateralNewMasterCmd() {
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
	        		{"ICollateralNewMasterTrxValue", "com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"newCollateralDescription", "java.lang.String", REQUEST_SCOPE},
	        		{ "collateralNewMasterObj", "com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster", FORM_SCOPE }
	               
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
			try {
				String event = (String) map.get("event");
				OBCollateralNewMaster collateralNewMaster = (OBCollateralNewMaster) map.get("collateralNewMasterObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ICollateralNewMasterTrxValue trxValueOut = new OBCollateralNewMasterTrxValue();
				
				String newCollarealDesc = (String) map.get("newCollateralDescription");
				boolean isCollateralDescUnique = false;
				if( event.equals("maker_draft_collateralNewMaster") ){
					isCollateralDescUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollarealDesc);				
					if(isCollateralDescUnique != false){
						exceptionMap.put("newCollateralDescriptionError", new ActionMessage("error.string.exist","Collataral Description"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
				
				if(event.equals("maker_update_draft_collateralNewMaster")){
					ICollateralNewMasterTrxValue trxValueIn = (OBCollateralNewMasterTrxValue) map.get("ICollateralNewMasterTrxValue");
					
					String oldCollarealDesc = "";
					if( trxValueIn.getStatus().equalsIgnoreCase("ACTIVE") )
						oldCollarealDesc = trxValueIn.getCollateralNewMaster().getNewCollateralDescription();
					else
						oldCollarealDesc = trxValueIn.getStagingCollateralNewMaster().getNewCollateralDescription();
					
					if(!newCollarealDesc.equals(oldCollarealDesc))
						isCollateralDescUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollarealDesc.trim());
					
					if(isCollateralDescUnique != false){
						exceptionMap.put("newCollateralDescriptionError", new ActionMessage("error.string.exist","Collateral Description"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					trxValueOut = getCollateralNewMasterProxy().makerUpdateSaveUpdateCollateralNewMaster(ctx, trxValueIn, collateralNewMaster);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				}else{
				
				trxValueOut = getCollateralNewMasterProxy().makerSaveCollateralNewMaster(ctx,collateralNewMaster);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				}
			}catch (CollateralNewMasterException ex) {
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

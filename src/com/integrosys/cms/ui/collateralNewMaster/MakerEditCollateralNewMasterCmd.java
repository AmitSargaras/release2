
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
* Command for edit CollateralNewMaster
 */
public class MakerEditCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	
	
	public MakerEditCollateralNewMasterCmd() {
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
				OBCollateralNewMaster collateralNewMaster = (OBCollateralNewMaster) map.get("collateralNewMasterObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ICollateralNewMasterTrxValue trxValueIn = (OBCollateralNewMasterTrxValue) map.get("ICollateralNewMasterTrxValue");

				boolean isCollarealDescUnique = false;
				String newCollarealDesc = (String) map.get("newCollateralDescription");
				String oldCollarealDesc = "";
				ICollateralNewMasterTrxValue trxValueOut = new OBCollateralNewMasterTrxValue();

				if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
					oldCollarealDesc = trxValueIn.getStagingCollateralNewMaster().getNewCollateralDescription();
					
					if(!newCollarealDesc.equals(oldCollarealDesc))
						isCollarealDescUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollarealDesc.trim());
					
					if(isCollarealDescUnique != false){
						exceptionMap.put("newCollateralDescriptionError", new ActionMessage("error.string.exist","Collateral Description"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					trxValueOut = getCollateralNewMasterProxy().makerUpdateSaveCreateCollateralNewMaster(ctx, trxValueIn, collateralNewMaster);
				}else{
					if ((event.equals("maker_edit_collateralNewMaster"))||event.equals("maker_delete_collateralNewMaster")||event.equals("maker_save_update")) {
						boolean validate = false;
						if ( event.equals("maker_edit_collateralNewMaster") ){
							oldCollarealDesc = trxValueIn.getCollateralNewMaster().getNewCollateralDescription();
							validate = true;
						}
						else if ( event.equals("maker_save_update") ){
							oldCollarealDesc = trxValueIn.getStagingCollateralNewMaster().getNewCollateralDescription();
							validate = true;
						}
						
						if( validate ){
							if(!newCollarealDesc.equals(oldCollarealDesc))
								isCollarealDescUnique = getCollateralNewMasterProxy().isCollateraNameUnique(newCollarealDesc.trim());
							
							if(isCollarealDescUnique != false){
								exceptionMap.put("newCollateralDescriptionError", new ActionMessage("error.string.exist","Collateral Description"));
								returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
								returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								return returnMap;
							}
						}
						trxValueOut = getCollateralNewMasterProxy().makerUpdateCollateralNewMaster(ctx, trxValueIn, collateralNewMaster);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getCollateralNewMasterProxy().makerEditRejectedCollateralNewMaster(ctx, trxValueIn, collateralNewMaster);
					} 
				}
					resultMap.put("request.ITrxValue", trxValueOut);
				
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


package com.integrosys.cms.ui.facilityNewMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue;
import com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
@author $Author: Abhijit R$
* Command for edit FacilityNewMaster
 */
public class MakerEditFacilityNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IFacilityNewMasterProxyManager facilityNewMasterProxy;

	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerEditFacilityNewMasterCmd() {
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
	        		{"IFacilityNewMasterTrxValue", "com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"newFacilityName", "java.lang.String", REQUEST_SCOPE},
	        		{ "facilityNewMasterObj", "com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster", FORM_SCOPE }
	               
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
				OBFacilityNewMaster facilityNewMaster = (OBFacilityNewMaster) map.get("facilityNewMasterObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IFacilityNewMasterTrxValue trxValueIn = (OBFacilityNewMasterTrxValue) map.get("IFacilityNewMasterTrxValue");

				boolean isFacilityNameUnique = false;
				boolean isLineNoUnique = false;
				
				String newFacilityName = (String) map.get("newFacilityName");
				String oldFacilityName = "";
				
				String newLineNo = facilityNewMaster.getLineNumber();
				String newSystem = facilityNewMaster.getNewFacilitySystem();
				String oldLineNo = "";
				
				IFacilityNewMasterTrxValue trxValueOut = new OBFacilityNewMasterTrxValue();

				if(trxValueIn.getFromState().equals("PENDING_PERFECTION")){
					
					oldFacilityName = trxValueIn.getStagingFacilityNewMaster().getNewFacilityName();
					
					if(!newFacilityName.equals(oldFacilityName))
						isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName.trim());
					
					if(isFacilityNameUnique != false){
						exceptionMap.put("newFacilityNameError", new ActionMessage("error.string.exist","Facility Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					/*oldLineNo = trxValueIn.getStagingFacilityNewMaster().getLineNumber();
					
					if ( newLineNo != null && !newLineNo.equals("") ) {
						if(!newLineNo.equals(oldLineNo))
							isLineNoUnique = getFacilityNewMasterProxy().isUniqueCode(newLineNo.trim(),newSystem.trim());
						
						if(isLineNoUnique != false){
							exceptionMap.put("lineNumberError", new ActionMessage("error.string.exist","Line No. With Same System "));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
					}*/
					trxValueOut = getFacilityNewMasterProxy().makerUpdateSaveCreateFacilityNewMaster(ctx, trxValueIn, facilityNewMaster);
				}else{
					if ((event.equals("maker_edit_facilityNewMaster"))||event.equals("maker_delete_facilityNewMaster")||event.equals("maker_save_update")) {
						
						boolean validateFacilityName = false;
						boolean validateLineNo = false;
						
						if ( event.equals("maker_edit_facilityNewMaster") ){ 
							oldFacilityName = trxValueIn.getFacilityNewMaster().getNewFacilityName();
							oldLineNo = trxValueIn.getFacilityNewMaster().getLineNumber();
							
							validateFacilityName = true;
							validateLineNo = true;
						}
						else if ( event.equals("maker_save_update") ){
							oldFacilityName = trxValueIn.getStagingFacilityNewMaster().getNewFacilityName();
							oldLineNo = trxValueIn.getStagingFacilityNewMaster().getLineNumber();
							
							validateFacilityName = true;
							validateLineNo = true;
						}
						
						if( validateFacilityName ){
							if (!newFacilityName.equals(oldFacilityName))
								isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(newFacilityName.trim());
							if (isFacilityNameUnique != false) {
								exceptionMap.put("newFacilityNameError",new ActionMessage("error.string.exist","Facility Name"));
								returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
								returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
								return returnMap;
							}
						}
												
						/*if( validateLineNo ){
							if ( newLineNo != null && !newLineNo.equals("") ) {
								if ( !newLineNo.equals(oldLineNo) )
									isLineNoUnique = getFacilityNewMasterProxy().isUniqueCode(newLineNo.trim(),newSystem.trim());
								if ( isLineNoUnique != false ) {
									exceptionMap.put("lineNumberError", new ActionMessage("error.string.exist","Line No. With Same System "));
									returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
									returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP,exceptionMap);
									return returnMap;
								}
							}
						}*/
						trxValueOut = getFacilityNewMasterProxy().makerUpdateFacilityNewMaster(ctx, trxValueIn, facilityNewMaster);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getFacilityNewMasterProxy().makerEditRejectedFacilityNewMaster(ctx, trxValueIn, facilityNewMaster);
					} 
				}
					resultMap.put("request.ITrxValue", trxValueOut);
				
			}catch (FacilityNewMasterException ex) {
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

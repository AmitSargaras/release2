/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

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
* Command for Create System Bank Branch
 */
public class MakerCreateFacilityNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	
	
	public MakerCreateFacilityNewMasterCmd() {
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
			boolean validCode=false;
			String event = (String) map.get("event");
			String facilityName = (String) map.get("newFacilityName");
			boolean isFacilityNameUnique = false;
			
				OBFacilityNewMaster facilityNewMaster = (OBFacilityNewMaster) map.get("facilityNewMasterObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				//IFacilityNewMasterTrxValue trxValueIn = (OBFacilityNewMasterTrxValue) map.get("IFacilityNewMasterTrxValue");

				if( event.equals("maker_create_facilityNewMaster") ){
					isFacilityNameUnique = getFacilityNewMasterProxy().isFacilityNameUnique(facilityName);				
					if(isFacilityNameUnique != false){
						exceptionMap.put("newFacilityNameError", new ActionMessage("error.string.exist","Facility Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				
					/*
					 * Below validation has been commented As discussd/confirmed with Ganesh on date 23-JUL-2016 
					 * if(facilityNewMaster.getLineNumber()!=null && facilityNewMaster.getLineNumber().trim()!=""){
						 try {
				validCode=getFacilityNewMasterProxy().isUniqueCode(facilityNewMaster.getLineNumber(),facilityNewMaster.getNewFacilitySystem());
						} catch (FacilityNewMasterException e) {
							throw new FacilityNewMasterException("LineNumber null !");
						}
						catch(Exception e){
							throw new FacilityNewMasterException("LineNumber null !");
						}
					}*/
				}
				
				/*if(validCode){
					exceptionMap.put("lineNumberError", new ActionMessage("error.string.exist","Line No. With Same System "));
					ISystemBankBranchTrxValue trxValueIn = null;
					resultMap.put("request.ITrxValue", trxValueIn);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
				}else{*/
					try {	
				IFacilityNewMasterTrxValue trxValueOut = new OBFacilityNewMasterTrxValue();
				trxValueOut = getFacilityNewMasterProxy().makerCreateFacilityNewMaster(ctx,facilityNewMaster);
					
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
//	    }
	    }


}

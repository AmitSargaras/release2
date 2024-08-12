package com.integrosys.cms.ui.valuationAmountAndRating;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingException;
import com.integrosys.cms.app.valuationAmountAndRating.proxy.IValuationAmountAndRatingProxyManager;
import com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue;
import com.integrosys.cms.app.valuationAmountAndRating.trx.OBValuationAmountAndRatingTrxValue;

/**
 * This command Edits the Relationship Manager selected for edition 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 */
public class MakerSubmitEditValuationAmountAndRatingCommand extends AbstractCommand implements ICommonEventConstant {


	private IValuationAmountAndRatingProxyManager valuationAmountAndRatingProxy;

	public IValuationAmountAndRatingProxyManager getValuationAmountAndRatingProxy() {
		return valuationAmountAndRatingProxy;
	}

	public void setValuationAmountAndRatingProxy(IValuationAmountAndRatingProxyManager valuationAmountAndRatingProxy) {
		this.valuationAmountAndRatingProxy = valuationAmountAndRatingProxy;
	}
	
	/**
	 * Default Constructor
	 */

	public MakerSubmitEditValuationAmountAndRatingCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
			{"IValuationAmountAndRatingTrxValue", "com.integrosys.cms.app.valuationAmountAndRating.trx.IValuationAmountAndRatingTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"remarks", "java.lang.String", REQUEST_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
            {"productCode", "java.lang.String", REQUEST_SCOPE},
    		{ "valuationAmountAndRatingObj", "com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", FORM_SCOPE }
		});
	}
	
	public String[][] getResultDescriptor() { 
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
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
//	        	boolean isRMCodeUnique = true;
//	        	boolean isValuationAmountAndRatingNameUnique = false;
//	        	boolean isEmpIdUnique = false;
//	        	String empId = "";
	        	OBValuationAmountAndRating valuationAmountAndRating = (OBValuationAmountAndRating) map.get("valuationAmountAndRatingObj");
	        	
//	        	String rmCode = valuationAmountAndRating.getValuationAmountAndRatingCode();
	        	//String newValuationAmountAndRatingName = valuationAmountAndRating.getValuationAmountAndRatingName();
	        	//String newEmployeeId = valuationAmountAndRating.getEmployeeId();
				//String oldValuationAmountAndRatingName = "";
				//valuationAmountAndRating.setCpsId(valuationAmountAndRating.getEmployeeId());
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IValuationAmountAndRatingTrxValue trxValueIn = (OBValuationAmountAndRatingTrxValue) map.get("IValuationAmountAndRatingTrxValue");
				IValuationAmountAndRatingTrxValue trxValueOut = new OBValuationAmountAndRatingTrxValue();
				
				/*if(trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)){//add - save - maker process - submit 
						Commented by Sandeep Shinde	
					
					if(rmCode!=null)
						isRMCodeUnique = getValuationAmountAndRatingProxyManager().isRMCodeUnique(rmCode.trim());

					if(isRMCodeUnique != false){
						exceptionMap.put("valuationAmountAndRatingCodeError", new ActionMessage("error.string.rmcode.exist"));
						IValuationAmountAndRatingTrxValue valuationAmountAndRatingTrxValue = null;
						resultMap.put("request.ITrxValue", valuationAmountAndRatingTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					// Start
					oldValuationAmountAndRatingName = trxValueIn.getStagingValuationAmountAndRating().getValuationAmountAndRatingName();
					if( ! oldValuationAmountAndRatingName.equals(newValuationAmountAndRatingName) )
						isValuationAmountAndRatingNameUnique = getValuationAmountAndRatingProxyManager().isValuationAmountAndRatingNameUnique(newValuationAmountAndRatingName.trim());
					
					empId = trxValueIn.getStagingValuationAmountAndRating().getEmployeeId();
					if( ! empId.equals(newEmployeeId) )
	        		isEmpIdUnique = getValuationAmountAndRatingProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
	        		
//					if(isValuationAmountAndRatingNameUnique != false){
//						exceptionMap.put("valuationAmountAndRatingNameError", new ActionMessage("error.string.exist","ValuationAmountAndRating Name"));
//						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//						return returnMap;
//					}
					
					if(isEmpIdUnique){
						exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					// End
					
					
						trxValueOut = getValuationAmountAndRatingProxyManager().makerUpdateCreateValuationAmountAndRating(ctx, trxValueIn, valuationAmountAndRating);
						
				}else{*/
					/*if (event.equals("maker_submit_edit") || event.equals("maker_confirm_resubmit_update")) {

						// Start
						oldValuationAmountAndRatingName = trxValueIn.getValuationAmountAndRating().getValuationAmountAndRatingName();
						if( ! oldValuationAmountAndRatingName.equals(newValuationAmountAndRatingName) )
							isValuationAmountAndRatingNameUnique = getValuationAmountAndRatingProxyManager().isValuationAmountAndRatingNameUnique(newValuationAmountAndRatingName.trim());
						
						empId = trxValueIn.getStagingValuationAmountAndRating().getEmployeeId();
						//System.out.println("empId:"+empId+" newEmployeeId:"+newEmployeeId);
						if( null!=empId &&  (! empId.equals(newEmployeeId) ))
			        		isEmpIdUnique = getValuationAmountAndRatingProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
		        		
		        		
		        		
//						if(isValuationAmountAndRatingNameUnique != false){
//							exceptionMap.put("valuationAmountAndRatingNameError", new ActionMessage("error.string.exist","ValuationAmountAndRating Name"));
//							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//							return returnMap;
//						}
						
						if(isEmpIdUnique){
							exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						// End
						
						trxValueOut = getValuationAmountAndRatingProxyManager().makerUpdateValuationAmountAndRating(ctx, trxValueIn, valuationAmountAndRating);
					}else if (event.equals("maker_save_update_relationship_mgr")){
						
						// Start						
						oldValuationAmountAndRatingName = trxValueIn.getValuationAmountAndRating().getValuationAmountAndRatingName();
						if( ! oldValuationAmountAndRatingName.equals(newValuationAmountAndRatingName) )
							isValuationAmountAndRatingNameUnique = getValuationAmountAndRatingProxyManager().isValuationAmountAndRatingNameUnique(newValuationAmountAndRatingName.trim());
						
						empId = trxValueIn.getStagingValuationAmountAndRating().getEmployeeId();
						if( ! empId.equals(newEmployeeId) )
		        		isEmpIdUnique = getValuationAmountAndRatingProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
		        		
		        		
//						if(isValuationAmountAndRatingNameUnique != false){
//							exceptionMap.put("valuationAmountAndRatingNameError", new ActionMessage("error.string.exist","ValuationAmountAndRating Name"));
//							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//							return returnMap;
//						}
						
						if(isEmpIdUnique){
							exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						// End
						
						trxValueOut = getValuationAmountAndRatingProxyManager().makerUpdateSaveValuationAmountAndRating(ctx, trxValueIn, valuationAmountAndRating);
					}else {*/
						// event is  maker_confirm_resubmit_edit
						
						// Start
						//oldValuationAmountAndRatingName = trxValueIn.getStagingValuationAmountAndRating().getValuationAmountAndRatingName();
//						if( ! oldValuationAmountAndRatingName.equals(newValuationAmountAndRatingName) )
//							isValuationAmountAndRatingNameUnique = getValuationAmountAndRatingProxyManager().isValuationAmountAndRatingNameUnique(newValuationAmountAndRatingName.trim());
//						
//						empId = trxValueIn.getStagingValuationAmountAndRating().getEmployeeId();
//						if( ! empId.equals(newEmployeeId) )
//			        		isEmpIdUnique = getValuationAmountAndRatingProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
//			        		
		        		
		        		
//						if(isValuationAmountAndRatingNameUnique != false){
//							exceptionMap.put("valuationAmountAndRatingNameError", new ActionMessage("error.string.exist","ValuationAmountAndRating Name"));
//							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//							return returnMap;
//						}
						
//						if(isEmpIdUnique){
//							exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
//							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//							return returnMap;
//						}
						// End
						
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getValuationAmountAndRatingProxy().makerEditRejectedValuationAmountAndRating(ctx, trxValueIn, valuationAmountAndRating);
//					} 
//				}
				resultMap.put("request.ITrxValue", trxValueOut);
	        	
	        } catch (ValuationAmountAndRatingException obe) {
	        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
	}
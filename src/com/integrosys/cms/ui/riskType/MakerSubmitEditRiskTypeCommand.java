package com.integrosys.cms.ui.riskType;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.riskType.bus.OBRiskType;
import com.integrosys.cms.app.riskType.bus.RiskTypeException;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue;
import com.integrosys.cms.app.riskType.trx.OBRiskTypeTrxValue;

/**
 * This command Edits the Relationship Manager selected for edition 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 */
public class MakerSubmitEditRiskTypeCommand extends AbstractCommand implements ICommonEventConstant {


	private IRiskTypeProxyManager riskTypeProxy;

	public IRiskTypeProxyManager getRiskTypeProxy() {
		return riskTypeProxy;
	}

	public void setRiskTypeProxy(IRiskTypeProxyManager riskTypeProxy) {
		this.riskTypeProxy = riskTypeProxy;
	}
	
	/**
	 * Default Constructor
	 */

	public MakerSubmitEditRiskTypeCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
			{"IRiskTypeTrxValue", "com.integrosys.cms.app.riskType.trx.IRiskTypeTrxValue", SERVICE_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"remarks", "java.lang.String", REQUEST_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
            {"productCode", "java.lang.String", REQUEST_SCOPE},
    		{ "riskTypeObj", "com.integrosys.cms.app.riskType.bus.OBRiskType", FORM_SCOPE }
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
//	        	boolean isRiskTypeNameUnique = false;
//	        	boolean isEmpIdUnique = false;
//	        	String empId = "";
	        	OBRiskType riskType = (OBRiskType) map.get("riskTypeObj");
	        	
//	        	String rmCode = riskType.getRiskTypeCode();
	        	//String newRiskTypeName = riskType.getRiskTypeName();
	        	//String newEmployeeId = riskType.getEmployeeId();
				//String oldRiskTypeName = "";
				//riskType.setCpsId(riskType.getEmployeeId());
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IRiskTypeTrxValue trxValueIn = (OBRiskTypeTrxValue) map.get("IRiskTypeTrxValue");
				IRiskTypeTrxValue trxValueOut = new OBRiskTypeTrxValue();
				
				/*if(trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)){//add - save - maker process - submit 
						Commented by Sandeep Shinde	
					
					if(rmCode!=null)
						isRMCodeUnique = getRiskTypeProxyManager().isRMCodeUnique(rmCode.trim());

					if(isRMCodeUnique != false){
						exceptionMap.put("riskTypeCodeError", new ActionMessage("error.string.rmcode.exist"));
						IRiskTypeTrxValue riskTypeTrxValue = null;
						resultMap.put("request.ITrxValue", riskTypeTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
					// Start
					oldRiskTypeName = trxValueIn.getStagingRiskType().getRiskTypeName();
					if( ! oldRiskTypeName.equals(newRiskTypeName) )
						isRiskTypeNameUnique = getRiskTypeProxyManager().isRiskTypeNameUnique(newRiskTypeName.trim());
					
					empId = trxValueIn.getStagingRiskType().getEmployeeId();
					if( ! empId.equals(newEmployeeId) )
	        		isEmpIdUnique = getRiskTypeProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
	        		
//					if(isRiskTypeNameUnique != false){
//						exceptionMap.put("riskTypeNameError", new ActionMessage("error.string.exist","RiskType Name"));
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
					
					
						trxValueOut = getRiskTypeProxyManager().makerUpdateCreateRiskType(ctx, trxValueIn, riskType);
						
				}else{*/
					/*if (event.equals("maker_submit_edit") || event.equals("maker_confirm_resubmit_update")) {

						// Start
						oldRiskTypeName = trxValueIn.getRiskType().getRiskTypeName();
						if( ! oldRiskTypeName.equals(newRiskTypeName) )
							isRiskTypeNameUnique = getRiskTypeProxyManager().isRiskTypeNameUnique(newRiskTypeName.trim());
						
						empId = trxValueIn.getStagingRiskType().getEmployeeId();
						//System.out.println("empId:"+empId+" newEmployeeId:"+newEmployeeId);
						if( null!=empId &&  (! empId.equals(newEmployeeId) ))
			        		isEmpIdUnique = getRiskTypeProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
		        		
		        		
		        		
//						if(isRiskTypeNameUnique != false){
//							exceptionMap.put("riskTypeNameError", new ActionMessage("error.string.exist","RiskType Name"));
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
						
						trxValueOut = getRiskTypeProxyManager().makerUpdateRiskType(ctx, trxValueIn, riskType);
					}else if (event.equals("maker_save_update_relationship_mgr")){
						
						// Start						
						oldRiskTypeName = trxValueIn.getRiskType().getRiskTypeName();
						if( ! oldRiskTypeName.equals(newRiskTypeName) )
							isRiskTypeNameUnique = getRiskTypeProxyManager().isRiskTypeNameUnique(newRiskTypeName.trim());
						
						empId = trxValueIn.getStagingRiskType().getEmployeeId();
						if( ! empId.equals(newEmployeeId) )
		        		isEmpIdUnique = getRiskTypeProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
		        		
		        		
//						if(isRiskTypeNameUnique != false){
//							exceptionMap.put("riskTypeNameError", new ActionMessage("error.string.exist","RiskType Name"));
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
						
						trxValueOut = getRiskTypeProxyManager().makerUpdateSaveRiskType(ctx, trxValueIn, riskType);
					}else {*/
						// event is  maker_confirm_resubmit_edit
						
						// Start
						//oldRiskTypeName = trxValueIn.getStagingRiskType().getRiskTypeName();
//						if( ! oldRiskTypeName.equals(newRiskTypeName) )
//							isRiskTypeNameUnique = getRiskTypeProxyManager().isRiskTypeNameUnique(newRiskTypeName.trim());
//						
//						empId = trxValueIn.getStagingRiskType().getEmployeeId();
//						if( ! empId.equals(newEmployeeId) )
//			        		isEmpIdUnique = getRiskTypeProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
//			        		
		        		
		        		
//						if(isRiskTypeNameUnique != false){
//							exceptionMap.put("riskTypeNameError", new ActionMessage("error.string.exist","RiskType Name"));
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
						trxValueOut = getRiskTypeProxy().makerEditRejectedRiskType(ctx, trxValueIn, riskType);
//					} 
//				}
				resultMap.put("request.ITrxValue", trxValueOut);
	        	
	        } catch (RiskTypeException obe) {
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
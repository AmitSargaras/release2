/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.systemBankBranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
@author $Author: Abhijit R$
* Command for Create System Bank Branch
 */
public class MakerSaveSystemBankBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}
	
	
	/**
	 * Default Constructor
	 */
	
	
	public MakerSaveSystemBankBranchCmd() {
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
	        		{"ISystemBankBranchTrxValue", "com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue", SERVICE_SCOPE},
	                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	                {"remarks", "java.lang.String", REQUEST_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	        		{ "systemBankBranchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch", FORM_SCOPE }
	               
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
				boolean validBranchCode=false;
				boolean validBranchName=false;
				boolean validRBICode=false;
				OBSystemBankBranch systemBankBranch = (OBSystemBankBranch) map.get("systemBankBranchObj");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ISystemBankBranchTrxValue trxValueOut = new OBSystemBankBranchTrxValue();
				if(event.equals("maker_update_draft_systemBankBranch")){
					ISystemBankBranchTrxValue trxValueIn = (OBSystemBankBranchTrxValue) map.get("ISystemBankBranchTrxValue");
					trxValueOut = getSystemBankBranchProxy().makerUpdateSaveUpdateSystemBankBranch(ctx, trxValueIn, systemBankBranch);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				}else{
					if(systemBankBranch.getSystemBankBranchCode()!=null && systemBankBranch.getSystemBankBranchCode().trim()!=""){
						 try {
							validBranchCode=getSystemBankBranchProxy().isUniqueCode("system_bank_branch_code",systemBankBranch.getSystemBankBranchCode());
						} catch (SystemBankBranchException e) {
							throw new SystemBankBranchException("Branch code null !");
						}
						catch(Exception e){
							throw new SystemBankBranchException("Branch code null !");
						}
					}
					if(systemBankBranch.getRbiCode()!=null && systemBankBranch.getRbiCode().trim()!=""){
						 try {
							validRBICode=getSystemBankBranchProxy().isUniqueCode("rbi_code",systemBankBranch.getRbiCode());
						} catch (SystemBankBranchException e) {
							throw new SystemBankBranchException("Branch code null !");
						}
						catch(Exception e){
							throw new SystemBankBranchException("Branch code null !");
						}
					}
					if(systemBankBranch.getSystemBankBranchName()!=null && systemBankBranch.getSystemBankBranchName().trim()!=""){
						 try {
							 validBranchName=getSystemBankBranchProxy().isUniqueCode("system_bank_branch_name",systemBankBranch.getSystemBankBranchName());
						} catch (SystemBankBranchException e) {
							throw new SystemBankBranchException("Branch Name null !");
						}
						catch(Exception e){
							throw new SystemBankBranchException("Branch Name null !");
						}
					}
					if(validBranchCode){
						exceptionMap.put("systemBankBranchDuplicateCodeError", new ActionMessage("error.string.duplicate"));
						ISystemBankBranchTrxValue trxValueIn = null;
						resultMap.put("request.ITrxValue", trxValueIn);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						return returnMap;
					}else if(validBranchName){
						exceptionMap.put("systemBankBranchDuplicateBranchNameError", new ActionMessage("error.string.duplicateBranchName"));
						ISystemBankBranchTrxValue trxValueIn = null;
						resultMap.put("request.ITrxValue", trxValueIn);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						return returnMap;
					}else if(validRBICode){
						exceptionMap.put("systemBankBranchDuplicateRBICodeError", new ActionMessage("error.string.duplicateRBICode"));
						ISystemBankBranchTrxValue trxValueIn = null;
						resultMap.put("request.ITrxValue", trxValueIn);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						return returnMap;
					}else{
				trxValueOut = getSystemBankBranchProxy().makerSaveSystemBankBranch(ctx,systemBankBranch);
					
					resultMap.put("request.ITrxValue", trxValueOut);
				}
				}	
			}catch (SystemBankBranchException ex) {
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

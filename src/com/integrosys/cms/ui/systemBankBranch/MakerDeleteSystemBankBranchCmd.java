/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.systemBankBranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
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
*@author $Author: Abhijit R$
*Command for Delete System Bank Branch
 */
public class MakerDeleteSystemBankBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	
	
	public MakerDeleteSystemBankBranchCmd() {
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
			boolean invalidHUB=false;
			try {
				OBSystemBankBranch systemBankBranch = (OBSystemBankBranch) map.get("systemBankBranchObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				ISystemBankBranchTrxValue trxValueIn = (OBSystemBankBranchTrxValue) map.get("ISystemBankBranchTrxValue");

				ISystemBankBranchTrxValue trxValueOut = new OBSystemBankBranchTrxValue();

				
					if (event.equals("maker_delete_systemBankBranch")) {
						trxValueOut = getSystemBankBranchProxy().makerDeleteSystemBankBranch(ctx, trxValueIn, systemBankBranch);
					} else {
						// event is  maker_confirm_resubmit_edit
						if(!(systemBankBranch.getIsHub().equalsIgnoreCase(trxValueIn.getSystemBankBranch().getIsHub()))){
							if(systemBankBranch.getIsHub().equals("N")){
								SearchResult systemBankBranchList = new SearchResult();
					            systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getAllActualBranch();
					            List branchList = new ArrayList(systemBankBranchList.getResultList());
					            for(int i=0;i<branchList.size();i++){
					            	OBSystemBankBranch branch= (OBSystemBankBranch) branchList.get(i);
					            	
					            	if(trxValueIn.getSystemBankBranch().getSystemBankBranchCode().equalsIgnoreCase(branch.getLinkedHub())){
					            		invalidHUB=true; 		
					            		break;
					            	}
					            	
					            }
							}
						}
							
							if(invalidHUB){
								exceptionMap.put("invalidHUBError", new ActionMessage("error.string.invalidHUB"));
								 trxValueIn = null;
								resultMap.put("request.ITrxValue", trxValueIn);
								returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
								returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
								return returnMap;
							}else{
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getSystemBankBranchProxy().makerEditRejectedSystemBankBranch(ctx, trxValueIn, systemBankBranch);
					} 
					}

					resultMap.put("request.ITrxValue", trxValueOut);
				
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

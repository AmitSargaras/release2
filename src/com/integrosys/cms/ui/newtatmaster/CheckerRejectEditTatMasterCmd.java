package com.integrosys.cms.ui.newtatmaster;

import java.util.Date;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.proxy.IComponentProxyManager;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.newtatmaster.bus.INewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.OBNewTatMaster;
import com.integrosys.cms.app.newtatmaster.bus.TatMasterException;
import com.integrosys.cms.app.newtatmaster.proxy.ITatmasterProxyManager;
import com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue;
import com.integrosys.cms.app.newtatmaster.trx.OBTatMasterTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerRejectEditTatMasterCmd extends AbstractCommand implements	ICommonEventConstant{
	
	private ITatmasterProxyManager tatMasterProxy;

	

	
	public ITatmasterProxyManager getTatMasterProxy() {
		return tatMasterProxy;
	}

	public void setTatMasterProxy(ITatmasterProxyManager tatMasterProxy) {
		this.tatMasterProxy = tatMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectEditTatMasterCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{"ITatMasterTrxValue", "com.integrosys.cms.app.newtatmaster.trx.ITatMasterTrxValue", SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE},
		
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		 HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        Date toDate=new Date();
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            ITatMasterTrxValue trxValueIn = (OBTatMasterTrxValue) map.get("ITatMasterTrxValue");
	          //  trxValueIn.getStagingtatMaster().setLastUpdatedBy(ctx.getUser().getLoginID());
			//	trxValueIn.getStagingtatMaster().setLastUpdatedOn(toDate);
	            HashMap exceptionMap = new HashMap();
	            String event = (String) map.get("event");
	            String remarks = (String) map.get("remarks");
	            if(remarks == null||remarks.trim().equals("")){
					exceptionMap.put("tatRemarksError", new ActionMessage("error.reject.remark"));
					ISystemBankBranchTrxValue trxValueInValidate = null;
					INewTatMaster stageTat = (OBNewTatMaster) trxValueIn.getStagingtatMaster();
					resultMap.put("trxId", trxValueIn.getTransactionID());
					resultMap.put("tatEventObj", stageTat);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
				}else{
				 try {
	            ctx.setRemarks(remarks);
	           ITatMasterTrxValue trxValueOut = getTatMasterProxy().checkerRejectTatMaster(ctx, trxValueIn);
	            resultMap.put("request.ITrxValue", trxValueOut);
	            
	        }catch (TatMasterException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
				}	
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}



	
	

}

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.fccBranch;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.OBFCCBranch;
import com.integrosys.cms.app.fccBranch.proxy.IFCCBranchProxyManager;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fccBranch.trx.OBFCCBranchTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
*@author $Author: Abhijit R$
*Command to read FCCBranch
 */
public class MakerReadFCCBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IFCCBranchProxyManager fccBranchProxy;

	
	
	
	

	/**
	 * @return the fccBranchProxy
	 */
	public IFCCBranchProxyManager getFccBranchProxy() {
		return fccBranchProxy;
	}

	/**
	 * @param fccBranchProxy the fccBranchProxy to set
	 */
	public void setFccBranchProxy(IFCCBranchProxyManager fccBranchProxy) {
		this.fccBranchProxy = fccBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerReadFCCBranchCmd() {
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
				 {"branchCode", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
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
				{ "fccBranchObj", "com.integrosys.cms.app.fccBranch.bus.OBFCCBranch", SERVICE_SCOPE },
				{ "fccBranchObj", "com.integrosys.cms.app.fccBranch.bus.OBFCCBranch", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "hubValueList", "java.util.List", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{"IFCCBranchTrxValue", "com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue", SERVICE_SCOPE}
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
		try {
			IFCCBranch fccBranch;
			IFCCBranchTrxValue trxValue=null;
			String fccBranchCode=(String) (map.get("branchCode"));
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			String event = (String) map.get("event");
			trxValue = (OBFCCBranchTrxValue) getFccBranchProxy().getFCCBranchTrxValue(Long.parseLong(fccBranchCode));
			fccBranch = (OBFCCBranch) trxValue.getFCCBranch();
			long id = fccBranch.getId();
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_HOLIDAY",id,"ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			
			resultMap.put("event", event);
			resultMap.put("startIndex",startIdx);	
			resultMap.put("IFCCBranchTrxValue", trxValue);
			resultMap.put("fccBranchObj", fccBranch);
		}catch (FCCBranchException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
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

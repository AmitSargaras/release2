package com.integrosys.cms.ui.otherbankbranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;

public class OtherBankBranchCheckerReadFileInsertListCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	

	
	private IOtherBranchProxyManager otherBranchProxyManager;

	
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	
	/**
	 * Default Constructor
	 */
	public OtherBankBranchCheckerReadFileInsertListCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
				
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"OtherBranchObj", "com.integrosys.cms.app.otherbranch.bus.OBOtherBranch", FORM_SCOPE },
				{"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"otherBankBranchList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
				{"startIndex", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,OtherBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IFileMapperId otherBank;
			IOtherBankBranchTrxValue trxValue=null;
			String transId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");

			String startIndex =(String) map.get("startIndex");
			SearchResult otherBankList=null;
			
			String login = (String)map.get("loginId");
			
			if(startIndex == null){
				startIndex ="0"; 
			}
			
			if(login==null){
				login="";
			}
			List result = new ArrayList();
			
		
			trxValue = (OBOtherBankBranchTrxValue) getOtherBranchProxyManager().getInsertFileByTrxIDBankBranch(transId);
		

			result = (List)  getOtherBranchProxyManager().getAllStageBankBranch(transId,login);
			
			// function to get stging value of system bank trx value
			otherBank = (OBFileMapperID) trxValue.getStagingFileMapperID();
			
			otherBankList = new SearchResult(Integer.parseInt(startIndex), 10, result.size(), result);
			
			resultMap.put("otherBankBranchList",otherBankList);
			resultMap.put("IOtherBankBranchTrxValue", trxValue);
			resultMap.put("OtherBranchObj", otherBank);
			resultMap.put("event", event);
			resultMap.put("startIndex", startIndex);
			resultMap.put("TrxId", transId);
		} catch (OtherBankException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
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

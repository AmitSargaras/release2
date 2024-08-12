package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PaginateOtherBankBranchListCommand extends AbstractCommand{
	
	private IOtherBankProxyManager otherBankProxyManager;

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	
	public PaginateOtherBankBranchListCommand() {
		DefaultLogger.debug(this, "In Default Constructor");
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"BankId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"type", "java.lang.String", REQUEST_SCOPE},
	            {"BranchCode", "java.lang.String", REQUEST_SCOPE},
	            {"BranchName", "java.lang.String", REQUEST_SCOPE},
	            {"State", "java.lang.String", REQUEST_SCOPE},
	            {"City", "java.lang.String", REQUEST_SCOPE},	            
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"startIndx", "java.lang.String", REQUEST_SCOPE},
	            {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"OtherBankObj", "com.integrosys.cms.app.otherbank.bus.OBOtherBank", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", SERVICE_SCOPE},
	            {"BranchCode", "java.lang.String", REQUEST_SCOPE},
	            {"BranchName", "java.lang.String", REQUEST_SCOPE},
	            {"State", "java.lang.String", REQUEST_SCOPE},
	            {"City", "java.lang.String", REQUEST_SCOPE},           
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE},
	            {"otherBranchList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"startIndx", "java.lang.String", REQUEST_SCOPE},
	            {"loginUser", "java.lang.String", SERVICE_SCOPE}
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
	        	String id = (String) map.get("BankId");
	        	String event = (String) map.get("event");
	        	String startInd = (String) map.get("startIndex");
	        	String startIndx = (String) map.get("startIndx");
	        	
	        	ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				String loginUser = user.getLoginID();
				
	        	if(startIndx == null)
	        		startIndx = "0";
	        	IOtherBank otherBank = new OBOtherBank();
	        	
	        	IOtherBankTrxValue otherBankTrxValue = getOtherBankProxyManager().getOtherBankTrxValue(Long.parseLong(id));
	        	otherBank = otherBankTrxValue.getOtherBank();
	        					
	        	String branchCode = (String) map.get("BranchCode");
				String branchName = (String) map.get("BranchName");
				String state = (String) map.get("State");
				String city = (String) map.get("City");
				
				if( branchCode.equals("null") )
					branchCode = "";
				
				if( branchName.equals("null") )
					branchName = "";
				
				if( state.equals("null") )
					state = "";
				
				if( city.equals("null") )
					city = "";
				
				SearchResult OtherBranchList = getOtherBankProxyManager().getOtherBranchList(branchCode, branchName,state,city,Long.parseLong(id));
				resultMap.put("otherBranchList", OtherBranchList);	        		
				resultMap.put("startIndex", startInd);
				resultMap.put("startIndx", startIndx);
	            resultMap.put("event", event);
	            resultMap.put("OtherBankObj", otherBank);
	            resultMap.put("IOtherBankTrxValue", otherBankTrxValue);
	            resultMap.put("loginUser",loginUser);
	            
	            resultMap.put("BranchCode", branchCode);
	            resultMap.put("BranchName", branchName);
	            resultMap.put("State", state);
	            resultMap.put("City",city);
	        } catch (OtherBankException obe) {
	        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	        return returnMap;
	    }
}

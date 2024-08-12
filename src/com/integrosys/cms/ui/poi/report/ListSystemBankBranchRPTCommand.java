/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.poi.report;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class ListSystemBankBranchRPTCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	public ListSystemBankBranchRPTCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "searchBranchCode", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE },
//				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",SERVICE_SCOPE },
//				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
//				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE }
				});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "systemBankBranchList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
//				{ "User_SearchCriteria", "com.integrosys.component.user.app.bus.CommonUserSearchCriteria",SERVICE_SCOPE }
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBFilter filter = (OBFilter) map.get("reportFormObj");
		if(filter.getReportId()!=null)
			result.put("reportFormObj", filter);
		
		String startIdx = (String) map.get("startIndex");
		if(startIdx==null){
			startIdx="0";
		}
		
		
		String event=(String) map.get("event");
		if(!"paginate_branch_list".equals(event)){
		String searchBranchCodeText=(String) map.get("searchBranchCode");
//		 TODO: uncomment and handle validation.
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String branchCodeLength = bundle.getString("branch.code.length");
		String errorCode="";
		if(ASSTValidator.isValidAlphaNumStringWithoutSpace(searchBranchCodeText)){
			exceptionMap.put("branchCodeError", new ActionMessage("error.string.invalidCharacter"));
		}
		/*else if (!(errorCode=Validator.checkString(searchBranchCodeText, true, 1, 4)).equals(Validator.ERROR_NONE)) {
			exceptionMap.put("branchCodeError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),1, branchCodeLength + ""));
		}*/
		else if(searchBranchCodeText.length() > Integer.parseInt(branchCodeLength)){
			exceptionMap.put("branchCodeError",new ActionMessage("error.number.greaterthan","1",branchCodeLength));
		}
		else if(!ASSTValidator.isNumeric(searchBranchCodeText)) {
			exceptionMap.put("branchCodeError", new ActionMessage("error.number.format"));
		}
		
		
		
		SearchResult systemBankBranchList = new SearchResult();
		try{
			// function to get system Bank according to search text and type
		    systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getSystemBranchList(searchBranchCodeText, null, null, null);
		}catch (SystemBankException ex) {
			 DefaultLogger.debug(this, "got exception in doExecute while performing search getSystemBranchList()" + ex);
		     ex.printStackTrace();
		     throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
		 DefaultLogger.debug(this, "got exception in doExecute" + e);
		 e.printStackTrace();
		 throw (new CommandProcessingException(e.getMessage()));
		}
		result.put("systemBankBranchList", systemBankBranchList);
		}
		result.put("startIndex", startIdx);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This command lists the Other Banks present
 * $Author:Dattatray Thoratsss
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23 
 */
public class ListOtherBankCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IOtherBankProxyManager otherBankProxyManager;

	
	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public ListOtherBankCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"BankCode", "java.lang.String", REQUEST_SCOPE},
	            {"BankName", "java.lang.String", REQUEST_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"OtherBankList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"loginUser", "java.lang.String", SERVICE_SCOPE},
	            {"event", "java.lang.String", SERVICE_SCOPE}
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
	        	String bankCode = (String) map.get("BankCode");
	        	String bankName = (String) map.get("BankName");
	        	String startInd = (String) map.get("startIndex");
	        	String event=(String) (map.get("event"));
	        	ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				String loginUser = user.getLoginID();
				
				if( bankCode!= null && ! bankCode.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(bankCode);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						bankCode= "";
					}
				}
				
				if( bankName!= null && ! bankName.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(bankName);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						bankName= "";
					}
				}
				
	            SearchResult OtherBankList= getOtherBankProxyManager().getOtherBankList(bankCode,bankName);
	            resultMap.put("OtherBankList", OtherBankList);
	            resultMap.put("startIndex", startInd);
	            resultMap.put("loginUser",loginUser);
	            resultMap.put("event", event);
	        } catch (OtherBankException obe) {
	            DefaultLogger.error(this, "got exception in doExecute" ,obe);
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




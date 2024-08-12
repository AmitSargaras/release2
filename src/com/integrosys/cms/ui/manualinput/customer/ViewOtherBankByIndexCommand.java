package com.integrosys.cms.ui.manualinput.customer;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This command displays the other bank present for bank id 
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23 
 */
public class ViewOtherBankByIndexCommand extends AbstractCommand implements ICommonEventConstant {

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

	public ViewOtherBankByIndexCommand() {
		DefaultLogger.debug(this, "in constructor");
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"BankId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"type", "java.lang.String", REQUEST_SCOPE},
	        	{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"ManualInputCustomerInfoForm","com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm","REQUEST_SCOPE"},
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
				 {"BankId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", SERVICE_SCOPE},
	            {"BranchCode", "java.lang.String", REQUEST_SCOPE},
	            {"ManualInputCustomerInfoForm","com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm","REQUEST_SCOPE"},
	            {"BranchName", "java.lang.String", REQUEST_SCOPE},
	            {"State", "java.lang.String", REQUEST_SCOPE},
	            {"City", "java.lang.String", REQUEST_SCOPE},	  
	        	{ "OBCMSCustomer",
					"com.integrosys.cms.app.customer.bus.ICMSCustomer",
					REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
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
	        	String type =  (String) map.get("type");
	        	String startInd = (String) map.get("startIndex");
	        	String startIndx = (String) map.get("startIndx");
	        	ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
	        	
	        	ManualInputCustomerInfoForm custForm = (ManualInputCustomerInfoForm) map.get("ManualInputCustomerInfoForm");
	        	
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
				
				if( branchCode!= null && ! branchCode.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(branchCode);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						branchCode= "";
					}
				}
				
				if( branchName!= null && ! branchName.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(branchName);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						branchName= "";
					}
				}
				
				if( state!= null && ! state.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(state);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						state= "";
					}
				}
				
				if( city!= null && ! city.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(city);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						city= "";
					}
				}
				
				SearchResult OtherBranchList = getOtherBankProxyManager().getOtherBranchList(branchCode, branchName,state,city,Long.parseLong(id));
				resultMap.put("otherBranchList", OtherBranchList);	        		
				resultMap.put("startIndex", startInd);
				resultMap.put("startIndx", startIndx);
	            resultMap.put("event", event);
	            resultMap.put("OtherBankObj", otherBank);
	            resultMap.put("IOtherBankTrxValue", otherBankTrxValue);
	            resultMap.put("loginUser",loginUser);
	           resultMap.put("OBCMSCustomer", obCustomer);
	            resultMap.put("BranchCode", branchCode);
	            resultMap.put("BranchName", branchName);
	            resultMap.put("BankId", id);
	            resultMap.put("State", state);
	            resultMap.put("City",city);
	            resultMap.put("ManualInputCustomerInfoForm",custForm);
	            
	            
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




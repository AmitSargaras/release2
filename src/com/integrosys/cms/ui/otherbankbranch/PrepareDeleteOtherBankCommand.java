package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
 * This command Prepares the other bank branch before deleting
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23
 */
public class PrepareDeleteOtherBankCommand extends AbstractCommand implements ICommonEventConstant {

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

	public PrepareDeleteOtherBankCommand() {
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
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"OtherBankObj", "com.integrosys.cms.app.otherbank.bus.OBOtherBank", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"flag", "java.lang.String", REQUEST_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE},
	            {"otherBranchList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
	            {"loginUser", "java.lang.String", REQUEST_SCOPE}
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
	        	boolean flag = false;
	        	String id = (String) map.get("BankId");
	        	String event = (String) map.get("event");
	        	IOtherBankTrxValue otherBankTrxValue = null;
	        	IOtherBank otherBank = new OBOtherBank();
	        	
	        	ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				String loginUser = user.getLoginID();
	        	
	        	otherBankTrxValue = getOtherBankProxyManager().getOtherBankTrxValue(Long.parseLong(id));
	        	otherBank = otherBankTrxValue.getOtherBank();
	            flag = getOtherBankProxyManager().checkOtherBranchById(Long.parseLong(id));
	            
	            String branchCode = (String) map.get("BranchCode");
				String branchName = (String) map.get("BranchName");
				String state = (String) map.get("State");
				String city = (String) map.get("City");
				
				if(otherBankTrxValue.getStatus().equals("PENDING_UPDATE") || otherBankTrxValue.getStatus().equals("PENDING_DELETE") 
						|| otherBankTrxValue.getStatus().equals("DRAFT")
						|| otherBankTrxValue.getStatus().equals("REJECTED")){
					resultMap.put("wip", "wip");
				}
				
	            SearchResult OtherBranchList = getOtherBankProxyManager().getOtherBranchList(branchCode, branchName,state,city,Long.parseLong(id));
				resultMap.put("otherBranchList", OtherBranchList);
	            

				LimitDAO limitDao = new LimitDAO();
						try {
						String migratedFlag = "N";	
						boolean status = false;	
						 status = limitDao.getCAMMigreted("CMS_OTHER_BANK",otherBank.getId(),"ID");
						
						if(status)
						{
							migratedFlag= "Y";
						}
						resultMap.put("migratedFlag", migratedFlag);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
	            resultMap.put("event", event);
	            resultMap.put("flag", Boolean.toString(flag));
	            resultMap.put("OtherBankObj", otherBank);
	            resultMap.put("IOtherBankTrxValue", otherBankTrxValue);
	            resultMap.put("loginUser",loginUser);
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
	        return returnMap;
	    }

	}




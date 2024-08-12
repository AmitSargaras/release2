package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
 * This command Shows the other bank branch present for branch id
 * $Author: Dattatray Thorat 
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23 
 */

public class ViewOtherBranchByIndexCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IOtherBranchProxyManager otherBranchProxyManager;

	
	/**
	 * @return the otherBranchProxyManager
	 */
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	/**
	 * @param otherBranchProxyManager the otherBranchProxyManager to set
	 */
	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	public ViewOtherBranchByIndexCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"BranchId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"startIndx", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"OtherBranchObj", "com.integrosys.cms.ui.otherbranch.OtherBankForm", FORM_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
	            {"startIndx", "java.lang.String", REQUEST_SCOPE}
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
	        	String id = (String) map.get("BranchId");
	        	String event = (String) map.get("event");
	        	String startIndx = (String) map.get("startIndx");
	        	IOtherBranch otherBranch = new OBOtherBranch();
	        	
	        	IOtherBankBranchTrxValue otherBranchTrxValue = getOtherBranchProxyManager().getOtherBranchTrxValue(Long.parseLong(id));
	        	otherBranch = otherBranchTrxValue.getOtherBranch();
	        	
	        	LimitDAO limitDao = new LimitDAO();
	    		try {
	    		String migratedFlag = "N";	
	    		boolean status = false;	
	    		 status = limitDao.getCAMMigreted("CMS_OTHER_BANK_BRANCH",otherBranch.getId(),"ID");
	    		
	    		if(status)
	    		{
	    			migratedFlag= "Y";
	    		}
	    		resultMap.put("migratedFlag", migratedFlag);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	
	        	resultMap.put("startIndx", startIndx);
	            resultMap.put("event", event);
	            resultMap.put("OtherBranchObj", otherBranch);
	            resultMap.put("IOtherBankBranchTrxValue", otherBranchTrxValue);
	        
	        } catch (OtherBranchException obe) {
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


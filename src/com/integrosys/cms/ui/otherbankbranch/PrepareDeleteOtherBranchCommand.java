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
 * This command Prepares the other bank branch before deleting
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23
 */
public class PrepareDeleteOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IOtherBranchProxyManager otherBranchProxyManager;

	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	public PrepareDeleteOtherBranchCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"BranchId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"OtherBranchObj", "com.integrosys.cms.app.otherbranch.bus.OBOtherBranch", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
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
	            IOtherBranch OtherBranch = new OBOtherBranch();
	            IOtherBankBranchTrxValue otherBankBranchTrxValue = null;
	            
	            OtherBranch= getOtherBranchProxyManager().getOtherBranchById(Long.parseLong(id));
	            otherBankBranchTrxValue = getOtherBranchProxyManager().getOtherBranchTrxValue(Long.parseLong(id));
	            
	            if(otherBankBranchTrxValue.getStatus().equals("PENDING_UPDATE") || otherBankBranchTrxValue.getStatus().equals("PENDING_DELETE") 
						|| otherBankBranchTrxValue.getStatus().equals("DRAFT")
						|| otherBankBranchTrxValue.getStatus().equals("REJECTED")){
					resultMap.put("wip", "wip");
				}
	            
	            LimitDAO limitDao = new LimitDAO();
	    		try {
	    		String migratedFlag = "N";	
	    		boolean status = false;	
	    		 status = limitDao.getCAMMigreted("CMS_OTHER_BANK_BRANCH",OtherBranch.getId(),"ID");
	    		
	    		if(status)
	    		{
	    			migratedFlag= "Y";
	    		}
	    		resultMap.put("migratedFlag", migratedFlag);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	            
	            resultMap.put("OtherBranchObj", OtherBranch);
	            resultMap.put("event", event);
	            resultMap.put("IOtherBankBranchTrxValue", otherBankBranchTrxValue);
	        
	            
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




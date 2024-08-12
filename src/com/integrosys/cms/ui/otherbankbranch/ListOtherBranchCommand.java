package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;

/**
 * This command lists the non-expired diary items belonging to a team $Author:
 * jtan $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/30 11:32:23 $ Tag: $Name: $
 */
public class ListOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {

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

	public ListOtherBranchCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"searchType", "java.lang.String", REQUEST_SCOPE},
	            {"searchVal", "java.lang.String", REQUEST_SCOPE}
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"OtherBranchList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
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
	        	String searchType = (String) map.get("searchType");
	        	String searchVal = (String) map.get("searchVal");
	            SearchResult OtherBranchList = new SearchResult();
	            OtherBranchList= getOtherBranchProxyManager().getOtherBranchList(searchType,searchVal);
	            resultMap.put("OtherBranchList", OtherBranchList);
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




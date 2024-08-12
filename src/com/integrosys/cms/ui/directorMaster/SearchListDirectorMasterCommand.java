package com.integrosys.cms.ui.directorMaster;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;

/**
 * Purpose : This command search the Director Master selected for search 
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public class SearchListDirectorMasterCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IDirectorMasterProxyManager directorMasterProxy;

	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
	}

	public SearchListDirectorMasterCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				 {"searchBy", "java.lang.String", REQUEST_SCOPE},
	                {"searchText", "java.lang.String", REQUEST_SCOPE}
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"directorMasterList", "java.util.ArrayList", REQUEST_SCOPE},
	               
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
	        try {
	        	String searchBy=(String) map.get("searchBy");
	        	String searchText=(String) map.get("searchText");
	        	SearchResult directorMasterList= (SearchResult)  getDirectorMasterProxy().getAllActual(searchBy, searchText);
	             resultMap.put("directorMasterList", directorMasterList);
	        }catch (DirectorMasterException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			} catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
}




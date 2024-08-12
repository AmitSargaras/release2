package com.integrosys.cms.ui.directorMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;


/**
 * Purpose : This command list the  all Director Master
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class ListDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {

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

	public ListDirectorMasterCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				 {"searchBy", "java.lang.String", REQUEST_SCOPE},
	             {"searchText", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 {"event", "java.lang.String", REQUEST_SCOPE}
				 
		});
	}

	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"directorMasterList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	                {"event", "java.lang.String", SERVICE_SCOPE},
	                {"event", "java.lang.String", REQUEST_SCOPE},
	                {"searchBy", "java.lang.String", REQUEST_SCOPE},
		             {"searchText", "java.lang.String", REQUEST_SCOPE},					 
	                { "startIndex", "java.lang.String", REQUEST_SCOPE },
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
	        	String event=(String) (map.get("event"));
	        	String startIdx = (String) map.get("startIndex");
	        	String searchBy=(String) map.get("searchBy");
	        	String searchText=(String) map.get("searchText");
	            SearchResult directorMasterList = null;
	            if( event.equals("maker_list_directorMaster") || event.equals("checker_list_directorMaster") )
	            	directorMasterList = (SearchResult)  getDirectorMasterProxy().getAllDirectorMaster();
	            else{
	            	if( searchText!= null && ! searchText.trim().equals("") ){
		    			boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchText);
		    			if( searchTextFlag == true){
		    				exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
		    				directorMasterList = (SearchResult)  getDirectorMasterProxy().getAllDirectorMaster();
		    			}
		    			else		    		
		    				directorMasterList= (SearchResult)  getDirectorMasterProxy().getAllActual(searchBy, searchText);
	            	}
	            }
	            
	            resultMap.put("directorMasterList", directorMasterList);
	            resultMap.put("event", event);	            
	            resultMap.put("searchBy", searchBy);
	            resultMap.put("searchText", searchText);	            
	            resultMap.put("startIndex", startIdx);
	        }catch (DirectorMasterException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException("Internal error while processing."));
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	        return returnMap;
	    }

	}




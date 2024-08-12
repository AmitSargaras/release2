package com.integrosys.cms.ui.manualinput.partygroup;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 *$Author: Bharat Waghela $ Command for Maker to list Party group
 */
public class ListPartyGroupCmd extends AbstractCommand implements
		ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private IPartyGroupProxyManager partyGroupProxy;

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	public ListPartyGroupCmd() {

	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "type", "java.lang.String", REQUEST_SCOPE },
			{ "text", "java.lang.String", REQUEST_SCOPE } 
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "partyGroupList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } 
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		int stindex = 0;
		String startIndex = (String) map.get("startIndex");
		
		try {
			
			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
			CMSTrxSearchCriteria globalSearch = (CMSTrxSearchCriteria) map
					.get(IGlobalConstant.GLOBAL_CMSTRXSEARCHCRITERIA_OBJ);

			CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
			boolean isFormScope = false;

			if (StringUtils.isBlank(startIndex)) {
				if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
					stindex = 0;
					startIndex = String.valueOf(stindex);
					resultMap.put("startIndex", startIndex);
				}
				else {
					stindex = Integer.parseInt(globalStartIndex);
					startIndex = globalStartIndex;
					resultMap.put("startIndex", startIndex);
				}
			}
			else {
				//stindex = Integer.parseInt(startIndex);	/* Commented by Sandeep Shinde as no need to Parse here ( Throws Exception)*/
				resultMap.put("startIndex", startIndex);
			}

			String event = (String) (map.get("event"));
			String type = (String) map.get("type");
			String searchText = (String) map.get("text");
			SearchResult PartyGroupList = new SearchResult();
			
			if( searchText!= null && ! searchText.trim().equals("") ){
				boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchText);
				if( searchTextFlag == true){
					exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
					searchText= "";
				}				
			}			
			PartyGroupList = getPartyGroupProxy().getPartyList(type, searchText);
			
			resultMap.put("partyGroupList", PartyGroupList);
			resultMap.put("startIndex", startIndex);
			resultMap.put("event", event);
		} catch (PartyGroupException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(
					"Internal error while processing."));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
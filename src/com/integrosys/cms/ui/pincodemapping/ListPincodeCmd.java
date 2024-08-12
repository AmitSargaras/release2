package com.integrosys.cms.ui.pincodemapping;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ListPincodeCmd extends AbstractCommand implements
ICommonEventConstant{

	private IPincodeMappingProxyManager pincodeMappingProxy;
	
	public IPincodeMappingProxyManager getPincodeMappingProxy() {
		return pincodeMappingProxy;
	}

	public void setPincodeMappingProxy(IPincodeMappingProxyManager pincodeMappingProxy) {
		this.pincodeMappingProxy = pincodeMappingProxy;
	}
	/**
	 * Default Constructor
	 */
	public ListPincodeCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "text", "java.lang.String", REQUEST_SCOPE },
			{ "type", "java.lang.String", REQUEST_SCOPE } 
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
			{ "pincodeMappingList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } 
			});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		
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
				//stindex = Integer.parseInt(startIndex);	
				resultMap.put("startIndex", startIndex);
			}

			String event = (String) (map.get("event"));
			String type = (String) map.get("type");
			String searchText = (String) map.get("text");
			SearchResult PincodeMappingList = new SearchResult();
			
			if( searchText!= null && ! searchText.trim().equals("") && type!= null && ! type.trim().equals("") ){
				boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(searchText);
				if( searchTextFlag == true){
					exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter","StatePincodeMapping"));
					searchText= "";
				}	
				
			}else {
				if(searchText!= null && ! searchText.trim().equals(""))
					{
						exceptionMap.put("searchTextFilterError", new ActionMessage("error.string.searchTextFilter"));
						type = null;
						searchText = null;
					}else if(type!= null && ! type.trim().equals(""))
						{
							exceptionMap.put("searchTextValueError", new ActionMessage("error.string.searchTextValue"));
							searchText = null;
							type = null;
						}
			}
			PincodeMappingList = getPincodeMappingProxy().getPincodeMappingList(type, searchText);
			resultMap.put("pincodeMappingList", PincodeMappingList);
			resultMap.put("startIndex", startIndex);
			resultMap.put("event", event);
		} catch (PincodeMappingException ex) {
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
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	
}

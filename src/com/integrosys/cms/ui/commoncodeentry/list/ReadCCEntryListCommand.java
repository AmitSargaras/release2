/**
 * CommonCodeParamEditCommand.java
 *
 * Created on January 29, 2007, 6:00 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchCriteria;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchResultItem;
import com.integrosys.cms.app.commoncode.bus.ICommonCodeType;
import com.integrosys.cms.app.commoncode.proxy.CommonCodeTypeManagerFactory;
import com.integrosys.cms.app.commoncode.proxy.ICommonCodeTypeProxy;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntries;
import com.integrosys.cms.app.commoncodeentry.proxy.CommonCodeEntriesProxyManagerFactory;
import com.integrosys.cms.app.commoncodeentry.trx.ICommonCodeEntriesTrxValue;
import com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class ReadCCEntryListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ CommonCodeEntryConstant.SELECTED_CATEGORY_CODE_ID, "java.lang.String", REQUEST_SCOPE },
				{ "categoryIdSession", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "go", "java.lang.String", REQUEST_SCOPE },
				{ "codeDescription", "java.lang.String", REQUEST_SCOPE },
				{ "codeValue", "java.lang.String", REQUEST_SCOPE },
				{ "codeDescriptionSession", "java.lang.String", SERVICE_SCOPE },
				{ "codeValueSession", "java.lang.String", SERVICE_SCOPE },
				{ "IsMaintainRef", "java.lang.String", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "categoryIdSession", "java.lang.String", SERVICE_SCOPE },
				{ CommonCodeEntryConstant.OFFSET, "java.lang.Integer", SERVICE_SCOPE },
				{ "commonCodeType", "com.integrosys.cms.app.commoncode.bus.ICommonCodeType", SERVICE_SCOPE },
				{ "codeDescriptionSession", "java.lang.String", SERVICE_SCOPE },
				{ "codeValueSession", "java.lang.String", SERVICE_SCOPE },
				{ "codeDescription", "java.lang.String", SERVICE_SCOPE },
				{ "codeValue", "java.lang.String", SERVICE_SCOPE },
				{ "isViewOnlyCommonCode", String.class.getName() , REQUEST_SCOPE },
				{ CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX,
				"com.integrosys.cms.app.commoncodeentry.trx.OBCommonCodeEntriesTrxValue", SERVICE_SCOPE }, };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		try {		
			String event=(String)map.get("event");
			String go=(String)map.get("go");
			String codeDescription=(String)map.get("codeDescription");
			String codeValue=(String)map.get("codeValue");
			String codeDescriptionSession=(String)map.get("codeDescriptionSession");
			String codeValueSession=(String)map.get("codeValueSession");
			// remove all values from session if common code is freshly entered
						if((event.equals("maker_edit_common_code_params_edit")||event.equals("read"))&&go==null)
							codeDescriptionSession=codeValueSession=null;
						//--> END removing values from session.
						
				// if go button is clicked then put values in session
				if(go!=null){
				if(go.equalsIgnoreCase("y")){
					codeDescriptionSession=codeDescription;
					codeValueSession=codeValue;
				}/*else{
					valuationAgencyCodeSession=valuationAgencyCode;
					valuationAgencyNameSession=valuationAgencyName;
				}*/
				}
				
				// get values from session.
				codeDescription=codeDescriptionSession;
				codeValue=codeValueSession;
			
			String categoryId="";
			String categoryIdSession="";
			if(null!=map.get(CommonCodeEntryConstant.SELECTED_CATEGORY_CODE_ID)){
				categoryId=(String)map.get(CommonCodeEntryConstant.SELECTED_CATEGORY_CODE_ID);
	        categoryIdSession=categoryId;
			}
			else{
				categoryIdSession=(String)map.get("categoryIdSession");
				categoryId=categoryIdSession;
			}
			long categoryID=0;
			if(null!=categoryId)
			categoryID=Long.parseLong(categoryId);
			DefaultLogger.debug(this, "retrieve from db - categoryID:" + categoryID);
			
			String isMaintainRef = (String) map.get("IsMaintainRef");
			
			ICommonCodeTypeProxy proxy = CommonCodeTypeManagerFactory.getCommonCodeTypeProxy();
			CommonCodeTypeSearchCriteria criteria = new CommonCodeTypeSearchCriteria();
			criteria.setCategoryID(categoryID);
			
			if (ICMSConstant.TRUE_VALUE.equals(isMaintainRef))
				criteria.setMaintainRef(true);
			
			SearchResult searchResult = proxy.getCategoryList(criteria);
			ICommonCodeType ccType = null;
			if (searchResult != null) {
				List resultList = (List)searchResult.getResultList();
				if (!resultList.isEmpty())
					ccType = (ICommonCodeType)((CommonCodeTypeSearchResultItem)resultList.get(0)).getCommonCodeType();
			}
			
			String isViewOnlyCommonCode = ICMSConstant.NO; 
			String viewOnlyCommonCodesStr = PropertyManager.getValue("common.code.view.only");
			if(StringUtils.isNotBlank(viewOnlyCommonCodesStr) && ccType!=null) {
				String[] viewOnlyCommonCodes =  viewOnlyCommonCodesStr.split(",");
				if(!ArrayUtils.isEmpty(viewOnlyCommonCodes)) {
					List<String> viewOnlyCommonCodesList = Arrays.asList(viewOnlyCommonCodes);
					if(viewOnlyCommonCodesList.contains(ccType.getCommonCategoryCode()))
						isViewOnlyCommonCode = ICMSConstant.YES;
				}
			}
			
			ICommonCodeEntriesTrxValue trxValue = null;
			if(null==codeDescription && null==codeValue)
			trxValue = getCommonCodeEntriesTrxValue(categoryID);
			else if(null!=codeDescription||null!=codeValue)
				trxValue = getEntryValues(categoryID,codeDescription,codeValue);
			if (!ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus())&&!"read".equals((String)map.get("event"))) {
				exceptionMap.put(CommonCodeEntryConstant.WORK_IN_PROGRESS,new ActionMessage (CommonCodeEntryConstant.WORK_IN_PROGRESS));
				exceptionMap.put("stop",new ActionMessage ("stop"));
			}
			else {
				MaintainCCEntryUtil.sortCCEntryList(trxValue);
			
				if ("Y".equals(isMaintainRef)) {
					trxValue.setTrxReferenceID(MaintainCCEntryUtil.REF_ID);
				}
				else {
					trxValue.setTrxReferenceID("");
				}
				result.put("codeDescriptionSession", codeDescriptionSession);
				result.put("codeValueSession", codeValueSession);
				result.put("categoryIdSession", categoryIdSession);
				result.put(CommonCodeEntryConstant.COMMON_CODE_OB_ENTRIES_TRX, trxValue);
				result.put("commonCodeType", ccType);

				result.put(CommonCodeEntryConstant.OFFSET, CommonCodeEntryConstant.INITIAL_OFFSET);
				
				result.put("isViewOnlyCommonCode", isViewOnlyCommonCode);
				
				
			}

		}
		catch (Exception e) {
			DefaultLogger.error(this,e.getMessage(),e);
			throw new CommandProcessingException(e.getMessage());
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

	private ICommonCodeEntriesTrxValue getCommonCodeEntriesTrxValue(long categoryID) throws Exception {
		ICommonCodeEntriesTrxValue trxValue = null;

		try {			
			trxValue = CommonCodeEntriesProxyManagerFactory.getICommonCodeEntriesProxy().getCategoryId(categoryID);
			trxValue.setStagingCommonCodeEntries(trxValue.getCommonCodeEntries());
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,
					"Possible new common code category detected , providing empty data for it's entries");
			// could mean a common code category has been created but no
			// transaction data for common code entry
			trxValue = createCommonCodeEntriesTrxValue(categoryID);
		}
		return trxValue;
	}
	
	private ICommonCodeEntriesTrxValue getEntryValues(long categoryID,String desc,String value) throws Exception {
		ICommonCodeEntriesTrxValue trxValue = null;

		try {			
			trxValue = CommonCodeEntriesProxyManagerFactory.getICommonCodeEntriesProxy().getEntryValues(categoryID,desc,value);
			trxValue.setStagingCommonCodeEntries(trxValue.getCommonCodeEntries());
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this,
					"Possible new common code category detected , providing empty data for it's entries");
			// could mean a common code category has been created but no
			// transaction data for common code entry
			trxValue = createCommonCodeEntriesTrxValue(categoryID);
		}
		return trxValue;
	}

	private ICommonCodeEntriesTrxValue createCommonCodeEntriesTrxValue(long categoryID) {
		ICommonCodeEntriesTrxValue trxValue = new OBCommonCodeEntriesTrxValue();
		OBCommonCodeEntries ccEntries = new OBCommonCodeEntries();
		
		ccEntries.setCategoryCodeId(categoryID);
		ccEntries.setReferenceID(String.valueOf(categoryID));
		ccEntries.setEntries(new ArrayList());
		ccEntries.setTransactionType((ICMSConstant.COMMON_CODE_ENTRY_INSTANCE_NAME));
		trxValue.setCommonCodeEntries(ccEntries);
		trxValue.setStagingCommonCodeEntries(ccEntries);
		trxValue.setTransactionID(String.valueOf(ICMSConstant.LONG_INVALID_VALUE));
		trxValue.setTransactionType(ICMSConstant.COMMON_CODE_ENTRY_INSTANCE_NAME);
		trxValue.setFromState(ICMSConstant.STATE_ACTIVE);
		trxValue.setReferenceID(String.valueOf(categoryID));
		return trxValue;
	}

}
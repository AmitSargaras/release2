package com.integrosys.cms.ui.creditriskparam.internallimit.list;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.InternalLimitParamComparator;
import com.integrosys.cms.ui.creditriskparam.internallimit.InternalLimitCommand;

/**
 * 
 * Copyright Integro Technologies Pte Ltd $Header$
 * CommonCodeEntryPaginateCommand.java Created on July 23, 2007, 11:00 AM
 * Purpose: Description:
 * 
 * @author $OEM$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class SavePage2SessionCommand extends InternalLimitCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "theILParamList", "java.util.ArrayList", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "theILParamFullList", "java.util.ArrayList", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "ERROR_ENTRY_MAP", "java.util.HashMap", REQUEST_SCOPE },
				{ "theILParamFullList", "java.util.ArrayList", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		List ilParamFullList = (List) map.get("theILParamFullList");
		List ilParamList = (List) map.get("theILParamList");
		Integer offset = (Integer) map.get("offset");

		// HashMap errorMap =
		// MaintainCCEntryUtil.isDuplicateEntry(entryList,offset,trxValue);
		// if(errorMap.size()>0){
		// exceptionMap.put("entryCode",new ActionMessage
		// ("error.entries.duplicate"));
		// //exceptionMap.put("stop",new ActionMessage
		// ("error.entries.duplicate"));
		// DefaultLogger.debug(this,"No of duplicated
		// entries:"+errorMap.size());
		// result.put("ERROR_ENTRY_MAP",errorMap);
		// }else{
		updateCurrentPage2EntryList(ilParamFullList, offset, ilParamList);
		Collections.sort(ilParamList, new InternalLimitParamComparator());
		resultMap.put("theILParamFullList", ilParamList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	public void updateCurrentPage2EntryList(List allEntryList, Integer offset,
			List aPageList) {
		if (aPageList == null || aPageList.size() == 0) {
			return;
		}
		for (int index = 0; index < aPageList.size(); index++) {
			IInternalLimitParameter src = (IInternalLimitParameter) aPageList
					.get(index);
			IInternalLimitParameter dest = (IInternalLimitParameter) allEntryList
					.get(index + offset.intValue());
			//dest.setDescription(src.getDescription());
			dest.setInternalLimitPercentage(src.getInternalLimitPercentage());
			//dest.setCurrency(src.getCurrency());
			dest.setCapitalFundAmount(src.getCapitalFundAmount());
			dest.setStatus(src.getStatus());
		}
	}

}
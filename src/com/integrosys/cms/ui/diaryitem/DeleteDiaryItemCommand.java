/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/UpdateDiaryItemCommand.java,v 1.3 2004/05/28 06:21:10 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;

/**
 * This command delete the details of a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/05/28 06:21:10 $ Tag: $Name: $
 */

public class DeleteDiaryItemCommand extends DiaryItemsCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "itemId", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
		String strItemID = (String) map.get("itemId");
		diaryItemJdbc.deleteDiaryItem("Y",Long.parseLong(strItemID));

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}


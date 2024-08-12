/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/item/UpdateTitleDocumentItemCommand.java,v 1.4 2004/10/14 04:09:29 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.TitleDocumentComparator;
import com.integrosys.cms.ui.commodityglobal.CommodityGlobalConstants;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/10/14 04:09:29 $ Tag: $Name: $
 */

public class UpdateTitleDocumentItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "titleDocItemObj", "com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument",
						FORM_SCOPE }, { "type", "java.lang.String", REQUEST_SCOPE },
				{ "titleDocumentObj", "java.util.HashMap", SERVICE_SCOPE },
				{ "documentDesc", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "titleDocumentObj", "java.util.HashMap", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		int index = Integer.parseInt((String) map.get("indexID"));
		HashMap titleDocObj = (HashMap) map.get("titleDocumentObj");

		boolean duplicated = false;
		Collection docList = (Collection) titleDocObj.get("stageTitleDocNeg");
		Iterator itr = docList.iterator();
		String description = ((String) map.get("documentDesc")).trim().toUpperCase();
		int i = 0;
		while (itr.hasNext() && !duplicated) {
			ITitleDocument titleObj = (ITitleDocument) itr.next();
			if ((index != i) && description.equals(titleObj.getName().trim().toUpperCase())) {
				duplicated = true;
			}
			i++;
		}
		if (!duplicated) {
			docList = (Collection) titleDocObj.get("stageTitleDocNonNeg");
			itr = docList.iterator();
			i = 0;
			while (itr.hasNext() && !duplicated) {
				ITitleDocument titleObj = (ITitleDocument) itr.next();
				if ((index != i) && description.equals(titleObj.getName().trim().toUpperCase())) {
					duplicated = true;
				}
				i++;
			}
		}
		if (duplicated) {
			exceptionMap.put("documentDesc", new ActionMessage("error.titledocument.duplicate"));
		}
		else {
			ITitleDocument obj = (ITitleDocument) map.get("titleDocItemObj");
			String type = (String) map.get("type");

			List titleDocList = new ArrayList();
			if (type.equals(CommodityGlobalConstants.NEGOTIABLE_DOCUMENT)) {
				titleDocList = (ArrayList) titleDocObj.get("stageTitleDocNeg");
			}
			else {
				titleDocList = (ArrayList) titleDocObj.get("stageTitleDocNonNeg");
			}

			titleDocList.set(index, obj);
			Collections.sort(titleDocList, new TitleDocumentComparator(TitleDocumentComparator.BY_NAME));
			/*
			 * ITitleDocument[] tempList =
			 * (ITitleDocument[])titleDocList.toArray(new ITitleDocument[0]);
			 * Arrays.sort(tempList, new
			 * TitleDocumentComparator(TitleDocumentComparator.BY_NAME));
			 * titleDocList = Arrays.asList(tempList);
			 */

			if (type.equals(CommodityGlobalConstants.NEGOTIABLE_DOCUMENT)) {
				titleDocObj.put("stageTitleDocNeg", titleDocList);
			}
			else {
				titleDocObj.put("stageTitleDocNonNeg", titleDocList);
			}
			result.put("titleDocumentObj", titleDocObj);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/list/ReadTitleDocumentListCommand.java,v 1.6 2005/10/24 01:44:24 wltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.TitleDocumentComparator;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue;
import com.integrosys.cms.app.commodity.main.trx.titledocument.OBTitleDocumentTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/24 01:44:24 $ Tag: $Name: $
 */

public class ReadTitleDocumentListCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "titleDocumentTrxValue",
						"com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue", SERVICE_SCOPE },
				{ "titleDocumentObj", "java.util.HashMap", SERVICE_SCOPE }, });
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

		String event = (String) map.get("event");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		ITitleDocumentTrxValue trxValue = new OBTitleDocumentTrxValue();
		if (!event.equals(EVENT_PREPARE) && !event.equals(EVENT_READ)) {
			String trxID = (String) map.get("trxID");
			try {
				trxValue = proxy.getTitleDocumentByTrxID(ctx, trxID);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "error at proxy get Title document by trxID");
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		else {
			try {
				trxValue = proxy.getTitleDocumentTrxValue(ctx);
				if (event.equals(EVENT_PREPARE) && !trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
					result.put("wip", "wip");
				}
				if (trxValue.getTitleDocument() != null) {
					ITitleDocument[] staging = (ITitleDocument[]) AccessorUtil.deepClone(trxValue.getTitleDocument());
					trxValue.setStagingTitleDocument(staging);
				}
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "error at proxy get Title Document Transaction Value");
				e.printStackTrace();
				throw new CommandProcessingException(e.getMessage());
			}
		}
		if (trxValue.getTitleDocument() != null) {
			Arrays.sort(trxValue.getTitleDocument(), new TitleDocumentComparator(TitleDocumentComparator.BY_NAME));
		}
		if (trxValue.getStagingTitleDocument() != null) {
			Arrays.sort(trxValue.getStagingTitleDocument(),
					new TitleDocumentComparator(TitleDocumentComparator.BY_NAME));
		}
		result.put("titleDocumentTrxValue", trxValue);

		HashMap titleDocObj = new HashMap();
		ArrayList actTitleDocNonNeg = new ArrayList();
		ArrayList actTitleDocNeg = new ArrayList();
		ITitleDocument[] actTitleDocList = trxValue.getTitleDocument();
		if (actTitleDocList != null) {
			for (int i = 0; i < actTitleDocList.length; i++) {
				if (actTitleDocList[i].getType().equals(ITitleDocument.NEGOTIABLE)) {
					actTitleDocNeg.add(actTitleDocList[i]);
				}
				else if (actTitleDocList[i].getType().equals(ITitleDocument.NON_NEGOTIABLE)) {
					actTitleDocNonNeg.add(actTitleDocList[i]);
				}
			}
		}
		titleDocObj.put("actualTitleDocNonNeg", actTitleDocNonNeg);
		titleDocObj.put("actualTitleDocNeg", actTitleDocNeg);

		ArrayList stageTitleDocNonNeg = new ArrayList();
		ArrayList stageTitleDocNeg = new ArrayList();
		ITitleDocument[] stageTitleDocList = trxValue.getStagingTitleDocument();
		if (stageTitleDocList != null) {
			for (int i = 0; i < stageTitleDocList.length; i++) {
				if (stageTitleDocList[i].getType().equals(ITitleDocument.NEGOTIABLE)) {
					stageTitleDocNeg.add(stageTitleDocList[i]);
				}
				else if (stageTitleDocList[i].getType().equals(ITitleDocument.NON_NEGOTIABLE)) {
					stageTitleDocNonNeg.add(stageTitleDocList[i]);
				}
			}
		}

		titleDocObj.put("stageTitleDocNonNeg", stageTitleDocNonNeg);
		titleDocObj.put("stageTitleDocNeg", stageTitleDocNeg);

		result.put("titleDocumentObj", titleDocObj);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/PrepareDocumentCommandHelper.java,v 1.8 2005/10/05 10:43:31 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.document;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.OrdinalNumberList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/10/05 10:43:31 $
 * Tag: $Name:  $
 */
/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareDocumentCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "priorityRankingIDs", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "priorityRankingValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) {
		TimeFreqList freqList = TimeFreqList.getInstance();
		OrdinalNumberList numberList = null;
		Collection priorityRankingIDs = null;
		Collection priorityRankingValues = null;

		result.put("freqValue", freqList.getTimeFreqValue());
		result.put("freqID", freqList.getTimeFreqID());
		HashMap limitMaplist = (HashMap) map.get("limitMap");
		if (limitMaplist!=null) {
			numberList = OrdinalNumberList.getInstance(limitMaplist.size());
			priorityRankingIDs = numberList.getOrdinalNumberValue();
			priorityRankingValues = numberList.getOrdinalNumberLabel();
			DefaultLogger.debug("PrepareDocumentComamnd",
					"VVVVVVVVVVVVVVVVVVVIIIIIIIIIIIIIIIIIIII before 1st arraycopy: FreqID length: " + limitMaplist.size());
		}
		result.put("priorityRankingIDs", priorityRankingIDs);
		result.put("priorityRankingValues", priorityRankingValues);

	}
}

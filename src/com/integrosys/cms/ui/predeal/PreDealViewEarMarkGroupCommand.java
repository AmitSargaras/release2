/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealViewEarMarkGroupCommand
 *
 * Created on 5:05:50 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.bus.EarMarkSearchCirteria;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 29, 2007 Time: 5:05:50 PM
 */
public class PreDealViewEarMarkGroupCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.FEED_ID, "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.EAR_MARK_GROUP, "java.lang.Object", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String feedId = (String) hashMap.get(PreDealConstants.FEED_ID);
		EarMarkSearchCirteria cirteria = new EarMarkSearchCirteria();
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		cirteria.setFeedId(feedId);

		try {
			result.put(PreDealConstants.EAR_MARK_GROUP, proxy.viewEarGroup(cirteria).getResultList());
			result.put(PreDealConstants.PRE_DEAL_RECORD, proxy.searchByFeedId(feedId));
		}
		catch (PreDealException e) {
			DefaultLogger.debug(this, "Error search ear group item for feed id : " + feedId, e);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}
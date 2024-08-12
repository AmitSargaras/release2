/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealSearchCommand
 *
 * Created on 10:57:10 AM
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 27, 2007 Time: 10:57:10 AM
 */
public class PreDealSearchCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.ISIN_CODE, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.COUNTER_NAME, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.RIC, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE, "java.lang.String", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.SEARCH_RESULT, "java.util.Collection", REQUEST_SCOPE },
				{ PreDealConstants.ISIN_CODE, "java.lang.String", SERVICE_SCOPE },
				{ PreDealConstants.COUNTER_NAME, "java.lang.String", SERVICE_SCOPE },
				{ PreDealConstants.RIC, "java.lang.String", SERVICE_SCOPE },
				{ PreDealConstants.STOCK_EXCHANGE_NAME, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE, "java.lang.String", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String counterName = (String) hashMap.get(PreDealConstants.COUNTER_NAME);
		String isinCode = (String) hashMap.get(PreDealConstants.ISIN_CODE);
		String ric = (String) hashMap.get(PreDealConstants.RIC);

		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();
		Collection collection = new ArrayList();

		DefaultLogger.debug(this, "Counter name : " + counterName);
		DefaultLogger.debug(this, "Isin code : " + isinCode);
		DefaultLogger.debug(this, "RIC : " + ric);

		try {
			if (Validator.validateMandatoryField(isinCode)) {
				collection = proxy.searchByIsinCode(isinCode);
			}
			else if (Validator.validateMandatoryField(counterName)) {
				collection = proxy.searchByCounterName(counterName);
			}
			else if (Validator.validateMandatoryField(ric)) {
				collection = proxy.searchByRIC(ric);
			}

			if (collection.size() > 0) {
				Iterator iter = collection.iterator();

				PreDealSearchRecord record = (PreDealSearchRecord) iter.next();

				result.put(PreDealConstants.STOCK_EXCHANGE_NAME, record.getStockExchangeName());
			}

		}
		catch (PreDealException e) {
			DefaultLogger.debug(this, "Error search predeal items", e);
		}

		DefaultLogger.debug(this, "Size of search result/collection : " + collection.size());

		DefaultLogger.debug(this, "Counter name : " + counterName);
		DefaultLogger.debug(this, "Isin code : " + isinCode);
		DefaultLogger.debug(this, "RIC : " + ric);

		result.put(PreDealConstants.SEARCH_RESULT, collection);
		result.put(PreDealConstants.COUNTER_NAME, counterName);
		result.put(PreDealConstants.ISIN_CODE, isinCode);
		result.put(PreDealConstants.RIC, ric);
		result.put(PreDealConstants.PRE_DEAL_SOURCE, null);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}

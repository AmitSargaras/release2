/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ShareCounterListTypeCommand
 *
 * Created on 11:30:37 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamSearchCriteria;
import com.integrosys.cms.app.creditriskparam.CreditRiskParamType;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamGroupException;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.proxy.CreditRiskParamProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.ICreditRiskParamProxy;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 11:30:37 AM
 */
public class MaintainShareCounterListTypeCommand extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return new String[][] { { ShareCounterConstants.GROUP_SUBTYPE, "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { ShareCounterConstants.STOCK_EXCHANGE_TYPE_LIST, "java.lang.Object", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		ICreditRiskParamProxy proxy = CreditRiskParamProxyManagerFactory.getICreditRiskParamProxy();
		CreditRiskParamSearchCriteria criteria = new CreditRiskParamSearchCriteria();
		Collection creditResult = new ArrayList();
		String groupSubType = (String) hashMap.get(ShareCounterConstants.GROUP_SUBTYPE);

		criteria.setCurrentIndex(0); // setting this has no effect actually on
										// the search result ...
		criteria.setNItems(10); // but this only for reference later to come in
								// other command classes

		criteria.setGroupSubType(groupSubType); // this will filter out
												// according to sub type

		try {
			creditResult = proxy.getSearchResultForCriteria(criteria, CreditRiskParamType.SHARE_COUNTER)
					.getResultList();
			prepareDescription(creditResult);
		}
		catch (CreditRiskParamGroupException e) {
			e.printStackTrace();
		}

		DefaultLogger.debug(this, "Size of types is : " + creditResult.size());

		result.put(ShareCounterConstants.STOCK_EXCHANGE_TYPE_LIST, creditResult);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

	private void prepareDescription(Collection creditResult) {

		Iterator iter = creditResult.iterator();

		while (iter.hasNext()) {
			ICreditRiskParamGroup group = (ICreditRiskParamGroup) iter.next();
			String description = CommonDataSingleton.getCodeCategoryLabelByValue(ShareCounterConstants.SHARE_TYPE,
					group.getStockType());
			group.setStockTypeDescription(description);
		}
	}

}

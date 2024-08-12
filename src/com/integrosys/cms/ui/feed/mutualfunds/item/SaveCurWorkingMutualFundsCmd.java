/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class SaveCurWorkingMutualFundsCmd extends MutualFundsCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ MutualFundsItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry", FORM_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			// just map from form to staging limit and save in trxValue object
			IMutualFundsFeedEntry mutualFunds = (IMutualFundsFeedEntry) (map.get(MutualFundsItemForm.MAPPER));
			IMutualFundsFeedGroupTrxValue mutualFundsTrxObj = (IMutualFundsFeedGroupTrxValue) (map.get("mutualFundsFeedGroupTrxValue"));

		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

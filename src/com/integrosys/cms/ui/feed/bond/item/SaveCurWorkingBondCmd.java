/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.feed.bond.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.bond.BondCommand;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SaveCurWorkingBondCmd extends BondCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", SERVICE_SCOPE },
				{ BondItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry", FORM_SCOPE }, });
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
			IBondFeedEntry bond = (IBondFeedEntry) (map.get(BondItemForm.MAPPER));
			IBondFeedGroupTrxValue bondTrxObj = (IBondFeedGroupTrxValue) (map.get("bondFeedGroupTrxValue"));

		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

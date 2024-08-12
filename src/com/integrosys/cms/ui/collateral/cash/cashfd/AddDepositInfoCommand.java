package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.util.HashMap;
import java.util.ArrayList;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.host.stp.common.IStpConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

public class AddDepositInfoCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { {"fdInfoList", "java.util.List", SERVICE_SCOPE}, 
				{"fdInfoIndex", "java.lang.String", REQUEST_SCOPE},
				{ "depositObj", "com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit", SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {  
				{ "form.depositObject", "java.lang.Object", FORM_SCOPE },				
		});
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

		ArrayList depositList = (ArrayList)map.get("fdInfoList");
        String strFdInfoIndex = (String)map.get("fdInfoIndex");
        int depositIndex = -1;
        if (StringUtils.isNotEmpty(strFdInfoIndex)) {
            depositIndex = Integer.parseInt(strFdInfoIndex);
		
            ICashDeposit fdInfo = (ICashDeposit)depositList.get(depositIndex);
            ICashDeposit deposit = (ICashDeposit)map.get("depositObj");
            deposit.setDepositCcyCode(fdInfo.getDepositCcyCode());
            deposit.setDepositAmount(fdInfo.getDepositAmount());
            deposit.setDepositCcyCode(fdInfo.getDepositAmount().getCurrencyCode());
            deposit.setDepositMaturityDate(fdInfo.getDepositMaturityDate());
            deposit.setDepositReceiptNo(fdInfo.getDepositReceiptNo());
            deposit.setDepositRefNo(fdInfo.getDepositRefNo());
            deposit.setTenure(fdInfo.getTenure());
            deposit.setTenureUOM(fdInfo.getTenureUOM());
            deposit.setHasValidated(true);
            result.put("form.depositObject", deposit);
        }
        else {
            exceptionMap.put("infoError", new ActionMessage("error.no.account.selected", ""));
        }
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}			
}

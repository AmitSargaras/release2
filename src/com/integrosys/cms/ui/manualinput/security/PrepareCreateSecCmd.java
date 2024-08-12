/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareCreateSecCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "inrValue", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "dispFieldMapper", "java.lang.Object", FORM_SCOPE },
				{ "secTrxObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
						{ "inrValue", "java.lang.String", REQUEST_SCOPE },
						{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
						{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
						{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
						{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");

			ICommonUser user = (ICommonUser) (map.get(IGlobalConstant.USER));
			OBCollateralTrxValue secTrxValue = new OBCollateralTrxValue();
			OBCollateral newCol = new OBCollateral();
			newCol.setCollateralLocation(user.getCountry());
			/*Commented and added newCol.setCurrencyCode("INR") for HDFC requirement*/
//			newCol.setCurrencyCode(CurrencyList.getInstance().getCurrencyCodeByCountry(user.getCountry()));
			newCol.setCurrencyCode("INR");
			secTrxValue.setStagingCollateral(newCol);
			secTrxValue.setCustomerID(0);

			result.put("fundedAmount", map.get("fundedAmount"));
			result.put("nonFundedAmount", map.get("nonFundedAmount"));
			result.put("memoExposer", map.get("memoExposer"));
			result.put("sanctionedLimit", map.get("sanctionedLimit"));
			result.put("inrValue", map.get("inrValue"));
			result.put("secTrxObj", secTrxValue);
			result.put("dispFieldMapper", secTrxValue);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		result.put(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, null);
		result.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, null);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

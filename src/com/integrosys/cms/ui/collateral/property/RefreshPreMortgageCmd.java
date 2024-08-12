package com.integrosys.cms.ui.collateral.property;

import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
 

public class RefreshPreMortgageCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "preMortgageDate", "java.lang.String", REQUEST_SCOPE }, 
								 { "collateralID", "java.lang.String", REQUEST_SCOPE },});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "preMortgageDataList", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
			String preMortgageDate = (String) (map.get("preMortgageDate"));
			String collateralID = (String) (map.get("collateralID"));
		    DefaultLogger.debug(this, "preMortgageDate:"+preMortgageDate+ " collateralID:"+collateralID);
			
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			if ((preMortgageDate != null) && !preMortgageDate.trim().equals("")) {
				try {
					result.put("preMortgageDataList", collateralDAO.getPreMortgageData(preMortgageDate, collateralID));
				} catch (Exception e) {
					e.printStackTrace();
					throw (new CommandProcessingException(e.getMessage()));
				}
			}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

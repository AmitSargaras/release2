/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/ReturnCollateralCommand.java,v 1.4 2003/09/19 08:49:33 hshii Exp $
 */

package com.integrosys.cms.ui.collateral;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.ICityDAO;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchDAO;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/19 08:49:33 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 12:13:00 PM
 * To change this template use Options | File Templates.
 */
public class CashFillFdCommand extends AbstractCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
				 { "cashDepID", "java.lang.String", REQUEST_SCOPE },
				 {"fdWebServiceFlag", "java.lang.String",REQUEST_SCOPE},
	});
    }
    
    public String[][] getResultDescriptor() {
        return (new String[][]{
        		 { "cashDeposit", "java.lang.String", REQUEST_SCOPE },
        		 //{ "utilizedAmunt", "java.lang.String", REQUEST_SCOPE }
        		 { "utilizedAmunt", "java.lang.String", SERVICE_SCOPE },
        		 {"fdWebServiceFlag", "java.lang.String",REQUEST_SCOPE},
        	});
    }
    /**
     * This method does the Business operations with the HashMap and put the
     * results back into the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
     *          on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
     *          on errors
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String cashDepID = (String) map.get("cashDepID");
		String fdWebServiceFlag = (String) map.get("fdWebServiceFlag");

		if (null != cashDepID || !"".equals(cashDepID)) {
			double totalLienAmount = 0.0;

			DefaultLogger.debug(this, "fdWebServiceFlag:" + fdWebServiceFlag);

			if (fdWebServiceFlag != null
					&& "Y".equalsIgnoreCase(fdWebServiceFlag)) {

				if (null != cashDepID && !("".equals(cashDepID))) {
					totalLienAmount = CollateralDAOFactory.getDAO()
							.getAllTotalLienAmount(cashDepID);

				}
				result.put("utilizedAmunt", String.valueOf(totalLienAmount));
			} else {
				String cashDepositIDString = CollateralDAOFactory.getDAO()
						.fillFD(cashDepID);

				String receiptNo = CollateralDAOFactory.getDAO()
						.getReceiptNoByDepositID(cashDepID);

				if (null != receiptNo && !("".equals(receiptNo))) {
					totalLienAmount = CollateralDAOFactory.getDAO()
							.getAllTotalLienAmount(receiptNo);

				}

				result.put("cashDeposit", cashDepositIDString);
				result.put("utilizedAmunt", String.valueOf(totalLienAmount));
			}
			if (null == fdWebServiceFlag) {
				fdWebServiceFlag = "N"; // when fdWebServiceFlag is null then it
										// is not webservice call
			}
			result.put("fdWebServiceFlag", fdWebServiceFlag);
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}

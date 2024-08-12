/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.OBCollateralPledgor;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.MILeSearchCriteria;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchPledgorDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "leIdType", "java.lang.String", REQUEST_SCOPE },
				{ "leId", "java.lang.String", REQUEST_SCOPE }, { "customerName", "java.lang.String", REQUEST_SCOPE },
				{ "IDType", "java.lang.String", REQUEST_SCOPE }, { "IDNo", "java.lang.String", REQUEST_SCOPE },
				{ "customerList", "java.util.Collection", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "displayList", "java.lang.String", REQUEST_SCOPE },
				{ "customerList", "java.util.Collection", SERVICE_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			String leIdType = (String) (map.get("leIdType"));
			String leId = (String) (map.get("leId"));
			String customerName = (String) (map.get("customerName"));
			String idType = (String) (map.get("IDType"));
			String idNo = (String) (map.get("IDNo"));
			MILeSearchCriteria crit = new MILeSearchCriteria();
			crit.setSourceId(leIdType);
			crit.setLeId(leId);
			crit.setCustomerName(customerName);
			crit.setIDType(idType);
			crit.setIDNo(idNo);
			MISecurityUIHelper helper = new MISecurityUIHelper();
			List l = helper.getSBMISecProxy().searchCustomerForPlgLink(crit);
			List pledgorList = new ArrayList();
			if (l != null) {
				for (int i = 0; i < l.size(); i++) {
					String[] arr = (String[]) (l.get(i));
					ICollateralPledgor newPledgor = new OBCollateralPledgor();
					newPledgor.setLegalID(arr[0]);
					newPledgor.setPledgorName(arr[1]);
					newPledgor.setSourceId(arr[2]);

					newPledgor.setPlgIdNumText(arr[3]);
					newPledgor.setPlgIdTypeCode(arr[4]);
					newPledgor.setPlgIdType(arr[5]);
					newPledgor.setLegalIDSourceCode(arr[6]);
					newPledgor.setLegalIDSource(arr[7]);

					newPledgor.setPledgorRelship("RELATED");
					newPledgor.setPledgorRelshipID("RELATIONSHIP");
					newPledgor.setPledgorStatus(ICMSConstant.HOST_STATUS_INSERT);
					pledgorList.add(newPledgor);
				}
			}
			result.put("displayList", "Y");
			result.put("customerList", pledgorList);
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

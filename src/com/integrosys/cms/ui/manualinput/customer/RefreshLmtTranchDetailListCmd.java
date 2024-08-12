/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.customer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshLmtTranchDetailListCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "referenceId", "java.lang.String", REQUEST_SCOPE },
				{ "facilityName", "java.lang.String", REQUEST_SCOPE }});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "estateType", "java.util.List", REQUEST_SCOPE },
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "facilityName", "java.lang.String", REQUEST_SCOPE },
				{ "commRealEstateType", "java.lang.String", REQUEST_SCOPE },
				{ "prioritySector", "java.lang.String", REQUEST_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String estateType ="";
		String commRealEstateType ="";
		String prioritySector ="";
		
			String serialNo = (String) (map.get("serialNo"));
			String facilityName = (String) (map.get("facilityName"));
			String referenceId = (String) (map.get("referenceId"));
						
			String []str = facilityName.split("-");
			facilityName = str[0].trim();
			
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			try {
				String []tranch = proxy.getLimitTranchListByCustID(facilityName, serialNo); 
				if(tranch!=null && tranch.length > 0){
					estateType = tranch[0];
					commRealEstateType = tranch[1];
					prioritySector = tranch[2];
				}
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		result.put("estateType", estateType);
		result.put("commRealEstateType", commRealEstateType);
		result.put("prioritySector", prioritySector);
		result.put("serialNo", serialNo);
		result.put("facilityName", facilityName);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

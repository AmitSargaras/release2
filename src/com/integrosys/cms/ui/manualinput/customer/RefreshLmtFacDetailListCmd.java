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
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshLmtFacDetailListCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "facilityFor", "java.lang.String", REQUEST_SCOPE },
				{ "referenceId", "java.lang.String", REQUEST_SCOPE },
				{ "facilityName", "java.lang.String", REQUEST_SCOPE }});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "serialNoList", "java.util.List", REQUEST_SCOPE },
				{ "facilityAmount", "java.lang.String", REQUEST_SCOPE },
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "facilityName", "java.lang.String", REQUEST_SCOPE },
				{ "lineNo", "java.lang.String", REQUEST_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String strFacilityAmount ="";
		String strLineNo ="";
		
			String facilityFor = (String) (map.get("facilityFor"));
			String facilityName = (String) (map.get("facilityName"));
			String referenceId = (String) (map.get("referenceId"));
			
			String []str = facilityName.split("-");
			facilityName = str[0].trim();
			
			List serialNoList = new ArrayList();
			
			List lbValList = new ArrayList();
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			try {
				List lmtList = proxy.getLimitSummaryListByCustID(referenceId, facilityName);
				if(lmtList!=null && lmtList.size()>0){
					String label;
					String value;
					for (int i = 0; i < lmtList.size(); i++) {
						LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
						strFacilityAmount = limitSummaryItem.getActualSecCoverage();
						strLineNo = limitSummaryItem.getLineNo();
					}
				}
				
				//---------------------
				List tranch = proxy.getLimitTranchListByFacilityFor(facilityName, facilityFor );
				if(tranch!=null && tranch.size()>0){
					String label;
					String value;
					for (int i = 0; i < tranch.size(); i++) {
						label = (String)tranch.get(i);
						value = (String)tranch.get(i);
						LabelValueBean lvBean = new LabelValueBean(label,value);
						lbValList.add(lvBean);
					}
				}
				
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			serialNoList = CommonUtil.sortDropdown(lbValList);
		
		result.put("lineNo", strLineNo);
	//	result.put("facilityAmount", strFacilityAmount);
		
		//Phase 3 CR:comma separated
		result.put("facilityAmount",UIUtil.formatWithCommaAndDecimal(strFacilityAmount));
		
		result.put("serialNoList", serialNoList);
		
		result.put("facilityName", facilityName);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

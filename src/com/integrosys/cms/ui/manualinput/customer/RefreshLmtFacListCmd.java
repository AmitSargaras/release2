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
public class RefreshLmtFacListCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "facilityFor", "java.lang.String", REQUEST_SCOPE },
				{ "referenceId", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityList", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		
			String facName = (String) (map.get("facilityFor"));
			String referenceId = (String) (map.get("referenceId"));
			
			List facilityList = new ArrayList();
			
			List lbValList = new ArrayList();
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			try {
				if(facName.equals("Capital Market Exposure") || facName.equals("Priority/Non priority Sector") 
						|| facName.equals("Real Estate Exposure")){
					List lmtList = proxy.getLimitListByFacilityFor(referenceId, facName);
					if(lmtList!=null && lmtList.size()>0){
						String label;
						String value;
						for (int i = 0; i < lmtList.size(); i++) {
							LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
							label=limitSummaryItem.getCmsLimitId() + " - " + limitSummaryItem.getProdTypeCode();
							value= limitSummaryItem.getCmsLimitId();
							LabelValueBean lvBean = new LabelValueBean(label,label);
							lbValList.add(lvBean);
						}
					}
				}else{
				
				List lmtList = proxy.getLimitSummaryListByCustID(referenceId);
				if(lmtList!=null && lmtList.size()>0){
					String label;
					String value;
					for (int i = 0; i < lmtList.size(); i++) {
						LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
						label=limitSummaryItem.getCmsLimitId() + " - " + limitSummaryItem.getProdTypeCode();
						value= limitSummaryItem.getCmsLimitId();
						LabelValueBean lvBean = new LabelValueBean(label,label);
						lbValList.add(lvBean);
					}
				}
			}
			} catch (LimitException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			facilityList = CommonUtil.sortDropdown(lbValList);
		
		result.put("facilityList", facilityList);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

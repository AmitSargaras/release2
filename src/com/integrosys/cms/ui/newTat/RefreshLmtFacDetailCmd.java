/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.newTat;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.newTat.bus.OBNewTat;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshLmtFacDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "facName", "java.lang.String", REQUEST_SCOPE },
				{ "facSel", "java.lang.String", REQUEST_SCOPE },
				 { "newTatObj.session", "com.integrosys.cms.app.newTat.bus.OBNewTat", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				 { "newFacilityNameMap", "java.util.Map", SERVICE_SCOPE },
				});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facDetailList", "java.util.List", REQUEST_SCOPE } ,
				{ "serialList", "java.util.List", REQUEST_SCOPE } 
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		OBNewTat tat = (OBNewTat)map.get("newTatObj.session");
		HashMap facilityNameMap = (HashMap)map.get("newFacilityNameMap");
			String facName = (String)facilityNameMap.get((String) (map.get("facName")));
			String facSel = (String) (map.get("facSel"));
			String custID = String.valueOf(cust.getCustomerID());
			String lspApprLmtId="";
			List facDetailList= new ArrayList();
			List facIdList= new ArrayList();
			List serialList = new ArrayList();
			List finalserialList = new ArrayList();
			List lbValList = new ArrayList();
//			System.out.println("custID++"+custID);
			try {
			MILimitUIHelper helper = new MILimitUIHelper();
			SBMILmtProxy proxy = helper.getSBMILmtProxy();
			if ((facName != null) && !facName.trim().equals("")) {
				
				if(facSel!=null && facSel.equals("SYSTEM")){
					List lmtList =proxy.getLimitSummaryListByCustID(tat.getLspLeId());
					for (int i = 0; i < lmtList.size(); i++) {
						LimitListSummaryItemBase itemBase= (LimitListSummaryItemBase) lmtList.get(i); 
						if(itemBase!=null){
							if(itemBase.getProdTypeCode()!=null && !itemBase.getProdTypeCode().trim().equals("")){
								if(itemBase.getProdTypeCode().trim().toUpperCase().equals(facName.trim().toUpperCase())){
									facIdList.add(itemBase.getCmsLimitId());
								}
							}
						}
					}
					List facList= proxy.getFacDetailList(facName, custID);
					OBLimit obLimit=(OBLimit) facList.get(0); 
					for(int i = 0 ;i<facIdList.size();i++){
						 serialList =(proxy.getLimitTranchListByFacilityFor(facIdList.get(i).toString(), ""));
						 finalserialList = ListUtils.union(finalserialList, serialList);
					}
					
			
				
							List idList = finalserialList;			
							
							for (int i = 0; i < idList.size(); i++) {
								String idVal = (String)idList.get(i);
								 
									String id = idVal;
									String val = idVal;
									LabelValueBean lvBean = new LabelValueBean(val, id);
									lbValList.add(lvBean);
								
							}
						
							lbValList=CommonUtil.sortDropdown(lbValList);
					
				
					
					
					if(serialList!=null && serialList.size()>0){
					obLimit.setSourceId(String.valueOf(serialList.get(0)));
					}
					facDetailList.add(obLimit);
				}else{
					List facList= proxy.getFacDetailList(facName, custID);
					OBLimit obLimit=(OBLimit) facList.get(0); 
					facDetailList.add(obLimit);
				}
			}
			
			} catch (Exception e) {
				e.printStackTrace();
			} 
			result.put("facDetailList",facDetailList );
			result.put("serialList",lbValList );
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}

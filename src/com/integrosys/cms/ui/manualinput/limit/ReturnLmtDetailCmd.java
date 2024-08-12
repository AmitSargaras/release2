/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.customer.bus.ILineCovenant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReturnLmtDetailCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				});

	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "lmtDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "collateralMap", "java.util.HashMap", SERVICE_SCOPE },
				{"newLimitDashboardList","java.util.List", SERVICE_SCOPE},
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{"lineCovenantObj",ILineCovenant.class.getName(), SERVICE_SCOPE }
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		String facCat = (String) (map.get("facCat"));
		if (exceptionMap.size() == 0) {
			DefaultLogger.debug(this, "**********In ReturnLmtDetailCmd(): Line 78 facCat=>"+facCat);
			try {
				ILimitTrxValue lmtTrxValue = (ILimitTrxValue) (map.get("lmtTrxObj"));
				String fromEvent = (String) map.get("fromEvent");
				// for read event render form from original object
				// otherwise reder form from staging object
				ILimit curLmt = null;
				if (EventConstant.EVENT_READ.equals(fromEvent) ) {
					curLmt = lmtTrxValue.getLimit();
				}
				else {
					MILimitUIHelper helper = new MILimitUIHelper();
					SBMILmtProxy proxy = helper.getSBMILmtProxy();
					
					if(lmtTrxValue.getStagingLimit() != null && lmtTrxValue.getStagingLimit().getFacilityCat() != null){
						facCat = lmtTrxValue.getStagingLimit().getFacilityCat();
					}
					
					if(facCat == null || facCat.trim().equals("")) {
						result.put("facNameList",new ArrayList());
					}else{
						result.put("facNameList", getFacName(proxy.getFacNameList(facCat)));
					}
					
					curLmt = lmtTrxValue.getStagingLimit();
					if(curLmt.getLimitSysXRefs() != null) {
						if(curLmt.getLimitSysXRefs().length >0) {
							for(int i = 0; i < curLmt.getLimitSysXRefs().length; i++){
								curLmt.getLimitSysXRefs()[i].getCustomerSysXRef().setFacilitySystem(curLmt.getFacilitySystem());
								curLmt.getLimitSysXRefs()[i].getCustomerSysXRef().setLineNo(curLmt.getLineNo());
							}
						}
					}
					
					ILimitSysXRef[] limitSysXRefs = curLmt.getLimitSysXRefs();
					TreeMap sortedMap= new TreeMap();
					if(limitSysXRefs != null){
					for (int i = 0; i < limitSysXRefs.length; i++) {
						ILimitSysXRef iLimitSysXRef = limitSysXRefs[i];
						String serialNo=iLimitSysXRef.getCustomerSysXRef().getSerialNo();
						if(null==serialNo || serialNo.equals("")) {
							serialNo = iLimitSysXRef.getCustomerSysXRef().getHiddenSerialNo();
						}
						if(!"HIDE".equalsIgnoreCase(iLimitSysXRef.getCustomerSysXRef().getStatus()))
						sortedMap.put(iLimitSysXRef.getCustomerSysXRef().getFacilitySystemID()+serialNo, iLimitSysXRef);
					}
					Collection values = sortedMap.values();
					curLmt.setLimitSysXRefs((ILimitSysXRef[])values.toArray(new ILimitSysXRef[values.size()]));
					}
				}
				
				curLmt.setFacilityCat(facCat);
				
				result.put("lmtDetailForm", curLmt);
				if ((fromEvent != null) || !fromEvent.trim().equals("")) {
					result.put("fromEvent", fromEvent);
				}
				
				result.put("newLimitDashboardList",new ArrayList());
				result.put("sessionCriteria",map.get("sessionCriteria"));
				result.put("lmtTrxObj", lmtTrxValue);

			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
		}
		result.put("sessionCriteria",map.get("sessionCriteria"));
		result.put("collateralMap",  getCollateralInfo());
		result.put("fundedAmount", map.get("fundedAmount"));
		result.put("nonFundedAmount", map.get("nonFundedAmount"));
		result.put("memoExposer", map.get("memoExposer"));
		result.put("sanctionedLimit", map.get("sanctionedLimit"));
		result.put("inrValue", map.get("inrValue"));
		result.put("lineCovenantObj", null);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "*********Out ReturnLmtDetailCmd(): Line 156 Done.");
		return temp;
	}
	
	private List getFacName(List lst) {
		List lbValList = new ArrayList();	
		OBLimit ob = null;
		for (int i = 0; i < lst.size(); i++) {						
				ob = (OBLimit)lst.get(i);
				LabelValueBean lvBean = new LabelValueBean(ob.getFacilityName(),ob.getFacilityName() );
				lbValList.add(lvBean);
		}
				
		return CommonUtil.sortDropdown(lbValList);
	}
	
	public HashMap getCollateralInfo() {
		HashMap map= new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc)BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result= collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list=(ArrayList)result.getResultList();
		for(int ab=0;ab<list.size();ab++){
			ICollateralNewMaster newMaster=(ICollateralNewMaster)list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());
		}
		return map;
	}
}

/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterDao;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.proxy.IRiskTypeProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshRiskTypeCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "facName", "java.lang.String", REQUEST_SCOPE },{ "facCat", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "riskTypeList", "java.util.List", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		List actualRiskTypeIds= new ArrayList();
		String facCat = (String) (map.get("facCat"));
		String facName = (String) (map.get("facName"));
		if ((facCat != null) && facName!=null) {
			try {
				List authorisedRiskTypes=getFacilityRiskTypeList(facCat,facName);
				List riskTypeList=getRiskTypeList();
				if(null!=authorisedRiskTypes && null!=riskTypeList) {
				 	for(int i=0;i<riskTypeList.size();i++) {
				 		LabelValueBean lvBean=(LabelValueBean)riskTypeList.get(i);
				 		if(authorisedRiskTypes.contains(lvBean.getValue())) {
				 			LabelValueBean lvBean1 = new LabelValueBean(lvBean.getLabel(), lvBean.getValue());
				 			actualRiskTypeIds.add(lvBean1);
				 		}
				 	}
				}
				result.put("riskTypeList", actualRiskTypeIds);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			  				
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getRiskTypeList() throws Exception {
		
		List<LabelValueBean> lbValList = new ArrayList<LabelValueBean>();
		IRiskTypeProxyManager riskTypeProxy = (IRiskTypeProxyManager)BeanHouse.get("riskTypeProxy");
		SearchResult riskTypeList= (SearchResult) riskTypeProxy.getAllActualRiskType();
		Iterator itr = riskTypeList.getResultList().iterator();
		
		while(itr.hasNext()) {
			IRiskType riskType = (IRiskType) itr.next();
			LabelValueBean lvBean = new LabelValueBean(riskType.getRiskTypeName(),riskType.getRiskTypeCode());
			lbValList.add(lvBean);
		}
		
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getFacilityRiskTypeList(String facilityCat,String facilityName) throws Exception {
		
		IFacilityNewMasterDao facilityNewMasterDao = (IFacilityNewMasterDao)BeanHouse.get("facilityNewMasterDao");
		System.out.println("Inside  RefreshRiskTypeCmd.java getFacilityNewMasterRiskType=> line number 100=>facilityCat=>"+facilityCat+"..facilityName=>"+facilityName);
		IFacilityNewMaster res= facilityNewMasterDao.getFacilityNewMasterRiskType(facilityCat,facilityName);
		String selectedRiskTypes=res.getSelectedRiskTypes();
		if(selectedRiskTypes!=null) {
			List<String> authorisedRiskTypes = Arrays.asList(selectedRiskTypes.split(","));
			return authorisedRiskTypes;
		}else {
			return null;
		}
	}
}

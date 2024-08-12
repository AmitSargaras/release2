package com.integrosys.cms.ui.cersaiMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingBusManager;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingJdbc;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.component.user.app.bus.OBCommonUser;

public class ListCersaiMappingCmd  extends AbstractCommand implements ICommonEventConstant{
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "startI", "java.lang.String", REQUEST_SCOPE },
			 });
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
			{ "masterNameList", "java.util.List", SERVICE_SCOPE },
			{ "masterNameList", "java.util.List", REQUEST_SCOPE },
			{ "masterListFlag", "java.lang.String", SERVICE_SCOPE },
			{ "masterListFlag", "java.lang.String", REQUEST_SCOPE },
			{ "startI", "java.lang.String", REQUEST_SCOPE },
			{ "startI", "java.lang.String", SERVICE_SCOPE },
			{ "startIn", "java.lang.String", REQUEST_SCOPE },
			{ "startIn", "java.lang.String", SERVICE_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	
	private ICersaiMappingProxyManager cersaiMappingProxy;
	
	

	public ICersaiMappingProxyManager getCersaiMappingProxy() {
		return cersaiMappingProxy;
	}



	public void setCersaiMappingProxy(ICersaiMappingProxyManager cersaiMappingProxy) {
		this.cersaiMappingProxy = cersaiMappingProxy;
	}

	
	public ListCersaiMappingCmd() {
	}


	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICersaiMappingProxyManager proxyMgr = getCersaiMappingProxy();
		ICersaiMapping cersaiMapping = (ICersaiMapping) map.get("cersaiMappingObj");
		ICersaiMappingJdbc cersaiMappingJdbc=(ICersaiMappingJdbc)BeanHouse.get("cersaiMappingJdbc");
		String startIdx = (String) map.get("startIndex");
		String startIdx1 = (String) map.get("startI");
		
		//Map<ICersaiMapping> masterValueList = getMasterValueList(cersaiMappingJdbc);
		
	    // HashMap<String, String> masterValueList = getMasterValueList(cersaiMappingJdbc);

	//	SearchResult sr = proxyMgr.getAllCersaiMapping(cersaiMapping);

		result.put("masterNameList", getMasterList(cersaiMappingJdbc));
		result.put("masterListFlag","N");
		result.put("startI", startIdx);
		result.put("startIn", startIdx1);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	/*private HashMap<String, String> getMasterValueList(ICersaiMappingJdbc cersaiMappingJdbc) {
		HashMap<String, String> masterValueList = new HashMap();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				 masterValueList = cersaiMappingJdbc.getMasterValueList();
				
					for (int i = 0; i < masterList.size(); i++) {
						String [] str = (String[]) masterList.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
			System.out.println();
		}
		return masterValueList;
	}*/

	
	private List getMasterList(ICersaiMappingJdbc cersaiMappingJdbc) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List masterList = cersaiMappingJdbc.getMasterList();
				
					for (int i = 0; i < masterList.size(); i++) {
						String [] str = (String[]) masterList.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
}

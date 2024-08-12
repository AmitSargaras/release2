package com.integrosys.cms.ui.cersaiMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingJdbc;
import com.integrosys.cms.app.cersaiMapping.proxy.ICersaiMappingProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class ListCersaiMappingOnSelectMasterCmd extends AbstractCommand implements ICommonEventConstant {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			 { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", FORM_SCOPE },
		{ "masterName", "java.lang.String", REQUEST_SCOPE }
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
			
			{ "cersaiMappingObj", "com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping", SERVICE_SCOPE },
			{ "masterNameList", "java.util.List", SERVICE_SCOPE },
			{ "masterNameList", "java.util.List", REQUEST_SCOPE },
			{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
			{ "masterValueList", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
			{ "masterName", "java.util.String", SERVICE_SCOPE },
			{ "masterName", "java.util.String", REQUEST_SCOPE },
			{ "masterListFlag", "java.lang.String", SERVICE_SCOPE },
			{ "masterListFlag", "java.lang.String", REQUEST_SCOPE },
			{"listOfMasterValue", "java.util.ArrayList", REQUEST_SCOPE},
			{"listOfMasterValue", "java.util.ArrayList", SERVICE_SCOPE},
			{ "masterValueOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", SERVICE_SCOPE },
			{ "masterValueOfValue", "com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping", REQUEST_SCOPE },
			
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

	
	public ListCersaiMappingOnSelectMasterCmd() {
	}


	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICersaiMappingProxyManager proxyMgr = getCersaiMappingProxy();
		ICersaiMapping cersaiMapping = (ICersaiMapping) map.get("cersaiMappingObj");
		ICersaiMappingJdbc cersaiMappingJdbc=(ICersaiMappingJdbc)BeanHouse.get("cersaiMappingJdbc");
		String masterName =(String) map.get("masterName");
		//Map<ICersaiMapping> masterValueList = getMasterValueList(cersaiMappingJdbc);
		
		SearchResult listOfMasterValue = new SearchResult();
		try {
			//listOfMasterValue= (SearchResult) getCersaiMappingProxy().getAllActualCersaiMapping();
			listOfMasterValue= (SearchResult) getCersaiMappingProxy().getAllActualCersaiMapping(masterName);
		} catch (CersaiMappingException e) {
			e.printStackTrace();
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		
		ICersaiMapping[] masterValueList =cersaiMappingJdbc.getMasterValueList(masterName);// getMasterValueList(cersaiMappingJdbc,masterName, cersaiMapping);
		
		ICersaiMapping[] masterValueOfValue =cersaiMappingJdbc.getMasterValueList(masterName);

	//	SearchResult sr = proxyMgr.getAllCersaiMapping(cersaiMapping);

		result.put("masterNameList", getMasterList(cersaiMappingJdbc));
		result.put("listOfMasterValue", listOfMasterValue);
		result.put("cersaiMappingObj", cersaiMapping);
		result.put("masterName",masterName);
		result.put("masterListFlag","Y");
		result.put("masterValueList", masterValueList);
		result.put("masterValueOfValue", masterValueOfValue);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	

	
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

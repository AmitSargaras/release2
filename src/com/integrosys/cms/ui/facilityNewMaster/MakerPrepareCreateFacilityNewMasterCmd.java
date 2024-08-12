/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.facilityNewMaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.riskType.bus.IRiskType;
import com.integrosys.cms.app.riskType.bus.IRiskTypeDao;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
*@author $Author: Abhijit R$
*Command to read FacilityNewMaster
 */
public class MakerPrepareCreateFacilityNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	
	private IFacilityNewMasterProxyManager facilityNewMasterProxy;

	
	
	
	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(
			IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateFacilityNewMasterCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  
					 
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{"IFacilityNewMasterTrxValue", "com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue", SERVICE_SCOPE},
				{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE },
				{ "facilityTypeList", "java.util.List", SERVICE_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "purposeList", "java.util.List", SERVICE_SCOPE },
				{ "productAllowedList", "java.util.List", SERVICE_SCOPE },
				{ "lineCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "actualRiskTypeIds", "java.util.ArrayList", SERVICE_SCOPE },
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		 
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		  OBFacilityNewMasterTrxValue facilityNewMasterTrxValue = new OBFacilityNewMasterTrxValue();
		  
			 resultMap.put("facilityCategoryList",getFacilityCategoryList() );
			 resultMap.put("facilityTypeList",getFacilityTypeList() );
			 resultMap.put("systemList",getSystemList() );
			 resultMap.put("purposeList",getPurposeList() );
	         resultMap.put("productAllowedList", getProductAllowedList());
	         resultMap.put("lineCurrencyList", getLineCurrencyList());
	         resultMap.put("actualRiskTypeIds", getActualRiskTypeIds());
	         
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private List getFacilityCategoryList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_CATEGORY);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id, val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getFacilityTypeList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_TYPE);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id,val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getSystemList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.SYSTEM);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id,val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getPurposeList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.PURPOSE);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id,val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getProductAllowedList() {
		
		IProductMasterDao productMasterDao = (IProductMasterDao) BeanHouse.get("productMasterDao");
		List productAllowedList = productMasterDao.getProductMasterList();
		List productMasterLbValList = new ArrayList();
		try {

			for (int i = 0; i < productAllowedList.size(); i++) {
				IProductMaster productMaster = (IProductMaster) productAllowedList.get(i);
				if (productMaster.getStatus().equals("ACTIVE")) {

					String id = productMaster.getProductCode();
					String val = productMaster.getProductCode();


					LabelValueBean lvBean = new LabelValueBean(val, id);
					productMasterLbValList.add(lvBean);

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return productAllowedList = CommonUtil.sortDropdown(productMasterLbValList);
	}
	   
	 private List getLineCurrencyList() {
			
			ILimitDAO dao = LimitDAOFactory.getDAO();
			List lineCurrencyList = dao.getCurrencyList();
			return lineCurrencyList;
		
  }
	 List riskTypeList = new ArrayList();

	 
	 private List getActualRiskTypeIds() {
			
		 IRiskTypeDao riskTypeDao = (IRiskTypeDao) BeanHouse.get("riskTypeDao");

			 riskTypeList = riskTypeDao.getRiskTypeList();
				List riskTypeLbValList = new ArrayList();
			try {

				for (int i = 0; i < riskTypeList.size(); i++) {
					IRiskType riskType = (IRiskType) riskTypeList.get(i);
					if (riskType.getStatus().equals("ACTIVE")) {

						String code = riskType.getRiskTypeCode();
						String name = riskType.getRiskTypeName();

						System.out.println("Risk Type Name :: "+name);
						LabelValueBean lvBean = new LabelValueBean(name,code);
						riskTypeLbValList.add(lvBean);

					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return riskTypeList = CommonUtil.sortDropdown(riskTypeLbValList);
		
		
  }

}	 
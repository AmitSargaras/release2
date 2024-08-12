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

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.facilityNewMaster.bus.FacilityNewMasterException;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue;
import com.integrosys.cms.app.facilityNewMaster.trx.OBFacilityNewMasterTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAO;
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
public class MakerReadFacilityNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	public MakerReadFacilityNewMasterCmd() {
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
				 {"facilityCode", "java.lang.String", REQUEST_SCOPE},
				
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
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
				{ "facilityNewMasterObj", "com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster", SERVICE_SCOPE },
				{ "facilityNewMasterObj", "com.integrosys.cms.app.facilityNewMaster.bus.OBFacilityNewMaster", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE },
				{ "facilityTypeList", "java.util.List", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "purposeList", "java.util.List", SERVICE_SCOPE },
				{ "productAllowedList", "java.util.List", SERVICE_SCOPE },
				{ "lineCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "actualRiskTypeIds", "java.util.ArrayList", SERVICE_SCOPE },


				{"IFacilityNewMasterTrxValue", "com.integrosys.cms.app.facilityNewMaster.trx.IFacilityNewMasterTrxValue", SERVICE_SCOPE}
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
		try {
			IFacilityNewMaster facilityNewMaster;
			IFacilityNewMasterTrxValue trxValue=null;
			String facilityCode=(String) (map.get("facilityCode"));
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			
			trxValue = (OBFacilityNewMasterTrxValue) getFacilityNewMasterProxy().getFacilityNewMasterTrxValue(Long.parseLong(facilityCode));
			facilityNewMaster = (OBFacilityNewMaster) trxValue.getFacilityNewMaster();

			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||(trxValue.getStatus().equals("REJECTED"))||(trxValue.getStatus().equals("DRAFT")))
			{
				resultMap.put("wip", "wip");
			}
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_FACILITY_NEW_MASTER",facilityNewMaster.getId(),"ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultMap.put("event", event);
			resultMap.put("length",new Integer(10));	
      	 resultMap.put("startIndex",startIdx);	
			resultMap.put("IFacilityNewMasterTrxValue", trxValue);
			resultMap.put("facilityNewMasterObj", facilityNewMaster);
			 resultMap.put("facilityCategoryList",getFacilityCategoryList() );
			 resultMap.put("facilityTypeList",getFacilityTypeList() );
			 resultMap.put("systemList",getSystemList() );
			 resultMap.put("purposeList",getPurposeList() );
	         resultMap.put("productAllowedList", getProductAllowedList());
	         resultMap.put("lineCurrencyList", getLineCurrencyList());
	         resultMap.put("actualRiskTypeIds", getActualRiskTypeIds());
	         
		}catch (FacilityNewMasterException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

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
				LabelValueBean lvBean = new LabelValueBean(id, val);
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
				LabelValueBean lvBean = new LabelValueBean(id, val);
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
				LabelValueBean lvBean = new LabelValueBean(id, val);
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
	//	List riskTypeLbValList = new ArrayList();
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

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insurancepolicy/PrepareAddtionalDocumentFacilityDetailsCommand.java,v 1.2 2006/04/11 09:04:37 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/04/11 09:04:37 $ Tag: $Name: $
 */

public class PrepareAddtionalDocumentFacilityDetailsCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "collateralID", "java.lang.String", SERVICE_SCOPE },
				{ "lmtProfileId", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
                { "isStpStatus", "java.lang.String", ICommonEventConstant.SERVICE_SCOPE },
				{ "insuranceStatusRadio", "java.lang.String", REQUEST_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "insuranceTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insurerNameID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insurerNameValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitProfileIds", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leid_bcarefIds", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "buildingOccupationID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "buildingOccupationValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "buildingTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "buildingTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "endorsementID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "endorsementValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "extensionRoofID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "extensionRoofValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "extensionWallTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "extensionWallTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insureTagID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "insureTagValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "perilsTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "perilsTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "roofTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "roofTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "wallTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "wallTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "floorTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "floorTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "perilsTypeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "perilsTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "event", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
                { "isStpStatus", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "insuranceCoverageList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				{ "currencyList", "java.util.List", REQUEST_SCOPE },
				{ "insuranceStatusRadio", "java.lang.String", SERVICE_SCOPE },
				
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Coming inside PrepareAddtionalDocumentFacilityDetailsCommand");
		ILimitProxy proxy = LimitProxyFactory.getProxy();
		ICheckListProxyManager checklistproxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

        String isStpStatus = (String) map.get("isStpStatus");
        DefaultLogger.debug(this, "isStpStatus: " + isStpStatus);
        result.put("isStpStatus", isStpStatus);

        String from_event = (String) map.get("event");
		DefaultLogger.debug(this, "from_event:" + from_event);

		String collateralId = (String) map.get("collateralID");
		long lCollateralId = 0;
		DefaultLogger.debug(this, "CollateralId:" + collateralId);

		String lmtProfileId = (String) map.get("lmtProfileId");
		long llmtProfileId = 0;
		DefaultLogger.debug(this, "lmtProfileId:" + lmtProfileId);

		if ((collateralId != null) && (collateralId.trim().length() > 0)) {
			lCollateralId = Long.parseLong(collateralId);
		}
		DefaultLogger.debug(this, "lCollateralId:" + lCollateralId);

		ICollateralTrxValue colTrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String collateralLoc = null;
		if (colTrxValue != null) {
			ICollateral col = colTrxValue.getStagingCollateral() == null ? colTrxValue.getCollateral() : colTrxValue
					.getStagingCollateral();
			collateralLoc = col.getCollateralLocation();
			DefaultLogger.debug(this, "collateralLoc:" + collateralLoc);
		}
		if ((collateralLoc == null) || "".equals(collateralLoc.trim())) {
			CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.INSURER_NAME);
			result.put("insurerNameID", commonCode.getCommonCodeValues());
			result.put("insurerNameValue", commonCode.getCommonCodeLabels());

//			commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ADD_DOC_FAC_DET _TYPE);
//			result.put("insuranceTypeID", commonCode.getCommonCodeValues());
//			result.put("insuranceTypeValue", commonCode.getCommonCodeLabels());
		}
		else {
			CommonCodeList commonCode = CommonCodeList.getInstance(collateralLoc, CategoryCodeConstant.INSURER_NAME);
			result.put("insurerNameID", commonCode.getCommonCodeValues());
			result.put("insurerNameValue", commonCode.getCommonCodeLabels());

//			commonCode = CommonCodeList.getInstance(collateralLoc, CategoryCodeConstant.ADD_DOC_FAC_DET _TYPE);
//			result.put("insuranceTypeID", commonCode.getCommonCodeValues());
//			result.put("insuranceTypeValue", commonCode.getCommonCodeLabels());
		}
		
		
		//----------------------------------------Added by thurien--------------------------------------------------//
		
		CommonCodeList commonCode = CommonCodeList.getInstance( CategoryCodeConstant.BUILDING_OCCUPATION);
		result.put("buildingOccupationID", commonCode.getCommonCodeValues());
		result.put("buildingOccupationValue", commonCode.getCommonCodeLabels());
//		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ADD_DOC_FAC_DET _TYPE);
		result.put("insuranceTypeID", commonCode.getCommonCodeValues());
		result.put("insuranceTypeValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.BUILDING_TYPE);	
		result.put("buildingTypeID", commonCode.getCommonCodeValues());
		result.put("buildingTypeValue", commonCode.getCommonCodeLabels());
		 commonCode = CommonCodeList.getInstance( CategoryCodeConstant.ENDORSEMENT);
		result.put("endorsementID", commonCode.getCommonCodeValues());
		result.put("endorsementValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance( CategoryCodeConstant.EXTENSION_ROOF);
		result.put("extensionRoofID", commonCode.getCommonCodeValues());
		result.put("extensionRoofValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance( CategoryCodeConstant.EXTENSION_WALL_TYPE);
		result.put("extensionWallTypeID", commonCode.getCommonCodeValues());
		result.put("extensionWallTypeValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance( CategoryCodeConstant.INSURER_TAG);
		result.put("insureTagID", commonCode.getCommonCodeValues());
		result.put("insureTagValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance( CategoryCodeConstant.PERILS_TYPE);
		result.put("perilsTypeID", commonCode.getCommonCodeValues());
		result.put("perilsTypeValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.ROOF_TYPE);
		result.put("roofTypeID", commonCode.getCommonCodeValues());
		result.put("roofTypeValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.WALL_TYPE);
		result.put("wallTypeID", commonCode.getCommonCodeValues());
		result.put("wallTypeValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.FLOOR_TYPE);
		result.put("floorTypeID", commonCode.getCommonCodeValues());
		result.put("floorTypeValue", commonCode.getCommonCodeLabels());
		commonCode = CommonCodeList.getInstance(CategoryCodeConstant.PERILS_TYPE);
		result.put("perilsTypeID", commonCode.getCommonCodeValues());
		result.put("perilsTypeValue", commonCode.getCommonCodeLabels());
		//-----------------------------------------------------------------------------------------------------------//

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());
		result.put("currencyList", getCurrencyList());
		/*
		HashMap limitProfileIdMap = null;
		try {
			limitProfileIdMap = proxy.getLimitProfileIds(lCollateralId);
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.getMessage(), e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		result.put("limitProfileId", limitProfileIdMap);
		Collection limitProfileIds = null;
		Collection leid_bcarefIds = null;
		if (limitProfileIdMap != null) {
			DefaultLogger.debug(this, "Coming inside if" + limitProfileIdMap.size());

			limitProfileIds = limitProfileIdMap.keySet();
			leid_bcarefIds = limitProfileIdMap.values();
		}
		result.put("limitProfileIds", limitProfileIds);
		result.put("leid_bcarefIds", leid_bcarefIds);
		if ((from_event != null) && (from_event.trim().equalsIgnoreCase("prepare_form"))) {
			DefaultLogger.debug(this, "Coming inside set docIds");
			if ((lmtProfileId != null) && (lmtProfileId.trim().length() > 0)) {
				llmtProfileId = Long.parseLong(lmtProfileId);
			}
			if (llmtProfileId != ICMSConstant.LONG_INVALID_VALUE) {
				DefaultLogger.debug(this, "llmtProfileId:" + llmtProfileId);
			}
		}
		*/
		
		//************ Added by Dattatray Thorat for getting Insurance Company Name ****************
		
		IInsuranceCoverageProxyManager insuranceCoverageProxyManager = (IInsuranceCoverageProxyManager) BeanHouse.get("insuranceCoverageProxyManager");
		
		SearchResult sr = insuranceCoverageProxyManager.getInsuranceCoverageList(null,null);
		
		ArrayList resultList = (ArrayList)sr.getResultList();
		result.put("insuranceCoverageList", getInsuranceCoverageList(resultList));
		
		//Uma Khot::Insurance Deferral maintainance
		
		result.put("insuranceStatusRadio", map.get("insuranceStatusRadio"));
		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);

		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	
	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getCurrencyIsoCode().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getInsuranceCoverageList(ArrayList resultList) {
		List lbValList = new ArrayList();
		try {
			
			for (int i = 0; i < resultList.size(); i++) {
				IInsuranceCoverage insuranceCoverage = (IInsuranceCoverage) resultList.get(i);
				String id = Long.toString(insuranceCoverage.getId());
				String val = insuranceCoverage.getCompanyName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
}

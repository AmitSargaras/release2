package com.integrosys.cms.ui.imageTag;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralComparator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This command refresh Image Tag
 * 
 * @author abhijit.rudrakshawar
 */

public class RefreshImageTagResultCommand extends AbstractCommand {

	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}


	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,
						"com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE },
				{ "securitySubType", "java.lang.String", REQUEST_SCOPE },
				{
						"customerSearchCriteria",
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						FORM_SCOPE },
				{
						"customerSearchCriteria1",
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						SERVICE_SCOPE },
				{ "ImageTagAddObj",
						"com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",
						FORM_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{
						IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						GLOBAL_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				{ "from", "java.lang.String", REQUEST_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{
						IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						GLOBAL_SCOPE },
				{ "customerList",
						"com.integrosys.base.businfra.search.SearchResult",
						FORM_SCOPE },
				{ "customerList",
						"com.integrosys.base.businfra.search.SearchResult",
						SERVICE_SCOPE },
				{ "customerList",
						"com.integrosys.base.businfra.search.SearchResult",
						REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{
						"customerSearchCriteria1",
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						SERVICE_SCOPE },

				{ "ImageTagAddObj",
						"com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",
						FORM_SCOPE },
				{ "imageTagAddForm",
						"com.integrosys.cms.ui.imageTag.ImageTagAddForm",
						FORM_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "securityOb", "java.util.HashMap", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",
						GLOBAL_SCOPE },
				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", GLOBAL_SCOPE },
				{
						IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						GLOBAL_SCOPE },
				{ "from", "java.lang.String", REQUEST_SCOPE },

				{
						"imageTagProxyManager",
						"com.integrosys.cms.app.customer.bus.IImageTagProxyManager",
						SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap lmtcolmap = new HashMap();
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map
				.get("theOBTrxContext");
		CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map
				.get("customerSearchCriteria");
		CustomerSearchCriteria searchCriteria = formCriteria;

		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
		}

		searchCriteria.setCtx(theOBTrxContext);


		CustomerSearchCriteria objSearch = (CustomerSearchCriteria) map
				.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);
		objSearch.setFrompage((String) map.get("frompage"));

		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ILimitProfile limitProfileOB = (ILimitProfile) map
				.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (limitProfileOB == null) {
			throw new CommandProcessingException(
					"ILimitProfile is null in session!");
		}
//		ILimit[] limits = limitProfileOB.getLimits();
//		try {
//			limits = limitProxy.getFilteredNilColCheckListLimits(
//					theOBTrxContext, limitProfileOB);
//		} catch (LimitException ex) {
//			throw new CommandProcessingException(
//					"Failed to filtered no collateral checklist for the limits ["
//							+ limitProfileOB + "]");
//		}

		lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);

		Map sortedCollateralLimitMap = new TreeMap(new Comparator() {

			public int compare(Object thisObj, Object thatObj) {
				ICollateral thisCol = (ICollateral) thisObj;
				ICollateral thatCol = (ICollateral) thatObj;

				long thisValue = thisCol.getCollateralID();
				long thatValue = thatCol.getCollateralID();

				return (thisValue < thatValue ? -1
						: (thisValue == thatValue ? 0 : 1));
			}
		});
		sortedCollateralLimitMap.putAll(lmtcolmap);
		OBCollateral obcol = new OBCollateral();
		List securityIdList = new ArrayList();
		String secSubType = (String) map.get("securitySubType");
		Set set = lmtcolmap.keySet();
		ICollateral[] cols = (ICollateral[]) set.toArray(new ICollateral[0]);
		Arrays.sort(cols, new CollateralComparator());
		Iterator i = Arrays.asList(cols).iterator();
		while (i.hasNext()) {
			obcol = ((OBCollateral) i.next());

			if (obcol.getCollateralSubType().equals(secSubType)) {
				securityIdList.add(String.valueOf(obcol.getCollateralID()));
			}

		}

		String 	secTypeCode = (String) (map.get("secType"));
		result.put("securityIdList", securityIdList);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeLabel(null));
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
			throw new ImageTagException("ERROR - Refresh Sec Sub Type command");
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSecuritySubtypeList(String secTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (secTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy()
						.getCollateralSubTypesByTypeCode(secTypeValue);
				if (subtypeLst != null) {
					for (int i = 0; i < subtypeLst.length; i++) {
						ICollateralSubType nextSubtype = subtypeLst[i];
						String id = nextSubtype.getSubTypeCode();
						String value = nextSubtype.getSubTypeName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
			}
		} catch (Exception ex) {
			throw new ImageTagException("ERROR - Refresh Sec Sub Type command");
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}

/*
 * Created on Apr 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.imageTag;

/**
 * 
 * 
 * @author abhijit.rudrakshawar
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

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralComparator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshSecSubtypeCmd extends AbstractCommand {

	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "securitySubType", "java.lang.String", REQUEST_SCOPE },
				{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", SERVICE_SCOPE },
				{ "ImageTagAddObj", "com.integrosys.cms.app.imageTag.bus.OBImageTagAdd", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE }, { "from", "java.lang.String", REQUEST_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "documentItemList", "java.util.List", REQUEST_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", SERVICE_SCOPE },
				{ "ImageTagAddObj", "com.integrosys.cms.app.imageTag.bus.OBImageTagAdd", FORM_SCOPE },
				{ "imageTagAddForm", "com.integrosys.cms.ui.imageTag.ImageTagAddForm", FORM_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", FORM_SCOPE },
				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "securityOb", "java.util.HashMap", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "from", "java.lang.String", REQUEST_SCOPE },
				{ "imageTagProxyManager", "com.integrosys.cms.app.customer.bus.IImageTagProxyManager", SERVICE_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException, AccessDeniedException {
		HashMap lmtcolmap = new HashMap();
		HashMap returnMap = new HashMap();

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap collateralCodeMap = getCollateralInfo();

		/*
		 * CustomerSearchCriteria objSearch = (CustomerSearchCriteria) map
		 * .get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ); if
		 * (objSearch == null) { objSearch = new CustomerSearchCriteria(); }
		 * else { } objSearch.setFrompage((String) map.get("frompage"));
		 */
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		String strLimitProfileId = (String) map.get("custLimitProfileID");
		long limitProfileID = Long.parseLong(strLimitProfileId);
		ILimitProfile limitProfileOB = new OBLimitProfile();
		try {
			limitProfileOB = limitProxy.getLimitProfile(limitProfileID);
		} catch (LimitException e1) {
			e1.printStackTrace();
		}
		/*
		 * ILimitProfile limitProfileOB = (ILimitProfile) map
		 * .get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ); if (limitProfileOB ==
		 * null) { throw new CommandProcessingException(
		 * "ILimitProfile is null in session!"); }
		 */

		lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);

		Map sortedCollateralLimitMap = new TreeMap(new Comparator() {

			public int compare(Object thisObj, Object thatObj) {
				ICollateral thisCol = (ICollateral) thisObj;
				ICollateral thatCol = (ICollateral) thatObj;

				long thisValue = thisCol.getCollateralID();
				long thatValue = thatCol.getCollateralID();

				return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
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
		String label;
		String value;
		while (i.hasNext()) {
			obcol = ((OBCollateral) i.next());

			if (obcol.getCollateralSubType().getSubTypeCode().equals(secSubType)) {
				label = obcol.getCollateralID() + " - " + collateralCodeMap.get(obcol.getCollateralCode());
				value = String.valueOf(obcol.getCollateralID());
				LabelValueBean lvBean = new LabelValueBean(label,value);
				securityIdList.add(lvBean);
			}

		}

		result.put("securityIdList", securityIdList);
		result.put("documentItemList", new ArrayList());
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance().getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance().getSecurityTypeLabel(null));
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
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy().getCollateralSubTypesByTypeCode(secTypeValue);
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

	public HashMap getCollateralInfo() {
		HashMap map = new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc) BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result = collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list = (ArrayList) result.getResultList();
		for (int ab = 0; ab < list.size(); ab++) {
			ICollateralNewMaster newMaster = (ICollateralNewMaster) list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());

		}
		return map;
	}
}

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/CommodityMainMapper.java,v 1.15 2006/09/15 12:41:34 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.LimitDetailsComparator;
import com.integrosys.cms.app.collateral.bus.type.commodity.IApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.IContract;
import com.integrosys.cms.app.collateral.bus.type.commodity.IHedgingContractInfo;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.IPreCondition;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBApprovedCommodityType;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBContract;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBHedgingContractInfo;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBPreCondition;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/09/15 12:41:34 $ Tag: $Name: $
 */

public class CommodityMainMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		CommodityMainForm aForm = (CommodityMainForm) cForm;
		HashMap trxValueMap = (HashMap) inputs.get("commodityMainTrxValue");
		ICommodityCollateral[] colList = (ICommodityCollateral[]) trxValueMap.get("staging");
		String from_event = (String) inputs.get("from_page");
		HashMap limit = (HashMap) trxValueMap.get("stageLimit");
		ILimitProfile limitProfileOB = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		DefaultLogger.debug(this, "<<<<<<<< limit profile id: " + limitProfileOB.getLimitProfileID());

		if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_PREPARE_UPDATE)) {
			if (aForm.getEvent().endsWith(CommodityMainAction.EVENT_DELETE_ITEM)) {
				if (aForm.getEvent().startsWith(CommodityMainConstant.APPROVED_COMMODITY)) {
					colList = deleteMultipleObject(colList, aForm.getDeleteAppCommodity(), aForm.getEvent());
				}
				else if (aForm.getEvent().startsWith(CommodityMainConstant.COMMODITY_CONTRACT)) {
					colList = deleteMultipleObject(colList, aForm.getDeleteContract(), aForm.getEvent());
				}
				else if (aForm.getEvent().startsWith(CommodityMainConstant.HEDGED_CONTRACT)) {
					colList = deleteMultipleObject(colList, aForm.getDeleteHedgeContract(), aForm.getEvent());
				}
				else if (aForm.getEvent().startsWith(CommodityMainConstant.LOAN_AGENCY)) {
					colList = deleteMultipleObject(colList, aForm.getDeleteLoanAgency(), aForm.getEvent());
				}
			}
			if (colList != null) {
				boolean preConditionChanged = CommodityMainUtil.isPreConditionChanged(colList[0], limitProfileOB
						.getLimitProfileID(), aForm.getPreConditions());

				for (int i = 0; i < colList.length; i++) {

					ICollateralLimitMap[] limitMap = colList[i].getCollateralLimits();
					if (limitMap != null) {
						for (int j = 0; j < limitMap.length; j++) {
							ICollateralLimitMap colLimit = limitMap[j];
							String limitID = CollateralHelper.getColLimitMapLimitID(colLimit);
							if (!limit.containsKey(limitID)) {
								continue;
							}
							limitMap[j] = (ICollateralLimitMap) limit.get(limitID);
						}
					}

					if (preConditionChanged) {
						OBPreCondition preCondition = (OBPreCondition) colList[i].retrievePreCondition(limitProfileOB
								.getLimitProfileID());
						if (preCondition == null) {
							preCondition = new OBPreCondition();
						}

						ICommonUser user = (ICommonUser) inputs.get(IGlobalConstant.USER);
						DefaultLogger.debug(this, "<<<<<<<<<<<< user id: " + user.getUserID() + "\tuser name: "
								+ user.getUserName());
						preCondition.setPreCondition(aForm.getPreConditions());
						preCondition.setUpdateDate(DateUtil.getDate());
						preCondition.setUserID(user.getUserID());
						preCondition.setUserInfo(user.getUserName());
						preCondition.setLimitProfileID(limitProfileOB.getLimitProfileID());
						colList[i].setPreCondition(preCondition);
					}
				}
			}
		}
		HashMap colListMap = new HashMap();
		colListMap.put("obj", colList);
		colListMap.put("limit", limit);

		return colListMap;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		CommodityMainForm aForm = (CommodityMainForm) cForm;

		Collection collateralPool = new ArrayList();
		Collection specificTrans = new ArrayList();
		Collection cash = new ArrayList();
		Collection cashReq = new ArrayList();
		HashMap colListMap = (HashMap) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ILimitProfile limitProfileOB = (ILimitProfile) inputs.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		DefaultLogger.debug(this, "<<<<<<<< limit profile id: " + limitProfileOB.getLimitProfileID());

		ICommodityCollateral[] colList = (ICommodityCollateral[]) colListMap.get("obj");

		HashMap limitMap = (HashMap) colListMap.get("limit");
		if (limitMap != null) {
			Collection valueSet = limitMap.values();
			Object[] tempArr = valueSet.toArray();
			Arrays.sort(tempArr, new LimitDetailsComparator());
			valueSet = Arrays.asList(tempArr);
			Iterator itr = valueSet.iterator();
			while (itr.hasNext()) {
				ICollateralLimitMap colLimitMap = (ICollateralLimitMap) itr.next();
				String key = CollateralHelper.getColLimitMapLimitID(colLimitMap);

				if (colLimitMap.getIsCollateralPool()) {
					collateralPool.add(key);
				}
				if (colLimitMap.getIsSpecificTrx()) {
					specificTrans.add(key);
				}
				if (colLimitMap.getCashReqPct() != ICMSConstant.DOUBLE_INVALID_VALUE) {
					cash.add(key);
					cashReq.add(MapperUtil.mapDoubleToString(colLimitMap.getCashReqPct(), 0, locale));
				}
				else {
					cash.add("");
					cashReq.add("");
				}
			}
		}
		aForm.setCollateralPool((String[]) collateralPool.toArray(new String[0]));
		aForm.setSpecificTrans((String[]) specificTrans.toArray(new String[0]));
		aForm.setCash((String[]) cash.toArray(new String[0]));
		aForm.setCashReq((String[]) cashReq.toArray(new String[0]));

		aForm.setDeleteAppCommodity(new String[0]);
		aForm.setDeleteContract(new String[0]);
		aForm.setDeleteHedgeContract(new String[0]);
		aForm.setDeleteLoanAgency(new String[0]);

		IPreCondition preCondition = colList[0].retrievePreCondition(limitProfileOB.getLimitProfileID());
		if (preCondition != null) {
			aForm.setPreConditions(preCondition.getPreCondition());
			aForm.setPreCondUpdatedDate(DateUtil.formatDate(locale, preCondition.getUpdateDate()));
			aForm.setPreCondLastUpdatedBy(preCondition.getUserInfo());
		}
		else {
			aForm.setPreConditions("");
			aForm.setPreCondUpdatedDate("");
			aForm.setPreCondLastUpdatedBy("");
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }, });
	}

	private ICommodityCollateral[] deleteMultipleObject(ICommodityCollateral[] colList, String[] deleteArr, String event) {
		int deleteInd = 0;
		int deleteValue = -1;
		Collection newList = new ArrayList();
		int count = 0;
		if ((colList != null) && (deleteArr != null)) {
			for (int i = 0; (deleteInd < deleteArr.length) && (i < colList.length); i++) {
				OBCommodityCollateral colObj = (OBCommodityCollateral) colList[i];
				deleteValue = Integer.parseInt(deleteArr[deleteInd]);
				newList = new ArrayList();
				Object[] objArr = null;
				if (event.startsWith(CommodityMainConstant.APPROVED_COMMODITY)) {
					objArr = colObj.getApprovedCommodityTypes();
				}
				else if (event.startsWith(CommodityMainConstant.COMMODITY_CONTRACT)) {
					objArr = colObj.getContracts();
				}
				else if (event.startsWith(CommodityMainConstant.HEDGED_CONTRACT)) {
					objArr = colObj.getHedgingContractInfos();
				}
				else if (event.startsWith(CommodityMainConstant.LOAN_AGENCY)) {
					objArr = colObj.getLoans();
				}
				if (objArr != null) {
					for (int j = 0; j < objArr.length; j++) {
						if (count != deleteValue) {
							newList.add(objArr[j]);
						}
						else {
							deleteInd++;
							if (deleteInd < deleteArr.length) {
								deleteValue = Integer.parseInt(deleteArr[deleteInd]);
							}
							else {
								deleteValue = -1;
							}
						}
						count++;
					}
					if (event.startsWith(CommodityMainConstant.APPROVED_COMMODITY)) {
						colObj.setApprovedCommodityTypes((IApprovedCommodityType[]) newList
								.toArray(new OBApprovedCommodityType[0]));
					}
					else if (event.startsWith(CommodityMainConstant.COMMODITY_CONTRACT)) {
						colObj.setContracts((IContract[]) newList.toArray(new OBContract[0]));
					}
					else if (event.startsWith(CommodityMainConstant.HEDGED_CONTRACT)) {
						colObj.setHedgingContractInfos((IHedgingContractInfo[]) newList
								.toArray(new OBHedgingContractInfo[0]));
					}
					else if (event.startsWith(CommodityMainConstant.LOAN_AGENCY)) {
						colObj.setLoans((ILoanAgency[]) newList.toArray(new OBLoanAgency[0]));
					}
					colList[i] = colObj;
				}
			}
		}
		return colList;
	}
}

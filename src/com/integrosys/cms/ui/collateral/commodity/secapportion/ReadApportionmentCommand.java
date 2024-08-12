/*
 * Created on Sep 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.collateral.commodity.secapportion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ISecApportionment;
import com.integrosys.cms.app.collateral.bus.OBSecApportionLmtDtl;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManager;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManagerHome;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.ui.collateral.assetbased.RankList;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentUtil;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentViewHelper;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class ReadApportionmentCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		// valid value of state will be: 1.init, 2. leId selected, 3.
		// subprofileid selected
		// 4. limitId selected
		return new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "apportionIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "limitDtlList", "java.util.List", SERVICE_SCOPE },
				{ "apportionstate", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ "leId", "java.lang.String", REQUEST_SCOPE }, { "subProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "limitDtlList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				{ "limitDtlListReq", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "fsv", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "rankID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "rankValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leIdList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "subprofileIdList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitIdValueList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "limitIdLabelList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "form.secApportionObject", "java.lang.Object", FORM_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "apportionIndexID", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.ICommand#doExecute(java.util.HashMap)
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			RankList rank = RankList.getInstance();
			Collection list = rank.getRankListID();
			result.put("rankID", list);
			list = rank.getRankListValue();
			result.put("rankValue", list);

			HashMap mapInMap = (HashMap) (map.get("commodityMainTrxValue"));
			String indexID = (String) (map.get("indexID"));
			ICollateralTrxValue[] trxValList = (ICollateralTrxValue[]) (mapInMap.get("trxValue"));
			ICollateralTrxValue itrxValue = null;
			// from_event will be "read" at maker view apportionment list
			// from_event will be "process" at checker review apportionment list
			// from_event will be "close" at maker close rejected security
			// detail
			// from_event will be null at maker update apportionment list
			// only for "read" case, apportionment should be populated from the
			// actual object
			// for the other 3 cases apportionment should be populated from the
			// staging object
			String from_event = (String) map.get("from_event");

			if ((from_event != null) && from_event.equals("process")) {
				itrxValue = getCol(trxValList, indexID);
			}
			else {
				itrxValue = trxValList[Integer.parseInt(indexID)];
			}
			String collateralId = "" + itrxValue.getCollateral().getCollateralID();

			List limitDtl = (List) (map.get("limitDtlList"));
			if (limitDtl == null) {
				SBCollateralBusManager manager = getCollateralBusManager();
				limitDtl = manager.getLimitDetailForNewApportionment(collateralId);
			}
			result.put("limitDtlList", limitDtl);
			SecApportionmentViewHelper helper = new SecApportionmentViewHelper(limitDtl);

			result.put("leIdList", helper.getLeIdDropDownValues());

			String state = (String) (map.get("apportionstate"));
			if (state.equals("1")) {
				result.put("subprofileIdList", new ArrayList());
				result.put("limitIdValueList", new ArrayList());
				result.put("limitIdLabelList", new ArrayList());
			}
			else if (state.equals("2")) {
				String leId = (String) (map.get("leId"));
				result.put("subprofileIdList", helper.getSubprofileIdDropDownValues(leId));
				result.put("limitIdValueList", new ArrayList());
				result.put("limitIdLabelList", new ArrayList());
			}
			else if (state.equals("3")) {
				String leId = (String) (map.get("leId"));
				String subProfileId = (String) (map.get("subProfileId"));
				result.put("subprofileIdList", helper.getSubprofileIdDropDownValues(leId));
				List[] arr = helper.getLimitIdDropDownValueLabels(leId, subProfileId);
				result.put("limitIdValueList", arr[0]);
				result.put("limitIdLabelList", arr[1]);
			}

			// for maker view and checker review of apportionment record
			// indexID will be the refID of the security apportionment
			// for maker update apportionment record
			// indexID will be a numeric index value
			long index = Long.parseLong((String) map.get("apportionIndexID"));
			double colFsv = 0;

			List secApportionmentList = null;

			if ("read".equals(from_event)) {
				secApportionmentList = itrxValue.getCollateral().getSecApportionment();
				Amount amt = itrxValue.getCollateral().getFSV();
				if (amt != null) {
					colFsv = amt.getAmount();
				}
			}
			else {
				secApportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
				Amount amt = itrxValue.getStagingCollateral().getFSV();
				if (amt != null) {
					colFsv = amt.getAmount();
				}
			}

			if (colFsv < 0) {
				colFsv = 0;
			}
			HashMap mappingObj = new HashMap();
			if ("process".equals(from_event)) {
				// required for staging/original object comparison, changed
				// field will be highlighted
				ISecApportionment origApportionment = getItem(itrxValue.getCollateral().getSecApportionment(), index);
				if (origApportionment != null) {
					mappingObj.put("form.origApportionObject", origApportionment);
				}
			}

			boolean requirePersist = false;
			if (from_event == null) {
				requirePersist = true;
			}
			Map availableColAmtMap = SecApportionmentUtil.recalColApportionAmt(colFsv, secApportionmentList,
					requirePersist);

			ISecApportionment curApportionment = null;

			if (from_event == null) {
				// at maker update, indexId will be a numeric index
				curApportionment = (ISecApportionment) (secApportionmentList.get((int) index));
			}
			else {
				curApportionment = getItem(secApportionmentList, index);
				// deleted apportionment, need to find from original
				if (curApportionment == null) {
					curApportionment = getItem(itrxValue.getCollateral().getSecApportionment(), index);
				}
			}

			mappingObj.put("limitIdDisp", getLimitIdDisp(limitDtl, curApportionment.getLimitID()));
			mappingObj.put("fsvAmt", new Double(colFsv));
			mappingObj
					.put("availableCollateralAmt", availableColAmtMap.get("" + curApportionment.getPriorityRanking()));
			mappingObj.put("form.newApportionObject", curApportionment);
			result.put("fsv", "" + colFsv);
			result.put("limitDtlListReq", limitDtl);
			result.put("form.secApportionObject", mappingObj);
			result.put("indexID", map.get("indexID"));
			result.put("apportionIndexID", map.get("apportionIndexID"));
			result.put("from_event", from_event);

			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;

		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CommandProcessingException("Error when processing PrepareApportionmentCommand");
		}

	}

	private SBCollateralBusManager getCollateralBusManager() throws Exception {
		SBCollateralBusManagerHome home = (SBCollateralBusManagerHome) BeanController.getEJBHome(
				ICMSJNDIConstant.SB_COLLATERAL_MGR_JNDI, SBCollateralBusManagerHome.class.getName());
		SBCollateralBusManager manager = home.create();
		return manager;

	}

	private String getLimitIdDisp(List limitDtlList, long curValue) {
		for (int i = 0; i < limitDtlList.size(); i++) {
			OBSecApportionLmtDtl curDtl = (OBSecApportionLmtDtl) (limitDtlList.get(i));
			if (curDtl.getCmsLspApprLmtId() == curValue) {
				return String.valueOf(curDtl.getLimitId());
			}
		}
		return "";
	}

	private ISecApportionment getItem(List iSecApportionmentList, long itemRef) {
		if (iSecApportionmentList != null) {
			for (int i = 0; i < iSecApportionmentList.size(); i++) {
				ISecApportionment curApportionment = (ISecApportionment) (iSecApportionmentList.get(i));
				if (curApportionment.getRefID() == itemRef) {
					return curApportionment;
				}
			}
		}
		return null;
	}

	private ICollateralTrxValue getCol(ICollateralTrxValue[] temp, String securityID) {
		ICollateralTrxValue item = null;
		if (temp == null) {
			return item;
		}

		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getStagingCollateral().getSCISecurityID().equals(securityID)) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
}

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

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManager;
import com.integrosys.cms.app.collateral.bus.SBCollateralBusManagerHome;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.collateral.assetbased.RankList;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentUtil;
import com.integrosys.cms.ui.collateral.secapportion.SecApportionmentViewHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class PrepareApportionmentCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		// valid value of state will be: 1.init, 2. leId selected, 3.
		// subprofileid selected
		// 4. limitId selected
		return new String[][] { { "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "limitDtlList", "java.util.List", SERVICE_SCOPE },
				{ "apportionstate", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "leId", "java.lang.String", REQUEST_SCOPE },
				{ "subProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, { "limitId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "form.availableColAmt", "java.lang.Object", FORM_SCOPE },
				{ "limitDtlList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
				{ "limitDtlListReq", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "praAmtByCharge", "java.util.List", ICommonEventConstant.REQUEST_SCOPE },
				{ "fsv", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "rankID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "rankValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "leIdList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, };
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
			ICollateralTrxValue itrxValue = trxValList[Integer.parseInt(indexID)];
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
				// this case is no longer valid after the modification
				String leId = (String) (map.get("leId"));
				result.put("subprofileIdList", helper.getSubprofileIdDropDownValues(leId));
				result.put("limitIdValueList", new ArrayList());
				result.put("limitIdLabelList", new ArrayList());
			}
			else if (state.equals("3") || state.equals("4")) {
				// this case is no longer valid after the modification
				String leId = (String) (map.get("leId"));
				String subProfileId = (String) (map.get("subProfileId"));
				result.put("subprofileIdList", helper.getSubprofileIdDropDownValues(leId));
				List[] arr = helper.getLimitIdDropDownValueLabels(leId, subProfileId);
				result.put("limitIdValueList", arr[0]);
				result.put("limitIdLabelList", arr[1]);
			}

			double colFsv = 0;
			String currencyCode = CommonUtil.getBaseCurrency((ICommonUser) map.get(IGlobalConstant.USER));
			List secApportionmentList = null;
			String from_event = (String) map.get("from_page");
			if ("read".equals(from_event)) {
				secApportionmentList = itrxValue.getCollateral().getSecApportionment();
				Amount amt = itrxValue.getCollateral().getFSV();
				if (amt != null) {
					colFsv = amt.getAmount();
				}
				currencyCode = itrxValue.getCollateral().getFSVCcyCode();
			}
			else {
				secApportionmentList = itrxValue.getStagingCollateral().getSecApportionment();
				Amount amt = itrxValue.getStagingCollateral().getFSV();
				if (amt != null) {
					colFsv = amt.getAmount();
				}
				currencyCode = itrxValue.getStagingCollateral().getFSVCcyCode();
			}

			if (colFsv < 0) {
				colFsv = 0;
			}
			HashMap mappingObj = new HashMap();
			String availableCollateralAmt = (String) (map.get("availableCollateralAmt"));
			// System.out.println("availableCollateralAmt is: " +
			// availableCollateralAmt);
			// if (availableCollateralAmt == null ||
			// availableCollateralAmt.equals(""))
			// {
			// first time access the page, derive available collateral amount
			// from previously added apportionments
			// double availableColAmtDerived =
			// SecApportionmentUtil.getAvailableCollateralAmt(colFsv,
			// secApportionmentList);
			// mappingObj.put("availableCollateralAmt", new
			// Double(availableColAmtDerived));
			// mappingObj.put("fsvCurrency", currencyCode);
			// }
			mappingObj.put("fsvCurrency", currencyCode);
			result.put("fsv", "" + colFsv);
			result.put("limitDtlListReq", limitDtl);
			result.put("praAmtByCharge", SecApportionmentUtil.getAvailableAmtForCharge(colFsv, limitDtl,
					secApportionmentList));
			result.put("form.availableColAmt", mappingObj);
			result.put("indexID", map.get("indexID"));

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
}

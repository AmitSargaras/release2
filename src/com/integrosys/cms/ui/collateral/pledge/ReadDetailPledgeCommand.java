package com.integrosys.cms.ui.collateral.pledge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyImpl;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ReadDetailPledgeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, { "index", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "from_event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "pledgeDetail", "java.util.List", FORM_SCOPE },
				{ "limitMap", "java.util.Map", REQUEST_SCOPE }, { "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "stageColLimitMap", "com.integrosys.cms.app.collateral.bus.ICollateralLimitMap", REQUEST_SCOPE },
				{ "actualColLimitMap", "com.integrosys.cms.app.collateral.bus.ICollateralLimitMap", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		try {
			ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
			String index = (String) map.get("index");
			String event = (String) map.get("event");
			String from_event = (String) map.get("from_event");

			ICollateral iCol = null;
			if (CollateralAction.EVENT_PREPARE_ADD_PLEDGE.equals(event)
					|| CollateralAction.EVENT_EDIT_PLEDGE_PREPARE.equals(event)
					|| (EVENT_VIEW.equals(event) && !(ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus()) || ICMSConstant.STATE_PENDING_PERFECTION
							.equals(trxValue.getStatus())))) {
				iCol = trxValue.getStagingCollateral();
			}
			else if (EVENT_VIEW.equals(event)
					&& (ICMSConstant.STATE_ACTIVE.equals(trxValue.getStatus()) || ICMSConstant.STATE_PENDING_PERFECTION
							.equals(trxValue.getStatus()))) {
				iCol = trxValue.getCollateral();
			}
			ICollateralLimitMap collateralLimitMap = null;
			ILimit limit = null;
			String limitProfileId = null;
			String limitId = (String) map.get("limitId");
			DefaultLogger.debug(this, "\n\n\n=========================================");
			DefaultLogger.debug(this, "index = " + index);
			DefaultLogger.debug(this, "limitId = " + limitId);
			ICollateralLimitMap[] collateralLimitMaps = iCol.getCollateralLimits();

			if (from_event != null && from_event.equals("process")) {
				collateralLimitMap = getItem(collateralLimitMaps, index);
				result.put("stageColLimitMap", collateralLimitMap);
				ICollateralLimitMap actualColLmtMap = (trxValue.getCollateral() != null ? getItem(trxValue
						.getCollateral().getCollateralLimits(), index) : null);
				result.put("actualColLimitMap", actualColLmtMap);
				if (collateralLimitMap == null)
					collateralLimitMap = actualColLmtMap;
			}
			else if (limitId != null && collateralLimitMaps!=null && collateralLimitMaps.length > 0 ) {
				collateralLimitMap = getColLimitMapByLimitId( collateralLimitMaps,limitId);
			}

			if (collateralLimitMap != null) {
				limitId = String.valueOf(collateralLimitMap.getLimitID());
				limitProfileId = String.valueOf(collateralLimitMap.getCmsLimitProfileId());
			}

			Map limitMap = new HashMap();

			if (CollateralAction.EVENT_PREPARE_ADD_PLEDGE.equals(event)
					|| CollateralAction.EVENT_EDIT_PLEDGE_PREPARE.equals(event)) {
				ILimitProfile limitProfile = null;
				ILimit[] limits = null;
				if (map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ) != null) {
					limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
					limits = limitProfile.getLimits();
				}
				else {
					iCol = trxValue.getCollateral();
					List limitProfileIdsList = new ArrayList();
					ILimitProxy proxy = new LimitProxyImpl();
					limitProfileIdsList = proxy.getLimitProfileIdsByApprLmts(iCol.getCollateralID());
					limits = null;

					List limitsList = new ArrayList();
					for (int i = 0; i < limitProfileIdsList.size(); i++) {
						Long limitProfileIdTemp = (Long) limitProfileIdsList.get(i);
						limitProfile = proxy.getLimitProfile(limitProfileIdTemp.longValue());
						limitsList.addAll(Arrays.asList(limitProfile.getLimits()));
					}
					limits = (ILimit[]) limitsList.toArray(new ILimit[0]);
				}

				List limitCode = new ArrayList();
				List limitLabel = new ArrayList();
				// if the limitID(facilityNo) already used in
				// collateralLimitMaps, then the facilityNo is not shown at the
				// dropdown list, except the one that is being edited
				if (collateralLimitMaps != null) {
					for (int i = 0; i < limits.length; i++) {
						boolean isFound = false;
						for (int j = 0; j < collateralLimitMaps.length; j++) {
							if (CollateralAction.EVENT_EDIT_PLEDGE_PREPARE.equals(event)
									&& limitId.equals(String.valueOf(((ILimit) limits[i]).getLimitID()))
									&& limitId.equals(String.valueOf(collateralLimitMaps[j].getLimitID()))) {
								limitCode.add(String.valueOf(((ILimit) limits[i]).getLimitID()));
								limitLabel.add(String.valueOf(((ILimit) limits[i]).getLimitID()));
								isFound = true;
								break;
							}
							if (limits[i].getLimitID() == collateralLimitMaps[j].getLimitID()
									&& !ICMSConstant.HOST_STATUS_DELETE.equals(collateralLimitMaps[j].getSCIStatus())) {
								isFound = true;
								break;
							}
						}
						if (!isFound) {
							limitCode.add(String.valueOf(((ILimit) limits[i]).getLimitID()));
							limitLabel.add(String.valueOf(((ILimit) limits[i]).getLimitID()));
						}
					}
				}
				else {
					for (int i = 0; i < limits.length; i++) {
						limitCode.add(String.valueOf(((ILimit) limits[i]).getLimitID()));
						limitLabel.add(String.valueOf(((ILimit) limits[i]).getLimitID()));
					}
				}
				limitMap.put("limitCode", limitCode);
				limitMap.put("limitLabel", limitLabel);

				if (limits != null) {
					for (int i = 0; i < limits.length; i++) {
						ILimit temp = (ILimit) limits[i];
						if (limitId != null && limitId.equals(String.valueOf(temp.getLimitID()))) {
							limit = temp;
						}
					}
				}
			}
			else {
				ILimitProxy proxy = new LimitProxyImpl();
				if (limitId != null) {
					limit = proxy.getLimit(Long.parseLong(limitId));
				}
			}

			List returnList = new ArrayList();

			// do not change the sequence of the add
			// 1 -> collateral
			// 2 -> collateralLimitMap
			// 3 -> limit
			returnList.add(iCol);
			returnList.add(collateralLimitMap);
			returnList.add(limit);

			result.put("trxID", map.get("trxID"));
			result.put("pledgeDetail", returnList);
			result.put("limitMap", limitMap);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	private ICollateralLimitMap getColLimitMapByLimitId(ICollateralLimitMap[] colLmtMap,String limitId)
	{
		for (int i = 0; i < colLmtMap.length; i++) {
			ICollateralLimitMap collateralLimitMap = colLmtMap[i];
			if(limitId.equals(String.valueOf( collateralLimitMap.getLimitID()))){
				return collateralLimitMap;
			}
		}
		return null;
	}

	private ICollateralLimitMap getItem(ICollateralLimitMap temp[], String itemRef) {
		ICollateralLimitMap item = null;
		if (temp == null) {
			return item;
		}
		long refID = Long.parseLong(itemRef);
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getLimitID() == refID) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}
}

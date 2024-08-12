package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.support.CollateralLimitEntry;
import com.integrosys.cms.app.common.DomainObjectStatusMapper;
import com.integrosys.cms.app.common.StpTrxStatusReadyIndicator;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.support.FacilityCodeBasedCollateralUpdateMetaInfo;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ReadListCollateralMapCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "collateralLimitMap", "java.util.Map", REQUEST_SCOPE },
				{ "collateralIdStatusMap", "java.util.Map", REQUEST_SCOPE },
				{ "collateralLimitEntryStatusMap", "java.util.Map", REQUEST_SCOPE },
				{ "collateralIdEditableMap", "java.util.Map", REQUEST_SCOPE },
				{ "collateralIdDeletableMap", "java.lang.Map", REQUEST_SCOPE },
				{ "fromLimitProfileId", "java.lang.Long", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		String teamTypeMembershipID = (String) map.get(CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID);
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		result.put("fromLimitProfileId", new Long(limitProfileOB.getLimitProfileID()));

		ILimitProxy limitProxy = LimitProxyFactory.getProxy();

		/* key is collateral instance, value is list of limit */
		Map collateralLimitMap = limitProxy.getCollateralLimitMap(limitProfileOB);

		// prepare collateral limit map
		Map sortedCollateralLimitMap = new TreeMap(new Comparator() {

			public int compare(Object thisObj, Object thatObj) {
				ICollateral thisCol = (ICollateral) thisObj;
				ICollateral thatCol = (ICollateral) thatObj;

				long thisValue = thisCol.getCollateralID();
				long thatValue = thatCol.getCollateralID();

				return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
			}
		});
		sortedCollateralLimitMap.putAll(collateralLimitMap);

		// prepare collateral limit status map
		Map collateralLimitEntryStatusMap = populateCollateralLimitMapStatus(collateralLimitMap);

		// prepare collateral status map
		// prepare collateral deletable map
		Map collateralIdDeletableMap = new HashMap();
		List collateralIdList = new ArrayList();
		Set collaterals = collateralLimitMap.keySet();
		for (Iterator itr = collaterals.iterator(); itr.hasNext();) {
			ICollateral col = (ICollateral) itr.next();

			Long collateralId = new Long(col.getCollateralID());
			collateralIdList.add(collateralId);

			// for the limit security map
			ICollateralLimitMap[] collateralLimitMapsOfCollateral = col.getCollateralLimits();
			if (collateralLimitMapsOfCollateral != null && collateralLimitMapsOfCollateral.length > 0) {
				boolean foundActiveLinkage = false;
				for (int i = 0; i < collateralLimitMapsOfCollateral.length && !foundActiveLinkage; i++) {
					ICollateralLimitMap theMap = collateralLimitMapsOfCollateral[i];
					if (!ICMSConstant.HOST_STATUS_DELETE.equals(theMap.getSCIStatus())) {
						collateralIdDeletableMap.put(collateralId, Boolean.FALSE);
						foundActiveLinkage = true;
					}
				}
				if (!foundActiveLinkage) {
					collateralIdDeletableMap.put(collateralId, Boolean.TRUE);
				}
			}
			else {
				collateralIdDeletableMap.put(collateralId, Boolean.TRUE);
			}

			// for the charge details
			if (SecuritySubTypeUtil.canCollateralMaintainMultipleCharge(col)) {
				ILimitCharge[] chargeDetails = col.getLimitCharges();
				if (chargeDetails != null && chargeDetails.length > 0) {
					boolean foundActiveCharge = false;
					for (int i = 0; i < chargeDetails.length && !foundActiveCharge; i++) {
						ILimitCharge charge = chargeDetails[i];
						if (!ICMSConstant.HOST_STATUS_DELETE.equals(charge.getStatus())) {
							collateralIdDeletableMap.put(collateralId, Boolean.FALSE);
							foundActiveCharge = true;
						}
					}
				}
			}
		}

		DomainObjectStatusMapper statusMapper = (DomainObjectStatusMapper) BeanHouse.get("collateralTrxStatusMapper");
		Map collateralIdStatusMap = statusMapper.mapStatus((Long[]) collateralIdList.toArray(new Long[0]));

		// prepare collateral id editable map
		FacilityCodeBasedCollateralUpdateMetaInfo[] tradingFacilityCollateralUpdateMetaInfos = (FacilityCodeBasedCollateralUpdateMetaInfo[]) BeanHouse
				.get("tradingFacilityCollateralUpdateMetaInfo");
		Map collateralIdEditableMap = new HashMap();
		for (Iterator itr = sortedCollateralLimitMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();

			ICollateral col = (ICollateral) entry.getKey();
			List limitList = (List) entry.getValue();

			boolean matchCollateralTypeOrSubTypeAndSource = false;
			boolean pledgedToTradingFacility = false;
			for (int i = 0; i < tradingFacilityCollateralUpdateMetaInfos.length
					&& !matchCollateralTypeOrSubTypeAndSource && !pledgedToTradingFacility; i++) {
				FacilityCodeBasedCollateralUpdateMetaInfo metainfo = tradingFacilityCollateralUpdateMetaInfos[i];
				if (metainfo.getCollateralSourceId().equals(col.getSourceId())) {
					if (ArrayUtils.contains(metainfo.getCollateralSubTypes(), col.getCollateralSubType()
							.getSubTypeCode())
							&& metainfo.getCollateralSourceId().equals(col.getSourceId())) {
						matchCollateralTypeOrSubTypeAndSource = true;
					}
					else if (ArrayUtils.contains(metainfo.getCollateralTypes(), col.getCollateralType().getTypeCode())) {
						matchCollateralTypeOrSubTypeAndSource = true;
					}

					if (matchCollateralTypeOrSubTypeAndSource) {
						for (Iterator itrLmts = limitList.iterator(); itrLmts.hasNext() && !pledgedToTradingFacility;) {
							Object limitObject = itrLmts.next();
							if (limitObject instanceof ILimit) {
								ILimit limit = (ILimit) limitObject;
								String collateralLimitMapStatus = (String) collateralLimitEntryStatusMap
										.get(new CollateralLimitEntry(col.getCollateralID(), limit.getLimitID()));
								if (ICMSConstant.HOST_STATUS_DELETE.equals(collateralLimitMapStatus)) {
									continue;
								}

								if (ArrayUtils.contains(metainfo.getApplicableFacilityCodes(), limit.getFacilityDesc())) {
									pledgedToTradingFacility = true;
								}
							}
						}
					}
				}
			}

			StpTrxStatusReadyIndicator colStpTrxStatusReadyIndicator = (StpTrxStatusReadyIndicator) collateralIdStatusMap
					.get(new Long(col.getCollateralID()));

			boolean isSSCMakerAndActiveStatus = (String.valueOf(ICMSConstant.TEAM_TYPE_SSC_MAKER).equals(teamTypeMembershipID)
					||String.valueOf(ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH).equals(teamTypeMembershipID))
					&& ICMSConstant.STATE_ACTIVE.equals(colStpTrxStatusReadyIndicator.getOriginalTrxStatus());

			// if don't have sibs id, can do edit
			// if have sibs id, if not pledged to trading facility, can do edit
			// if have sibs id, if pledged to trading facility, cannot do edit
			if (col.getSCISecurityID() == null && isSSCMakerAndActiveStatus) {
				collateralIdEditableMap.put(new Long(col.getCollateralID()), Boolean.TRUE);
			}
			else if (col.getSCISecurityID() != null && isSSCMakerAndActiveStatus) {
				if (matchCollateralTypeOrSubTypeAndSource && pledgedToTradingFacility) {
					collateralIdEditableMap.put(new Long(col.getCollateralID()), Boolean.FALSE);
				}
				else {
					collateralIdEditableMap.put(new Long(col.getCollateralID()), Boolean.TRUE);
				}
			}
			else {
				collateralIdEditableMap.put(new Long(col.getCollateralID()), Boolean.FALSE);
			}
		}

		result.put("collateralIdEditableMap", collateralIdEditableMap);
		result.put("collateralIdDeletableMap", collateralIdDeletableMap);
		result.put("collateralIdStatusMap", collateralIdStatusMap);
		result.put("collateralLimitMap", sortedCollateralLimitMap);
		result.put("collateralLimitEntryStatusMap", collateralLimitEntryStatusMap);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	/**
	 * Return the status map of each collateral limit map, key is the
	 * <code>CollateralLimitEntry</code> instance, value is the status, either
	 * <code>I</code>,<code>U</code> or <code>D</code>.
	 * 
	 * @param collateralLimitMap key is collateral instance, value is list of
	 *        limit
	 * @return a map which key is the <code>CollateralLimitEntry</code>
	 *         instance, value is the status of the map
	 */
	private Map populateCollateralLimitMapStatus(Map collateralLimitMap) {
		Map collateralLimitEntryStatusMap = new HashMap();
		for (Iterator itr = collateralLimitMap.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			ICollateral col = (ICollateral) entry.getKey();
			List limitList = (List) entry.getValue();

			// iterate the limits
			if (limitList != null) {
				for (Iterator itrLimit = limitList.iterator(); itrLimit.hasNext();) {
					ILimit limit = (ILimit) itrLimit.next();
					CollateralLimitEntry collateralLimitKey = new CollateralLimitEntry(col.getCollateralID(), limit
							.getLimitID());

					// iterate the collateral limit map of the limit to retrieve
					// the linkage status
					for (int i = 0; i < limit.getCollateralAllocations().length; i++) {
						ICollateralAllocation limitColMap = limit.getCollateralAllocations()[i];
						if (limitColMap.getCollateral().getCollateralID() == col.getCollateralID()) {
							collateralLimitEntryStatusMap.put(collateralLimitKey, limitColMap.getHostStatus());
						}
					}
				}
			}
		}

		return collateralLimitEntryStatusMap;
	}
}

package com.integrosys.cms.ui.collateral.pledge;

import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

public class SavePledgeCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "pledgeDetail", "com.integrosys.cms.app.collateral.bus.ICollateralLimitMap", FORM_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		String event = (String) map.get("event");
		String subtype = (String) map.get("subtype");
		ICollateralLimitMap collateralLimitMap = (ICollateralLimitMap) map.get("pledgeDetail");
		ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
		ICollateral iCol = trxValue.getStagingCollateral();

		ICollateralLimitMap[] collateralLimitMaps = SecuritySubTypeUtil.retrieveNonDeletedCollateralLimitMap(iCol);
		int existingCollateralLimitMapSize = (collateralLimitMaps == null) ? 0 : collateralLimitMaps.length;

		if (CollateralAction.EVENT_UPDATE_PLEDGE.equals(event)) {
			if (collateralLimitMaps != null) {
				for (int i = 0; i < collateralLimitMaps.length; i++) {
					if (collateralLimitMap.getSCISysGenID() == collateralLimitMaps[i].getSCISysGenID()) {
						if (existingCollateralLimitMapSize > 1) {
							validateCollateralPledgeAmountUsageIndicator(iCol, collateralLimitMap, exceptionMap);
						}

						if (exceptionMap.isEmpty()) {
							collateralLimitMaps[i] = collateralLimitMap;
							if (ICMSConstant.HOST_STATUS_DELETE.equals(collateralLimitMaps[i].getSCIStatus())) {
								collateralLimitMaps[i].setSCIStatus(ICMSConstant.HOST_STATUS_UDPATE);
							}
							updateCollateralPledgeAmountUsageIndicator(iCol, collateralLimitMap);
						}

						break;
					}
				}
			}
		}
		else if (CollateralAction.EVENT_CREATE_PLEDGE.equals(event)) {
			if (existingCollateralLimitMapSize > 0) {
				validateCollateralPledgeAmountUsageIndicator(iCol, collateralLimitMap, exceptionMap);
			}

			if (existingCollateralLimitMapSize == 0) {
				updateCollateralPledgeAmountUsageIndicator(iCol, collateralLimitMap);
			}

			if (exceptionMap.isEmpty()) {
				// there might be the case that add back the previously
				// deleted one
				boolean foundInExisting = false;
				collateralLimitMaps = iCol.getCollateralLimits();
				if (collateralLimitMaps != null) {
					for (int i = 0; !foundInExisting && i < collateralLimitMaps.length; i++) {
						if (collateralLimitMap.getSCISysGenID() == collateralLimitMaps[i].getSCISysGenID()) {
							collateralLimitMaps[i] = collateralLimitMap;
							if (ICMSConstant.HOST_STATUS_DELETE.equals(collateralLimitMaps[i].getSCIStatus())) {
								collateralLimitMaps[i].setSCIStatus(ICMSConstant.HOST_STATUS_UDPATE);
							}
							foundInExisting = true;
						}
					}
				}

				if (!foundInExisting) {
					collateralLimitMaps = (ICollateralLimitMap[]) ArrayUtils.add(collateralLimitMaps,
							collateralLimitMap);
					iCol.setCollateralLimits(collateralLimitMaps);
				}
			}
		}
		result.put("serviceColObj", trxValue);
		result.put("subtype", subtype);

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private void validateCollateralPledgeAmountUsageIndicator(ICollateral collateral,
			ICollateralLimitMap collateralLimitMap, HashMap exceptionMap) {
		if (ICollateral.CHARGE_INFO_AMOUNT_USAGE.equals(collateral.getChargeInfoDrawAmountUsageIndicator())) {
			if (collateralLimitMap.getDrawAmount() == null) {
				exceptionMap.put("amountDraw", new ActionMessage("error.charge.info.usage.amount"));
			}
		}
		else if (ICollateral.CHARGE_INFO_PERCENTAGE_USAGE.equals(collateral.getChargeInfoDrawAmountUsageIndicator())) {
			if (collateralLimitMap.getDrawAmount() != null) {
				exceptionMap.put("amountDrawPercentage", new ActionMessage("error.charge.info.usage.percentage"));
			}
		}

		if (ICollateral.CHARGE_INFO_AMOUNT_USAGE.equals(collateral.getChargeInfoPledgeAmountUsageIndicator())) {
			if (collateralLimitMap.getPledgeAmount() == null) {
				exceptionMap.put("pledgeAmount", new ActionMessage("error.charge.info.usage.amount"));
			}
		}
		else if (ICollateral.CHARGE_INFO_PERCENTAGE_USAGE.equals(collateral.getChargeInfoPledgeAmountUsageIndicator())) {
			if (collateralLimitMap.getPledgeAmount() != null) {
				exceptionMap.put("pledgeAmountPercentage", new ActionMessage("error.charge.info.usage.percentage"));
			}
		}

	}

	private void updateCollateralPledgeAmountUsageIndicator(ICollateral collateral,
			ICollateralLimitMap collateralLimitMap) {
		Character chargeInfoDrawAmountUsageIndicator = (collateralLimitMap.getDrawAmount() != null) ? ICollateral.CHARGE_INFO_AMOUNT_USAGE
				: ICollateral.CHARGE_INFO_PERCENTAGE_USAGE;
		collateral.setChargeInfoDrawAmountUsageIndicator(chargeInfoDrawAmountUsageIndicator);

		Character chargeInfoPledgeAmountUsageIndicator = (collateralLimitMap.getPledgeAmount() != null) ? ICollateral.CHARGE_INFO_AMOUNT_USAGE
				: ICollateral.CHARGE_INFO_PERCENTAGE_USAGE;
		collateral.setChargeInfoPledgeAmountUsageIndicator(chargeInfoPledgeAmountUsageIndicator);
	}
}
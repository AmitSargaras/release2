package com.integrosys.cms.ui.collateral.pledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.ILimitChargeMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

public class DeletePledgeCommand extends AbstractCommand {

	private boolean removeChargeForDeletedPledge = false;

	public DeletePledgeCommand() {
	}

	/**
	 * Constructor to indicate whether to remove charge for deleted pledge
	 * (normally for those collateral that can't maintain multiple charge, ie,
	 * Cash, Guarantee, Documentation, Insurance, Marketable Securities, Asset
	 * Based - Receivable, PDC). Default value is <b>false</b>
	 * @param removeChargeForDeletedPledge whether to remove charge for deleted
	 *        pledge
	 */
	public DeletePledgeCommand(boolean removeChargeForDeletedPledge) {
		this.removeChargeForDeletedPledge = removeChargeForDeletedPledge;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "pledgeRemove", "java.util.List", FORM_SCOPE }, { "subtype", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "subtype", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		List pledgeRemove = (List) map.get("pledgeRemove");
		if (pledgeRemove != null) {
			ICollateralTrxValue trxValue = (ICollateralTrxValue) map.get("serviceColObj");
			ICollateral iCol = trxValue.getStagingCollateral();

			boolean requiredLimitChargeMapAutoTagDelete = false;
			if (!SecuritySubTypeUtil.canCollateralMaintainMultipleCharge(iCol)) {
				requiredLimitChargeMapAutoTagDelete = true;
			}

			ICollateralLimitMap[] collateralLimitMaps = iCol.getCollateralLimits();
			if (collateralLimitMaps != null) {
				List collateralLimitMapList = new ArrayList();

				for (int i = 0; i < collateralLimitMaps.length && exceptionMap.isEmpty(); i++) {
					boolean isFound = false;
					for (int j = 0; j < pledgeRemove.size(); j++) {
						int index = Integer.parseInt(String.valueOf(pledgeRemove.get(j)));
						if (i == index) {
							isFound = true;
							// auto tag to delete for limit charge map and
							// charge detail
							ILimitCharge[] chargeDetails = iCol.getLimitCharges();
							if (chargeDetails != null && chargeDetails.length > 0) {
								for (int k = 0; k < chargeDetails.length; k++) {
									ICollateralLimitMap[] limitChargeMaps = (ICollateralLimitMap[]) chargeDetails[k]
											.getLimitMaps();
									if (limitChargeMaps != null && limitChargeMaps.length > 0) {
										boolean foundLimitChargeMap = false;
										for (int l = 0; l < limitChargeMaps.length && !foundLimitChargeMap; l++) {
											ICollateralLimitMap chargeMap = limitChargeMaps[l];
											if (chargeMap.getChargeID() == collateralLimitMaps[i].getChargeID()) {
												foundLimitChargeMap = true;
												if (chargeMap instanceof ILimitChargeMap) {
													if (requiredLimitChargeMapAutoTagDelete) {
														if (this.removeChargeForDeletedPledge) {
															ILimitChargeMap limitChargeMap = (ILimitChargeMap) chargeMap;
															limitChargeMap.setStatus(ICMSConstant.STATE_DELETED);
														}
													}
													else {
														exceptionMap.put("collateralPledge", new ActionMessage(
																"error.collateral.remove.charge.first"));
													}
												}
												else {
													if (requiredLimitChargeMapAutoTagDelete) {
														if (this.removeChargeForDeletedPledge) {
															ICollateralLimitMap[] updatedMap = (ICollateralLimitMap[]) ArrayUtils
																	.remove(limitChargeMaps, l);
															chargeDetails[k].setLimitMaps(updatedMap);
														}
													}
													else {
														exceptionMap.put("collateralPledge", new ActionMessage(
																"error.collateral.remove.charge.first"));
													}
												}
											}
										}
									}
								}
							}
						}
					}

					if (!isFound) {
						collateralLimitMapList.add(collateralLimitMaps[i]);
					}
				}

				if (exceptionMap.isEmpty()) {
					iCol.setCollateralLimits((ICollateralLimitMap[]) collateralLimitMapList
							.toArray(new ICollateralLimitMap[0]));
					trxValue.setStagingCollateral(iCol);
				}
			}

			result.put("serviceColObj", trxValue);
		}

		result.put("subtype", map.get("subtype"));
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
package com.integrosys.cms.ui.liquidation;

import java.util.Iterator;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.liquidation.bus.IRecovery;
import com.integrosys.cms.app.liquidation.bus.IRecoveryIncome;
import com.integrosys.cms.ui.collateral.SecuritySubTypeUtil;

/**
 * Liquidation helper class
 * 
 * @author $Author: Siew Kheat$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationHelper {

	/**
	 * Get the collateral based on the collateralID, the retrieved collateral
	 * does not have all details
	 * @param collateralID
	 * @return
	 */
	public static String getCollateralViewPath(long collateralID, int flag) {

		String path = "ToDo.do?event=prepare&isMenu=Y";
		try {
			ICollateralProxy proxy = CollateralProxyFactory.getProxy();
			ICollateral iCol = proxy.getCollateral(collateralID, false);
			String subType = iCol.getCollateralSubType().getSubTypeCode();
			path = SecuritySubTypeUtil.getUrl(subType, flag);
			path = path + "&collateralID=" + collateralID;

			DefaultLogger.debug(LiquidationHelper.class.getName(), "The collateral view path Url path : " + path);

		}
		catch (Exception e) {
			DefaultLogger.warn(LiquidationHelper.class.getName(), "failed to retrieve collateral using collatera id ["
					+ collateralID + "], ignored", e);
		}
		return path;
	}

	/**
	 * To check if there is any difference in the recovery income collection of
	 * both actual and stage recovery
	 * @param actualRecovery
	 * @param stageRecovery
	 * @return
	 */
	public static boolean isRecoveryIncomeDifferent(IRecovery actualRecovery, IRecovery stageRecovery) {

		try {
			if (stageRecovery.getRecoveryIncome() == null) {
				return false;
			}

			if ((actualRecovery.getRecoveryIncome() == null) && (stageRecovery.getRecoveryIncome() != null)) {
				return true;
			}

			if ((actualRecovery.getRecoveryIncome() != null) && (stageRecovery.getRecoveryIncome() != null)
					&& (actualRecovery.getRecoveryIncome().size() != stageRecovery.getRecoveryIncome().size())) {
				return true;
			}

			IRecoveryIncome stage = null;
			IRecoveryIncome actual = null;
			boolean differenceFound = false;
			for (Iterator stageIterator = stageRecovery.getRecoveryIncome().iterator(); stageIterator.hasNext();) {
				stage = (IRecoveryIncome) stageIterator.next();

				for (Iterator actualIterator = actualRecovery.getRecoveryIncome().iterator(); actualIterator.hasNext();) {
					actual = (IRecoveryIncome) actualIterator.next();

					if (actual.getRecoveryIncomeID().longValue() == stage.getRefID()) {

						differenceFound = !(CompareOBUtil.compOB(stage, actual, "remarks"));
						if (differenceFound) {
							return true;
						}

						differenceFound = !(CompareOBUtil.compOB(stage, actual, "recoveryDate"));
						if (differenceFound) {
							return true;
						}

						if ((actual != null) && (stage != null) && !differenceFound) {
							if (actual.getTotalAmountRecovered() != null) {
								if ((actual.getTotalAmountRecovered().getAmountAsDouble() == stage
										.getTotalAmountRecovered().getAmountAsDouble())
										&& actual.getTotalAmountRecovered().getCurrencyCode().equals(
												stage.getTotalAmountRecovered().getCurrencyCode())) {
									differenceFound = false;
								}
								else {
									differenceFound = true;
								}
							}
							else {
								differenceFound = true;
							}
						}
						else {
							differenceFound = true;
						}

						if (differenceFound) {
							return true;
						}
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.warn(LiquidationHelper.class.getName(),
					"failed to compare recovery and income different, ignored", e);
		}

		return false;
	}
}

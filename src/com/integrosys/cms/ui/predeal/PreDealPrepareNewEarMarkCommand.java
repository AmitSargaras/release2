/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealPrepareNewEarMarkCommand
 *
 * Created on 11:30:40 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.predeal.bus.IEarMarkGroup;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 28, 2007 Time: 11:30:40 AM
 */
public class PreDealPrepareNewEarMarkCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.FEED_ID, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE, "java.lang.String", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.EVENT_ERROR_PAGE, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_LABELS, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_VALUES, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE, "java.lang.String", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String feedId = (String) hashMap.get(PreDealConstants.FEED_ID);
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		try {
			PreDealSearchRecord record = proxy.searchByFeedId(feedId);

			DefaultLogger.debug(this, "record.getStockExchangeCode () : '" + record.getStockExchangeCode() + "'");
			DefaultLogger.debug(this, "record.getBoardType () : '" + record.getBoardType() + "'");

			// Getting Bank Entity Group
			Collection c = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY_GROUP).getCommonCodeValues();
			String bankGroup = "";
			if (c.size() > 0) {
				bankGroup = (String) c.iterator().next();
			}

			IPolicyCapProxyManager proxy2 = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
			IPolicyCapGroup policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(
					record.getStockExchangeCode() != null ? record.getStockExchangeCode().trim() : "", bankGroup);
			IPolicyCap[] policyCapList = policyCapGroup.getPolicyCapArray();

			String broadType = record.getBoardType();

			boolean found = false;

			if (policyCapList != null) {
				for (int loop = 0; loop < policyCapList.length; loop++) {
					if ((broadType != null) && (policyCapList[loop].getBoard() != null)
							&& broadType.trim().equalsIgnoreCase(policyCapList[loop].getBoard().trim())) {
						found = true;

						record.setGroupQuotaCollCapNonFi(policyCapList[loop].getQuotaCollateralCapNonFI());
						record.setGroupMaxCollCapNonFi(policyCapList[loop].getMaxCollateralCapNonFI());
						record.setGroupQuotaCollCapFi(policyCapList[loop].getQuotaCollateralCapFI());
						record.setGroupMaxCollCapFi(policyCapList[loop].getMaxCollateralCapFI());

						if ((policyCapList[loop].getMaxCollateralCapFI() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (policyCapList[loop].getQuotaCollateralCapFI() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (policyCapList[loop].getMaxCollateralCapNonFI() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (policyCapList[loop].getQuotaCollateralCapNonFI() == ICMSConstant.DOUBLE_INVALID_VALUE)) {
							DefaultLogger.debug(this, "Policy data is all equal to ICMSConstant.DOUBLE_INVALID_VALUE.");

							result.put(PreDealConstants.EVENT_ERROR_PAGE, PreDealConstants.EVENT_ERROR_PAGE);
						}

						break;
					}
				}
			}

			DefaultLogger.debug(this, "Policy cap group record found ? : " + found);

			if (!found) {
				result.put(PreDealConstants.EVENT_ERROR_PAGE, PreDealConstants.EVENT_ERROR_PAGE);

				DefaultLogger.debug(this, "Max cap and Quota cap not set.");
			}

			String sourceSystem = (String) hashMap.get(PreDealConstants.PRE_DEAL_SOURCE);

			if (sourceSystem != null) {
				policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(record.getStockExchangeCode() != null ? record
						.getStockExchangeCode().trim() : "", sourceSystem);

				if (policyCapGroup != null) {
					policyCapList = policyCapGroup.getPolicyCapArray();

					broadType = record.getBoardType();

					found = false;

					if (policyCapList != null) {
						for (int loop = 0; loop < policyCapList.length; loop++) {
							if ((broadType != null) && (policyCapList[loop].getBoard() != null)
									&& broadType.trim().equalsIgnoreCase(policyCapList[loop].getBoard().trim())) {
								found = true;

								record.setQuotaCollCapNonFi(policyCapList[loop].getQuotaCollateralCapNonFI());
								record.setMaxCollCapNonFi(policyCapList[loop].getMaxCollateralCapNonFI());
								record.setQuotaCollCapFi(policyCapList[loop].getQuotaCollateralCapFI());
								record.setMaxCollCapFi(policyCapList[loop].getMaxCollateralCapFI());

								break;
							}
						}
					}

					DefaultLogger.debug(this, "Policy cap record found ? : " + found);
				}

				try {
					// Getting cmsActualHolding, earMarkHolding and
					// earMarkCurrent at high price
					IEarMarkGroup earMarkGroup = proxy.getEarMarkGroupBySourceAndFeedId(sourceSystem, record
							.getFeedId());

					if (earMarkGroup != null) {
						record.setCmsActualHolding(earMarkGroup.getCmsActualHolding());
						record.setEarmarkHolding(earMarkGroup.getEarMarkHolding());
						record.setEarmarkCurrent(earMarkGroup.getEarMarkCurrent());
						record.setTotalUnits(earMarkGroup.getTotalOfUnits());
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "Error search predeal item for feed id : " + feedId + " sourceSystem : "
							+ sourceSystem, e);
					record.setCmsActualHolding(0);
					record.setEarmarkHolding(0);
					record.setEarmarkCurrent(0);
					record.setTotalUnits(0);
				}

			}

			result.put(PreDealConstants.PRE_DEAL_RECORD, record);
			result.put(PreDealConstants.PRE_DEAL_SOURCE_LABELS, CommonCodeList.getInstance(
					PreDealConstants.PRE_DEAL_SOURCE).getCommonCodeLabels());
			result.put(PreDealConstants.PRE_DEAL_SOURCE_VALUES, CommonCodeList.getInstance(
					PreDealConstants.PRE_DEAL_SOURCE).getCommonCodeValues());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error search predeal item for feed id : " + feedId, e);

			result.put(PreDealConstants.PRE_DEAL_RECORD, new PreDealSearchRecord());
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}
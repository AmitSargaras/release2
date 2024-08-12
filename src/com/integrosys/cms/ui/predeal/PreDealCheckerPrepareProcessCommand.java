/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealCheckerPrepareProcessCommand
 *
 * Created on 2:12:35 PM
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
import com.integrosys.cms.app.predeal.bus.OBPreDeal;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.app.predeal.trx.OBPreDealTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 29, 2007 Time: 2:12:35 PM
 */
public class PreDealCheckerPrepareProcessCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.TRX_ID, "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.UPDATE_TYPE, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.TRX_ID, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_LABELS, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_VALUES, "java.lang.Object", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String trxId = (String) hashMap.get(PreDealConstants.TRX_ID);
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		try {
			OBPreDealTrxValue value = (OBPreDealTrxValue) proxy.getEarByTrxID(trxId);
			OBPreDeal ob = (OBPreDeal) value.getStagingPreDeal();
			PreDealSearchRecord record = proxy.searchByFeedId(String.valueOf(ob.getFeedId()));
			String updateType = ob.getEarMarkStatus();

			DefaultLogger.debug(this, "UpdateType : " + updateType);
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

			// Getting Group Quota and Max Cap
			if (policyCapGroup != null) {
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
								DefaultLogger.debug(this,
										"Policy data is all equal to ICMSConstant.DOUBLE_INVALID_VALUE.");

								result.put(PreDealConstants.EVENT_ERROR_PAGE, PreDealConstants.EVENT_ERROR_PAGE);
							}

							break;
						}
					}
					DefaultLogger.debug(this, "Policy cap record found ? : " + found);
				}
			}

			// Getting entity quota and max cap
			String sourceSystem = ob.getSourceSystem();

			if (sourceSystem != null) {
				policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(record.getStockExchangeCode() != null ? record
						.getStockExchangeCode().trim() : "", sourceSystem);

				if (policyCapGroup != null) {
					IPolicyCap[] policyCapList = policyCapGroup.getPolicyCapArray();

					String broadType = record.getBoardType();

					boolean found = false;

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

						// To sum up the entity/source earmark units with
						// current earmark units
						record.setEarmarkCurrent(earMarkGroup.getEarMarkCurrent() + ob.getEarMarkUnits());
						record.setTotalUnits(earMarkGroup.getTotalOfUnits());
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "Error search predeal item for source System : " + sourceSystem, e);
					record.setCmsActualHolding(0);
					record.setEarmarkHolding(0);
					record.setEarmarkCurrent(0);
					record.setTotalUnits(0);
				}
			}

			result.put(PreDealConstants.UPDATE_TYPE, updateType);
			result.put(PreDealConstants.OB_PRE_DEAL_TRX_VALUE, value);
			result.put(PreDealConstants.PRE_DEAL_RECORD, record);
			result.put(PreDealConstants.PRE_DEAL_FROM, ob);
			result.put(PreDealConstants.TRX_ID, trxId);
			result.put(PreDealConstants.PRE_DEAL_SOURCE_LABELS, CommonCodeList.getInstance(
					PreDealConstants.PRE_DEAL_SOURCE).getCommonCodeLabels());
			result.put(PreDealConstants.PRE_DEAL_SOURCE_VALUES, CommonCodeList.getInstance(
					PreDealConstants.PRE_DEAL_SOURCE).getCommonCodeValues());
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error retriving data for transaction id : " + trxId, e);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
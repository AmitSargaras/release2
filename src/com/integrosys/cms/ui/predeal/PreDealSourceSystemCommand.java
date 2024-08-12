/**
 * 
 */
package com.integrosys.cms.ui.predeal;

import java.util.Collection;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

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
 * Retrieve the source system related earmark maximum cap, actual holding,
 * earmarks held User: Siew Kheat Date: Sep 7, 2007 Time: 19:19 PM
 */
public class PreDealSourceSystemCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.TRX_ID, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.EVENT_ERROR_PAGE, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.TRX_ID, "java.lang.String", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_LABELS, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE_VALUES, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.PRE_DEAL_SOURCE, "java.lang.String", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		String trxId = (String) hashMap.get(PreDealConstants.TRX_ID);
		OBPreDeal ob = (OBPreDeal) hashMap.get(PreDealConstants.PRE_DEAL_FROM);
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		DefaultLogger.debug(this, "Trx ID : " + trxId);

		try {
			// Getting the cmsActualHolding, earmarkHolding, earmarkCurrent,
			// totalUnits
			String sourceSystem = ob.getSourceSystem();

			PreDealSearchRecord record = proxy.searchByFeedId(String.valueOf(ob.getFeedId()));

			// Getting Bank Entity Group
			Collection c = CommonCodeList.getInstance(CategoryCodeConstant.BANK_ENTITY_GROUP).getCommonCodeValues();
			String bankGroup = "";
			if (c.size() > 0) {
				bankGroup = (String) c.iterator().next();
			}

			// Getting bank group respective cap
			IPolicyCapProxyManager proxy2 = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
			IPolicyCapGroup policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(
					record.getStockExchangeCode() != null ? record.getStockExchangeCode().trim() : "", bankGroup);
			IPolicyCap[] policyCapList = policyCapGroup.getPolicyCapArray();

			String broadType = record.getBoardType();

			boolean found = false;
			double firstLimit = 0;
			double secondLimit = 0;

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

			// getting entity respective cap
			policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(record.getStockExchangeCode() != null ? record
					.getStockExchangeCode().trim() : "", sourceSystem);

			if (policyCapGroup != null) {
				policyCapList = policyCapGroup.getPolicyCapArray();

				broadType = record.getBoardType();

				found = false;
				firstLimit = 0;
				secondLimit = 0;

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

			// Getting cmsActualHolding, earMarkHolding and earMarkCurrent at
			// high price
			if ((sourceSystem != null) && (sourceSystem.length() > 0)) {

				try {
					IEarMarkGroup earMarkGroup = proxy.getEarMarkGroupBySourceAndFeedId(sourceSystem, record
							.getFeedId());

					if (earMarkGroup != null) {
						record.setCmsActualHolding(earMarkGroup.getCmsActualHolding());
						record.setEarmarkHolding(earMarkGroup.getEarMarkHolding());

						// Display current earmark = record earmark + on screen
						// earmark units
						record.setEarmarkCurrent(earMarkGroup.getEarMarkCurrent() + ob.getEarMarkUnits());
						record.setTotalUnits(earMarkGroup.getTotalOfUnits());
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "Error in getting Earmark Group for " + sourceSystem + " "
							+ record.getFeedId());
					e.printStackTrace();
					record.setCmsActualHolding(0);
					record.setEarmarkHolding(0);
					record.setEarmarkCurrent(0);
					record.setTotalUnits(0);
					exceptionMap.put("sourceSystem", new ActionMessage("error.predeal.sourceSystem.notfound"));
				}
			}

			if ((trxId != null) && !trxId.trim().equals("")) {
				DefaultLogger.debug(this, "From maker's Todo not Predeal");

				OBPreDealTrxValue value = (OBPreDealTrxValue) proxy.getEarByTrxID(trxId);
				OBPreDeal obtrx = (OBPreDeal) value.getStagingPreDeal();

				value.getStagingPreDeal().setAaNumber(ob.getAaNumber());
				value.getStagingPreDeal().setAccountNo(ob.getAccountNo());
				value.getStagingPreDeal().setBranchCode(ob.getBranchCode());
				value.getStagingPreDeal().setBranchName(ob.getBranchName());
				value.getStagingPreDeal().setCifNo(ob.getCifNo());
				value.getStagingPreDeal().setCustomerName(ob.getCustomerName());
				value.getStagingPreDeal().setEarMarkUnits(ob.getEarMarkUnits());
				value.getStagingPreDeal().setSecurityId(ob.getSecurityId());
				value.getStagingPreDeal().setSourceSystem(ob.getSourceSystem());
				value.getStagingPreDeal().setPurposeOfEarmarking(ob.getPurposeOfEarmarking());

				result.put(PreDealConstants.TRX_ID, trxId);
				result.put(PreDealConstants.OB_PRE_DEAL_TRX_VALUE, value);
				result.put(PreDealConstants.PRE_DEAL_FROM, obtrx);
			}

			result.put(PreDealConstants.PRE_DEAL_RECORD, record);
			result.put(PreDealConstants.PRE_DEAL_SOURCE_LABELS, CommonCodeList.getInstance(
					PreDealConstants.PRE_DEAL_SOURCE).getCommonCodeLabels());
			result.put(PreDealConstants.PRE_DEAL_SOURCE_VALUES, CommonCodeList.getInstance(
					PreDealConstants.PRE_DEAL_SOURCE).getCommonCodeValues());
			result.put(PreDealConstants.PRE_DEAL_SOURCE, sourceSystem);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error in source system predeal item for feed id : " + ob.getFeedId(), e);

			result.put(PreDealConstants.PRE_DEAL_RECORD, new PreDealSearchRecord());
			result.put(PreDealConstants.PRE_DEAL_FROM, ob);
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}

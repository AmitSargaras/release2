/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealPreUpdateRejectedCommand
 *
 * Created on 11:11:07 AM
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

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
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
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 5, 2007 Time: 11:11:07 AM
 */
public class PreDealPreUpdateRejectedCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.EVENT, "java.lang.String", REQUEST_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.NEXT_EVENT, "java.lang.String", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		OBPreDealTrxValue value = (OBPreDealTrxValue) hashMap.get(PreDealConstants.OB_PRE_DEAL_TRX_VALUE);
		OBTrxContext trxContext = (OBTrxContext) hashMap.get("theOBTrxContext");
		OBPreDeal ob = (OBPreDeal) hashMap.get(PreDealConstants.PRE_DEAL_FROM);
		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();

		try {
			DefaultLogger.debug(this, AccessorUtil.printMethodValue(ob));
			DefaultLogger.debug(this, AccessorUtil.printMethodValue(ob));

			PreDealSearchRecord record = proxy.searchByFeedId(String.valueOf(ob.getFeedId()));

			DefaultLogger.debug(this, "record.getStockExchangeCode () : '" + record.getStockExchangeCode() + "'");
			DefaultLogger.debug(this, "record.getBoardType () : '" + record.getBoardType() + "'");

			IPolicyCapProxyManager proxy2 = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();

			String broadType = record.getBoardType();

			boolean found = false;
			double firstLimit = 0;
			double secondLimit = 0;

			// Get the limit that belongs to the source system selected
			IPolicyCapGroup policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(
					record.getStockExchangeCode() != null ? record.getStockExchangeCode().trim() : "", ob
							.getSourceSystem());

			if (policyCapGroup != null) {
				IPolicyCap[] policyCapList = policyCapGroup.getPolicyCapArray();

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

							if (record.getIsFi()) {

								firstLimit = policyCapList[loop].getQuotaCollateralCapFI();
								secondLimit = policyCapList[loop].getMaxCollateralCapFI();
							}
							else {
								firstLimit = policyCapList[loop].getQuotaCollateralCapNonFI();
								secondLimit = policyCapList[loop].getMaxCollateralCapNonFI();
							}

							break;
						}
					}
				}

				DefaultLogger.debug(this, "Policy cap record found ? : " + found);
			}

			// Getting cmsActualHolding, earMarkHolding and earMarkCurrent at
			// high price
			IEarMarkGroup earMarkGroup = proxy.getEarMarkGroupBySourceAndFeedId(ob.getSourceSystem(), record
					.getFeedId());

			if (earMarkGroup != null) {
				record.setCmsActualHolding(earMarkGroup.getCmsActualHolding());
				record.setEarmarkHolding(earMarkGroup.getEarMarkHolding());
				record.setEarmarkCurrent(earMarkGroup.getEarMarkCurrent());
				record.setTotalUnits(earMarkGroup.getTotalOfUnits());
			}

			long totalUnits = record.getTotalUnits();
			long cmsActual = record.getCmsActualHolding();
			long earMarkolding = record.getEarmarkHolding();
			long earMarkCurrent = record.getEarmarkCurrent();
			long earMarkNew = ob.getEarMarkUnits();
			long listedShare = record.getListedShareQuantity();

			double newConcentration = (totalUnits + cmsActual + earMarkolding + earMarkCurrent + earMarkNew) * 100
					/ (double) listedShare;

			value.setTrxContext(trxContext);
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

			DefaultLogger.debug(this, "First limit : " + firstLimit);
			DefaultLogger.debug(this, "Second limit : " + secondLimit);
			DefaultLogger.debug(this, "New concentration : " + newConcentration);

			if ((newConcentration > firstLimit) && (newConcentration <= secondLimit)) {
				value.getStagingPreDeal().setWaiveApproveInd(true);

				result.put(PreDealConstants.LIMIT_LEVEL_BREACHED, PreDealConstants.QUOTA_CAP_BREACH);

				DefaultLogger.debug(this, "Quota cap breached !");
			}
			else if (newConcentration > secondLimit) {
				// todo , do something about it

				result.put(PreDealConstants.LIMIT_LEVEL_BREACHED, PreDealConstants.MAX_CAP_BREACH);

				DefaultLogger.debug(this, "Max cap breached !");
			}

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error updating rejected ear mark item ", e);
		}

		result.put(PreDealConstants.NEXT_EVENT, PreDealConstants.EVENT_MAKER_SUBMIT_UPDATE_REJECT); //
		result.put(PreDealConstants.OB_PRE_DEAL_TRX_VALUE, value);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}
}
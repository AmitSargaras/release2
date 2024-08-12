/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealPreSubmitNewEarMarkCommand
 *
 * Created on 3:53:28 PM
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

import java.util.Calendar;
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
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.predeal.PreDealException;
import com.integrosys.cms.app.predeal.bus.IEarMarkGroup;
import com.integrosys.cms.app.predeal.bus.OBPreDeal;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.app.predeal.trx.OBPreDealTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 4, 2007 Time: 3:53:28 PM
 */
public class PreDealPreSubmitNewEarMarkCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ PreDealConstants.PRE_DEAL_FROM, "java.lang.Object", FORM_SCOPE },
				{ PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.OB_PRE_DEAL_TRX_VALUE, "java.lang.Object", SERVICE_SCOPE },
				{ PreDealConstants.LIMIT_LEVEL_BREACHED, "java.lang.String", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap retValue = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();

		IPreDealProxy proxy = PreDealProxyManagerFactory.getIPreDealProxy();
		OBPreDeal ob = (OBPreDeal) hashMap.get(PreDealConstants.PRE_DEAL_FROM);
		OBPreDealTrxValue trxValue = new OBPreDealTrxValue(); // (
																// OBPreDealTrxValue
																// ) hashMap.get
																// (
																// PreDealConstants
																// .
																// OB_PRE_DEAL_TRX_VALUE
																// ) ;
		OBTrxContext trxContext = (OBTrxContext) hashMap.get("theOBTrxContext");

		try {
			PreDealSearchRecord record = proxy.searchByFeedId(String.valueOf(ob.getFeedId()));

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
			long earMarkHolding = record.getEarmarkHolding();
			long earMarkCurrent = record.getEarmarkCurrent();
			long earMarkNew = ob.getEarMarkUnits();
			long listedShare = record.getListedShareQuantity();

			double newConcentration = (totalUnits + cmsActual + earMarkHolding + earMarkCurrent + earMarkNew) * 100
					/ (double) listedShare;

			trxValue.setTrxContext(trxContext);
			ob.setEarMarkStatus(PreDealConstants.EARMARK_STATUS_EARMARKED);
			ob.setEarMarkingDate(Calendar.getInstance().getTime());
			ob.setHoldingInd(false);
			ob.setVersionTime(System.currentTimeMillis());
			ob.setInfoCorrectInd(false);
			ob.setStatus(true);
			ob.setWaiveApproveInd(false);

			DefaultLogger.debug(this, "record.getStockExchangeCode () : " + record.getStockExchangeCode());
			DefaultLogger.debug(this, "ob.getSourceSystem () : " + ob.getSourceSystem());

			IPolicyCapProxyManager proxy2 = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
			IPolicyCapGroup policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(
					record.getStockExchangeCode() != null ? record.getStockExchangeCode().trim() : "", ob
							.getSourceSystem());

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

						if (record.getIsFi()) {
							firstLimit = policyCapList[loop].getQuotaCollateralCapFI();
							secondLimit = policyCapList[loop].getMaxCollateralCapFI();

						}
						else {
							firstLimit = policyCapList[loop].getQuotaCollateralCapNonFI();
							secondLimit = policyCapList[loop].getMaxCollateralCapNonFI();
						}

						if ((policyCapList[loop].getMaxCollateralCapFI() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (policyCapList[loop].getQuotaCollateralCapFI() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (policyCapList[loop].getMaxCollateralCapNonFI() == ICMSConstant.DOUBLE_INVALID_VALUE)
								&& (policyCapList[loop].getQuotaCollateralCapNonFI() == ICMSConstant.DOUBLE_INVALID_VALUE)) {
							result.put(PreDealConstants.EVENT_ERROR_PAGE, PreDealConstants.EVENT_ERROR_PAGE);
						}

						break;
					}
				}
			}

			DefaultLogger.debug(this, "Policy cap record found ? : " + found);

			if (!found) {
				result.put(PreDealConstants.EVENT_ERROR_PAGE, PreDealConstants.EVENT_ERROR_PAGE);
			}

			// DefaultLogger.debug ( this , AccessorUtil.printMethodValue ( ob )
			// ) ;

			DefaultLogger.debug(this, "First limit : " + firstLimit);
			DefaultLogger.debug(this, "Second limit : " + secondLimit);
			DefaultLogger.debug(this, "New concentration : " + newConcentration);

			if ((newConcentration > firstLimit) && (newConcentration <= secondLimit)) {
				result.put(PreDealConstants.LIMIT_LEVEL_BREACHED, PreDealConstants.QUOTA_CAP_BREACH);

				DefaultLogger.debug(this, "Quota cap breached !");
			}
			else if (newConcentration > secondLimit) {
				ob.setWaiveApproveInd(true);

				result.put(PreDealConstants.LIMIT_LEVEL_BREACHED, PreDealConstants.MAX_CAP_BREACH);

				DefaultLogger.debug(this, "Max cap breached !");
			}

			trxValue.setStagingPreDeal(ob);
		}
		catch (PreDealException e) {
			e.printStackTrace();

			throw new CommandProcessingException(e.getMessage());
		}
		catch (PolicyCapException e) {
			DefaultLogger.debug(this, "Error in retrieving policy cap data for pre deal : " + e.getMessage());
		}

		result.put(PreDealConstants.OB_PRE_DEAL_TRX_VALUE, trxValue);

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

}
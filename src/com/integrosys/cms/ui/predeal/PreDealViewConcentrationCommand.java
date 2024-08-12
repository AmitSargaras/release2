/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealViewConcentrationCommand
 *
 * Created on 5:06:57 PM
 *
 * Purpose: PreDeal Concentration DTO
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
import java.util.Iterator;

import com.integrosys.base.businfra.currency.Amount;
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
import com.integrosys.cms.app.predeal.bus.PreDealConcentrationRecord;
import com.integrosys.cms.app.predeal.bus.PreDealSearchRecord;
import com.integrosys.cms.app.predeal.proxy.IPreDealProxy;
import com.integrosys.cms.app.predeal.proxy.PreDealProxyManagerFactory;
import com.integrosys.cms.ui.common.CommonCodeList;

/**
 * Prepare the necessary data for Concentration User: Siew Kheat Date: Sep 14,
 * 2007 Time: 4:09:47 PM
 */
public class PreDealViewConcentrationCommand extends AbstractCommand {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommand#getParameterDescriptor
	 * ()
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { PreDealConstants.FEED_ID, "java.lang.String", REQUEST_SCOPE } };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommand#getResultDescriptor()
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { PreDealConstants.PRE_DEAL_RECORD, "java.lang.Object", REQUEST_SCOPE },
				{ PreDealConstants.CONCENTRATION, "java.lang.Object", REQUEST_SCOPE } };

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.AbstractCommand#doExecute(java.util
	 * .HashMap)
	 */
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

			CommonCodeList ccList = CommonCodeList.getInstance(PreDealConstants.PRE_DEAL_SOURCE);
			Collection sourceValues = CommonCodeList.getInstance(PreDealConstants.PRE_DEAL_SOURCE)
					.getCommonCodeValues();

			IPolicyCapProxyManager proxy2 = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();

			Iterator sourceIterator = sourceValues.iterator();
			DefaultLogger.debug(this, "SourceIterator : " + sourceIterator);

			PreDealConcentrationRecord concentration = new PreDealConcentrationRecord();
			concentration.setCounterName(record.getName());

			// setting total market capitalization
			Amount total = new Amount(record.getUpdatePrice().getAmountAsBigDecimal(), record.getUpdatePrice()
					.getCurrencyCodeAsObject());
			total.setAmount(total.getAmount() * (double) record.getListedShareQuantity());
			concentration.setMarketCapitalization(total);

			// setting paid up capital
			Amount paidUp = new Amount(record.getParValue().getAmountAsBigDecimal(), record.getParValue()
					.getCurrencyCodeAsObject());
			paidUp.setAmount(paidUp.getAmount() * record.getListedShareQuantity());
			concentration.setTotalPaidUpCapital(paidUp);

			concentration.setUnitPrice(record.getUpdatePrice());

			// getting records for every sourcesystem
			while (sourceIterator.hasNext()) {

				PreDealSearchRecord temp = new PreDealSearchRecord();
				AccessorUtil.copyValue(record, temp);

				String sourceSystem = (String) sourceIterator.next();
				DefaultLogger.debug(this, "sourceSystem : " + sourceIterator);

				// Getting the Max Cap
				getMaxCap(proxy2, sourceSystem, temp);

				// Getting cmsActualHolding, earMarkHolding and earMarkCurrent
				// at high price
				try {
					IEarMarkGroup earMarkGroup = proxy.getEarMarkGroupBySourceAndFeedId(sourceSystem, record
							.getFeedId());

					if (earMarkGroup != null) {
						temp.setCmsActualHolding(earMarkGroup.getCmsActualHolding());
						temp.setEarmarkHolding(earMarkGroup.getEarMarkHolding());
						temp.setEarmarkCurrent(earMarkGroup.getEarMarkCurrent());
						temp.setTotalUnits(earMarkGroup.getTotalOfUnits());
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				concentration.getConcentrationMap().put(ccList.getCommonCodeLabel(sourceSystem), temp);
			}

			DefaultLogger.debug(this, "****************************************************************");
			DefaultLogger.debug(this, "Concentration : " + concentration);
			DefaultLogger.debug(this, "****************************************************************");

			result.put(PreDealConstants.CONCENTRATION, concentration);
			result.put(PreDealConstants.PRE_DEAL_RECORD, record);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error search predeal item for feed id : " + feedId, e);

			result.put(PreDealConstants.CONCENTRATION, new PreDealConcentrationRecord());
			result.put(PreDealConstants.PRE_DEAL_RECORD, new PreDealSearchRecord());
		}

		retValue.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		retValue.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return retValue;
	}

	/**
	 * Getting Quota & Maximum Cap for the respective sourceSystem and boardType
	 * @param proxy2 policyCap proxy manager
	 * @param sourceSystem source system of the record
	 * @param record existing search record
	 * @throws Exception
	 */
	private void getMaxCap(IPolicyCapProxyManager proxy2, String sourceSystem, PreDealSearchRecord record)
			throws Exception {

		IPolicyCapGroup policyCapGroup = proxy2.getPolicyCapGroupByExchangeBank(
				record.getStockExchangeCode() != null ? record.getStockExchangeCode().trim() : "", sourceSystem);

		if (policyCapGroup != null) {
			IPolicyCap[] policyCapList = policyCapGroup.getPolicyCapArray();

			String broadType = record.getBoardType();

			if (policyCapList != null) {
				for (int loop = 0; loop < policyCapList.length; loop++) {
					if ((broadType != null) && (policyCapList[loop].getBoard() != null)
							&& broadType.trim().equalsIgnoreCase(policyCapList[loop].getBoard().trim())) {
						record.setQuotaCollCapNonFi(policyCapList[loop].getQuotaCollateralCapNonFI());
						record.setMaxCollCapNonFi(policyCapList[loop].getMaxCollateralCapNonFI());
						record.setQuotaCollCapFi(policyCapList[loop].getQuotaCollateralCapFI());
						record.setMaxCollCapFi(policyCapList[loop].getMaxCollateralCapFI());

						break;
					}
				}
			}
		}
	}
}

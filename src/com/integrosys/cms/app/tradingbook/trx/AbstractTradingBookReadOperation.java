/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.IDealValuation;
import com.integrosys.cms.app.tradingbook.bus.IGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADeal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookDAO;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookDAOFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Abstract class that contain methods that is common among the set of trading
 * book trx read operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractTradingBookReadOperation extends CMSTrxOperation implements ITrxReadOperation {

	protected IISDACSADealVal[] updateISDACSADealDetail(IDealValuation[] dealValArray) throws TrxOperationException {
		try {
			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();

			ArrayList arrList = new ArrayList();

			for (int i = 0; i < dealValArray.length; i++) {

				IDealValuation val = (IDealValuation) dealValArray[i];

				IISDACSADeal deal = mgr.getISDACSADeal(val.getCMSDealID());
				OBISDACSADealVal dealVal = new OBISDACSADealVal(val);
				dealVal.setISDACSADealDetail(deal);
				arrList.add(dealVal);

			}
			return (IISDACSADealVal[]) arrList.toArray(new OBISDACSADealVal[0]);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught at updateISDACSADealDetail " + e.toString());
		}
	}

	protected IGMRADealVal[] updateGMRADealDetail(IDealValuation[] dealValArray) throws TrxOperationException {
		try {
			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();

			ArrayList arrList = new ArrayList();

			for (int i = 0; i < dealValArray.length; i++) {

				IDealValuation val = (IDealValuation) dealValArray[i];

				IGMRADeal deal = mgr.getGMRADeal(val.getCMSDealID());
				OBGMRADealVal dealVal = new OBGMRADealVal(val);
				dealVal.setGMRADealDetail(deal);
				arrList.add(dealVal);

			}

			return (IGMRADealVal[]) arrList.toArray(new OBGMRADealVal[0]);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught at updateGMRADealDetail " + e.toString());
		}
	}

	protected void updateCPAgreementDetail(ICPAgreementTrxValue dealTrx) throws TrxOperationException {
		try {
			String actualRef = dealTrx.getReferenceID();
			if (actualRef == null) {
				throw new TrxOperationException("agreementID is null!");
			}

			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			ICPAgreementDetail cpAgree = dao.getCounterPartyAgreementSummary(Long.parseLong(actualRef));

			dealTrx.setCPAgreementDetail(cpAgree);
			dealTrx.setAgreementID(Long.parseLong(actualRef));

		}
		catch (SearchDAOException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	protected void updateCPAgreementDetail(long agreementID, ICPAgreementTrxValue dealTrx) throws TrxOperationException {
		try {
			if (agreementID == ICMSConstant.LONG_INVALID_VALUE) {
				throw new TrxOperationException("agreementID is null!");
			}
			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			ICPAgreementDetail cpAgree = dao.getCounterPartyAgreementSummary(agreementID);

			dealTrx.setCPAgreementDetail(cpAgree);
			dealTrx.setAgreementID(agreementID);

		}
		catch (SearchDAOException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	protected IGMRADealVal[] getGMRADealValuationByAgreementID(long agreementID) throws TrxOperationException {
		try {
			ITradingBookDAO dao = TradingBookDAOFactory.getDAO();
			IDealValuation[] dealValList = dao.getDealValuationByAgreementID(agreementID);
			return updateGMRADealDetail(dealValList);

		}
		catch (SearchDAOException e) {
			throw new TrxOperationException("FinderException caught at getGMRADealValuationByAgreementID "
					+ e.toString());
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught at getGMRADealValuationByAgreementID " + e.toString());
		}
	}

	protected void updateTotalCashInterest(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			BigDecimal total = new BigDecimal(0);
			ICashMargin[] cashMargin = value.getCashMargin();
			if (cashMargin != null) {
				for (int i = 0; i < cashMargin.length; i++) {

					if (cashMargin[i].getCashInterest() != null) {
						BigDecimal cashInt = new BigDecimal(cashMargin[i].getCashInterest().doubleValue());
						cashInt = cashInt.setScale(TradingBookHelper.SCALE, TradingBookHelper.ROUNDING_MODE);

						if (cashMargin[i].getNAPSignAddInd()) {
							total = total.add(cashInt);
						}
						else {
							total = total.subtract(cashInt);
						}

					}
				}
				// total = total.setScale( TradingBookHelper.SCALE,
				// TradingBookHelper.ROUNDING_MODE );
				value.setTotalCashInterest(new Double(total.doubleValue()));
			}

		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

}

/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ReadCreditRiskParamByTrxIdOperation
 *
 * Created on 2:45:48 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.CreditRiskParamDAO;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.SBCreditRiskParamBusManager;
import com.integrosys.cms.app.creditriskparam.bus.SBCreditRiskParamBusManagerHome;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 22, 2007 Time: 2:45:48 PM
 */
public class ReadCreditRiskParamByTrxIdOperation extends AbstractCreditRiskParamOperation implements ITrxReadOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CREDIT_RISK_PARAM_READBY_TRXID;
	}

	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		CreditRiskParamDAO dao = new CreditRiskParamDAO();

		try {
			DefaultLogger.debug(this, "Transaction id : " + value.getTransactionID());

			// The ItrxValue must at least have trx ID , the rest the field can
			// be empty
			ICMSTrxValue cms = getTrxManager().getTransaction(value.getTransactionID());

			String refId = cms.getReferenceID(); // reference id is always the
													// feed_group_id in tables
													// cms_feed_group and
													// cms_price_feed

			DefaultLogger.debug(this, "Reference ID : \'" + refId + "\'");

			ICMSTrxValue trxValue = getTrxManager().getTrxByRefIDAndTrxType(refId,
					ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_SHARE_COUNTER);
			ICreditRiskParamGroup actualGroup = dao.getCreditRiskParams(Long.parseLong(refId));
			SBCreditRiskParamBusManager manager = getSBCreditRiskParamBusManagerStaging();
			ICreditRiskParamGroup stagingGroup = manager.getStagingCreditRiskParameters(Long.parseLong(refId));
			OBCreditRiskParamGroupTrxValue obGroup = new OBCreditRiskParamGroupTrxValue(trxValue);

			obGroup.setCreditRiskParamGroup(actualGroup);
			obGroup.setStagingCreditRiskParamGroup(stagingGroup);

			// DefaultLogger.debug ( this ,
			// " stagingGroup.getFeedEntries ()[0].getIsIntSuspend () : " +
			// stagingGroup.getFeedEntries ()[0].getIsIntSuspend () ) ;
			// DefaultLogger.debug ( this ,
			// " stagingGroup.getFeedEntries ()[0].getIsLiquid () : " +
			// stagingGroup.getFeedEntries ()[0].getIsLiquid () ) ;
			// DefaultLogger.debug ( this ,
			// " stagingGroup.getFeedEntries ()[0].getIntSuspend () : " +
			// stagingGroup.getFeedEntries ()[0].getIntSuspend () ) ;
			// DefaultLogger.debug ( this ,
			// " stagingGroup.getFeedEntries ()[0].getLiquid () : " +
			// stagingGroup.getFeedEntries ()[0].getLiquid () ) ;

			return obGroup;
		}
		catch (Exception e) {
			e.printStackTrace();

			throw new TransactionException(e);
		}
	}

	private SBCreditRiskParamBusManager getSBCreditRiskParamBusManagerStaging() {
		return (SBCreditRiskParamBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CREDIT_RISK_PARAM_BUS_STAGING_MANAGER_HOME, SBCreditRiskParamBusManagerHome.class
						.getName());
	}
}
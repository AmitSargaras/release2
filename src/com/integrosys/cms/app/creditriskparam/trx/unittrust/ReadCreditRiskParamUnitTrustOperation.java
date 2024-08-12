/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ReadCreditRiskParamUnitTrustOperation
 *
 * Created on 4:54:17 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx.unittrust;

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
import com.integrosys.cms.app.creditriskparam.trx.AbstractCreditRiskParamOperation;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA. User: Sai Heng Date: Feb 27, 2007 Time: 4:54:17 PM
 */
public class ReadCreditRiskParamUnitTrustOperation extends AbstractCreditRiskParamOperation implements
		ITrxReadOperation {
	public String getOperationName() {
		return ICMSConstant.ACTION_CREDIT_RISK_PARAM_READ;
	}

	public ITrxValue getTransaction(ITrxValue value) throws TransactionException {
		ICMSTrxValue cms = (ICMSTrxValue) value;
		CreditRiskParamDAO dao = new CreditRiskParamDAO();
		String refId = cms.getReferenceID(); // reference id is always the
												// feed_group_id in tables
												// cms_feed_group and
												// cms_price_feed

		try {
			DefaultLogger.debug(this, "Reference ID : \'" + refId + "\'");

			ICMSTrxValue trxValue = getTrxManager().getTrxByRefIDAndTrxType(refId,
					ICMSConstant.INSTANCE_CREDIT_RISK_PARAM_UNIT_TRUST);
			ICreditRiskParamGroup actualGroup = dao.getCreditRiskParams(Long.parseLong(refId));
			SBCreditRiskParamBusManager manager = getSBCreditRiskParamBusManagerStaging();
			ICreditRiskParamGroup stagingGroup = manager.getCreditRiskParameters(Long.parseLong(refId));
			OBCreditRiskParamGroupTrxValue obGroup = new OBCreditRiskParamGroupTrxValue(trxValue);

			obGroup.setCreditRiskParamGroup(actualGroup);
			obGroup.setStagingCreditRiskParamGroup(stagingGroup);

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

package com.integrosys.cms.host.eai.security.bus;

import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.core.AbstractPostProcessMessageHandler;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.ui.collateral.CollateralStpValidator;
import com.integrosys.cms.ui.collateral.CollateralStpValidatorFactory;

import java.util.Map;
import java.util.HashMap;

/**
 * Implementation of {@link AbstractPostProcessMessageHandler} to update the stp
 * ready status indicator of a single collateral
 * 
 * @author Chong Jun Yong
 * 
 */
public class StpReadyStatusPopulatePostProcessMsgHandler extends AbstractPostProcessMessageHandler {

	private ICollateralProxy collateralProxy;

	private CollateralStpValidatorFactory collateralStpValidatorFactory;

	private ICollateralDAO collateralJdbcDao;

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	public CollateralStpValidatorFactory getCollateralStpValidatorFactory() {
		return collateralStpValidatorFactory;
	}

	public ICollateralDAO getCollateralJdbcDao() {
		return collateralJdbcDao;
	}

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public void setCollateralStpValidatorFactory(CollateralStpValidatorFactory collateralStpValidatorFactory) {
		this.collateralStpValidatorFactory = collateralStpValidatorFactory;
	}

	public void setCollateralJdbcDao(ICollateralDAO collateralJdbcDao) {
		this.collateralJdbcDao = collateralJdbcDao;
	}

	protected void doPostProcessMessage(EAIMessage eaiMessage) {
		SecurityMessageBody securityMsgBody = (SecurityMessageBody) eaiMessage.getMsgBody();
		ApprovedSecurity security = securityMsgBody.getSecurityDetail();

		ICollateralTrxValue trxValue = null;
		long cmsSecurityId = security.getCMSSecurityId();
		try {
			trxValue = getCollateralProxy().getCollateralTrxValue(null, cmsSecurityId);
		}
		catch (CollateralException e) {
			throw new EAITransactionException("failed to retrieve collateral trx value for cms collateral id ["
					+ cmsSecurityId + "]", e);
		}

		ICollateral stgCollateral = trxValue.getStagingCollateral();
		CollateralStpValidator collateralStpValidator = getCollateralStpValidatorFactory().getCollateralStpValidator(
				stgCollateral);
        Map context = new HashMap();
        context.put(CollateralStpValidator.COL_OB, stgCollateral);
        context.put(CollateralStpValidator.COL_TRX_VALUE, trxValue);
        boolean isStpReady = collateralStpValidator.validate(context);

		getCollateralJdbcDao().updateOrInsertStpReadyStatus(trxValue.getTransactionID(), isStpReady);

	}
}

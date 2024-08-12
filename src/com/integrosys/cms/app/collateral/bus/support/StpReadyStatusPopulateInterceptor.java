package com.integrosys.cms.app.collateral.bus.support;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * To populate Stp ready indicator into a mapping table when transaction
 * operation is invoked for maker save/update operation.
 * 
 * @author Chong Jun Yong
 * @see ICollateralDAO#updateOrInsertStpReadyStatus(String, boolean)
 */
public class StpReadyStatusPopulateInterceptor implements MethodInterceptor {

	private ICollateralDAO collateralJdbcDao;

	public void setCollateralJdbcDao(ICollateralDAO collateralJdbcDao) {
		this.collateralJdbcDao = collateralJdbcDao;
	}

	public ICollateralDAO getCollateralJdbcDao() {
		return collateralJdbcDao;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();

		if (method.getName().equals("performProcess")) {
			ICollateralTrxValue paramTrxValue = (ICollateralTrxValue) arguments[0];
			ITrxContext context = paramTrxValue.getTrxContext();

			boolean isStpAllowed = context.getStpAllowed();

			ITrxResult result = (ITrxResult) invocation.proceed();

			ICollateralTrxValue trxValue = (ICollateralTrxValue) result.getTrxValue();
			getCollateralJdbcDao().updateOrInsertStpReadyStatus(trxValue.getTransactionID(), isStpAllowed);

			return result;
		}

		return invocation.proceed();

	}
}

package com.integrosys.cms.app.limit.bus.support;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.cms.app.limit.bus.IFacilityJdbc;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * To populate Stp ready indicator into a mapping table when transaction
 * operation is invoked for maker save/update operation.
 * 
 * @author Chong Jun Yong
 * @see IFacilityJdbc#updateOrInsertStpReadyStatus(String, boolean)
 */
public class StpReadyStatusPopulateInterceptor implements MethodInterceptor {

	private IFacilityJdbc facilityJdbcDao;

	public void setFacilityJdbcDao(IFacilityJdbc facilityJdbcDao) {
		this.facilityJdbcDao = facilityJdbcDao;
	}

	public IFacilityJdbc getFacilityJdbcDao() {
		return facilityJdbcDao;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] arguments = invocation.getArguments();

		if (method.getName().equals("performProcess")) {
			IFacilityTrxValue paramTrxValue = (IFacilityTrxValue) arguments[0];
			ITrxContext context = paramTrxValue.getTrxContext();

			boolean isStpAllowed = context.getStpAllowed();

			ITrxResult result = (ITrxResult) invocation.proceed();

			IFacilityTrxValue trxValue = (IFacilityTrxValue) result.getTrxValue();
			getFacilityJdbcDao().updateOrInsertStpReadyStatus(trxValue.getTransactionID(), isStpAllowed);

			return result;
		}

		return invocation.proceed();
	}

}

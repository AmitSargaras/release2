package com.integrosys.cms.host.stp.proxy.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.host.stp.proxy.IStpAsyncProxy;

/**
 * <p>
 * An invocation interceptor to be used by AOP alliance framework to intercepts
 * ITrxOperation call for interfacing with STP module.
 * 
 * <p>
 * Immediately after the invocation of
 * {@link ITrxOperation#performProcess(ITrxResult)}, it will submit task of
 * transaction value to STP queue.
 * 
 * @author Chong Jun Yong
 * @author Andy Wong
 * @see TrxOperationInvocationHandler
 * @since 24.09.2008
 */
public class TrxOperationMethodInterceptor implements MethodInterceptor {

	private IStpAsyncProxy stpProxy;

	public void setStpProxy(IStpAsyncProxy stpProxy) {
		this.stpProxy = stpProxy;
	}

	public IStpAsyncProxy getStpProxy() {
		return stpProxy;
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		Object target = methodInvocation.getThis();
		Object retVal = null;

		if (!(target instanceof ITrxOperation)) {
			throw new IllegalStateException("target [" + target + "] is not an instance of ITrxOperation.");
		}

		if (method.getName().equals("performProcess")) {
			try {
				retVal = methodInvocation.proceed();
			}
			catch (InvocationTargetException ex) {
				throw ex.getCause();
			}

			ITrxResult trxResult = (ITrxResult) retVal;
			ICMSTrxValue trxValue = (ICMSTrxValue) trxResult.getTrxValue();

            //Andy Wong, 11 March 2009: dont fire deleted collateral when Sibs creation never take place
            if (StringUtils.equals(trxValue.getTransactionType(), ICMSConstant.INSTANCE_COLLATERAL)) {
                ICollateral collateral = ((ICollateralTrxValue) trxValue).getCollateral();
                if (StringUtils.isBlank(collateral.getSCISecurityID())
                        && StringUtils.equals(collateral.getStatus(), ICMSConstant.STATE_DELETED)) {
                    return retVal;
                }
            }

			getStpProxy().submitTask(trxValue.getTransactionID(), Long.parseLong(trxValue.getReferenceID()),
					trxValue.getTransactionType());

		}
		else {
			try {
				retVal = methodInvocation.proceed();
			}
			catch (InvocationTargetException ex) {
				throw ex.getCause();
			}
		}

		return retVal;
	}
}

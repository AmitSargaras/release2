package com.integrosys.cms.host.stp.proxy.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.host.stp.proxy.IStpAsyncProxy;

/**
 * <p>
 * An invocation handler to be used by JDK dynamic proxy to intercept
 * ITrxOperation call for interfacing with STP module.
 * 
 * <p>
 * Immediately after the invocation of
 * {@link ITrxOperation#postProcess(ITrxResult)} , it will create a new Thread,
 * and submit task of transaction value to STP queue.
 * 
 * @author Chong Jun Yong
 * @since 24.09.2008
 * @see TrxOperationMethodInterceptor
 * @see java.lang.reflect.Proxy#newProxyInstance(ClassLoader, Class[],
 *      InvocationHandler)
 */
public class TrxOperationInvocationHandler implements InvocationHandler {

	private IStpAsyncProxy stpProxy;

	private ITrxOperation target;

	public void setTarget(ITrxOperation target) {
		this.target = target;
	}

	public ITrxOperation getTarget() {
		return target;
	}

	public void setStpProxy(IStpAsyncProxy stpProxy) {
		this.stpProxy = stpProxy;
	}

	public IStpAsyncProxy getStpProxy() {
		return stpProxy;
	}

	public TrxOperationInvocationHandler(ITrxOperation trxOperation) {
		this.target = trxOperation;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (method.getName().equals("equals")) {
			// Only consider equal when proxies are identical.
			return (proxy == args[0] ? Boolean.TRUE : Boolean.FALSE);
		}
		else if (method.getName().equals("hashCode")) {
			// Use hashCode of proxy.
			return new Integer(System.identityHashCode(proxy));
		}
		else if (method.getName().equals("postProcess")) {
			Object retVal = null;

			try {
				retVal = method.invoke(this.target, args);
			}
			catch (InvocationTargetException ex) {
				throw ex.getCause();
			}

			ITrxResult trxResult = (ITrxResult) retVal;
			final ICMSTrxValue trxValue = (ICMSTrxValue) trxResult.getTrxValue();
			ITrxContext context = trxValue.getTrxContext();

			if ((context != null) && (context.getStpAllowed())) {
				// run in separate thread to interface with stp
				Thread stpThread = new Thread("STP for [" + Thread.currentThread().getName() + "]") {
					public void run() {
						getStpProxy().submitTask(trxValue.getTransactionID(),
								Long.parseLong(trxValue.getReferenceID()), trxValue.getTransactionType());
					}
				};
				stpThread.start();
			}

			return retVal;
		}

		try {
			return method.invoke(this.target, args);
		}
		catch (InvocationTargetException ex) {
			throw ex.getCause();
		}
	}

}

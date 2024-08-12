package com.integrosys.cms.host.stp.proxy.interceptor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.proxy.IStpAsyncProxy;

/**
 * <p/>
 * A method interceptor to be used by AOP alliance framework to intercepts
 * TrxHandler call for interfacing with STP module.
 * <p/>
 * <p/>
 * Immediately after the invocation of
 * {@link com.integrosys.cms.host.eai.limit.trxhandler.FacilityTrxHandler#transact(java.util.Map trxValueMap, com.integrosys.cms.host.eai.Message msg)}
 * , it will submit task of transaction value to STP queue.
 * 
 * @author Andy Wong
 * @see com.integrosys.cms.host.stp.proxy.interceptor.TrxHandlerMethodInterceptor
 * @since 2 Jan 2009
 */
public class TrxHandlerMethodInterceptor implements MethodInterceptor {

	private IStpAsyncProxy stpProxy;

	/**
	 * to indicate whether this controller has the access to stp system, default
	 * is <code>false</code>
	 */
	private boolean accessStpSystem = false;

    /**
     * List of facility status to stp direct without user interference
     */
    private List facilityStatusDirectStpList;

	public void setStpProxy(IStpAsyncProxy stpProxy) {
		this.stpProxy = stpProxy;
	}

	public IStpAsyncProxy getStpProxy() {
		return stpProxy;
	}

	public boolean isAccessStpSystem() {
		return accessStpSystem;
	}

	public void setAccessStpSystem(boolean accessStpSystem) {
		this.accessStpSystem = accessStpSystem;
	}

    public List getFacilityStatusDirectStpList() {
        return facilityStatusDirectStpList;
    }

    public void setFacilityStatusDirectStpList(List facilityStatusDirectStpList) {
        this.facilityStatusDirectStpList = facilityStatusDirectStpList;
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		Object arguments[] = methodInvocation.getArguments();
		Object retVal = null;

		retVal = methodInvocation.proceed();

		if (method.getName().equals("transact")) {
			Message msg = (Message) arguments[0];
			Map retValMap = (Map) retVal;

			AAMessageBody aaMsgBody = (AAMessageBody) ((EAIMessage) msg).getMsgBody();
			Vector limitVector = aaMsgBody.getLimits();
			if (limitVector == null)
				limitVector = new Vector();
			for (Iterator itr = limitVector.iterator(); itr.hasNext();) {
				Limits limit = (Limits) itr.next();
				if (retValMap == null) {
					return retVal;
				}
				IFacilityTrxValue trxValue = (IFacilityTrxValue) retValMap.get(limit.getLimitGeneral().getLOSLimitId());

				// cater for 1st time publish and republish trx
				if (isAccessStpSystem()
						&& getFacilityStatusDirectStpList().contains(limit.getFacilityMaster().getFacilityStatusEntryCode())) {
					getStpProxy().submitTask(trxValue.getTransactionID(), Long.parseLong(trxValue.getReferenceID()),
							trxValue.getTransactionType());
				}
			}
		}

		return retVal;
	}
}
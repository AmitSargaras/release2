package com.integrosys.cms.host.eai.limit.interceptor;

import java.lang.reflect.Method;
import java.util.Properties;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IMessageHandler;
import com.integrosys.cms.host.eai.limit.response.InquiryMessageBody;
import com.integrosys.cms.host.eai.service.MessageSender;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 * <p>
 * Interceptor to intercept transaction operation for the system update from
 * host system. This will check whether all the facility and collateral of a
 * limit profile has been pumped to the host then update back to the origination
 * (LOS).
 * 
 * <p>
 * The check on whether all facility and collateral of a limit profile has been
 * pumped to the host is based on the stp master transaction status whether is
 * <code>COMPLETE</code>.
 * 
 * @author Chong Jun Yong
 * 
 */
public class TrxOperationMethodInterceptor implements MethodInterceptor {

	private Logger logger = LoggerFactory.getLogger(TrxOperationMethodInterceptor.class);

	private IMessageHandler facilityInfoResponseMessageHandler;

	private MessageSender originationMessageSender;

	private ILimitDAO limitJdbcDao;

	private ICommonUserProxy commonUserProxy;

	public IMessageHandler getFacilityInfoResponseMessageHandler() {
		return facilityInfoResponseMessageHandler;
	}

	public MessageSender getOriginationMessageSender() {
		return originationMessageSender;
	}

	public ILimitDAO getLimitJdbcDao() {
		return limitJdbcDao;
	}

	public ICommonUserProxy getCommonUserProxy() {
		return commonUserProxy;
	}

	public void setFacilityInfoResponseMessageHandler(IMessageHandler facilityInfoResponseMessageHandler) {
		this.facilityInfoResponseMessageHandler = facilityInfoResponseMessageHandler;
	}

	public void setOriginationMessageSender(MessageSender originationMessageSender) {
		this.originationMessageSender = originationMessageSender;
	}

	public void setLimitJdbcDao(ILimitDAO limitJdbcDao) {
		this.limitJdbcDao = limitJdbcDao;
	}

	public void setCommonUserProxy(ICommonUserProxy commonUserProxy) {
		this.commonUserProxy = commonUserProxy;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();

		if (method.getName().equals("performProcess")) {
			ITrxResult result = (ITrxResult) invocation.proceed();
			ICMSTrxValue cmsTrxValue = (ICMSTrxValue) result.getTrxValue();
            //Andy Wong: get user OB by login ID, due to some user without trx
			ICommonUser user = getCommonUserProxy().getUser(cmsTrxValue.getLoginId());

			long cmsLimitProfileId = cmsTrxValue.getLimitProfileID();
			ILimitProfile limitProfile = LimitProxyFactory.getProxy().getLimitProfile(cmsLimitProfileId);

			if (requiredResponse(limitProfile)) {
				InquiryMessageBody inquiry = new InquiryMessageBody();
				inquiry.setCmsLimitProfileId(limitProfile.getLimitProfileID());
				inquiry.setLosAANumber(limitProfile.getLosLimitProfileReference());
				inquiry.setStpDate(cmsTrxValue.getTransactionDate());
				inquiry.setUser(user);

				EAIMessage message = new EAIMessage();
				message.setMsgBody(inquiry);

				Properties msgContext = getFacilityInfoResponseMessageHandler().processMessage(message);
				EAIMessage response = (EAIMessage) msgContext.get(IEaiConstant.MSG_OBJ);

				getOriginationMessageSender().send(response);
			}

			return result;
		}

		return invocation.proceed();
	}

	/**
	 * To check whether all facility and collateral that belong to the limit
	 * profile has been sent to the host system
	 * 
	 * @param limitProfile a limit profile (AA) to be checked against
	 * @return whether all facility and collateral has been sent to the host
	 *         system.
	 */
	protected boolean requiredResponse(ILimitProfile limitProfile) {
		return getLimitJdbcDao().checkLimitProfileStpComplete(limitProfile);
	}
}

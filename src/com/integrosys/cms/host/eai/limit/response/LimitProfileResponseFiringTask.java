package com.integrosys.cms.host.eai.limit.response;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IMessageHandler;
import com.integrosys.cms.host.eai.service.MessageSender;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * A Limit Profile response firing task, responsible to fire response back to
 * source (normally LOS) to indicate that all collateral and facility of a Limit
 * Profile has been successfully fired into the host system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class LimitProfileResponseFiringTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(LimitProfileResponseFiringTask.class);

	private static final String RESPONSE_CODE_TAG_NAME = "ResponseCode";

	private static final String GOOD_RESPONSE_CODE = "0";

	private IMessageHandler facilityInfoResponseMessageHandler;

	private Map sourceIdOriginationMessageSenderMap;

	private ILimitDAO limitJdbcDao;

	private ICommonUserProxy commonUserProxy;

	private int singleLimitProfileFirePeriod = -1;

	private boolean isRunning = false;

	private final Object runningMonitor = new Object();

	public void setFacilityInfoResponseMessageHandler(IMessageHandler facilityInfoResponseMessageHandler) {
		this.facilityInfoResponseMessageHandler = facilityInfoResponseMessageHandler;
	}

	public void setSourceIdOriginationMessageSenderMap(Map sourceIdOriginationMessageSenderMap) {
		this.sourceIdOriginationMessageSenderMap = sourceIdOriginationMessageSenderMap;
	}

	public void setLimitJdbcDao(ILimitDAO limitJdbcDao) {
		this.limitJdbcDao = limitJdbcDao;
	}

	public void setCommonUserProxy(ICommonUserProxy commonUserProxy) {
		this.commonUserProxy = commonUserProxy;
	}

	/**
	 * <p>
	 * The time (in second unit) to sleep for the thread before fire another
	 * limit profile response.
	 * <p>
	 * Default is -1, meaning fire immediately.
	 * @param singleLimitProfileFirePeriod number in second
	 */
	public void setSingleLimitProfileFirePeriod(int singleLimitProfileFirePeriod) {
		this.singleLimitProfileFirePeriod = singleLimitProfileFirePeriod;
	}

	public void run() {
		if (this.isRunning) {
			return;
		}

		synchronized (runningMonitor) {
			this.isRunning = true;
		}

		List limitProfileList = this.limitJdbcDao.retrieveListOfCmsLimitProfileIdNoResponseToSource();

		for (Iterator itr = limitProfileList.iterator(); itr.hasNext();) {
			ILimitProfile limitProfile = (ILimitProfile) itr.next();

			if (this.limitJdbcDao.checkLimitProfileStpComplete(limitProfile)) {
				logger.info("Limit Profile has completed all stp process, for Limit Profile internal key ["
						+ limitProfile.getLimitProfileID() + "]");

				Long cmsLimitProfileId = new Long(limitProfile.getLimitProfileID());

				// -------------------------------------------------------------------
				// retrieve latest transaction value based on the limit profile
				// id,
				// for COL and FACILITY transaction only
				// -------------------------------------------------------------------

				ICMSTrxValue latestTrxValue = this.limitJdbcDao
						.retrieveLatestCollateralOrFacilityTrxValueByCmsLimitProfileId(cmsLimitProfileId.longValue());
				if (latestTrxValue == null) {
					logger.warn("No Collateral and Facility transaction value associated with the Limit Profile, "
							+ "internal key provided [" + cmsLimitProfileId + "], skip this.");
					continue;
				}

				ICommonUser user = null;
				try {
					user = this.commonUserProxy.getUser(latestTrxValue.getLoginId());
				}
				catch (EntityNotFoundException ex) {
					// ignored
				}
				catch (RemoteException ex) {
					// ignored
				}

				InquiryMessageBody inquiry = new InquiryMessageBody();
				inquiry.setCmsLimitProfileId(limitProfile.getLimitProfileID());
				inquiry.setLosAANumber(limitProfile.getLosLimitProfileReference());
				inquiry.setStpDate(latestTrxValue.getTransactionDate());
				inquiry.setUser(user);

				EAIMessage message = new EAIMessage();
				message.setMsgBody(inquiry);

				EAIMessage response = null;
				try {
					Properties msgContext = this.facilityInfoResponseMessageHandler.processMessage(message);
					response = (EAIMessage) msgContext.get(IEaiConstant.MSG_OBJ);
				}
				catch (EAIMessageException ex) {
					logger.error("Failed to prepare response for Limit Profile internal key [" + cmsLimitProfileId
							+ "], skipped " + ex);
					continue;
				}

				MessageSender msgSender = (MessageSender) this.sourceIdOriginationMessageSenderMap.get(limitProfile
						.getSourceID());
				if (msgSender == null) {
					logger.error("Limit Profile, internal key [" + cmsLimitProfileId + "] having unknown source id ["
							+ limitProfile.getSourceID() + "], please rectify");
					continue;
				}

				try {
					String acknowledge = (String) msgSender.sendAndReceive(response);
					if (acknowledge != null) {
						String responseCode = EAIHeaderHelper.getHeaderValue(acknowledge, RESPONSE_CODE_TAG_NAME);
						if (GOOD_RESPONSE_CODE.equals(responseCode)) {
							this.limitJdbcDao.updateLimitProfileResponseLogAfterFired(limitProfile);

							// sleep for a while after fire and get good
							// response
							if (this.singleLimitProfileFirePeriod > 0) {
								try {
									Thread.sleep(this.singleLimitProfileFirePeriod * 1000);
								}
								catch (InterruptedException ex) {
								}
							}
						}
						else {
							logger.error("Response retrieved from Origination for Limit Profile, LOS AA Number ["
									+ limitProfile.getLosLimitProfileReference() + "] is bad [" + acknowledge + "]");
						}
					}
				}
				catch (EAIMessageException ex) {
					logger.error("Failed to send response to host for Limit Profile internal key [" + cmsLimitProfileId
							+ "], skipped " + ex);
					continue;
				}
			}
		}

		synchronized (runningMonitor) {
			this.isRunning = false;
		}
	}
}
